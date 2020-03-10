package reply_challenge;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Calcolatore {
	
	int reminingHQ, reminingR;
	float rapporto;
	Campo campo, campoModificabile;
	List<String> path;
	int[][] distances; /*matrice che contiene le distanze di ogni 
						customerHeadquarters rispetto a tutti gli altri
						la matrice è organizzata in modo che la posizione rispetto alla riga indica
						quale HQ stiamo considerando e la posizione rispetto alla colonna indica la 
						relazione rispetto agli altri HQ*/
	int[][] pesiDiOgniPunto;
	Graph graph;
	ArrayList<Vertex> nodes = new ArrayList<Vertex>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	PathMaker dijkstra;
	ReplyOffice[] replyOfficies;
	Map<String, Terreno> campoMappato;
	int rapportoCorretto;
	
	public Calcolatore(Campo campo, int numberOfHQ, int numberOfR) {
		this.campo = campo;
		this.campoModificabile = campo;
		reminingHQ = numberOfHQ;
		reminingR = numberOfR;
		distances = new int[campo.numberCustomerHeadquarters][campo.numberCustomerHeadquarters];
		rapporto = reminingHQ / reminingR;
		
		pesiDiOgniPunto = new int[campo.width*campo.height][campo.numberCustomerHeadquarters];
		
		creaCampoMappato();
		makeGraph();
		replyOfficies = new ReplyOffice[campo.maxReplyOffice];
		//queste andranno spostate
		dijkstra = new PathMaker(graph);
		distanzeDaPunti(campo.customers);
		//distanzeTraHQ(campo.customers);
		//calcolaDistanze();
		//mostaPosizioniHQ();
		//mostraDistanze();
	}
	
	private void distanzeDaPunti(CustomerHeadquarters[] customers) {
		
		for(int i=0;i<pesiDiOgniPunto.length; i++) {
			Vertex start = new Vertex(campoMappato.get(Integer.toString(i)).id, campoMappato.get(Integer.toString(i)).id);
			for(int j=0;j<pesiDiOgniPunto[0].length; j++) {
				Vertex end = new Vertex(Integer.toString(campo.customers[j].id), Integer.toString(campo.customers[j].id));
				pesiDiOgniPunto[i][j] = calcolaDistanza(start, end);
			}
		}
		
		writeMatrix("res/matrix.txt", pesiDiOgniPunto);
		
		//trovo un punto che minimizzi la distana dagli un numero di hq uguale a rapporto corretto
		int puntoPartenza;
		CustomerHeadquarters[] selezionati = new CustomerHeadquarters[rapportoCorretto];
		int pesoMin = 0;
		int pesoSelezionato = 0;
		CustomerHeadquarters[] minTemp = new CustomerHeadquarters[rapportoCorretto];
		int k = 0;
		
		for(int i=0; i<pesiDiOgniPunto.length; i++) {
			for(int j=0; j<pesiDiOgniPunto[0].length; j++) {
				if(j<rapportoCorretto) {
					selezionati[k] = campo.customers[j];
					k++;
					pesoSelezionato += pesiDiOgniPunto[i][j];
				}else {
					for(int t=0; t<selezionati.length; t++) {
						if(pesiDiOgniPunto[i][selezionati[t].id] > pesiDiOgniPunto[i][j]) {
							pesoSelezionato -= pesiDiOgniPunto[i][selezionati[t].id];
							selezionati[t] = campo.customers[j];
							pesoSelezionato += pesiDiOgniPunto[i][j];
						}
					}
				}
			}
			if(i==0) {
				minTemp = selezionati;
				puntoPartenza = i;
				pesoMin = pesoSelezionato;
			}else {
				if(pesoMin > pesoSelezionato) {
					minTemp = selezionati;
					pesoMin = pesoSelezionato;
					puntoPartenza = i;
				}
			}
		}
		
		//ora so dove devo costruire il primo supply
		
		
	}

	public void solveProblem() {
		int i, j;
		
		//cerco se c'è qualche HQ isolato e nel caso posiziono li uno dei replyOffice
		for(i=0; i<campo.numberCustomerHeadquarters; i++) {
			for(j=0; j<campo.numberCustomerHeadquarters; j++) {
				if(distances[j][i] != 0) {
					continue;
				}
			}
			if(j==campo.numberCustomerHeadquarters) { //ho trovato una colonna di 0 e quindi un hq non è collegatop con nessun altro hq
				posizioneReplyOffice(i); //passo l'id del hq a cui bigona costruire l'hq
				//non cerrà considerato il caso in cui l'hq sia stato messo in mezzo alle montagne ma non mi sembra rilevante
				reminingHQ--;
				reminingR--;
				rapporto = reminingHQ /reminingR;
			}
		}
		//a questo punto avrò sistemato quelli isolati e mi devo basare sul rapporto per capire quanti hq devono essere collegati ad ogni replyOffice
		
		rapportoCorretto = (int) Math.ceil(rapporto);
		int g = reminingR;
		//per ogni replyOffice che posso ancora costruire
		for(i=0; i<g; i++) {
			//per ogni hq che devo collegare al replyOffice
			for(j=0; j<rapportoCorretto; j++) {
				//controllo che l'hq non sia già stato connesso
			}
		}
		
		
		
	}
	
	private void posizioneReplyOffice(int i) {
		// TODO Auto-generated method stub
		
	}

	private void creaCampoMappato() {
		
		campoMappato = new HashMap<String, Terreno>();
		for(int i=0; i<campo.width; i++) {
			for(int j=0; j<campo.height; j++) {
				campoMappato.put(campo.campo[i][j].id, campo.campo[i][j]);
			}
		}
		
	}

	public void distanzeTraHQ(CustomerHeadquarters[] customers) {
		int i, j = 0;
		
		i=0;
		for(CustomerHeadquarters c1 : customers) {
			Vertex start = new Vertex(campo.campo[c1.y][c1.x].id, campo.campo[c1.y][c1.x].id);
			j=0;
			for(CustomerHeadquarters c2 : customers) {
				if(c1.id == c2.id) {
					distances[i][j] = 0;
				}else {
					Vertex end = new Vertex(campo.campo[c2.y][c2.x].id, campo.campo[c2.y][c2.x].id);
					distances[i][j] = calcolaDistanza(start, end);
				}
				
				j++;
			}
			i++;
		}
	}
	
	public void makeGraph() {
		
		
		for(int i=0; i<campo.campo.length; i++) {
			for(int j=0; j<campo.campo[0].length; j++) {
				Vertex v = new Vertex(campo.campo[i][j].id, campo.campo[i][j].id);
				nodes.add(v);
				addNeighbours(v, i, j);
			}
		}
		
		graph = new Graph(nodes, edges);
	}
	
	public int calcolaDistanza(Vertex start, Vertex end) {
		
		
		//System.out.println("inizio esecuzione di dijkstra per il nodo "+ start.id + " al nodo " +end.id);
		dijkstra.executeDijkstra(start);
		//System.out.println("finisco esecuzione di dijkstra per il nodo "+ start.id);
		LinkedList<Vertex> path = dijkstra.getPath(end);
		
		//showPath(path);
				
		return calcolaPesoPath(path); 
	}
	
	private int calcolaPesoPath(LinkedList<Vertex> path) {
		
		int pesoTot = 0;
		if(path == null) {
			System.out.println("path non trovata");
	    	return -1;
		}
       if(!(path.size() >= 0)){
    	   System.out.println("path non trovata");
    	   return -1;
       }
        
        for(int i=0; i<path.size(); i++) {
        	pesoTot += campoMappato.get(path.get(i).id).points;
        }
		
		return pesoTot;
	}

	private void addNeighbours(Vertex source, int i, int j) {
		
		if(i>0) { //non sono nella prima riga del campo quindi ha un vicino anche sopra
			//edge_id --> Edge_idNodo_idEdge
			String edgeId;
			edgeId = "Edge_"+source.id+"_0";
			Vertex dest = new Vertex(campo.campo[i-1][j].id, campo.campo[i-1][j].id);
			if(campo.campo[i][j].points != Integer.MAX_VALUE) {
				Edge e = new Edge(edgeId, source, dest, campo.campo[i][j].points);
				edges.add(e);
			}
			
		}
		
		if(i<campo.height - 1) { //non sono nell'ultima riga del campo quindi ha un vicino anche sotto
			//edge_id --> Edge_idNodo_idEdge
			String edgeId;
			edgeId = "Edge_"+source.id+"_0";
			Vertex dest = new Vertex(campo.campo[i+1][j].id, campo.campo[i+1][j].id);
			if(campo.campo[i][j].points != Integer.MAX_VALUE) {
				Edge e = new Edge(edgeId, source, dest, campo.campo[i][j].points);
				edges.add(e);
			}
		}
		
		if(j > 0) { //non sono nella prima colonna del campo quindi ha un vicino anche a sinistra
			//edge_id --> Edge_idNodo_idEdge
			String edgeId;
			edgeId = "Edge_"+source.id+"_0";
			Vertex dest = new Vertex(campo.campo[i][j-1].id, campo.campo[i][j-1].id);
			if(campo.campo[i][j].points != Integer.MAX_VALUE) {
				Edge e = new Edge(edgeId, source, dest, campo.campo[i][j].points);
				edges.add(e);
			}
		}
		
		if(j < campo.width-1) { //non sono nella prima colonna del campo quindi ha un vicino anche a destra
			//edge_id --> Edge_idNodo_idEdge
			String edgeId;
			edgeId = "Edge_"+source.id+"_0";
			Vertex dest = new Vertex(campo.campo[i][j+1].id, campo.campo[i][j+1].id);
			if(campo.campo[i][j].points != Integer.MAX_VALUE) {
				Edge e = new Edge(edgeId, source, dest, campo.campo[i][j].points);
				edges.add(e);
			}
		}
		
	}
	
	//utili per il debug 
	private void mostaPosizioniHQ() {
		
		for(int i=0; i<campo.numberCustomerHeadquarters; i++) {
			System.out.print("(" + campo.customers[i].x +", " + campo.customers[i].y + ")\t");
		}
		System.out.println();

		for(int i=0; i<campo.numberCustomerHeadquarters; i++) {
			System.out.print(campo.customers[i].id + "\t");
			
		}
		System.out.println();
		
	}

	private void mostraDistanze() {
		
		for(int i=0; i<distances.length; i++) {
			System.out.println();
			for(int j=0; j<distances[0].length; j++) {
				System.out.print(distances[i][j] + "\t");
			}
		}
	}

	private void showPath(LinkedList<Vertex> path) {
		
		if(path == null) {
			System.out.println("nessuna path trovata");
		}else {
			if(!(path.size() >= 0)){
	        	System.out.println("nessuna path trovata");
	        }else {
	        	for (Vertex vertex : path) {
	                System.out.println(vertex);
	            }
	        }
		}
		
	}

	
	void writeMatrix(String filename, int[][] matrix) {
	    try {
	        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

	        for (int i = 0; i < matrix.length; i++) {
	        	for (int j = 0; j < matrix[i].length; j++) {
	        	    bw.write(matrix[i][j] + ((j == matrix[i].length-1) ? "" : ","));
	        	}
	            bw.newLine();
	        }
	        bw.flush();
	    } catch (IOException e) {}
	}
}
