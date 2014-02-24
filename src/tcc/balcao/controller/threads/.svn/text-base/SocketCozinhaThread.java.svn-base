package tcc.balcao.controller.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketCozinhaThread implements Runnable {
	private ServerSocket ss;
	
	public SocketCozinhaThread() {}
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(1238);
			while(true){
				Socket socket = ss.accept();
				new Thread(new ReceptorCozinha(socket)).start();
			}
		} catch (IOException e) {
			System.out.println();
			e.printStackTrace();
		} 
	}

}
