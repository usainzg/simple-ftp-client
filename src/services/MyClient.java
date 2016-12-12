package services;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import classes.Model;

public class MyClient {
	
	private final String initialDirectory = "/";
	private final String destinationDirectory = "./";
	
	private String selectedDirectory;
	private String selectedFile = "";
	
	private String userName;
	private String password;
	private String server;
	
	private JList<String> lista;
	private DefaultListModel<String> modelo;
	
	private FTPClient client;
	
	public MyClient(String username, String password, String server, JList<String> lista) {
		this.userName = username;
		this.password = password;
		this.server = server.equalsIgnoreCase("") ? Model.FTP_SERVER_ADDR : server;
		client = new FTPClient();
		this.lista = lista;
		modelo = new DefaultListModel<>();
	}
	
	public int getConnection() {
		
		try {
			client.connect(server);
			boolean login = client.login(userName, password);
			return login ? 1 : 0;
		}catch(IOException e){
			return -1;
		}
	}
	
	public FTPFile[] getFilesList() throws IOException{
		if(client.isConnected()) return client.listFiles();
		else return null;
	}
	
	public void changeDirectory() throws IOException{
		if(client.isConnected()) client.changeWorkingDirectory(initialDirectory);
	}
	
	public int logoutClient() throws IOException {
		if(client.isConnected()) {
			return client.logout() ? 1 : 0;
		}
		return -1;
	}
	
	public void disconnectClient() throws IOException {
		int outputLogout = logoutClient();
		if(outputLogout == 1) client.disconnect();
	}
	
	public void inflateList(FTPFile[] ficheros, String directorioActual){
		
		modelo.addElement(initialDirectory);
		for(int i = 0; i < ficheros.length; i++){
			
			if(!(ficheros[i].getName()).equals(".") && !(ficheros[i].getName()).equals("..")){
				String f = ficheros[i].getName();
				if(ficheros[i].isDirectory()) f = "(DIR) " + f;
				modelo.addElement(f);
			}
		}
		
		lista.setModel(modelo);
	}
	
	

}
