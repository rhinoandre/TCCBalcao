package tcc.balcao.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.swing.UnsupportedLookAndFeelException;

import tcc.balcao.controller.action.ControllerAction;
import tcc.balcao.controller.threads.BuscarMesasSocketThread;
import tcc.balcao.controller.threads.SocketCozinhaThread;
import tcc.balcao.controller.threads.SocketMesaThread;
import tcc.balcao.interfaces.Acoes;
import tcc.balcao.interfaces.StatusMesas;
import tcc.balcao.interfaces.TiposPagamento;
import tcc.balcao.model.DAO.ContaDAO;
import tcc.balcao.model.DAO.ContapagamentoDAO;
import tcc.balcao.model.DAO.ItemDAO;
import tcc.balcao.model.DAO.MesasDAO;
import tcc.balcao.model.DAO.PedidosDAO;
import tcc.balcao.model.DAO.TipoItemDAO;
import tcc.balcao.model.entity.Conta;
import tcc.balcao.model.entity.Contapagamento;
import tcc.balcao.model.entity.IntervaloDataRelatorio;
import tcc.balcao.model.entity.Item;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Pedidos;
import tcc.balcao.view.TelaBalcaoPrincipal;
import tcc.balcao.view.TelaCadastroMesa;
import tcc.balcao.view.TelaCadastroProduto;
import tcc.balcao.view.TelaError;
import tcc.balcao.view.TelaMesaEspecificacoes;
import tcc.balcao.view.TelaRelatorio;
import tcc.balcao.view.TelaRelatorioFinanceiro;
import tcc.balcao.view.listeners.CadastroProdutoListener;
import tcc.balcao.view.listeners.MesaEspecificacoesListeners;
import tcc.balcao.view.listeners.StatusMesaListener;
import tcc.balcao.view.listeners.TelaBalcaoListener;
import tcc.balcao.view.listeners.TelaErrorAdapter;
import tcc.balcao.view.listeners.TelaRelatorioListeners;

@SuppressWarnings("unused")
public class BalcaoController {
	private TelaBalcaoPrincipal telaBalcao;
	private TelaCadastroProduto telaCadastroProduto;
	private TelaMesaEspecificacoes telaMesaEspecificacoes;
	private TelaRelatorio telaRelatorio;
	
	private PedidosDAO pedidosDAO;
	private MesasDAO mesasDAO;
	private ArrayList<Mesa> mesas;
	
	public BalcaoController() {
		
		pedidosDAO = new PedidosDAO();
		mesasDAO = new MesasDAO();
		
		iniciar();

		new Thread(new BuscarMesasSocketThread()).start();
		new Thread(new SocketMesaThread(new StatusMesaListener() {
			public void alterarStatusMesa(Mesa mesa) {
				telaBalcao.alterarStatusMesa(mesa);
			}
		})).start();
		new Thread(new SocketCozinhaThread()).start();
		
		try {

			MulticastSocket socket = new MulticastSocket(1231);
			socket.joinGroup(InetAddress.getByName("234.0.0.95"));

			while(true){
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				buf = new byte[0];
				DatagramPacket returnPacket = new DatagramPacket(buf,buf.length,packet.getAddress(), packet.getPort());
				socket.send(returnPacket);
			}
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void iniciar() {
		try {
			mesas = new MesasDAO().findAll();
			
			telaBalcao = new TelaBalcaoPrincipal(mesas);
			telaBalcao.addListener(new TelaBalcaoListener() {
				
				public void cadastroProdutoAction() {
					try {
						telaCadastroProduto = new TelaCadastroProduto(new TipoItemDAO().findAll());
						telaCadastroProduto.addLitener(new CadastroProdutoListener(){

							@Override
							public void cancelarButtonAction() {
								telaCadastroProduto.dispose();
							}

							@Override
							public void limparButtonAction() {
								
							}

							@Override
							public void salvarButtonAction(Item item) {
								new ItemDAO().insert(item);
								telaCadastroProduto.dispose();
							}
							
						});
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				public void mostrarEspecificacoesMesa(Mesa mesa) {
					final Conta conta = mesa.getConta();
					final Mesa mesa1 = mesa;
					try {
						ArrayList<Pedidos> pedidos = pedidosDAO.pedidosConta(mesa.getConta());
						final Double valor = ControllerAction.getInstance().getSomaValoresStatus(pedidos);
						HashMap<String, Object> valores = new HashMap<String, Object>();
						valores.put("Pedidos", pedidos);
						valores.put("Mesa", mesa);
						valores.put("ValorTotal", valor);
						valores.put("Porcentagem", valor * .1);
						valores.put("TotalPorcentagem", valor * 1.1);

						telaMesaEspecificacoes = new TelaMesaEspecificacoes(valores);
						telaMesaEspecificacoes.addListener(new MesaEspecificacoesListeners() {
							
							@Override
							public void fecharConta() {
								try {
									final TelaError telaErro = new TelaError(Acoes.PAGAMENTO, "Fechar Conta");
									telaErro.addListener(new TelaErrorAdapter() {
										
										@Override
										public void dinheiroAction() {
											Contapagamento cp = new Contapagamento();
											cp.setConta(conta);
											cp.setTipopagamento(TiposPagamento.DINHEIRO);
											cp.setValor(valor * 1.1);
											new ContapagamentoDAO().insert(cp);
											
											conta.setDtFech(new Date());
											new ContaDAO().update(conta);
											
											bloquearMesa(mesa1);
											telaErro.dispose();
											telaMesaEspecificacoes.dispose();
										}

										@Override
										public void cartaoAction() {
											Contapagamento cp = new Contapagamento();
											cp.setConta(mesa1.getConta());
											cp.setTipopagamento(TiposPagamento.CARTAO_CREDITO);
											cp.setValor(valor * 1.1);
											new ContapagamentoDAO().insert(cp);
											
											conta.setDtFech(new Date());
											new ContaDAO().update(conta);
											
											bloquearMesa(mesa1);
											telaErro.dispose();
											telaMesaEspecificacoes.dispose();
										}
									});
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								} catch (InstantiationException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (UnsupportedLookAndFeelException e) {
									e.printStackTrace();
								}
							}
						});
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void produtoRelatorio() {
					try {
						telaRelatorio = new TelaRelatorio();
						telaRelatorio.addListener(new TelaRelatorioListeners(){

							@Override
							public void okAction(int opcao) {
								// TODO FAZER
							}

							@Override
							public void okAction(IntervaloDataRelatorio idr) {
								// TODO FAZER
							}

						});
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void financeiroRelatorio() {
					try {
						telaRelatorio = new TelaRelatorio();
						telaRelatorio.addListener(new TelaRelatorioListeners(){

							@Override
							public void okAction(int opcao) {
								// TODO FAZER
							}

							@Override
							public void okAction(IntervaloDataRelatorio idr) {
								try {
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									ArrayList<Conta> contas = pedidosDAO.relatorioContaPeriodo(sdf.parse(idr.getDataInicio()), sdf.parse(idr.getDataFim()));
									for (Conta conta : contas) {
										System.out.println(conta.getIdconta());
									}
									Double valorT = pedidosDAO.relatorioValorPeriodo(sdf.parse(idr.getDataInicio()), sdf.parse(idr.getDataFim()));
									
									HashMap<String, Object> dados = new HashMap<String, Object>();
									dados.put("contas", contas);
									dados.put("valor", valorT);
									TelaRelatorioFinanceiro telaFinanceiro = new TelaRelatorioFinanceiro(dados);
									
									for (Conta conta : contas) {
										Set<Contapagamento> cps = conta.getContapagamentos();
										Double valor = 0.0;
										for (Contapagamento contapagamento : cps) {
											valor += contapagamento.getValor();
											System.out.println("valor");
										}
										telaFinanceiro.addItemLista(conta, valor);
									}
									telaFinanceiro.addItemLista(null, valorT);
									
								} catch (ParseException e) {
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								} catch (InstantiationException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (UnsupportedLookAndFeelException e) {
									e.printStackTrace();
								}
							}
						});
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void tempoDeEsperaRelatorio() {
					try {
						telaRelatorio = new TelaRelatorio();
						telaRelatorio.addListener(new TelaRelatorioListeners(){

							@Override
							public void okAction(int opcao) {
								// TODO FAZER
							}

							@Override
							public void okAction(IntervaloDataRelatorio idr) {
								// TODO FAZER
							}

						});
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void cadastroMesaAction() {
					try {
						new TelaCadastroMesa();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				}
			});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void bloquearMesa(Mesa mesa) {
		try {
			ModulosConectados.getInstance().getMesa(mesa).bloquearMesa();
			
			mesa.setStatusmesa(StatusMesas.LIVRE);
			mesa.setConta(null);
			mesasDAO.update(mesa);
			
			telaBalcao.alterarStatusMesa(mesa);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
