package tcc.balcao.controller.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import tcc.balcao.controller.ModulosConectados;
import tcc.balcao.interfaces.Acoes;
import tcc.balcao.interfaces.StatusPedidos;
import tcc.balcao.model.TransferObject;
import tcc.balcao.model.DAO.PedidosDAO;
import tcc.balcao.model.DAO.StatusPedidosDAO;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Pedidos;
import tcc.balcao.model.entity.Statuspedido;

public class ReceptorCozinha implements Runnable {

	Socket socket;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	TransferObject obj = null;
	PedidosDAO pedidosDAO;
	Boolean parar = false;
	
	ArrayList<Pedidos> pedidos = new ArrayList<Pedidos>();
	
	public ReceptorCozinha(Socket socket) throws IOException {

		this.socket = socket;
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		pedidosDAO = new PedidosDAO();

	}

	@Override
	public void run() {
		while (!parar) {
				Object object;
				try {
					object = ois.readObject();

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@SuppressWarnings("unchecked")
	private void verificarOpcao(int opc) throws IOException{

		switch (opc) {
		case Acoes.INICIAR_CONEXAO_COZINHA:
			if (obj.getObj() instanceof String) {
				if(ModulosConectados.getInstance().setCozinha((String) obj.getObj(), this)){
					
					ArrayList<Pedidos> pedidos = new ArrayList<Pedidos>();
					
					pedidos.addAll(pedidosDAO.pedidosPorStatus(StatusPedidos.ABERTO));
					pedidos.addAll(pedidosDAO.pedidosPorStatus(StatusPedidos.EM_EXECUCAO));
					
					enviarResposta(new TransferObject(Acoes.INICIAR_CONEXAO_COZINHA, pedidos));
					
				} else {
					enviarErro("Não foi possível cadastrar a cozinha");
				}
			} else {
				enviarErro("Objeto recebido: "+obj.getObj().getClass()+". Objecto esperado: "+Mesa.class);				
			}
			
			break;
			
		case Acoes.BUSCAR_PEDIDOS:
			if (obj.getObj() instanceof Date) {
				Date data = (Date) obj.getObj();
				
				GregorianCalendar calendar = new GregorianCalendar(data.getYear(),data.getMonth(), data.getDay());

				calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH), calendar.get(GregorianCalendar.DATE), 6, 0);
				Date dateInic = calendar.getTime();

				calendar = new GregorianCalendar();
				calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH), calendar.get(GregorianCalendar.DATE), 23, 59);
				Date dateFim = calendar.getTime();

				ArrayList<Pedidos> pedidos = new PedidosDAO().pedidosPorPeriodo(dateInic, dateFim);
				for (Pedidos pedidos2 : pedidos) {
					System.out.println(pedidos2.getItem().getNome());
				}
				enviarResposta(new TransferObject(Acoes.BUSCAR_PEDIDOS, pedidos));
			}
			break;
			
		case Acoes.NOVO_PEDIDO:
			if (obj.getObj() instanceof ArrayList<?>) {
				ArrayList<Pedidos> pedidos = (ArrayList<Pedidos>) obj.getObj();
				enviarResposta(new TransferObject(Acoes.NOVO_PEDIDO, pedidos));
			} else {
				enviarErro("Objeto recebido: "+obj.getObj().getClass()+". Objecto esperado: ArrayList<Pedidos>");				
			}
			break;
		case Acoes.ALTERAR_STATUS:
			if (obj.getObj() instanceof Pedidos) {
				Pedidos pedido = (Pedidos) obj.getObj();
				pedidosDAO.update(pedido);
			}
			break;
			
		case Acoes.FECHAR_CONEXAO:
				ModulosConectados.getInstance().removeCozinha();
				enviarResposta(Boolean.TRUE);
				this.socket.close();
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
	
	public void enviarResposta(Object obj) throws IOException{
		oos.writeObject(obj);
	}
	
	private void erroConexao(){
		ModulosConectados.getInstance().removeCozinha();
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
}
