package reply_challenge;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GestoreFile {

	public List<String> creaLista(String path){
		List<String> allLines = null;
		try {
			allLines = Files.readAllLines(Paths.get(path));
			/*		DEBUG 
			for(String line : allLines) {
				System.out.println(line);
			}*/
		}catch (IOException e) {
			System.out.println(e);
		}
		
		return allLines;
		
	}
}
