package tcc.balcao.controller;

import java.util.HashMap;

import javax.swing.JOptionPane;

import tcc.balcao.controller.threads.ReceptorCozinha;
import tcc.balcao.controller.threads.ReceptorMesas;
import tcc.balcao.model.entity.Mesa;

public class ModulosConectados {
	private static final ModulosConectados instance = new ModulosConectados();
	private HashMap<String, ReceptorMesas> mesas = new HashMap<String, ReceptorMesas>();
	private ReceptorCozinha cozinha = null;
	
	private ModulosConectados() {}
	public static ModulosConectados getInstance() {
		return instance;
	}
	
	public synchronized boolean addNovaMesa(Mesa mesa, ReceptorMesas receptor) {
		if(!mesas.containsKey(mesa.getMesa())){
			mesas.put(mesa.getMesa(), receptor);
			return true;
		}
		return false;
	}
	
	public synchronized boolean removeMesa(Mesa mesa) {
		if(mesas.containsKey(mesa.getMesa())){
			mesas.remove(mesa.getMesa());
			return true;
		}
		return false;
	}
	
	public synchronized ReceptorMesas getMesa(Mesa mesa){
		if(mesas.containsKey(mesa.getMesa())){
			return mesas.get(mesa.getMesa());
		}
		return null;
	}
	
	public synchronized Boolean setCozinha(String cozinha, ReceptorCozinha receptor){
		if (this.cozinha == null) {
			this.cozinha = receptor;
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public ReceptorCozinha getCozinha(){
		return cozinha;
	}
	
	public Boolean removeCozinha(){
		if (cozinha != null) {
			cozinha = null;
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
