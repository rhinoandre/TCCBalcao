package tcc.balcao.model;

import java.io.Serializable;

public class TransferObject implements Serializable{
	private static final long serialVersionUID = 4030206856920071262L;
	
	private int opcao = 0;
	private Object obj = null;

	public TransferObject() {}
	
	public TransferObject(int opcao, Object obj) {
		this.opcao = opcao;
		this.obj = obj;
	}
	
	public int getOpcao() {
		return opcao;
	}
	public void setOpcao(int opcao) {
		this.opcao = opcao;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
