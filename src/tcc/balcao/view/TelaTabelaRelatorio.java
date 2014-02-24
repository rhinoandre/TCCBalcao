package tcc.balcao.view;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.Pedidos;

public class TelaTabelaRelatorio extends JFrame {
	

	private static final long serialVersionUID = 8236601753985946461L;
	private JPanel panelLista;
	private JTable tabelaPedidos;
	public DefaultTableModel tableModel;
	private ArrayList<Pedidos> pedidos;
	
	public TelaTabelaRelatorio() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		pedidos = new ArrayList<Pedidos>();
		iniciar();
		criarComponentes();
		pack();
		setVisible(true);
	}
	
	private void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new MigLayout("insets 0"));
		setAlwaysOnTop(true);		
	}
	
	private void criarComponentes() {
		getTabelaPedidos();
	}

	public DefaultTableModel getTableModel() {
		if (tableModel == null) {
			tableModel = new DefaultTableModel(null, new String[] {
					"Data", "Pedido ID", "Mesa", "Item", "Valor" }) {

				private static final long serialVersionUID = -1475015549982259479L;

				public boolean isCellEditable(int row, int col) {
					return false;
				}

			};
		}
		return tableModel;
	}

	public JPanel getPanelLista() {
		if(panelLista == null){
			panelLista = new JPanel(new MigLayout());
		}
		return panelLista;
	}
	
	public void getTabelaPedidos() {
		if(tabelaPedidos == null){
			tabelaPedidos = new JTable();
			tabelaPedidos.setModel(getTableModel());
			JScrollPane scrollPane = new JScrollPane();
			tabelaPedidos.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					
				}
			});
			scrollPane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
			scrollPane.setViewportView(tabelaPedidos);
			getPanelLista().add(scrollPane, "dock north");
		}
		add(getPanelLista());
	}
	
	public void setPedido(Pedidos pedido){
		this.pedidos.add(pedido);
		addItemLista(pedido);
	}
	
	private void addItemLista(Pedidos pedido) {
		String[] linha = new String[] { };// Passar os valores.
		getTableModel().addRow(linha);
	}

}
