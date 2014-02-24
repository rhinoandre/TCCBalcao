package tcc.balcao.controller.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import tcc.balcao.controller.ModulosConectados;
import tcc.balcao.controller.action.ControllerAction;
import tcc.balcao.interfaces.Acoes;
import tcc.balcao.interfaces.StatusMesas;
import tcc.balcao.interfaces.StatusPedidos;
import tcc.balcao.model.TransferObject;
import tcc.balcao.model.DAO.ContaDAO;
import tcc.balcao.model.DAO.ItemDAO;
import tcc.balcao.model.DAO.MesasDAO;
import tcc.balcao.model.DAO.PedidosDAO;
import tcc.balcao.model.DAO.StatusPedidosDAO;
import tcc.balcao.model.entity.Conta;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Pedidos;
import tcc.balcao.model.entity.Statusmesa;
import tcc.balcao.model.entity.Statuspedido;
import tcc.balcao.view.listeners.StatusMesaListener;

public class ReceptorMesas implements Runnable {

	
	Socket socket;
	StatusMesaListener listener;
	HashMap<String, Statusmesa> statusMesas = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	TransferObject obj = null;
	PedidosDAO pedidosDAO;
	MesasDAO mesaDAO = null;
	ContaDAO contaDAO;
	Conta conta;
	Mesa mesa;
	Boolean parar = false;
	
	ArrayList<Pedidos> pedidos = new ArrayList<Pedidos>();
	
	public ReceptorMesas(Socket socket, StatusMesaListener listener) throws IOException {

		this.listener = listener;
		this.socket = socket;
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		pedidosDAO = new PedidosDAO();
		contaDAO = new ContaDAO();
		mesaDAO = new MesasDAO();
		
	}

	@Override
	public void run() {
		while (!parar) {
			try {
				Object object = ois.readObject();

				if (object instanceof TransferObject) {
					obj = (TransferObject) object;
					this.verificarOpcao(obj.getOpcao());
				} else {
					enviarErro("Objeto recebido: "+object.getClass()+". Objecto esperado: "+TransferObject.class);
				}
			} catch (IOException e) {
				erroConexao();
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void verificarOpcao(int opc) throws IOException {

		switch (opc) {
		case Acoes.INICIAR_CONEXAO:
			if (obj.getObj() instanceof Mesa) {
				mesa = (Mesa) obj.getObj();
				if (mesa.getStatusmesa().getStatus().equals(StatusMesas.OFFILINE.getStatus())) {
					mesa.setStatusmesa(StatusMesas.LIVRE);
				} 
				
				this.alteraStatusMesa(mesa);
				mesaDAO.update(mesa);
				
				if(ModulosConectados.getInstance().addNovaMesa(mesa, this)){
					enviarResposta(new TransferObject(Acoes.INICIAR_CONEXAO, mesa));
					if (mesa.getStatusmesa().getStatus().equals(StatusMesas.OCUPADA.getStatus())) {
						Double valor = ControllerAction.getInstance().getSomaValoresTotal(pedidosDAO.pedidosConta(mesa.getConta()));
						enviarResposta(new TransferObject(Acoes.TOTAL_CONTA, valor));
					}
				} else {
					enviarErro("Não consigo cadastrar a " + mesa.getMesa() + " pra receber pedidos");
				}
			} else {
				enviarErro("Objeto recebido: "+obj.getObj().getClass()+". Objecto esperado: "+Mesa.class);				
			}
			break;
			
		case Acoes.DESBLOQUEAR_MESA:
			conta  = new Conta();
			this.conta.setIdconta(ControllerAction.getInstance().criarIdPorData());
			this.conta.setDtAber(new Date());
			contaDAO.insert(conta);
			mesa.setStatusmesa(StatusMesas.OCUPADA);
			mesa.setConta(conta);
			mesaDAO.update(mesa);

			this.alteraStatusMesa(mesa);
			enviarResposta(new TransferObject(Acoes.DESBLOQUEAR_MESA, conta));

			break;

		case Acoes.BUSCAR_TIPO_ITENS:
			enviarResposta(new TransferObject(Acoes.BUSCAR_TIPO_ITENS, new ItemDAO().selectProdutosTipos()));
			break;

		case Acoes.NOVO_PEDIDO:
			if (obj.getObj() instanceof ArrayList<?>) {
				ArrayList<Pedidos> pedidos = (ArrayList<Pedidos>) obj.getObj();
				tratarNovoPedido(pedidos);
//				ENVIO O PEDIDO PARA A COZINHA
				if (ModulosConectados.getInstance().getCozinha() != null){
					ModulosConectados.getInstance().getCozinha().enviarResposta(new TransferObject(Acoes.NOVO_PEDIDO, pedidos));
				}
				Double valor = ControllerAction.getInstance().getSomaValoresTotal(pedidosDAO.pedidosConta(mesa.getConta()));
				enviarResposta(new TransferObject(Acoes.TOTAL_CONTA, valor));
				
				pedidos = null;
			} else {
				enviarErro("Objeto recebido: "+obj.getObj().getClass()+". Objecto esperado: ArrayList<Pedidos>");				
			}
			break;
			
		case Acoes.BUSCAR_PEDIDOS_CONTA:
			if (obj.getObj() instanceof Conta) {
				conta = (Conta) obj.getObj();
				ArrayList<Pedidos> pedidosMesa = pedidosDAO.pedidosConta(conta);
				TransferObject to = new TransferObject(Acoes.PEDIDOS_CONTA, pedidosMesa);
				enviarResposta(to);
			} else {
				enviarErro("Objeto recebido: "+obj.getObj().getClass()+". Objeto esperado: "+Conta.class);				
			}
			break;
		case Acoes.CANCELAR_PEDIDO:
			if (obj.getObj() instanceof Pedidos) {
				Pedidos pedido = (Pedidos) obj.getObj();
				System.out.println(pedido.getStatuspedido().getStatus());
				pedidosDAO.update(pedido);
				enviarResposta(new TransferObject(Acoes.CANCELAR_PEDIDO, pedido));
//					ENVIO O PEDIDO PARA A COZINHA
				if (ModulosConectados.getInstance().getCozinha() != null){
					ModulosConectados.getInstance().getCozinha().enviarResposta(new TransferObject(Acoes.CANCELAR_PEDIDO, pedido));
				}
			}
			break;
		case Acoes.FECHAR_CONTA:
			mesa.setStatusmesa(StatusMesas.AGUARDANDO);
			mesaDAO.update(mesa);
			
			alteraStatusMesa(mesa);
			
			break;
		case Acoes.FECHAR_CONEXAO:
				mesa.setConta(null);
				mesa.setStatusmesa(StatusMesas.OFFILINE);
				mesaDAO.update(mesa);
				
				ModulosConectados.getInstance().removeMesa(mesa);
				alteraStatusMesa(mesa);
				
				enviarResposta(new Boolean(true));
//				this.socket.close();
				parar = true;
			break;
		}
	}

	private void tratarNovoPedido(ArrayList<Pedidos> pedidos) {
		for (Pedidos pedido : pedidos) {
			pedido.setDtAber(new Date());
			pedido.setStatuspedido(new StatusPedidosDAO().findByPK(1));
			pedidosDAO.insert(pedido);
		}
//		TODO: Notificar o controller da cozinha/bar que existe novos pedidos
	}
	
	@SuppressWarnings("unused")
	private Object receberRequisicao() throws IOException, ClassNotFoundException{
		return ois.readObject();
	}
	
	private void enviarResposta(Object obj) throws IOException{
		oos.writeObject(obj);
	}
	
	private void erroConexao(){
		if (mesa.getConta() != null) {
			mesa.setStatusmesa(StatusMesas.ERRO);
		} else {
			mesa.setStatusmesa(StatusMesas.OFFILINE);
		}
		ModulosConectados.getInstance().removeMesa(mesa);
		mesaDAO.update(mesa);
		this.alteraStatusMesa(mesa);
		
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parar = true;
	}
	
	private void enviarErro(String msg){
		try {
			oos.writeObject(new TransferObject(Acoes.ERRO, msg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void alteraStatusMesa(Mesa mesa){
		this.listener.alterarStatusMesa(mesa);
	}
	
	public void bloquearMesa() throws IOException{
		mesa.setStatusmesa(StatusMesas.LIVRE);
		mesaDAO.update(mesa);
		enviarResposta(new TransferObject(Acoes.BLOQUEAR_TELA, mesa));
	}
}
