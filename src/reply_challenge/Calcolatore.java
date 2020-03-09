package reply_challenge;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Calcolatore {
	
	int reminingHQ, reminingR;
	float rapporto;
	Campo campo, campoModificabile;
	List<String> path;
	Vector2f[][] distances; //matrice che contiene le distanze di ogni customerHeadquarters rispetto a tutti gli altri
	Graph graph;
	ArrayList<Vertex> nodes = new ArrayList<Vertex>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	
	public Calcolatore(Campo campo, int numberOfHQ, int numberOfR) {
		this.campo = campo;
		this.campoModificabile = campo;
		reminingHQ = numberOfHQ;
		reminingR = numberOfR;
		distances = new Vector2f[campo.numberCustomerHeadquarters][campo.numberCustomerHeadquarters];
		rapporto = campo.numberCustomerHeadquarters / campo.maxReplyOffice;
		//calcolaDistanze();
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
		PathMaker dijkstra = new PathMaker(graph);
		System.out.println("inizio esecuzione di dijkstra");
		dijkstra.executeDijkstra(nodes.get(0));
		System.out.println("finisco esecuzione di dijkstra");
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(12));
		
		assertNotNull(path);
        assertTrue(path.size() > 0);

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }
	}
	
	
	private void addNeighbours(Vertex source, int i, int j) {
		
		if(i>0/*Integer.parseInt(source.id) > campo.width - 1*/) { //non sono nella prima riga del campo quindi ha un vicino anche sopra
			//edge_id --> Edge_idNodo_idEdge
			String edgeId;
			edgeId = "Edge_"+source.id+"_0";
			Vertex dest = new Vertex(campo.campo[i-1][j].id, campo.campo[i-1][j].id);
			Edge e = new Edge(edgeId, source, dest, campo.campo[i][j].points);
			edges.add(e);
		}
		
		if(i<campo.height - 1/*Integer.parseInt(source.id) < (campo.width*campo.height) - campo.width*/) { //non sono nell'ultima riga del campo quindi ha un vicino anche sotto
			//edge_id --> Edge_idNodo_idEdge
			String edgeId;
			edgeId = "Edge_"+source.id+"_0";
			Vertex dest = new Vertex(campo.campo[i+1][j].id, campo.campo[i+1][j].id);
			Edge e = new Edge(edgeId, source, dest, campo.campo[i][j].points);
			edges.add(e);
		}
		
		if(j > 0) { //non sono nella prima colonna del campo quindi ha un vicino anche a sinistra
			//edge_id --> Edge_idNodo_idEdge
			String edgeId;
			edgeId = "Edge_"+source.id+"_0";
			Vertex dest = new Vertex(campo.campo[i][j-1].id, campo.campo[i][j-1].id);
			Edge e = new Edge(edgeId, source, dest, campo.campo[i][j].points);
			edges.add(e);
		}
		
		if(j < campo.width-1) { //non sono nella prima colonna del campo quindi ha un vicino anche a destra
			//edge_id --> Edge_idNodo_idEdge
			String edgeId;
			edgeId = "Edge_"+source.id+"_0";
			Vertex dest = new Vertex(campo.campo[i][j+1].id, campo.campo[i][j+1].id);
			Edge e = new Edge(edgeId, source, dest, campo.campo[i][j].points);
			edges.add(e);
		}
		
		
		
	}
	/* 													CODICE PER ORA SENZA SENSO
	 * 
	//riempie la matrice delle distanze
	public void calcolaDistanze() {
		for(int i =0; i< distances.length; i ++) {
			for(int j=0; j<distances[0].length; j++) {
				if(i == j) {
					distances[i][j] = new Vector2f(0,0);
				}else {
					Vector2f v = calcolaDistanzeDateCoordinate(campo.customers[i].x, campo.customers[i].y, campo.customers[j].x, campo.customers[j].y);
					
				}
			}
		}
	}

	//calcola la effettiva distanza considerando solo le x e le y che intercorrono
	private Vector2f calcolaDistanzeDateCoordinate(int x, int y, int x2, int y2) {
		
		int x3 = x2 -x;
		int y3 = y2 -y;
		Vector2f v = new Vector2f(x3, y3);
		
		return v;
		
	}
	
	public void calcolaPercorsoPerCollegareHQSenzaAcqua(CustomerHeadquarters hq1, CustomerHeadquarters hq2) {
		//dal rapporto so quanti hq devo riuscire a collegare insieme quindi dopo averne collegati due cerco il posto migliore per collegarne altri
		//in questo caso trascuro l'acqua perchè molto sconveniente 
		//dalla migliore alla peggiore ipotesi di terreno c'è una differenza di 4 caselle
		
		
	}
	*/
	
}
