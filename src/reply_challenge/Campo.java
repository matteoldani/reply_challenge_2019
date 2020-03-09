package reply_challenge;

import java.util.List;

public class Campo {
	
	int width, height, numberCustomerHeadquarters, maxReplyOffice;
	Terreno[][] campo;
	List<String> file;
	CustomerHeadquarters[] customers;
	
	public Campo(List<String> file) {
		this.file = file;
		setSizeOfCampo();
		campo = new Terreno[width][height];
		initializeCustumers();
		initializeTerreno();
		setHeadquartesOnCampo();
		//DEBUG
		print_campo();
		
		
	}
	
	private void print_campo() {
		for(int i=0; i<height; i++) {
			for(int j=0; j< width; j++) {
				System.out.print(campo[i][j].tipo);
			}
			System.out.println();
		}
		
		System.out.println("\n\n\n\n Mostro campo con id");
		
		for(int i=0; i<height; i++) {
			for(int j=0; j< width; j++) {
				System.out.print(campo[i][j].id + "\t");
			}
			System.out.println();
		}
		
		
	}

	private void setHeadquartesOnCampo() {
		for(int i=0; i<customers.length; i++) {
			campo[customers[i].y][customers[i].x].hasHQ = true;
			campo[customers[i].y][customers[i].x].customer = customers[i];
			
		}
		
	}

	private void initializeCustumers() {
		customers = new CustomerHeadquarters[numberCustomerHeadquarters];
		for(int i = 0; i<numberCustomerHeadquarters; i++) {
			customers[i] = new CustomerHeadquarters(file.get(i+1), i, numberCustomerHeadquarters);
		}
		
	}

	private void initializeTerreno() {
		int count = 0;
		for(int i = 0; i < height; i++) {
			String[] line = file.get(i+1+numberCustomerHeadquarters).split("");
			for(int j=0; j<width; j++) {
				campo[i][j] = new Terreno(line[j].charAt(0), count);
				count++;
			}
			
		}
		
	}

	private void setSizeOfCampo() {
		
		String firstRow = file.get(0);
		String[] line = firstRow.split(" ");
		//DEBUG
		/*
		for(int i = 0; i<line.length; i++) {
			System.out.println(line[i]);
		}
		*/
		width = Integer.parseInt(line[0]);
		height = Integer.parseInt(line[1]);
		numberCustomerHeadquarters = Integer.parseInt(line[2]);
		maxReplyOffice = Integer.parseInt(line[3]);
	}
	
	
}
