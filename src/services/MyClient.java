package services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class MyClient {
	
	private final String initialDirectory = "/";
	private final String destinationDirectory = "./";
	
	private String selectedDirectory;
	private String selectedFile = "";
	
	private JTextField usernameTxt;
	private JPasswordField passwordTxt;
	private JTextField serverTxt;
	
	private JList<String> list;
	private DefaultListModel<String> model;
	
	private FTPClient client;
	
	private JLabel msgLbl, errLbl;
	
	public MyClient(JTextField usernameTxt, JPasswordField passwordTxt, JTextField serverTxt, JList<String> list, JLabel msgLbl, JLabel errLbl) {
		this.usernameTxt = usernameTxt;
		this.passwordTxt = passwordTxt;
		this.serverTxt = serverTxt;
		client = new FTPClient();
		this.list = list;
		model = new DefaultListModel<>();
		selectedDirectory = initialDirectory;
		this.msgLbl = msgLbl;
		this.errLbl = errLbl;
	}
	
	/*
	 * Gets connection with ftp server
	 * @return int: 0 if connection is ok and -1 if error occurs
	 */
	public int getConnection() {
		try {
			client.connect(serverTxt.getText());
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
			boolean login = client.login(usernameTxt.getText(), new String(passwordTxt.getPassword()));
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
	
	public int logoutClient() throws IOException {
		if(client.isConnected()) {
			return client.logout() ? 1 : 0;
		}
		return -1;
	}
	
	public void disconnectClient() throws IOException {
		if(client.isConnected()) client.disconnect();
	}
	
	private void inflateList(FTPFile[] ficheros){
		
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
	
	private void getClearAndInflateList() throws Exception{
		FTPFile[] files = getFilesList();
		clearList();
		inflateList(files);
	}
	
	private void changeToParentDir() throws Exception{
		if(client.printWorkingDirectory().equals("/")) return;
		
		client.changeToParentDirectory();
		selectedDirectory = client.printWorkingDirectory();
		client.changeWorkingDirectory(selectedDirectory);
	}
	
	private void changeToSelectedDir() throws Exception{
		client.changeWorkingDirectory(selectedDirectory);
	}
	
	public void changeDirAndInflateList() throws Exception{
		changeToSelectedDir();
		getClearAndInflateList();
	}
	
	public void changeToParentDirAndInflateList() throws Exception{
		changeToParentDir();
		getClearAndInflateList();
	}
	
	public void uploadFile(File file){
		FileInputStream fis = null;
		try{
			client.setFileType(FTP.BINARY_FILE_TYPE);
			String filePath = file.getAbsolutePath();
			String name = file.getName();
			fis = new FileInputStream(filePath);
			client.storeFile(name, fis);
			getClearAndInflateList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void downloadFile(String fileName){
		BufferedOutputStream out = null;
		try {
			String path = client.printWorkingDirectory() + File.separator + fileName;
			String filePath = System.getProperty("user.dir") + File.separator + fileName;
			client.setFileType(FTP.BINARY_FILE_TYPE);
			out = new BufferedOutputStream(new FileOutputStream(filePath));
			boolean success = client.retrieveFile(path, out);
			if(success){
				msgLbl.setText("DESCARGA CORRECTA");
			}else{
				errLbl.setText("ERROR AL DESCARGAR");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	

}
