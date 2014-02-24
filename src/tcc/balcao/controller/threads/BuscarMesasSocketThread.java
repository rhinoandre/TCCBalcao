package tcc.balcao.controller.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import tcc.balcao.interfaces.StatusMesas;
import tcc.balcao.model.TransferObject;
import tcc.balcao.model.DAO.MesasDAO;
import tcc.balcao.model.entity.Mesa;

public class BuscarMesasSocketThread implements Runnable {
	private ServerSocket buscaMesasSocket;
	private MesasDAO mesasDAO = new MesasDAO();
	@Override
	public void run() {
		try {
			buscaMesasSocket = new ServerSocket(1234);
			ObjectOutputStream oosMesas;
			ObjectInputStream oisMesas;
			while (true) {
				Socket socket = buscaMesasSocket.accept();

				oosMesas = new ObjectOutputStream(socket.getOutputStream());
				oisMesas = new ObjectInputStream(socket.getInputStream());
				
				Object object = oisMesas.readObject();

				if (object instanceof TransferObject) {
					TransferObject to = (TransferObject) object;

					ArrayList<Mesa> mesasReturn = new ArrayList<Mesa>();

					mesasReturn.addAll(mesasDAO.getMesasByStatus(StatusMesas.OFFILINE));
					mesasReturn.addAll(mesasDAO.getMesasByStatus(StatusMesas.ERRO));
					
					to.setObj(mesasReturn);
					
					oosMesas.writeObject(to);
				}
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
