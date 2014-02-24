package tcc.balcao.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tcc.balcao.interfaces.StatusPedidos;
import tcc.balcao.model.entity.Pedidos;


public class ControllerAction {
	private static ControllerAction instance = new ControllerAction();
	
	private ControllerAction() {}

	public static ControllerAction getInstance() {
		return instance;
	}
	
	public long criarIdPorData() {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSS");
		return Long.parseLong(format.format(new Date()).toString());
	}
	
	public byte[] carregarImagem(String link) throws IOException{
		File file = new File(link); 
		FileInputStream fis = new FileInputStream(file);
		byte[] bytes = new byte[(int) file.length()];
		
		fis.read(bytes);

		return bytes;
	}
	
	public Double getSomaValoresStatus(ArrayList<Pedidos> pedidos){
		Double valor = 0.0;
		
		for (Pedidos pedido : pedidos) {
			if(!pedido.getStatuspedido().getStatus().equals(StatusPedidos.ABERTO.getStatus()) && 
			   !pedido.getStatuspedido().getStatus().equals(StatusPedidos.CANCELADO.getStatus())){
				valor += (pedido.getItem().getValor() * pedido.getQuantidade());
			}
		}
		
		return valor;
	}
	
	public Double getSomaValoresTotal(ArrayList<Pedidos> pedidos){
		Double valor = 0.0;
		
		for (Pedidos pedido : pedidos) {
			valor += (pedido.getItem().getValor() * pedido.getQuantidade());
		}
		
		return valor;
	}
}
