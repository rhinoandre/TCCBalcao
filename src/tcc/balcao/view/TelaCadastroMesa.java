package tcc.balcao.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.view.listeners.CadastroMesaListener;

public class TelaCadastroMesa extends JFrame {

	private static final long serialVersionUID = 9103858618754255379L;
	
	private JLabel mesaLabel;
	private JPanel mesaPanel;
	private NumberField numeroMesaTextField;
	private JButton okButton, fecharButton;
	private ArrayList<CadastroMesaListener> cadastroMesaListeners = new ArrayList<CadastroMesaListener>();

	public TelaCadastroMesa() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, ParseException {
		iniciar();
		construirComponentes();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void construirComponentes() throws ParseException {
		getMesaPanel().add(getMesaLabel());
		getMesaPanel().add(getNumeroMesaTextField(), "width 130, wrap");
		getMesaPanel().add(getOkButton());
		getMesaPanel().add(getFecharButton());
		add(getMesaPanel());
		
	}

	public void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Cadastro de Mesa");
		setResizable(false);
		setLayout(new MigLayout("insets 0"));
	}

	private JPanel getMesaPanel() {
		if(mesaPanel == null){
			mesaPanel = new JPanel(new MigLayout());
			mesaPanel.setBackground(new Color(59, 63, 111, 255));
		}
		return mesaPanel;
	}
	
	private JLabel getMesaLabel() {
		if(mesaLabel == null){
			mesaLabel = new JLabel("Número da mesa: ");
			mesaLabel.setFont(new Font("Calibri", 0, 15));
			mesaLabel.setForeground(Color.white);
		}
		return mesaLabel;
	}


	private JTextField getNumeroMesaTextField() throws ParseException {
		if(numeroMesaTextField == null){
			numeroMesaTextField = new NumberField();
			numeroMesaTextField.setForeground(new Color(34, 36, 59, 255));
			numeroMesaTextField.setFont(new Font("Calibri", 0, 15));
			numeroMesaTextField.setBorder(null);
			numeroMesaTextField.setBackground(new Color(118, 122, 175, 255));
		}
		return numeroMesaTextField;
	}

	private JButton getOkButton() {
		if(okButton == null){
			okButton = new JButton(new ImageIcon("imgs\\imgOk.png"));
			okButton.setPressedIcon(new ImageIcon("imgs\\imgOkPressed.png"));
			configurarBotoes(okButton);
			okButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					for (CadastroMesaListener listener : cadastroMesaListeners) {
						listener.okAction("Mesa" + numeroMesaTextField.getText());
					}
				}
			});
		}
		return okButton;
	}

	private JButton getFecharButton() {
		if(fecharButton == null){
			fecharButton = new JButton(new ImageIcon("imgs\\imgFechar.png"));
			fecharButton.setPressedIcon(new ImageIcon("imgs\\imgFecharPressed.png"));
			configurarBotoes(fecharButton);
			fecharButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return fecharButton;
	}
	
	private void configurarBotoes(JButton btn) {
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setMargin(new Insets(-5, -5, -5, -5));
	}
	
	public void addListener(CadastroMesaListener listener){
		this.cadastroMesaListeners.add(listener);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, ParseException {
		new TelaCadastroMesa();
	}

}
