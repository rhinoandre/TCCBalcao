package tcc.balcao.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.MaskFormatter;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.IntervaloDataRelatorio;
import tcc.balcao.view.listeners.TelaRelatorioListeners;


public class TelaRelatorio extends JFrame {
	
	private static final long serialVersionUID = -8375052804345187909L;
	
	private JPanel panelGeral, geralBG;
	private JLabel escolhaLabel;
	private JRadioButton radioButton, dataEspecRadioButton;
	private ButtonGroup btnGroup;
	private MaskFormatter formatter;
	private IntervaloDataRelatorio idr;
	private JFormattedTextField dataIniTextField, dataFimTextField;
	private JButton okButton;
	private Font font = new Font("Calibri", 0, 15);
	
	private ArrayList<TelaRelatorioListeners> telaRelatorioListeners = new ArrayList<TelaRelatorioListeners>();

	public TelaRelatorio() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, ParseException {
		iniciar();

		getGeralBG().add(getEscolhaLabel(), "wrap");
		criarRadioButtons();
		getPanelGeral().add(getDataEspecRadioButton(), "newline");
		getPanelGeral().add(getDataIniTextField());
		getPanelGeral().add(getDataFimTextField(), "wrap");
		getPanelGeral().add(getOkButton());
		getGeralBG().add(getPanelGeral());
		
		add(getGeralBG());
		pack();
		setVisible(true);
	}

	private void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(550,167));
		setAlwaysOnTop(true);
		setTitle("Cardapio Eletronico - Relatorio");
		setResizable(false);
		setLayout(new MigLayout("insets 0"));
	}
	
	private void criarRadioButtons(){
		 String tempo[] = {"Anual", "Semestral", "Trimensal", "Bimestral", "Mês", "Semana"};
		 for(int i=0; i<tempo.length; i++){ 
			 getPanelGeral().add(getRadioButton(tempo[i], i));
		 }
		 
	}
	
	private JPanel getPanelGeral() {
		if(panelGeral == null){
			panelGeral = new JPanel(new MigLayout());
			panelGeral.setOpaque(false);
		}
		return panelGeral;
	}

	private JPanel getGeralBG() {
		if(geralBG == null){
			geralBG = new JPanel(new MigLayout());
			geralBG.setBackground(new Color(59, 63, 111, 255));
		}
		return geralBG;
	}

	private JLabel getEscolhaLabel() {
		if(escolhaLabel == null){
			escolhaLabel = new JLabel("Escolha o tempo desejado:");
			escolhaLabel.setForeground(Color.white);
			escolhaLabel.setFont(new Font("Calibri", 2, 20));
		}
		return escolhaLabel;
	}

	private JRadioButton getRadioButton(String tempo, int i) {
		radioButton = new JRadioButton(tempo);
		radioButton.setFont(font);
		radioButton.setForeground(Color.white);
		radioButton.setBackground(new Color(118, 122, 175, 255));
		getBtnGroup().add(radioButton);
		radioButton.setMnemonic(i);
		return radioButton;
	}

	public JTextField getDataIniTextField() throws ParseException {
		if(dataIniTextField == null){
			formatter = new MaskFormatter("##/##/####");
			formatter.setValidCharacters("0123456789");
			dataIniTextField = new JFormattedTextField(formatter);
			dataIniTextField.setBackground(new Color(118, 122, 175, 255));
			dataIniTextField.setFont(new Font("Calibri", 0, 15));
			dataIniTextField.setForeground(Color.white);
			dataIniTextField.setBorder(null);
			dataIniTextField.setEnabled(false);
			dataIniTextField.setDisabledTextColor(Color.white);
			dataIniTextField.setPreferredSize(new Dimension(150, 20));
		}
		return dataIniTextField;
	}
	
	public JTextField getDataFimTextField() throws ParseException {
		if(dataFimTextField == null){
			formatter = new MaskFormatter("##/##/####");
			formatter.setValidCharacters("0123456789");
			dataFimTextField = new JFormattedTextField(formatter);
			dataFimTextField.setBackground(new Color(118, 122, 175, 255));
			dataFimTextField.setFont(new Font("Calibri", 0, 15));
			dataFimTextField.setForeground(Color.white);
			dataFimTextField.setBorder(null);
			dataFimTextField.setEnabled(false);
			dataFimTextField.setDisabledTextColor(Color.white);
			dataFimTextField.setPreferredSize(new Dimension(150, 20));
		}
		return dataFimTextField;
	}
	
	private JButton getOkButton() {
		if(okButton == null){
			okButton = new JButton(new ImageIcon("imgs\\imgOk.png"));
			okButton.setPressedIcon(new ImageIcon("imgs\\imgOkPressed.png"));
			configurarBotoes(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for(TelaRelatorioListeners listener : telaRelatorioListeners) {
						if(!btnGroup.getSelection().isSelected()){
							JOptionPane.showConfirmDialog(null, "Selecione uma opção!");
						}
						else if(btnGroup.getSelection().getMnemonic() == 6){
							try {
								idr = new IntervaloDataRelatorio(getDataIniTextField().getText(), getDataFimTextField().getText());
								System.out.println(idr.getDataFim());
								listener.okAction(idr);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						else{
							listener.okAction(btnGroup.getSelection().getMnemonic());
						}
					}
				}
			});
		}
		return okButton;
	}


	private JRadioButton getDataEspecRadioButton() {
		if(dataEspecRadioButton == null){
			dataEspecRadioButton = new JRadioButton("Data Especifica:");
			dataEspecRadioButton.setForeground(Color.white);
			dataEspecRadioButton.setBackground(new Color(118, 122, 175, 255));
			dataEspecRadioButton.setFont(font);
			getBtnGroup().add(dataEspecRadioButton);
			dataEspecRadioButton.setMnemonic(6);
			dataEspecRadioButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
						try {
							getDataIniTextField().setEnabled(true);
							getDataFimTextField().setEnabled(true);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						
				}
			});
		}
		return dataEspecRadioButton;
	}

	private ButtonGroup getBtnGroup() {
		if(btnGroup == null){
			btnGroup = new ButtonGroup();
		}
		return btnGroup;
	}
	
	private void configurarBotoes(JButton btn) {
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setMargin(new Insets(-5, -5, -5, -5));
	}
	
	public void addListener(TelaRelatorioListeners listener){
		this.telaRelatorioListeners.add(listener);
	}
	
	public static void main(String[] args) {
		try {
			try {
				new TelaRelatorio();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
