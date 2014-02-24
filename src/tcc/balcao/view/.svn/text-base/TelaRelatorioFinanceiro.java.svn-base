package tcc.balcao.view;

import java.util.HashMap;

import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import tcc.balcao.model.entity.Conta;

public class TelaRelatorioFinanceiro extends TelaTabelaRelatorio {
	
	private static final long serialVersionUID = -4721825259543871318L;

	private HashMap<String, Object> dados;
	
	public TelaRelatorioFinanceiro(HashMap<String, Object> dados) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		super();
		
		this.dados = dados;
	}
	
	public DefaultTableModel getTableModel() {
		if (tableModel == null) {
			tableModel = new DefaultTableModel(null, new String[] {
					"Data", "Conta ID", "Valor"}) {

				private static final long serialVersionUID = -1475015549982259479L;

				public boolean isCellEditable(int row, int col) {
					return false;
				}

			};
		}
		return tableModel;
	}
	
	public void addItemLista(Conta conta, Double valor) {
		if(conta != null){
			
			String[] linha = new String[] {conta.getDtFech().toString(), String.valueOf(conta.getIdconta()), valor.toString()};// Passar os valores.
			getTableModel().addRow(linha);
		} else {
//			TODO: GUSTAVO, FAÇA AQUI ELE ADD O VALOR TOTAL
		}
	}
}
