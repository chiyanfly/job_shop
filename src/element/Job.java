package element;
import java.util.ArrayList;


public class Job {
		private ArrayList<Operation>  operationslist;
		
		
		
		public Job() {
			// TODO Auto-generated constructor stub
			operationslist= new ArrayList<>();
		}
		public Job(ArrayList<Operation> a) {
			// TODO Auto-generated constructor stub
		
		operationslist=a;
		}
		
		
		
		public ArrayList<Operation> getOperationslist() {
			return operationslist;
		}
		
		public void setOperationslist(ArrayList<Operation> operationslist) {
			this.operationslist = operationslist;
		}
		
		@Override
		public String toString() {
		// TODO Auto-generated method stub
	
			int i=1;
			for(Operation operation : operationslist) {
				
				//System.out.println("operation "+i );
				for(int j=0;j<operation.getNummachinelist().size();j++ ) {
					
					//System.out.print("machine "+operation.getNummachinelist().get(j));
					//System.out.println("time "+operation.getTimelist().get(j));
					
				}

				i++;
			}
			
			
			return "";
		}
	
}
