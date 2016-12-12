package util;

public class Utilidades {
	
	public static String sliceSelectedItem(String sel){
		if(sel.substring(0, 5).equals("(DIR)")){
			return sel.substring(5);
		}else {
			return sel;
		}
	}

}
