
import java.util.*;

public class TrAndNrRelation {

	private int width_x;
	private int length_y;
	private SensorNode[] Node;
	private int N;
	private int Tr;
	private Scanner input;
	private boolean[][] hasEdge;
	
	public TrAndNrRelation (int N, int Tr) {
		this.width_x = 50;
		this.length_y = 50;
		this.N = N;
	
		this.Tr = Tr;
		this.Node = new SensorNode[N];
		this.hasEdge = null;
		
	}
	
	public void connectingRelation () {
		
		 for (int i = 0; i < N; i++) {
	    	 System.out.println("Create ID for the new born node : ");
	    	 String ID = input.nextLine();
	    	 Node[i] = new SensorNode();
	    	 double x, y;
	    	 Node[i].setxPosition( width_x);
	    	 Node[i].setyPosition(length_y);
	    	 System.out.println("Node" + ID + " is created successfully! ");
	     }
		 
		 /* to create adjacency matrix */
	    	double distanceOfTwoNodes = 0; 
	    	double a = 0;
	    	for (int i = 0; i < Node.length; i++) {
	    		
	    		for (int j = 0; j < Node.length; j++) {
	    			if ( i != j) {
	    				a = (Node[i].getxPosition() - Node[j].getxPosition()) * (Node[i].getxPosition() - Node[j].getxPosition()) + 
	    						(Node[i].getyPosition() - Node[j].getyPosition()) * (Node[i].getyPosition() - Node[j].getyPosition());
	    				
	    				distanceOfTwoNodes = Math.pow(a, 0.5);
	    				
	    				if (distanceOfTwoNodes <= Tr) {
	    					 hasEdge[i][j] = true;
	    				}
	    				else {
	    					hasEdge[i][j] = false;
	    				}
	    			} else {
	    				hasEdge[i][j] = false;
	    			}
	    		}
	    	}/* to create adjacency matrix */
	    	
	    	/* BFS traverse from Node[0] */
	    	Queue<SensorNode> q = new LinkedList<SensorNode> ();
	    	boolean[] visited = new boolean[N];
	    	SensorNode[] parent = new SensorNode[N];
	    	
	    	for ( int i = 0; i < N; i++) { 
	    		visited[i] = false;
	    	}
	    	/*add the first Node in the queue, and mark it as visited */
	    	q.add(Node[0]);
	    	visited[0] = true;
	    	parent[0] = null;
	    	
	    	int i, element = -1;   SensorNode n;
	    	while ( !q.isEmpty()) {
	    		
	    		n = q.remove();
	    		for (int index = 0; index < N; index++) {
	    			 if (n == Node[index]) {
	    			     element = index;
	    			 }
	    		}
	    		
	    		i = element;
	    		//System.out.print(i + "\t");
	    		
	    		while (i <= N) {
	    			
	    			if (hasEdge[element][i] == true && visited[i] == false) {
	    				q.add(Node[i]);
	    				visited[i] = true;
	    				parent[i] = Node[element];
	    			}
	    			
	    			i++;
	    		}
	    	}
	    	
         for (int index = 0; index < N; index++) {
         	
         	if (visited[index] == false) {
         		System.out.println("Sensor network" + "(" + Tr + ","+ N +")" + " is not connected!");
         		break;
         	}	
         	
	     }
         
         System.out.println("Sensor network" + "(" + Tr + ","+ N +")" + " is connected!");
	}
}
