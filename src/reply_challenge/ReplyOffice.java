package reply_challenge;

import java.util.ArrayList;
import java.util.List;

public class ReplyOffice {
	
	int id; 
	String path;
	List<CustomerHeadquarters> collegamenti;
	int x, y; //posizione nella mappa
	
	public ReplyOffice(int id, int x, int y) {
		collegamenti = new ArrayList<CustomerHeadquarters>();
		this.id = id;
		this.x = x;
		this.y = y;
	}
}
