package services;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

import classes.Model;

public class MyClient {
	
	private final String initialDirectory = "/";
	private final String destinationDirectory = "./";
	
	private String selectedDirectory;
	private String selectedFile = "";
	
	private String userName;
	private String password;
	
	private FTPClient client;
	
	public MyClient(String username, String password) {
		this.userName = username;
		this.password = password;
		client = new FTPClient();
	}
	
	public int getConnection() {
		try {
			
			client.connect(Model.FTP_SERVER_ACCESS_ADDR);
			boolean login = client.login(userName, password);
			
			return login ? 1 : 0;
		}catch(IOException e){
			return -1;
		}
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
	
	

}
