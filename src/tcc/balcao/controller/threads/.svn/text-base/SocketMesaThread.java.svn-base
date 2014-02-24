package tcc.balcao.controller.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tcc.balcao.view.listeners.StatusMesaListener;

public class SocketMesaThread implements Runnable {
	private ServerSocket ss;
	private StatusMesaListener listener;
	
	public SocketMesaThread(StatusMesaListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(1236);
			while(true){
				Socket socket = ss.accept();
				new Thread(new ReceptorMesas(socket, listener)).start();
			}
		} catch (IOException e) {
			System.out.println();
			e.printStackTrace();
		} 
	}

}
