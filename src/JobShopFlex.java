import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;

import javax.sound.midi.VoiceStatus;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.*;

import element.Job;
import element.Operation;
import element.Point;

public class JobShopFlex {

	private DirectedWeightedMultigraph<Point, DefaultWeightedEdge> multiGraph = new DirectedWeightedMultigraph<Point, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);
	Point p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
	Set<DefaultWeightedEdge> setedge;
	Set<Point> setpoint;
	ArrayList<Job> jobArrayList = new ArrayList<Job>();
	ArrayList<String> jobstringlist = new ArrayList<String>();
	ArrayList<ArrayList<Point>> Jobpointlist;
	// this list saves the point which can run on more than one machine
	ArrayList<Point>  pointwithconflitlist =  new ArrayList<>();
	
	
	

	private Job analyse(String s) {
		s = s.replace("\t", " ").replace("  ", " ").trim().replace(" ", "/");
		String[] array = s.split("/");
		// System.out.println(s);
		int[] arrayint = new int[array.length];
		for (int j = 0; j < array.length; j++) {
			arrayint[j] = Integer.parseInt(array[j]);
		}
		// transmettre arraystring to arrayint
		for (int j = 0; j < arrayint.length; j++) {
			// System.out.print(arrayint[j]);
		}
		// System.out.println();
		// i is index
		int i = 1;

		Job job = new Job();

		if (arrayint[0] != 0) {

			while (i < arrayint.length) {

				int machine_num_total = arrayint[i];
				// System.out.print(i +" ");
				// System.out.println(machine_num_total);
				Operation operation = new Operation();

				for (int j = 1; j <= machine_num_total; j++) {

					operation.getNummachinelist().add(arrayint[i + 1]);
					operation.getTimelist().add(arrayint[i + 2]);
					// index plus 2 chaque fois on analyse a pair
					i = i + 2;
				}
				i = i + 1;
				job.getOperationslist().add(operation);

			}
		}
		job.toString();

		return job;

	}

	public void readfile(String path) {

		File f = new File(path);

		try {
			BufferedReader br = new BufferedReader(new FileReader(f));

			String line;
			int linenum = 0;

			while ((line = br.readLine()) != null) {

				if (linenum != 0 && !line.equals("\n")) {

					jobstringlist.add(line);
				}
				// System.out.println(line);

				linenum++;
			}

			// System.out.println(jobstringlist.size());
			jobstringlist.remove(jobstringlist.size() - 1);
			for (String operationstring : jobstringlist) {

				// System.out.println(operationstring);
				jobArrayList.add(analyse(operationstring));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/*
	 * 
	 * 
	 * this fonction is used for put all the points in the resourcelist but the  operation is not well distributed the machinenum and the teh cost
	 * which is done in the fonction choose_machinefor_each_operation   
	 * 
	 * 
	 * */
	public void draw_for_operation_in_order() {

		// draw all the points and the edges in one job
		// the point debut
		// Point p_commence = new Point("Begin", 0, -1, -1);

		int index_of_job = 1;
		int index_of_operation = 1;

		Jobpointlist = new ArrayList<ArrayList<Point>>();
		for (Job job : jobArrayList) {

			ArrayList<Point> pList = new ArrayList<>();

			for (Operation operation : job.getOperationslist()) {

				// we need to choose one machine in the operation.getmachinelist
				// to draw the point
				// System.out.println("random value is "+Math.random()*100);
				/*
				 * int n = (int) (Math.random()*100 % (operation.getNummachinelist().size()));
				 * // which machine and what the cost is int numofmachine =
				 * operation.getNummachinelist().get(n); int time =
				 * operation.getTimelist().get(n);
				 * 
				 */
				String label = Integer.toString(index_of_operation) + "." + Integer.toString(index_of_job);
				// we will decide which machine and the cost later , now we will just use _-1
				// for the machine
				Point point = new Point(label, -1, -1, -1,operation.getNummachinelist());
				pList.add(point);
				index_of_operation++;

			}
			Jobpointlist.add(pList);
			index_of_operation = 1;
			index_of_job++;

		}

		// add the begin point and the end point

	

	}

	
	
	
	/*
	 * 
	 * 
	 * this fonction is used for painting the edges between one job(the order)
	 * 
	 * */
	
	public void dessine_edges_entre_un_job() {

		// put into the point begin and point end
		Point pointbegin = new Point("BEGIN", 0, -1, -1,null);
		Point pointend = new Point("END", 0, -1, -1,null);
		ArrayList<Point> listbegin_end = new ArrayList<>();
		listbegin_end.add(pointbegin);
		listbegin_end.add(pointend);

		multiGraph.addVertex(pointbegin);
		multiGraph.addVertex(pointend);
		Jobpointlist.add(listbegin_end);

		// DefaultWeightedEdge[] edgearray = new DefaultWeightedEdge[1000];

		// int edgeindex = 0;
		// System.out.println(multiGraph.isAllowingLoops());

		for (ArrayList<Point> pointlist : Jobpointlist) {

			int index = 0;
			for (Point p : pointlist) {

				// add the edge between pointbegin and the first point of this job

				if (index == 0) {

					if (!p.getLabel().equals("BEGIN")) {
						DefaultWeightedEdge edge = multiGraph.addEdge(pointbegin, p);
						multiGraph.setEdgeWeight(edge, 0);
					}
					// DefaultWeightedEdge edge = multiGraph.addEdge(pointbegin, p);

				}

				if (index < pointlist.size() - 1 && !p.getLabel().equals("BEGIN")) {
					DefaultWeightedEdge edge = multiGraph.addEdge(p, pointlist.get(index + 1));
					multiGraph.setEdgeWeight(edge, p.getMachinecost());
				}

				// add the edge between pointend and the last point of this job
				if (index == pointlist.size() - 1) {
					if (!p.getLabel().equals("END")) {
						DefaultWeightedEdge edge = multiGraph.addEdge(p, pointend);
						multiGraph.setEdgeWeight(edge, p.getMachinecost());
					}

				}
				// edgeindex++;
				index++;
			}

		}

		setedge = multiGraph.edgeSet();
		setpoint = multiGraph.vertexSet();

		for (Point p : setpoint) {

			// System.out.println(p.getLabel() + " " + p.getValue() + " machine : " +
			// p.getMachine() + " cout :" + p.getMachinecost());

		}

	}

	/*
	 * 
	 * 
	 * this fonction is used for painting the edges between the operation of conflits(those operations on the same machine)
	 * 
	 * */
	
	public void dessine_edges_conflit() {

		// the idea is that Jobpointlist j1 compare with j2-end j2 compare with j3-end......

		int index = 0;
		for (ArrayList<Point> job : Jobpointlist) {

			if (index < Jobpointlist.size() - 1) {
				int i = index;
				for (; i < Jobpointlist.size() - 1; i++) {
					Compare(index, job, Jobpointlist.get(i + 1));
				}
			}
			index++;

		}
// this is for output test
		System.out.println("  ");

		for (DefaultWeightedEdge edge : setedge) {

			// System.out.println(multiGraph.getEdgeSource(edge).getLabel() + " "
			// + multiGraph.getEdgeTarget(edge).getLabel() + " " +
			// multiGraph.getEdgeWeight(edge));

		}

		System.out.println("  ");

	}
/*
 * this fontion si used for painting the conflits between two jobs
 * 
 * */
	private void Compare(int index, ArrayList<Point> job, ArrayList<Point> jobapres) {
		// TODO Auto-generated method stub

		for (Point jobp : job) {

			for (Point jobpa : jobapres) {

				if (jobp.getMachine() == jobpa.getMachine()) {
					// TODO le sens d'un arc
					DefaultWeightedEdge edge = multiGraph.addEdge(jobp, jobpa);
					multiGraph.setEdgeWeight(edge, jobp.getMachinecost());

				}

			}

		}
	}

	/*
	 * this fontion is used for 
	 *  choosing machine for_each_machine ramdomly TODO
	 *  and put the points into the graph
	 * */

	public void choose_machinefor_each_operation() {

		int index_of_job = 1;
		int index_of_operation = 1;

		for (Job job : jobArrayList) {

			ArrayList<Point> pList = new ArrayList<>();

			for (Operation operation : job.getOperationslist()) {

				// we need to choose one machine in the operation.getmachinelist
				// to draw the point
				// System.out.println("random value is "+Math.random()*100);
				int n = (int) (Math.random() * 100 % (operation.getNummachinelist().size()));
				
				
				// which machine and what the cost is
				int numofmachine = operation.getNummachinelist().get(n);
				int time = operation.getTimelist().get(n);

				String label = Integer.toString(index_of_operation) + "." + Integer.toString(index_of_job);
				// we will decide which machine and the cost later , now we will just use _-1
				// for the machine

				if (operation.getNummachinelist().size()>1) {
					pointwithconflitlist.add(new Point(label, -1, numofmachine, time,operation.getNummachinelist()));
				}
				
				for (ArrayList<Point> list1 : Jobpointlist) {

					for (Point p : list1) {

						if (p.getLabel().equals(label)) {
							p.setMachine(numofmachine);
							p.setMachinecost(time);
						}
					}

				}

				index_of_operation++;

			}
			index_of_operation = 1;
			index_of_job++;

		}
		
		
		for (ArrayList<Point> list : Jobpointlist) {

			for (Point p : list) {

				System.out.println(p.getLabel() + " " + p.getValue() + " " + p.getMachine() + " " + p.getMachinecost());
				multiGraph.addVertex(p);
			}

			// System.out.println(" ");

		}

		
	}

	
	
	
	
	
	
	/* 
	 * this fonction is used for find the situation just near the situation initiale 
	 * it will return a  list with the type mutigraph
	 * 
	 * **/
	
	
	
	public  ArrayList<Multigraph<Point, DefaultWeightedEdge>>  find_voisin(){
		
		/*
		 *firstly we need to find the list with  the  operation which can be excuted  on
		* more than one machine and  after this for each operation in the list we will change
		* the machine it will run on  
		 * */
		for(Point point_conflit: pointwithconflitlist) {
			
		
			for(Integer i:point_conflit.getList_possible_machine()) {
				
				
				if (i!=point_conflit.getMachine()) {
					
					
					
					
				}
				
			}
			
			
		}
		
		
		return null;
	}
	
	
	
	
	
	public JobShopFlex() {
		// TODO Auto-generated constructor stub
		/*
		 * p1 = new Point("A", 0); p2 = new Point("o1.1", -1); p3 = new Point("o1.2",
		 * -1); p4 = new Point("o1.3", -1); p5 = new Point("o2.1", -1); p6 = new
		 * Point("o2.2", -1); p7 = new Point("o2.3", -1); p8 = new Point("o3.1", -1); p9
		 * = new Point("o3.2", -1); p10 = new Point("S", -1);
		 * 
		 * multiGraph.addVertex(p1); multiGraph.addVertex(p2); multiGraph.addVertex(p3);
		 * multiGraph.addVertex(p4); multiGraph.addVertex(p5); multiGraph.addVertex(p6);
		 * multiGraph.addVertex(p7); multiGraph.addVertex(p8); multiGraph.addVertex(p9);
		 * multiGraph.addVertex(p10);
		 * 
		 * DefaultWeightedEdge edgeA1 = multiGraph.addEdge(p1, p2); DefaultWeightedEdge
		 * edgeA2 = multiGraph.addEdge(p1, p3); DefaultWeightedEdge edgeA3 =
		 * multiGraph.addEdge(p1, p4);
		 * 
		 * DefaultWeightedEdge edgeI1 = multiGraph.addEdge(p2, p5); DefaultWeightedEdge
		 * edgeI2 = multiGraph.addEdge(p5, p8); DefaultWeightedEdge edgeI3 =
		 * multiGraph.addEdge(p3, p6); DefaultWeightedEdge edgeI4 =
		 * multiGraph.addEdge(p6, p9); DefaultWeightedEdge edgeI5 =
		 * multiGraph.addEdge(p4, p7);
		 * 
		 * DefaultWeightedEdge edgeS1 = multiGraph.addEdge(p8, p10); DefaultWeightedEdge
		 * edgeS2 = multiGraph.addEdge(p9, p10); DefaultWeightedEdge edgeS3 =
		 * multiGraph.addEdge(p7, p10);
		 * 
		 * multiGraph.setEdgeWeight(edgeA1, 0); multiGraph.setEdgeWeight(edgeA2, 0);
		 * multiGraph.setEdgeWeight(edgeA3, 0);
		 * 
		 * multiGraph.setEdgeWeight(edgeI1, 3); multiGraph.setEdgeWeight(edgeI2, 2);
		 * multiGraph.setEdgeWeight(edgeI3, 4); multiGraph.setEdgeWeight(edgeI4, 2);
		 * multiGraph.setEdgeWeight(edgeI5, 2);
		 * 
		 * multiGraph.setEdgeWeight(edgeS1, 5); multiGraph.setEdgeWeight(edgeS2, 2);
		 * multiGraph.setEdgeWeight(edgeS3, 3);
		 * 
		 * // ajouter les edges conflit
		 * 
		 * DefaultWeightedEdge conflitM1 = multiGraph.addEdge(p2, p6);
		 * multiGraph.setEdgeWeight(conflitM1, 3);
		 * 
		 * DefaultWeightedEdge conflitM21 = multiGraph.addEdge(p3, p5);
		 * multiGraph.setEdgeWeight(conflitM21, 4);
		 * 
		 * DefaultWeightedEdge conflitM22 = multiGraph.addEdge(p5, p7);
		 * multiGraph.setEdgeWeight(conflitM22, 2);
		 * 
		 * DefaultWeightedEdge conflitM31 = multiGraph.addEdge(p4, p9);
		 * multiGraph.setEdgeWeight(conflitM31, 2);
		 * 
		 * DefaultWeightedEdge conflitM32 = multiGraph.addEdge(p9, p8);
		 * multiGraph.setEdgeWeight(conflitM32, 2);
		 * 
		 * // System.out.println(multiGraph.getEdgeWeight(edge1));
		 * 
		 * CycleDetector<Point, DefaultWeightedEdge> cycleDetector = new
		 * CycleDetector<Point, DefaultWeightedEdge>( multiGraph);
		 * 
		 * System.out.println(cycleDetector.detectCycles());
		 */

	}

	// trouver les points précedents

	public ArrayList<Point> pointsprecedents(Point porigi) {

		ArrayList<Point> result = new ArrayList<>();

		for (DefaultWeightedEdge d : setedge) {

			if (porigi.getLabel().equals(multiGraph.getEdgeTarget(d).getLabel())) {

				result.add(multiGraph.getEdgeSource(d));

			}

		}

		return result;

	}

	public int iteration(Point f) {

		ArrayList<Point> arrayList = pointsprecedents(f);
		ArrayList<Integer> tempvaleur = new ArrayList<>();

		for (Point point : arrayList) {

			if (point.getValue() == -1) {

				iteration(point);

			}
		}
		int j = -1;

		for (Point point : arrayList) {

			DefaultWeightedEdge d = null;

			for (DefaultWeightedEdge di : setedge) {
				// System.out.println(f.getLabel() + point.getLabel());
				if (multiGraph.getEdgeSource(di).getLabel().equals(point.getLabel())
						&& multiGraph.getEdgeTarget(di).getLabel().equals(f.getLabel())) {

					d = di;
				}
			}

			int length = (int) (point.getValue() + multiGraph.getEdgeWeight(d));
			// System.out.println(point.getLabel()+ " "+point.getValue());
			if (length > j) {
				j = length;
			}

		}

		f.setValue(j);

		if (arrayList.size() == 0) {

			f.setValue(0);

		}
		return j;

	}

	public int calculer() {

		setedge = multiGraph.edgeSet();
		setpoint = multiGraph.vertexSet();
		int result = 0;
		for (Point ptocalculer : setpoint) {

			iteration(ptocalculer);

		}

		for (Point p : setpoint) {

			// System.out.println(p.getLabel() + ":" + p.getValue());
			if (p.getLabel().equals("END")) {
				result = p.getValue();
			}
		}

		return result;

	}

}
