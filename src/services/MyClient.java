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
	
	private String selectedDirectory;
	
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
			errLbl.setText("Servidor no disponible, compruebe la conexion");
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
			errLbl.setText("Error al intentar logearse.");
			return -1;
		}
	}
	
	/*
	 * Get files list of the working directory
	 * @return FTPFile[]: if client is connected returns files list, else returns null
	 */
	public FTPFile[] getFilesList() throws IOException{
		try{
			if(client.isConnected()) return client.listFiles();
			else return null;
		}catch(IOException e){
			errLbl.setText("Error al recoger lista de archivos.");
			return null;
		}
		
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
	
	private void changeToParentDir(){
		try{
			if(client.printWorkingDirectory().equals("/")) return;
			
			client.changeToParentDirectory();
			selectedDirectory = client.printWorkingDirectory();
			client.changeWorkingDirectory(selectedDirectory);
		}catch(IOException e){
			errLbl.setText("Conexion perdida con el servidor.");
		}
		
	}
	
	private void changeToSelectedDir(){
		try{
			client.changeWorkingDirectory(selectedDirectory);
		}catch(IOException e){
			errLbl.setText("Conexion perdida con el servidor.");
		}
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
			errLbl.setText("Error al subir " + file.getName() + " al servidor.");
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
				errLbl.setText("DESCARGA NO COMPLETADA");
			}
		} catch (IOException e) {
			errLbl.setText("Conexion perdida con el servidor");
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
	
	public void createDir(String dirName){
		try{
			boolean dirCreated = client.makeDirectory(dirName);
			if(dirCreated) msgLbl.setText("Directorio creado");
			else errLbl.setText("No se ha podido crear el directorio");
			getClearAndInflateList();
		}catch(Exception e){
			errLbl.setText("Conexion con el servidor perdida");
		}
	}
	
	public void deleteDir(){
		try{
			String dirToDelete = client.printWorkingDirectory();
			changeToParentDir();
			boolean dirDeleted = client.removeDirectory(dirToDelete);
			if(dirDeleted) msgLbl.setText("Directorio eliminado");
			else errLbl.setText("No se ha podido borrar el directorio");
			getClearAndInflateList();
		}catch(Exception e){
			errLbl.setText("Conexion con el servidor perdida");
		}
	}
	
	public void deleteFile(String fileName){
		try{
			boolean fileDeleted = client.deleteFile(fileName);
			if(fileDeleted) msgLbl.setText("Fichero eliminado");
			else errLbl.setText("No se ha podido borrar el fichero");
			getClearAndInflateList();
		}catch(Exception e){
			errLbl.setText("Conexion con el servidor perdida");
		}
	}
}
