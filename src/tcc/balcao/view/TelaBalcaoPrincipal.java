package tcc.balcao.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.view.listeners.TelaBalcaoListener;

public class TelaBalcaoPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel menuPanel, mesasTotalPanel; 
	private JPanelBackgroundImage mesaPanel;
	private JPanelBackgroundImage geralBGPanel;

	private JLabel mesaLabel;
	private Image imgMesaStatus, imgMesaStatusMenor;
	private JMenuBar menuBar;
	private JMenuItem menuItem;
	private JMenu menu;
	private ArrayList<Mesa> mesas;
	private HashMap<Mesa, JPanel> painelMesas;
	private ArrayList<TelaBalcaoListener> telaBalcaoListeners  = new ArrayList<TelaBalcaoListener>();

	public TelaBalcaoPrincipal(ArrayList<Mesa> mesas) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, MalformedURLException {

		this.mesas = mesas;
		
		iniciar();
		getGeralBGPanel().add(criarMenu(), "dock north, width 100%, growx, growy, wrap");
		getGeralBGPanel().add(criarMesasPanel(), "dock south, width 100%, growx, height 100%, growy");

		add(getGeralBGPanel(), "width 100%, growx, height 100%, growy");
		
		pack();
		setVisible(true);
	}

	private void iniciar() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
//		setAlwaysOnTop(true);
		setTitle("Cardapio Eletronico - Gerenciamento");
		setUndecorated(true); 
		setResizable(false);
		setLayout(new MigLayout("insets 0"));
	}

	private JPanel criarMenu() {

		if (menuPanel == null) {
			menuPanel = new JPanel(new MigLayout("insets 0"));
			menuBar = new JMenuBar();
			menu = new JMenu("Relatorios");
			menuItem = new JMenuItem("Produtos");
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (TelaBalcaoListener listener : telaBalcaoListeners) {
						listener.produtoRelatorio();
					}
				}
			});
			menu.add(menuItem);
			
			menuItem = new JMenuItem("Financeiro");
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (TelaBalcaoListener listener : telaBalcaoListeners) {
						listener.financeiroRelatorio();
					}
				}
			});
			menu.add(menuItem);
			
			menuItem = new JMenuItem("Tempo de espera");
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (TelaBalcaoListener listener : telaBalcaoListeners) {
						listener.tempoDeEsperaRelatorio();
					}
				}
			});
			menu.add(menuItem);
			menu.setFont(new Font("Arial", 2, 20));
			menuBar.add(menu);

			menu = new JMenu("Cadastros");
			menuItem = new JMenuItem("Produtos");
			menu.add(menuItem);
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (TelaBalcaoListener balcaoPrincipalListener : telaBalcaoListeners) {
						balcaoPrincipalListener.cadastroProdutoAction();
					}
				}
			});
			menuItem = new JMenuItem("Mesas");
			menu.add(menuItem);
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (TelaBalcaoListener balcaoPrincipalListener : telaBalcaoListeners) {
						balcaoPrincipalListener.cadastroMesaAction();
					}
				}
			});
			menu.setFont(new Font("Arial", 2, 20));
			menuBar.add(menu);
			
			menu = new JMenu("Ajuda");
			menuItem = new JMenuItem("Sobre");
			menu.setFont(new Font("Arial", 2, 20));
			menu.add(menuItem);
			menuBar.add(menu);
			menuBar.setPreferredSize(new Dimension(0, 100));
			menuPanel.add(menuBar, "growx, width 100%");
		}
		return menuPanel;
	}

	private JPanel criarMesasPanel() throws MalformedURLException {
		if(mesasTotalPanel == null){
			mesasTotalPanel = new JPanel(new MigLayout());
			mesasTotalPanel.setOpaque(false);
			painelMesas = new HashMap<Mesa, JPanel>();
			for (final Mesa mesa : mesas) {
				mesaPanel = new JPanelBackgroundImage(new ImageIcon("imgs\\mesas\\mesaBG.png").getImage(), new MigLayout(), 0, 0);
				mesaPanel.setOpaque(false);
				mesaLabel = new JLabel(mesa.getMesa());
				mesaLabel.setFont(new Font("Arial", 2, 30));
				mesaLabel.setForeground(new Color(27, 34, 77, 255));
				//DIMINUINDO TAMANHO DA IMAGEM
				imgMesaStatus = Toolkit.getDefaultToolkit().getImage(mesa.getStatusmesa().getImgs());
				imgMesaStatusMenor = imgMesaStatus.getScaledInstance(194, 179, Image.SCALE_DEFAULT);
				///////////////////////////////
				mesaPanel.add(mesaLabel, "align center, wrap");
				mesaPanel.add(new JLabel(new ImageIcon(imgMesaStatusMenor)),"align center");
				mesaPanel.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent arg0) {
						for (TelaBalcaoListener listener : telaBalcaoListeners) {
							for (Mesa mesaAtual : mesas) {
								if(mesaAtual.getIdmesa().compareTo(mesa.getIdmesa()) == 0){
									listener.mostrarEspecificacoesMesa(mesaAtual);
									break;
								}
							}
						}
					}
				});
				mesaPanel.repaint();
				mesaPanel.revalidate();
				painelMesas.put(mesa, mesaPanel);
				
				mesasTotalPanel.add(mesaPanel, "width 100, height 100");
				
			}
		}
		return mesasTotalPanel;
	}

	public void alterarStatusMesa(Mesa mesa){
		for (int i=0;i<mesas.size();i++) {
			if (mesas.get(i).getMesa().equals(mesa.getMesa())) {
				if(painelMesas.containsKey(mesas.get(i))){
					
					JPanel painelTemp = painelMesas.remove(mesas.get(i));
					
					painelTemp.removeAll();
					mesaLabel = new JLabel(mesa.getMesa());
					mesaLabel.setFont(new Font("Arial", 2, 30));
					mesaLabel.setForeground(new Color(27, 34, 77, 255));
					
					imgMesaStatus = Toolkit.getDefaultToolkit().getImage(mesa.getStatusmesa().getImgs());
					imgMesaStatusMenor = imgMesaStatus.getScaledInstance(194, 179, Image.SCALE_DEFAULT);
					painelTemp.add(mesaLabel, "align center, wrap");
					painelTemp.add(new JLabel(new ImageIcon(imgMesaStatusMenor)), "align center");
					painelTemp.revalidate();
					painelTemp.repaint();
					
					mesas.remove(i);
					mesas.add(i, mesa);
					
//					TODO:Altera o painel com a nova imagem
					painelMesas.put(mesa, painelTemp);
					
					
					
				}
			}
		}
	}
	
	private JPanelBackgroundImage getGeralBGPanel() {
		if(geralBGPanel == null){
			geralBGPanel = new JPanelBackgroundImage(new ImageIcon("imgs\\geralBG.jpg").getImage(), new MigLayout(), 0, 0);
		}
		return geralBGPanel;
	}
	
	public void addListener(TelaBalcaoListener listener){
		this.telaBalcaoListeners.add(listener);
	}
	
	
//	public static void main(String[] args) throws ClassNotFoundException,
//			InstantiationException, IllegalAccessException,
//			UnsupportedLookAndFeelException {
//		new TelaBalcaoPrincipal();
	
//	}
}
