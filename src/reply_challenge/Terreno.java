package reply_challenge;

public class Terreno {
	char tipo;
	int points;
	String name;
	String id;
	
	boolean hasHQ = false;
	CustomerHeadquarters customer = null;
	
	public Terreno(char tipo, int id) {
		this.id = Integer.toString(id);
		this.tipo = tipo;
		switch (tipo) {
			case '#': 
				points = 0;
				name = "mountains";
				break;
			case '~':
				points = 800;
				name = "water";
				break;
			case '*':
				points = 200;
				name = "traffic_jam";
				break;
			case '+': 
				points = 150;
				name = "dirt";
				break;
			case 'X': 
				points = 120;
				name = "railway_level_crossing";
				break;
			case '_':
				points = 100;
				name = "standard_terrain";
				break;
			case 'H':
				points = 70;
				name = "highway";
				break;
			case 'T': 
				points = 50;
				name = "railway";
				break;
			default:
				System.out.println("terreno non esistente, point settati a -1 e nome settato a terreno_non_esistente");
				points = -1;
				name = "terreno_non_esistente";
		}
				
	}
}
