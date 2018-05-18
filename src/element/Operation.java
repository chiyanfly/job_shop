package element;

import java.util.ArrayList;
//pour chaque operation on peux l'excuter sur plusieurs de machines donc 
//ici on utilise une list pour le garder
public class Operation {

	private ArrayList<Integer> nummachinelist;
	private ArrayList<Integer> timelist;

	
	
	public Operation() {
		// TODO Auto-generated constructor stub
		
		nummachinelist = new ArrayList<>();
		timelist= new ArrayList<Integer>();
	}
	public Operation(ArrayList<Integer> n, ArrayList<Integer> t) {
		// TODO Auto-generated constructor stub

		nummachinelist = n;
		timelist = t;
	}
	
	
	public ArrayList<Integer> getNummachinelist() {
		return nummachinelist;
	}
	
	public void setNummachinelist(ArrayList<Integer> nummachinelist) {
		this.nummachinelist = nummachinelist;
	}
	
	public ArrayList<Integer> getTimelist() {
		return timelist;
	}
	
	public void setTimelist(ArrayList<Integer> timelist) {
		this.timelist = timelist;
	}
}
