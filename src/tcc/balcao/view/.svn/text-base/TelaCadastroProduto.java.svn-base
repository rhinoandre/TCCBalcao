package tcc.balcao.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;
import tcc.balcao.model.entity.Item;
import tcc.balcao.model.entity.Tipoitem;
import tcc.balcao.view.listeners.CadastroProdutoListener;

public class TelaCadastroProduto extends JFrame {
	private static final long serialVersionUID = 715372509212776938L;

	private JPanel principalPanel, botoesPanel;
	private JPanel geralBG;
	private JLabel nomeLabel, tipoLabel, descricaoLabel, valorLabel, imagemLabel, novoTipoLabel;
	private JTextField nomeTextField, novoTipoTextField, urlTextField;
	private NumberField valorTextField;
	private JTextArea descricaoTextArea;
	private JComboBox tipoComboBox;
	private Font fontLabel = new Font("Calibri", 0, 15);
	private Font fontTextField = new Font("Calibri", 0, 15);
	private ArrayList<Tipoitem> tipos;
	private ArrayList<CadastroProdutoListener> cadastroProdutoListeners = new ArrayList<CadastroProdutoListener>();

	private JButton salvarButton, cancelarButton, limparButton, procurarButton;

	public TelaCadastroProduto(ArrayList<Tipoitem> tipos) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, ParseException {
		this.tipos = tipos;
		iniciar();
		criarPanelPrincipal();
		criarBotoesPanel();
		add(getGeralBG(), "width 402, height 446");
		pack();
		setVisible(true);
	}




	private void iniciar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new MigLayout("insets 0"));
		setResizable(false);
		setTitle("Cardápio Eletrônico - Cadastrar Produtos");
	}
	
	private void criarPanelPrincipal() throws ParseException {
		getPrincipalPanel().add(getNomeLabel());
		getPrincipalPanel().add(getNomeTextField(), "wrap, width 350");
		getPrincipalPanel().add(getTipoLabel());
		getPrincipalPanel().add(getTipoComboBox(), "wrap");
		getPrincipalPanel().add(getNovoTipoLabel());
		getPrincipalPanel().add(getNovoTipoTextField(), "wrap, width 190");
		getPrincipalPanel().add(getValorLabel());
		getPrincipalPanel().add(getValorTextField(), "wrap, width 100");
		getPrincipalPanel().add(getDescricaoLabel());
		getPrincipalPanel().add(getDescricaoTextArea(), "wrap, growx, height 200");
		getPrincipalPanel().add(getImagemLabel());
		getPrincipalPanel().add(getUrlTextField(), "growx");
		getPrincipalPanel().add(getProcurarButton());
		
		
		getGeralBG().add(principalPanel, "width 400, height 400, wrap");
	}
	




	private void criarBotoesPanel() {
		getBotoesPanel();
		botoesPanel.add(getLimparButton());
		botoesPanel.add(getCancelarButton());
		botoesPanel.add(getSalvarButton());
		
		getGeralBG().add(botoesPanel, "width 400, height 40");
		
	}
	
	
	public JPanel getPrincipalPanel() {
		if(principalPanel == null){
			principalPanel = new JPanel(new MigLayout());
			principalPanel.setOpaque(false);
		}
		return principalPanel;
	}


	public JPanel getBotoesPanel() {
		if(botoesPanel == null){
			botoesPanel = new JPanel(new MigLayout("", "[]push[][]"));
			botoesPanel.setBackground(new Color(118, 122, 175, 255));
		}
		return botoesPanel;
	}


	public JLabel getNomeLabel() {
		if(nomeLabel == null){
			nomeLabel = new JLabel("Nome: ");
			nomeLabel.setFont(fontLabel);
			nomeLabel.setForeground(Color.white);
		}
		return nomeLabel;
	}
	
	
	public JLabel getTipoLabel() {
		if(tipoLabel == null){
			tipoLabel = new JLabel("Tipo: ");
			tipoLabel.setFont(fontLabel);
			tipoLabel.setForeground(Color.white);
		}
		return tipoLabel;
	}
	
	public JComboBox getTipoComboBox() {
		if(tipoComboBox == null){
			String[] stringTipos = new String[tipos.size()];
			stringTipos[0] = "Selecione um tipo";
			for (int i = 1; i < tipos.size(); i++) {
				stringTipos[i] = tipos.get(i-1).getTipo();
			}
			tipoComboBox = new JComboBox(stringTipos);
			tipoComboBox.setForeground(new Color(34, 36, 59, 255));
		}
		else{ 
			tipoComboBox.addMouseListener(new MouseAdapter() {
		
				public void mouseClicked(MouseEvent arg0) {
					if(!tipoComboBox.isEnabled()){
						tipoComboBox.setEnabled(true);
						getNovoTipoTextField().setEditable(false);
						getNovoTipoTextField().setText("Clique para criar um novo tipo.");
					}
				}
			});
		}	
		return tipoComboBox;
	}
	
	public JLabel getDescricaoLabel() {
		if(descricaoLabel == null){
			descricaoLabel = new JLabel("Descrição: ");
			descricaoLabel.setFont(fontLabel);
			descricaoLabel.setForeground(Color.white);
		}
		return descricaoLabel;
	}
	
	
	public JLabel getValorLabel() {
		if(valorLabel == null){
			valorLabel = new JLabel("Valor: R$");
			valorLabel.setFont(fontLabel);
			valorLabel.setForeground(Color.white);
		}
		return valorLabel;
	}
	
	
	public JLabel getImagemLabel() {
		if(imagemLabel == null){
			imagemLabel = new JLabel("Imagem: ");
			imagemLabel.setFont(fontLabel);
			imagemLabel.setForeground(Color.white);
		}
		return imagemLabel;
	}
	
	
	public JTextField getNomeTextField() {
		if(nomeTextField == null){
			nomeTextField = new JTextField();
			nomeTextField.setForeground(new Color(34, 36, 59, 255));
			nomeTextField.setFont(fontTextField);
			nomeTextField.setBorder(null);
			nomeTextField.setBackground(new Color(118, 122, 175, 255));
		}
		return nomeTextField;
	}
	
	
	public JTextField getValorTextField() throws ParseException {
		if(valorTextField == null){
			valorTextField = new NumberField();
			valorTextField.setFont(fontTextField);
			valorTextField.setBorder(null);
			valorTextField.setForeground(new Color(34, 36, 59, 255));
			valorTextField.setBackground(new Color(118, 122, 175, 255));
		}
		return valorTextField;
	}
	
	
	public JTextArea getDescricaoTextArea() {
		if(descricaoTextArea == null){
			descricaoTextArea = new JTextArea();
			descricaoTextArea.setFont(fontTextField);
			descricaoTextArea.setForeground(new Color(34, 36, 59, 255));
			descricaoTextArea.setBorder(null);
			descricaoTextArea.setBackground(new Color(118, 122, 175, 255));
			descricaoTextArea.setLineWrap(true);
			descricaoTextArea.setCaretPosition(0);
		}
		return descricaoTextArea;
	}
	
	
	public JButton getSalvarButton() {
		if(salvarButton == null){
			salvarButton = new JButton("Salvar");
			salvarButton.setBackground(new Color(118, 122, 175, 255));
			salvarButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					//SETANDO ITEM
					Item item = new Item();
					item.setNome(getNomeTextField().getText());
					item.setDescricao(getDescricaoTextArea().getText());
					try {
						item.setValor(Double.valueOf(getValorTextField().getText()));
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					if(tipoComboBox.isEnabled()){
						item.setTipoitem(tipos.get(tipoComboBox.getSelectedIndex()-1));						
					}
					else{
						File dir = new File("" + System.getProperty("user.dir")+ "\\imgs\\itens\\" +novoTipoTextField.getText());  
						if (dir.mkdir()) {  
						    System.out.println("Diretorio criado com sucesso!");  
						} else {  
						    System.out.println("Erro ao criar diretorio!");  
						}  
						Tipoitem tipoitem = new Tipoitem();
						tipoitem.setTipo(novoTipoTextField.getText());
						item.setTipoitem(tipoitem);	
					}
					//////////////
					String[] splits = getUrlTextField().getText().split("\\\\");
					String urlBanco = "imgs\\\\itens\\\\" + item.getTipoitem().getTipo()+"\\\\"+splits[(splits.length)-1];
					String urlCop = System.getProperty("user.dir")+"\\imgs\\itens\\" + item.getTipoitem().getTipo() + "\\" + splits[(splits.length)-1];
					
					item.setImg(urlBanco);
					try {
						FileChannel oriChannel;
						oriChannel = new FileInputStream(getUrlTextField().getText()).getChannel();
						FileChannel destChannel = new FileOutputStream(urlCop).getChannel();
						destChannel.transferFrom(oriChannel, 0, oriChannel.size());
						
						// Fecha channels
						oriChannel.close();
						destChannel.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					for (CadastroProdutoListener listener : cadastroProdutoListeners) {
						listener.salvarButtonAction(item);
					}
				}
				
			});
		}
		return salvarButton;
	}
	
	
	public JButton getCancelarButton() {
		if(cancelarButton == null){
			cancelarButton = new JButton("Cancelar");
			cancelarButton.setBackground(new Color(118, 122, 175, 255));
			cancelarButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					for (CadastroProdutoListener listener : cadastroProdutoListeners) {
						listener.cancelarButtonAction();
					}
				}
				
			});
		}
		return cancelarButton;
	}
	
	private JLabel getNovoTipoLabel() {
		if(novoTipoLabel == null){
			novoTipoLabel = new JLabel("Novo tipo: ");
			novoTipoLabel.setFont(fontLabel);
			novoTipoLabel.setForeground(Color.white);
		}
		return novoTipoLabel;
	}
	
	private JTextField getNovoTipoTextField() {
		if(novoTipoTextField == null){
			novoTipoTextField = new JTextField();
			novoTipoTextField.setToolTipText("Digite o novo tipo a ser cadastrado.");
			novoTipoTextField.setEditable(false);
			novoTipoTextField.setBorder(null);
			novoTipoTextField.setFont(fontTextField);
			novoTipoTextField.setForeground(new Color(34, 36, 59, 255));
			novoTipoTextField.setBackground(new Color(118, 122, 175, 255));
			novoTipoTextField.setText("Clique para criar um novo tipo.");
			novoTipoTextField.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent arg0) {
					getTipoComboBox().setEnabled(false);
					novoTipoTextField.setEditable(true);
					novoTipoTextField.setText("");
				}
			});
		}
		return novoTipoTextField;
	}
	
	public JButton getLimparButton() {
		if(limparButton == null){
			limparButton = new JButton("Limpar tudo");
			limparButton.setBackground(new Color(118, 122, 175, 255));
			limparButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					for (CadastroProdutoListener listener : cadastroProdutoListeners) {
						listener.limparButtonAction();
					}
				}
				
			});
		}
		return limparButton;
	}
		
	private JTextField getUrlTextField() {
		if(urlTextField == null){
			urlTextField = new JTextField();
			urlTextField.setForeground(new Color(34, 36, 59, 255));
			urlTextField.setFont(fontTextField);
			urlTextField.setBorder(null);
			urlTextField.setEditable(false);
			urlTextField.setBackground(new Color(118, 122, 175, 255));
		}
		return urlTextField;
	}
	
	private JButton getProcurarButton() {
		if(procurarButton == null){
			procurarButton = new JButton("Procurar...");
			procurarButton.setBackground(new Color(118, 122, 175, 255));
			procurarButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					getUrlTextField().setText(escolheArquivo());
				}
			});
		}
		return procurarButton;
	}
	
	private JPanel getGeralBG() {
		if(geralBG == null){
			geralBG = new JPanel(new MigLayout("insets 0"));
			geralBG.setBackground(new Color(59, 63, 111, 255));
		}
		return geralBG;
	}
	
	 public static String escolheArquivo(){   
         
         String arquivoWave;      
         JFileChooser arquivo = new JFileChooser();      
         
         arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);    
           
         if(arquivo.showOpenDialog(arquivo) == JFileChooser.APPROVE_OPTION){   
             arquivoWave = arquivo.getSelectedFile().getPath();   
         }          
         else{   
             arquivoWave = "";        
         }   

         return arquivoWave;   
    }   
	
	public void addLitener(CadastroProdutoListener listener){
		this.cadastroProdutoListeners.add(listener);
	}
}
