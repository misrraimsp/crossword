import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IO {
	String readpath;
	
	
	public IO(String path){
		readpath = path;
	}
	
	public ArrayList<String> loadLemmas() {
		ArrayList<String> words = new ArrayList<String>();
		try {
	    	BufferedReader input =new BufferedReader(new FileReader(readpath));
	    	String line = new String();
	    	while((line = input.readLine())!= null) words.add(line);
	    	input.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return words;
	}
}