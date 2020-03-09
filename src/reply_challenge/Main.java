package reply_challenge;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
	
		GestoreFile gFile = new GestoreFile();
		Scanner s = new Scanner(System.in);
		List<String> allLinesFile;
		Campo campo;
		Calcolatore calc;
				
		System.out.println("inserire la path del file da leggere: ");
		String path = s.next();
		allLinesFile = gFile.creaLista(path);
		campo = new Campo(allLinesFile);
		
		calc = new Calcolatore(campo, campo.numberCustomerHeadquarters, campo.maxReplyOffice);
		calc.makeGraph();
		
		

	}

}
