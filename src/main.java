import org.jgrapht.graph.*;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	
		JobShopFlex jobShopFlex = new JobShopFlex();
		//jobShopFlex.calculer();
		jobShopFlex.readfile("/home/hxu/Bureau/meta/TextData/Monaldo/Fjsp/Job_Data/Brandimarte_Data/Text/Mk01.fjs");
		jobShopFlex.draw_for_operation_in_order();
		jobShopFlex.dessine_edges_entre_un_job();
		jobShopFlex.dessine_edges_conflit();
		jobShopFlex.calculer();
		//jobShopFlex.readfile("/home/hxu/Bureau/meta/1.txt");
		
		
	}

}

