package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame implements ActionListener{

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		setTitle("Simple FTP Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{61, 0, 0, 124, 0, 0, 122, 55, 0};
		gbl_contentPane.rowHeights = new int[]{47, 0, 0, 0, 0, 0, -11, 0, 59, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_btnConnect.gridx = 6;
		gbc_btnConnect.gridy = 2;
		contentPane.add(btnConnect, gbc_btnConnect);
		
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
		
		btnExit = new JButton("Salir");
		btnExit.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		gbc_btnExit.gridx = 6;
		gbc_btnExit.gridy = 5;
		contentPane.add(btnExit, gbc_btnExit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		// add action listeners
		btnConnect.addActionListener(this);
		btnExit.addActionListener(this);
		btnDownload.addActionListener(this);
		btnUpload.addActionListener(this);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}