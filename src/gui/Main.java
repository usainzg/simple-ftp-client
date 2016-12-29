package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import classes.Model;
import services.MyClient;
import util.Utilidades;

public class Main extends JFrame implements ActionListener, MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField txtPass;
	private JTextField txtServer;
	private JButton btnConnect, btnUpload, btnDownload, btnExit;
	private JList<String> list;
	private JLabel lblTxt;
	private JLabel lblError;
	private MyClient ftpClient;
	private JButton btnDisconnect;
	private String selectedItem;
	private JButton btnCrearDirectorio;
	private JButton btnEliminarDirectorio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setMinimumSize(new Dimension(1024, 728));
		setMaximumSize(new Dimension(1024, 728));
		setResizable(false);
		setTitle("Simple FTP Client");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 716, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{61, 0, 0, 124, 0, 0, 122, 55, 0};
		gbl_contentPane.rowHeights = new int[]{47, 0, 0, 0, 0, 0, 0, 0, -11, 0, 0, 59, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 1;
		contentPane.add(lblUsuario, gbc_lblUsuario);
		
		txtUser = new JTextField();
		GridBagConstraints gbc_txtUser = new GridBagConstraints();
		gbc_txtUser.insets = new Insets(0, 0, 5, 5);
		gbc_txtUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUser.gridx = 3;
		gbc_txtUser.gridy = 1;
		contentPane.add(txtUser, gbc_txtUser);
		txtUser.setColumns(10);
		
		JLabel lblServidorFtp = new JLabel("Servidor FTP:");
		GridBagConstraints gbc_lblServidorFtp = new GridBagConstraints();
		gbc_lblServidorFtp.insets = new Insets(0, 0, 5, 5);
		gbc_lblServidorFtp.anchor = GridBagConstraints.EAST;
		gbc_lblServidorFtp.gridx = 5;
		gbc_lblServidorFtp.gridy = 1;
		contentPane.add(lblServidorFtp, gbc_lblServidorFtp);
		
		txtServer = new JTextField();
		GridBagConstraints gbc_txtServer = new GridBagConstraints();
		gbc_txtServer.insets = new Insets(0, 0, 5, 5);
		gbc_txtServer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtServer.gridx = 6;
		gbc_txtServer.gridy = 1;
		contentPane.add(txtServer, gbc_txtServer);
		txtServer.setColumns(10);
		
		JLabel lblConstrasea = new JLabel("Contraseña:");
		GridBagConstraints gbc_lblConstrasea = new GridBagConstraints();
		gbc_lblConstrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblConstrasea.gridx = 1;
		gbc_lblConstrasea.gridy = 2;
		contentPane.add(lblConstrasea, gbc_lblConstrasea);
		
		txtPass = new JPasswordField();
		GridBagConstraints gbc_txtPass = new GridBagConstraints();
		gbc_txtPass.insets = new Insets(0, 0, 5, 5);
		gbc_txtPass.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPass.gridx = 3;
		gbc_txtPass.gridy = 2;
		contentPane.add(txtPass, gbc_txtPass);
		
		btnConnect = new JButton("Conectar");
		btnConnect.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnConnect.gridx = 5;
		gbc_btnConnect.gridy = 2;
		contentPane.add(btnConnect, gbc_btnConnect);
		
		btnDisconnect = new JButton("Desconectarse");
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnDisconnect.gridx = 6;
		gbc_btnDisconnect.gridy = 2;
		contentPane.add(btnDisconnect, gbc_btnDisconnect);
		
		btnUpload = new JButton("Subir");
		btnUpload.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_btnUpload = new GridBagConstraints();
		gbc_btnUpload.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpload.gridx = 6;
		gbc_btnUpload.gridy = 3;
		contentPane.add(btnUpload, gbc_btnUpload);
		
		btnDownload = new JButton("Descargar");
		btnDownload.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_btnDownload = new GridBagConstraints();
		gbc_btnDownload.insets = new Insets(0, 0, 5, 5);
		gbc_btnDownload.gridx = 6;
		gbc_btnDownload.gridy = 4;
		contentPane.add(btnDownload, gbc_btnDownload);
		
		btnCrearDirectorio = new JButton("Crear Directorio");
		btnCrearDirectorio.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_btnCrearDirectorio = new GridBagConstraints();
		gbc_btnCrearDirectorio.insets = new Insets(0, 0, 5, 5);
		gbc_btnCrearDirectorio.gridx = 6;
		gbc_btnCrearDirectorio.gridy = 5;
		contentPane.add(btnCrearDirectorio, gbc_btnCrearDirectorio);
		
		btnEliminarDirectorio = new JButton("Eliminar Directorio");
		btnEliminarDirectorio.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_btnEliminarDirectorio = new GridBagConstraints();
		gbc_btnEliminarDirectorio.insets = new Insets(0, 0, 5, 5);
		gbc_btnEliminarDirectorio.gridx = 6;
		gbc_btnEliminarDirectorio.gridy = 6;
		contentPane.add(btnEliminarDirectorio, gbc_btnEliminarDirectorio);
		
		btnExit = new JButton("Salir");
		btnExit.setForeground(Color.BLACK);
		btnExit.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		gbc_btnExit.gridx = 6;
		gbc_btnExit.gridy = 7;
		contentPane.add(btnExit, gbc_btnExit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		lblTxt = new JLabel("-");
		GridBagConstraints gbc_lblTxt = new GridBagConstraints();
		gbc_lblTxt.insets = new Insets(0, 0, 5, 5);
		gbc_lblTxt.gridx = 1;
		gbc_lblTxt.gridy = 9;
		contentPane.add(lblTxt, gbc_lblTxt);
		
		lblError = new JLabel("-");
		lblError.setForeground(Color.RED);
		lblError.setHorizontalTextPosition(SwingConstants.LEFT);
		lblError.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 10;
		contentPane.add(lblError, gbc_lblError);
		
		// add action listeners
		btnConnect.addActionListener(this);
		btnDisconnect.addActionListener(this);
		btnExit.addActionListener(this);
		btnDownload.addActionListener(this);
		btnUpload.addActionListener(this);
		btnCrearDirectorio.addActionListener(this);
		btnEliminarDirectorio.addActionListener(this);
		list.addMouseListener(this);
		
		btnConnect.setEnabled(true);
		btnDownload.setEnabled(false);
		btnUpload.setEnabled(false);
		btnDisconnect.setEnabled(false);
		
		int opt = JOptionPane.showConfirmDialog(this, "¿Desea cargar la direccion del servidor por defecto?", "Servidor por defecto", JOptionPane.YES_NO_OPTION);
		if(opt == JOptionPane.YES_OPTION){
			txtServer.setText(Model.FTP_SERVER_ADDR);
		}
		
		ftpClient = new MyClient(txtUser, txtPass, txtServer, list, lblTxt, lblError);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnConnect){
			
			if(!txtUser.getText().equalsIgnoreCase("") && 
					!new String(txtPass.getPassword()).equalsIgnoreCase("") && !txtServer.getText().equalsIgnoreCase("")){
				
				int out = ftpClient.getConnection();
				
				if(out == 0) out = ftpClient.logUser();
				
				switch(out){
				
				case 1:
					JOptionPane.showMessageDialog(this, "Usuario logeado correctamente.");
					btnConnect.setEnabled(false);
					btnDownload.setEnabled(false);
					btnUpload.setEnabled(true);
					btnDisconnect.setEnabled(true);
					
					try {
						ftpClient.changeDirAndInflateList();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
				case 0:
					JOptionPane.showMessageDialog(this, "Datos logeo incorrectos.");
					break;
				case -1:
					JOptionPane.showMessageDialog(this, "Error al establecer conexion con el servidor");
					break;
				}
				
			}else {
				JOptionPane.showMessageDialog(this, "Debes completar todos los campos para poder logearte...");
			}
			
		}else if(e.getSource() == btnExit){
			if(btnConnect.isEnabled())
				try {
					ftpClient.disconnectClient();
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			else
				JOptionPane.showMessageDialog(this, "Debes cerrar sesion antes de desconectarte!", "Error", JOptionPane.WARNING_MESSAGE);
		}else if(e.getSource() == btnDownload){
			
			String file = Utilidades.sliceSelectedItem(selectedItem);
			ftpClient.downloadFile(file);
			
			
		}else if(e.getSource() == btnUpload){
			
			JFileChooser jFile = new JFileChooser();
			jFile.setDialogTitle("Seleccione el fichero a subir");
			jFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = jFile.showDialog(this, "Subir");
			
			if(result == JFileChooser.APPROVE_OPTION){
				
				File fich = jFile.getSelectedFile();
				ftpClient.uploadFile(fich);
				
			}
			
		}else if(e.getSource() == btnDisconnect){
			
			if(!btnConnect.isEnabled())
				try {
					ftpClient.logoutClient();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
			btnConnect.setEnabled(true);
			btnDownload.setEnabled(false);
			btnUpload.setEnabled(false);
			btnDisconnect.setEnabled(false);
			
			ftpClient.clearList();	
		}else if(e.getSource() == btnCrearDirectorio){
			
			String newDirName = JOptionPane.showInputDialog("Introduce el nombre del nuevo fichero");
			
			if(!newDirName.equals("")) ftpClient.createDir(newDirName);
		}else if(e.getSource() == btnEliminarDirectorio){
			
			if(list.isSelectionEmpty()) {
				ftpClient.deleteDir();
			}else if(!isDir(list.getSelectedValue())){
				ftpClient.deleteFile(list.getSelectedValue());
			}
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource() == list){
			
			if(list.getSelectedIndex() == 0){
				btnEliminarDirectorio.setText("Eliminar Directorio");
				try {
					ftpClient.changeToParentDirAndInflateList();
				} catch (Exception e1) {
				}
			}else {
				
				selectedItem = list.getSelectedValue();
				
				lblTxt.setText(selectedItem);
				
				if(isDir(selectedItem)){
					btnDownload.setEnabled(false);
					btnEliminarDirectorio.setText("Eliminar Directorio");
					String dir = Utilidades.sliceSelectedItem(selectedItem).trim();
					ftpClient.setSelectedDirectory(dir);
					try {
						ftpClient.changeDirAndInflateList();
					} catch (Exception e1) {
					}
					
				}else {
					btnEliminarDirectorio.setText("Eliminar Archivo");
					btnDownload.setEnabled(true);
				}
				
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	
	private boolean isDir(String item){
		return item.startsWith("(DIR)");
	}
}
