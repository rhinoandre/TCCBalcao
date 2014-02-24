package tcc.balcao.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Pedidos;
import tcc.balcao.view.listeners.MesaEspecificacoesListeners;

public class TelaMesaEspecificacoes extends JFrame {
	private static final long serialVersionUID = 6566785817526599747L;
	private ArrayList<MesaEspecificacoesListeners> listeners = new ArrayList<MesaEspecificacoesListeners>();
	
	private JPanel panelPrincipal, panelBotoes;
	private JLabel valorLabel, porcentagemLabel, valorTotalLabel;
	private JButton fecharContaButton;
	private JTable tabelaProdutos;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private Mesa mesa;
	private HashMap<String, Object> valores;
	
// Se quiser retornar os comentários para alterar entre mais e menos detalhes volte na 137
	public TelaMesaEspecificacoes(HashMap<String, Object> valores) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		this.valores = valores;
		
		this.mesa = (Mesa) valores.get("Mesa");
		
		iniciarComponentes();
		criarTabelaProdutos((ArrayList<Pedidos>) valores.get("Pedidos"));
		add(criarBotoes(), "dock south, growy");
		pack();
		setVisible(true);

	}

	private void iniciarComponentes() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new MigLayout());
		setAlwaysOnTop(true);
		setResizable(false);
		// setLocationRelativeTo(null);
	}

	private void criarTabelaProdutos(ArrayList<Pedidos> pedidos) {
		//TODO: Puxar do banco os itens da mesa
		if(panelPrincipal != null){
			panelPrincipal.removeAll();
			panelPrincipal = null;
		}
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel(new MigLayout());
			tabelaProdutos = new JTable();
			scrollPane = new JScrollPane();
			tabelaProdutos.setModel(getTableModel());
			tabelaProdutos.setModel(getTableModel());
			tabelaProdutos.getColumnModel().setColumnSelectionAllowed(false);
			tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
			tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(20);
			tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(280);
			tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(20);
			tabelaProdutos.setRowHeight(30);
			tabelaProdutos.setFont(new Font("Calibri", 2, 20));
			tabelaProdutos.setForeground(Color.white);
			tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelaProdutos.setGridColor(new Color(34, 36, 59, 255));
			tabelaProdutos.setBackground(new Color(59, 63, 111, 255));
			tabelaProdutos.setSelectionBackground(new Color(118, 122, 175, 255));
			tabelaProdutos.setPreferredScrollableViewportSize(new Dimension(527,497));
			tabelaProdutos.setMinimumSize(new Dimension(527,497));
			JTableHeader cabecalho = tabelaProdutos.getTableHeader();  
			cabecalho.setFont(new Font("Calibri", 0, 15));
			cabecalho.setForeground(Color.white);
			cabecalho.setBackground(new Color(118, 122, 175, 255));
			cabecalho.setEnabled(false);
			cabecalho.setOpaque(false);
			scrollPane.setViewportView(tabelaProdutos);
			scrollPane.getViewport().setBackground(new Color(59, 63, 111, 255));
			scrollPane.setPreferredSize(tabelaProdutos.getPreferredScrollableViewportSize());
			scrollPane.setMinimumSize(tabelaProdutos.getMinimumSize());
			scrollPane.setBorder(BorderFactory.createLineBorder(new Color(34, 36, 59, 255), 1));
			tabelaProdutos.setModel(getTableModel());
			setDados(pedidos);
			scrollPane.setViewportView(tabelaProdutos);
			panelPrincipal.add(scrollPane, "dock north");
		}
		add(panelPrincipal);
	}

	private DefaultTableModel getTableModel() {
		if (tableModel == null) {
			tableModel = new DefaultTableModel(null, new String[] {"Quantidade", "Valor", "Produto", "Status" }) {

				private static final long serialVersionUID = -8330378991264238030L;

				public boolean isCellEditable(int row, int col) {
					return false;
				}

			};
		}
		return tableModel;
	}

	private void setDados(ArrayList<Pedidos> pedidos) {
		for (Pedidos pedido : pedidos) {
			if(!pedido.getStatuspedido().getStatus().equals("Cancelado")){
				String[] linha = new String[] { Integer.toString(pedido.getQuantidade()),
						String.format("%.2f", pedido.getItem().getValor()),
						pedido.getItem().getNome(),
						pedido.getStatuspedido().getStatus()};
				getTableModel().addRow(linha);
			}
		}
	}
	
	private JPanel criarBotoes() {
		if (panelBotoes == null) {
			panelBotoes = new JPanel(new MigLayout("", "[]push[][]"));
			fecharContaButton = new JButton(new ImageIcon("imgs\\imgFecharConta.png"));
			fecharContaButton.setPressedIcon(new ImageIcon("imgs\\imgFecharContaPressed.png"));
			configurarBotoes(fecharContaButton);
//			if(mesa.getStatusmesa().getIdstatusMesa() != 3){
//				fecharContaButton.setEnabled(false);
//			}
			fecharContaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (MesaEspecificacoesListeners listener : listeners) {
						listener.fecharConta();
					}
				}
			});
			
			
			panelBotoes.setBackground(new Color(118, 122, 175, 255));
			panelBotoes.add(getValorLabel((Double)valores.get("ValorTotal")), "wrap");
			panelBotoes.add(getPorcentagemLabel((Double) valores.get("Porcentagem")), "wrap");
			panelBotoes.add(getValorTotalLabel((Double) valores.get("TotalPorcentagem")));
			panelBotoes.add(fecharContaButton);
			
		}
		return panelBotoes;
	}
	
	

	public JLabel getValorLabel(Double valor) {
		valorLabel = new JLabel("Valor: R$ " + String.format("%.2f", valor));
		valorLabel.setFont(new Font("Calibri", 2, 20));
		valorLabel.setForeground(Color.WHITE);
		return valorLabel;
	}

	public JLabel getPorcentagemLabel(Double porcentagem) {
		porcentagemLabel = new JLabel("10%: R$ " + String.format("%.2f", porcentagem));
		porcentagemLabel.setFont(new Font("Calibri", 2, 20));
		porcentagemLabel.setForeground(Color.WHITE);
		return porcentagemLabel;
	}

	public JLabel getValorTotalLabel(Double valorTotal) {
		valorTotalLabel = new JLabel("Valor total: R$ " + String.format("%.2f", valorTotal));
		valorTotalLabel.setFont(new Font("Calibri", 2, 20));
		valorTotalLabel.setForeground(Color.WHITE);
		return valorTotalLabel;
	}
	
	private void configurarBotoes(JButton btn) {
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setMargin(new Insets(-5, -5, -5, -5));
	}
	

	public void addListener(MesaEspecificacoesListeners listener){
		System.out.println("teste");
		this.listeners.add(listener);
	}
}
