import org.jgrapht.graph.*;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	
	
		int i=0;
		int result=100;
		while (i<1000) {
			JobShopFlex jobShopFlex = new JobShopFlex();
			//jobShopFlex.calculer();
			jobShopFlex.readfile("/home/hxu/Bureau/meta/TextData/Monaldo/Fjsp/Job_Data/Brandimarte_Data/Text/test.fjs");
			jobShopFlex.draw_for_operation_in_order();
			jobShopFlex.choose_machinefor_each_operation();		
			jobShopFlex.dessine_edges_entre_un_job();
	
			jobShopFlex.dessine_edges_conflit();
			
			
			int r= jobShopFlex.calculer();
			if (r<result) {
				result=r;
			}
			System.out.println("result is" +r);
			i++;
			
		}
		
		
		 System.out.println("result is" +result);	
		
		
	
		//jobShopFlex.readfile("/home/hxu/Bureau/meta/1.txt");
		
		
	}

}

