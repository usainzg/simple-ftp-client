package services;

import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class MyClient {
	
	private final String initialDirectory = "/";
	private final String destinationDirectory = "./";
	
	private String selectedDirectory;
	private String selectedFile = "";
	
	private String userName;
	private String password;
	private String server;
	
	private JList<String> list;
	private DefaultListModel<String> model;
	
	private FTPClient client;
	
	public MyClient(String username, String password, String server, JList<String> list) {
		// set username and password
		this.userName = username;
		this.password = password;
		
		// set server addr
		this.server = server;
		
		// init FTPClient obj
		client = new FTPClient();
		
		// set lista
		this.list = list;
		
		// set new model for the list
		model = new DefaultListModel<>();
		
		// set selectedDirectory equals to initialDirectory (the root directory "/")
		selectedDirectory = initialDirectory;
		
	}
	
	/*
	 * Gets connection with ftp server
	 * @return int: 0 if connection is ok and -1 if error occurs
	 */
	public int getConnection() {
		try {
			client.connect(server);
			return 0;
		}catch(IOException e){
			return -1;
		}
	}
	
	/*
	 * Login of the user
	 * @return int: 1 if is logged, 0 if is not logged and -1 if error occurs
	 */
	public int logUser(){
		try {
			boolean login = client.login(userName, password);
			return login ? 1 : 0;
		} catch (IOException e) {
			return -1;
		}
	}
	
	/*
	 * Get files list of the working directory
	 * @return FTPFile[]: if client is connected returns files list, else returns null
	 */
	public FTPFile[] getFilesList() throws IOException{
		if(client.isConnected()) return client.listFiles();
		else return null;
	}
	
	
	public void changeDirectory() throws IOException{
		if(client.isConnected()) client.changeWorkingDirectory(selectedDirectory);
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
		
		model.addElement(initialDirectory);
		for(int i = 0; i < ficheros.length; i++){
			
			if(!(ficheros[i].getName()).equals(".") && !(ficheros[i].getName()).equals("..")){
				String f = ficheros[i].getName();
				if(ficheros[i].isDirectory()) f = "(DIR) " + f;
				model.addElement(f);
			}
		}
		
		list.setModel(model);
	}
	
	public void setSelectedDirectory(String dir){
		selectedDirectory = dir;
	}
	
	public void clearList(){
		model.removeAllElements();
	}
	
	

}
