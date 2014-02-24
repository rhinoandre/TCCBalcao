package tcc.balcao.view;

import java.awt.Dimension;
import java.awt.dnd.DropTarget;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.DAO.ItemDAO;
import tcc.balcao.model.entity.Item;

public class TelaItensCadastrados extends JFrame {

	private static final long serialVersionUID = -2930866811811389056L;
	
	private JPanel principalPanel;
	private JTable tabelaProdutos;
	private ArrayList<Item> itens;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	


	public TelaItensCadastrados(ArrayList<Item> itens) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.itens = itens;
		iniciar();
		criarComponentes();
		pack();
		setVisible(true);
	}


	private void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new MigLayout("insets 0"));
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Cardápio Eletrônico - Produtos Cadastrados");		
	}

	private void criarComponentes() {
		getPrincipalPanel().add(getScrollPane());
		add(getPrincipalPanel());
	}
	
	
	public JPanel getPrincipalPanel() {
		if(principalPanel == null){
			principalPanel = new JPanel(new MigLayout("insets 0"));
		}
		return principalPanel;
	}
	
	
	public JTable getTabelaProdutos() {
		if(tabelaProdutos == null){
			tabelaProdutos = new JTable();
			tabelaProdutos.setModel(getTableModel());
			tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(320);
			tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(130);
			tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(50);
			tabelaProdutos.setDragEnabled(false);
			setDados();
			
		}
		return tabelaProdutos;
	}
	
	
	public JScrollPane getScrollPane() {
		if(scrollPane == null){
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTabelaProdutos());
			scrollPane.setPreferredSize(new Dimension(500,600));
			
		}
		return scrollPane;
	}
	
	public DefaultTableModel getTableModel() {
		if (tableModel == null) {
			tableModel = new DefaultTableModel(null, new String[] {
					"Produto", "Tipo", "Valor"}) {

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int col) {
					return false;
			}
			};
		}
		return tableModel;
	}
	
	private void setDados() {
		for (Item item : itens) {
			String[] linha = new String[] {item.getNome(), item.getTipoitem().getTipo(), Double.toString(item.getValor()) };
			getTableModel().addRow(linha);
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		new TelaItensCadastrados(new ItemDAO().findAll());
	}
}
