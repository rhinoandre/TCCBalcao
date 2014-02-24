package tcc.balcao.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.interfaces.Acoes;
import tcc.balcao.view.listeners.TelaErrorListener;


public class TelaError extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelOk, panelSimNao, panelGeral, panelPagamento;
	private JButton okButton, simButton, naoButton, cartaoButton, dinheiroButton, fecharButton;
	private JLabel msgLabel;
	private ArrayList<TelaErrorListener> telaErrorListeners = new ArrayList<TelaErrorListener>();
	
	public TelaError(int erro, String msg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		iniciar();
		getPanelGeral().add(getMsgLabel(msg), "dock north, gap 10px 10px 10px 10px");
		
		if(erro == Acoes.ERRO){
			getPanelGeral().add(getPanelOk(), "dock south");
		}
		else if(erro == Acoes.WARNING){
			getPanelGeral().add(getPanelSimNao(), "dock south");
		}
		else if(erro == Acoes.PAGAMENTO){
			getPanelGeral().add(getPanelPagamento(), "dock south");
			setCoresBalcao();
		}
		
		add(getPanelGeral());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setLayout(new MigLayout("insets 0"));
		setAlwaysOnTop(true);
		setUndecorated(true);
		setResizable(false);
		setTitle("Erro");		
	}

	private JPanel getPanelGeral() {
		if(panelGeral == null){
			panelGeral = new JPanel(new MigLayout());
			panelGeral.setBackground(new Color(73, 33, 33, 255));
			panelGeral.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		}
		return panelGeral;
	}

	private JPanel getPanelOk() {
		if(panelOk == null){
			panelOk = new JPanel(new MigLayout());
			panelOk.add(getOkButton(), "align center");
			panelOk.setOpaque(false);
		}
		return panelOk;
	}
	
	private JPanel getPanelSimNao(){
		if(panelSimNao == null){
			panelSimNao = new JPanel(new MigLayout());
			panelSimNao.add(getSimButton());
			panelSimNao.add(getNaoButton());
			panelSimNao.setOpaque(false);
		}
		return panelSimNao;
	}

	public JPanel getPanelPagamento() {
		if(panelPagamento == null){
			panelPagamento = new JPanel(new MigLayout());
			panelPagamento.add(getCartaoButton());
			panelPagamento.add(getDinheiroButton());
			panelPagamento.add(getFecharButton());
			panelPagamento.setOpaque(false);
		}
		return panelPagamento;
	}

	private JButton getOkButton() {
		if(okButton == null){
			okButton = new JButton(new ImageIcon("imgs\\telaError\\imgOk.png"));
			okButton.setPressedIcon(new ImageIcon("imgs\\telaError\\imgOkPressed.png"));
			configurarBotoes(okButton);
			okButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (TelaErrorListener listener : telaErrorListeners) {
						listener.okAction();
					}
				}
				
			});
		}
		return okButton;
	}

	private JButton getSimButton() {
		if(simButton == null){
			simButton = new JButton(new ImageIcon("imgs\\telaError\\imgSim.png"));
			simButton.setPressedIcon(new ImageIcon("imgs\\telaError\\imgSimPressed.png"));
			configurarBotoes(simButton);
			simButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (TelaErrorListener listener : telaErrorListeners) {
						listener.simAction();
					}
				}
				
			});
		}
		return simButton;
	}

	private JButton getNaoButton() {
		if(naoButton == null){
			naoButton = new JButton(new ImageIcon("imgs\\telaError\\imgNao.png"));
			naoButton.setPressedIcon(new ImageIcon("imgs\\telaError\\imgNaoPressed.png"));
			configurarBotoes(naoButton);
			naoButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (TelaErrorListener listener : telaErrorListeners) {
						listener.naoAction();
						dispose();
					}
				}
				
			});
		}
		return naoButton;
	}
	
	private JButton getCartaoButton() {
		if(cartaoButton == null){
			cartaoButton = new JButton(new ImageIcon("imgs\\imgCartao.png"));
			cartaoButton.setPressedIcon(new ImageIcon("imgs\\imgCartaoPressed.png"));
			configurarBotoes(cartaoButton);
			cartaoButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (TelaErrorListener listener : telaErrorListeners) {
						listener.cartaoAction();
					}
				}
			});
		}
		return cartaoButton;
	}

	private JButton getDinheiroButton() {
		if(dinheiroButton == null){
			dinheiroButton = new JButton(new ImageIcon("imgs\\imgDinheiro.png"));
			dinheiroButton.setPressedIcon(new ImageIcon("imgs\\imgDinheiroPressed.png"));
			configurarBotoes(dinheiroButton);
			dinheiroButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (TelaErrorListener listener : telaErrorListeners) {
						listener.dinheiroAction();
					}
				}
			});
		}
		return dinheiroButton;
	}
	
	

	private JButton getFecharButton() {
		if(fecharButton == null){
			fecharButton = new JButton(new ImageIcon("imgs\\imgFechar.png"));
			fecharButton.setPressedIcon(new ImageIcon("imgs\\imgFecharPressed.png"));
			configurarBotoes(fecharButton);
			fecharButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
						dispose();
				}
			});
		}
		return fecharButton;
	}

	private JLabel getMsgLabel(String msg) {
		if(msgLabel == null){
			msgLabel = new JLabel(msg);
			msgLabel.setForeground(Color.white);
			msgLabel.setFont(new Font("Calibri", 1, 15));
		}
		return msgLabel;
	}
	
	private void setCoresBalcao(){
		getPanelGeral().setBackground(new Color(59, 63, 111, 255));
	}
	
	private void configurarBotoes(JButton btn) {
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setMargin(new Insets(-5, -5, -5, -5));
	}
	
	public void addListener(TelaErrorListener listener){
		telaErrorListeners.add(listener);
	}	
}
