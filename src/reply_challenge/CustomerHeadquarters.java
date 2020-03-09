package reply_challenge;

public class CustomerHeadquarters {
	int x, y, reward, id, numberOfCustomers;
	boolean connected = false;
	boolean[] customerConnections;
	
	
	public CustomerHeadquarters(String custumer, int id, int numberOfCustomers) {
		String[] line = custumer.split(" ");
		this.x = Integer.parseInt(line[0]);
		this.y = Integer.parseInt(line[1]);
		this.reward = Integer.parseInt(line[2]);
		this.id = id;
		this.numberOfCustomers = numberOfCustomers;
		initializeArrayOfConnections();
	}


	private void initializeArrayOfConnections() {
		customerConnections = new boolean[numberOfCustomers];
		for(int i =0; i< numberOfCustomers; i++) {
			customerConnections[i] = false;
		}
		customerConnections[id] = true;
	}
}
