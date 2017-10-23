

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;


public class GsensorNetwork {
	private int width_x;
	private int length_y;
	private SensorNode[] Node;   // all sensor nodes list
	private int numOfNode;       // number of Nodes including DG 
	private int numOfDG;         // number of DG 
	private int numOfDataItem;   // number of data item for each DG
	private Scanner input;
	private LinkedList<SensorNode> [] adj; // adjacency list for each node
	private List<SensorNode>  path;
	private boolean[] visited;             // store visiting result of each node
	private Queue<SensorNode> q = new LinkedList<SensorNode>(); // queue for BFS
    private int[] sequence; // to store the index in visited order
  
	
	public GsensorNetwork () {
	
		input = new Scanner (System.in);
		
		System.out.println("Enter  width x : ");
	    this.width_x = input.nextInt();
	     
	    System.out.println("Enter  length y : ");
	    this.length_y = input.nextInt();
	     
	    System.out.println(width_x + " * " + length_y + " plot is created successfully! ");
	    System.out.println();
	    System.out.println();
		
		System.out.println("Enter  number of sensor nodes N : ");
	    this.numOfNode = input.nextInt();
	    
	    System.out.println("Enter number of DGs p : ");
	    this.numOfDG = input.nextInt();	
	    
	    System.out.println("Enter number of data item for each DG q : ");
	    this.numOfDataItem = input.nextInt();

	    
	    this.Node = new SensorNode[numOfNode];
        
	    adj = new LinkedList[numOfNode];
	    
	    visited = new boolean[numOfNode];
	    
	    sequence = new int[numOfNode];
	    
	    for (int i = 0; i < numOfNode; i++) {
			visited[i] = false;
			sequence[i] = -1;
			adj[i] = new LinkedList();
		}  
	}
	
	/*constructor for testing*/
	public GsensorNetwork (int x, int y) {

	    this.width_x = x;
	     
	  
	    this.length_y = y;
	     
	    System.out.println(width_x + " * " + length_y + " plot is created successfully! ");
	    System.out.println();
	    System.out.println();

	    this.numOfNode = 10;

	    this.numOfDG = 1;	

	    this.numOfDataItem = 4;

	    this.Node = new SensorNode[numOfNode];
        
	    adj = new LinkedList[numOfNode];
	    
	    visited = new boolean[numOfNode];
	    
	    sequence = new int[numOfNode];
	    
	    for (int i = 0; i < numOfNode; i++) {
			visited[i] = false;
			sequence[i] = -1;
			adj[i] = new LinkedList();
		}  
	}
	
    public void buildNetwork (){
    	
    	 //SensorNode n = new SensorNode();
    	SensorNode n = new SensorNode(); //only for testing
   	    System.out.println("n transmission range : " + n.getTransmissionRange());
   	  /*create non-DG nodes*/
	    for (int i = 0; i < numOfNode - numOfDG; i++) {
	    	Node[i] = new SensorNode(n.getInitialEnergy(), n.getTransmissionRange());
	    	Node[i].setID(i);
	    	Node[i].setStorageCapacity(n.getStorageCapacity());
	    	Node[i].setxPosition(width_x);
	    	Node[i].setxPosition(length_y);
	    }
	    
	    /*print out Non - DG Nodes*/
	    for (int i = 0; i < numOfNode - numOfDG; i++) {
	    	 System.out.println("Node " + i + " transmission range : " + Node[i].getTransmissionRange());
	    	 System.out.println("Node(non-DG) " + Node[i].getID() + " is created successfully! ");
	    	 System.out.println();
	    	 System.out.println();
	    }
	    
	    
	    /*create DG nodes*/
	   
	    
	    for (int i = numOfNode - numOfDG; i < numOfNode; i ++) {
	    	Node[i] = new SensorNode(n.getInitialEnergy(), n.getTransmissionRange());
	    	Node[i].setID(i);
	    	Node[i].setDG(true);
	    	Node[i].setNumOfDataItem(numOfDataItem);
	    	Node[i].setxPosition(width_x);
	    	Node[i].setxPosition(length_y);
	    }
	    
	    /*print out DG Nodes*/
	    for (int i = numOfNode - numOfDG; i < numOfNode; i ++) {
	    	 System.out.println("Node " + i + " transmission range : " + Node[i].getTransmissionRange());
	    	 System.out.println("Node (DG) " + Node[i].getID() + " is created successfully! ");
	    	 System.out.println();
	    	 System.out.println();
	    }	
	    
    	
    }

		  
	public void creatingAdjacencyList() {

		  /*create adjacency list for each sensor node*/
		        System.out.println("---------create adjacency list for each sensor node---------- ");
				for (int i = 0; i < numOfNode; i++) {
					
					System.out.println("Node " + i + " adjacency list : ");
					//System.out.println("Node " + i + " Transmission Range: " + Node[i].getTransmissionRange());
					for (int j = 0; j < numOfNode; j++) {
						Edge e = new Edge(Node[i], Node[j]);
						
						//System.out.print("edge " + i + j + " Length :" + e.getLength() + "\t");
						//System.out.println("edge " + i + j + " isExist :" + e.isExist());
						if (e.isExist()) {
							
							if (i != j) {
								
								adj[i].add(Node[j]); /* add node to adjacency list */
								System.out.print( j + " -> ");
							}
						}
					}
					System.out.println();
					System.out.println();
				}
				
			   for (int i = 0; i < adj.length; i++) {
				   System.out.print("adj[" + i +"] : ");
				   for (int j = 0; j < adj[i].size(); j++) {
					   System.out.print(adj[i].get(j).getID() + " ");  
				   }
				   System.out.println(); 
			   }
			   
			   System.out.println(); 
			   System.out.println(); 
	}
		  
		  
	
	public boolean isConnected () {

		    	/* BFS traverse from Node[0] */

		    	/*add the first Node in the queue, and mark it as visited */
		    	q.add(Node[0]);
		    	visited[0] = true;
		    
		    	
		        int index= 0; SensorNode s; 
		    	while ( !q.isEmpty()) { 
		    	    /*Dequeue a vertex from queue and print it*/
		    		s = q.remove();
		    	    System.out.println("pop Node id : " + s.getID() + " "); 
		    	    
		    	    for (int i = 0; i < numOfNode; i++) {
		    	    	if (s.equals(Node[i])) {
		    	    		index = i;
		    	    	}
		    	    }
		    		System.out.print("index : " + index + " (");
		    		
		    		for (int i = 0; i < adj[index].size(); i++) {
		    			System.out.print(adj[index].get(i).getID() + " ");
		    		}
		    		System.out.print(")");
		    		System.out.println();
		    		
		    	    /* Get all adjacent vertices of the dequeued vertex s
		             If a adjacent has not been visited, then mark it
		             visited and enqueue it  */
		    	    ListIterator<SensorNode> it = adj[index].listIterator();
		    	    
		    	    while (it.hasNext())  {
		                SensorNode n = it.next();
		                System.out.println("it.next() id :" + n.getID());
		                if (!visited[n.getID()])
		                {
		                    visited[n.getID()] = true;
		                    q.add(n);
		                    
		                }
		            }
		    	   System.out.println(); 
		    	}
		    	
                for (int i = 0; i < numOfNode; i++) {
                	System.out.println("visited[" + i +"] = " + visited[i]);
                	
                	if (visited[i] == false) {
                		System.out.println("This sensor network is not connected!");
                		System.out.println();
                		return false;
                		
                	}	
		    	}
                
                System.out.println("This sensor network is connected!");
                System.out.println();  
                return true;
     }
	
     
	
	/* Output 3: If connected, ask for input of two nodes u and v, output the energy
	 *  consumption of sending a data from u to v using shortest path between them.
	 * */

    /*Dijkstra Shortest Path algorithm to get shortest path from source to target*/
	public List<SensorNode> computeshortestPaths(int sPara, int tPara) {
		
		/*user input source and target Node*/
//		input = new Scanner (System.in);
//		System.out.println("Input the source Node id: ");
//		int s = input.nextInt();
		//System.out.println("Node[" + s + "]" + "id :" + Node[s].getID());

	

//		System.out.println("Input the target Node id: ");
//		int t = input.nextInt();
		
		SensorNode source = Node[sPara];    // get the source node
		SensorNode target = Node[tPara];    // get the target node
		
        source.minDistance = 0.;
        
        PriorityQueue<SensorNode> vertexQueue = new PriorityQueue<SensorNode>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
        	
            SensorNode u = vertexQueue.poll();
            int index = -1 ;
            /*get index of u in Node[] list*/
            for (int i = 0; i < numOfNode; i++) {
            	if (Node[i].equals(u)) {
            		index = i;
            	}
            }

            // Visit each edge exiting u
            for (int i = 0; i < adj[index].size(); i++) {
            	
                SensorNode v = adj[index].get(i);
                System.out.println("v id : " + v.getID());
                
                Edge e = new Edge (Node[index],v);
                System.out.println("e length : " + e.getLength());
                
                int weight = e.getLength();
                int distanceThroughU = (int) (u.minDistance + weight);
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
 
        List<SensorNode> path = new ArrayList<SensorNode>();
        for (SensorNode v = target; v != null; v = v.previous)
            path.add(v);

        Collections.reverse(path);
        return path; 
      }
	
	
	public double computingEnergyConsumption(int start, int end)  // parameters start node and end node, and the data item a
	{
		     int  sourceID=start;
		     int  targetID=end;
		     int index=0,p;
		     int engeryConsumption = 0;
	         double remainEnergy=0;
	         
		    path = computeshortestPaths(sourceID, targetID);
		    System.out.print("Shortest Path is : ");
		    for (int i = 0; i < path.size(); i++) {
		    	System.out.print( path.get(i).getID() + "->");
		    }
		    System.out.println();
		    
		    
		    double[] Et = new double[path.size()-1];/////????????????????????????????????
		    Edge[] e = new Edge[path.size() - 1];////???????////
		    
		   
		 
		    	for (int i = 0; i < Et.length; i++) 
		   {
		    	e[i] = new Edge(path.get(i),path.get(i + 1));
		    	System.out.println("e[i] length: " + e[i].getLength());
		    	if(i==0)
		    	{
		    		Et[i]=2 * 100  * 4;
		    		
		    		
		    		// to get the index of visiting Node
		    		 for (p = 0; p < Node.length; p++) 
		             	if (Node[p].getID()==(path.get(i).getID())) 
		             	{
		             		index = p;
		             	}
		    		
		    	//	path.get(i).setInitialEnergy((path.get(i).getInitialEnergy()-Et[i]));
		    		Node[index].setInitialEnergy(Node[index].getInitialEnergy()-Et[i]);
		    		 System.out.println("******INDEX is: "+index);
		    		 System.out.println("******Energy is: "+Node[index].getInitialEnergy());
		    	}
		    	
		    	else if(i==Et.length-1)
		    		{
		    		     Et[i]= 2 * 100 * 4 ;
		    		
		    		     for (p = 0; p < numOfNode; p++) 
		    		    	 if (Node[p].getID()==(path.get(i).getID())) 
				             	{
				             		index = p;
				             	}
				    		
				    	//	path.get(i).setInitialEnergy((path.get(i).getInitialEnergy()-Et[i]));
				    		Node[index].setInitialEnergy(Node[index].getInitialEnergy()-Et[i]);
				    		 System.out.println("******INDEX is: "+index); 
				    		 System.out.println("******Energy is: "+Node[index].getInitialEnergy());
				    		 
		    	
		    		}
		    	else
		         	{
		    				Et[i] = 2 * 100 * 4 + 100  * 4 * e[i].getLength() * e[i].getLength();
		    			
		    		
		    			 for (p = 0; p < numOfNode; p++) 
		    				 if (Node[p].getID()==(path.get(i).getID())) 
				             	{
				             		index = p;
				             	}
				    		
				    	//	path.get(i).setInitialEnergy((path.get(i).getInitialEnergy()-Et[i]));
				    		Node[index].setInitialEnergy(Node[index].getInitialEnergy()-Et[i]);
				    		 System.out.println("******INDEX is: "+index);
				    		 System.out.println("******Energy is: "+Node[index].getInitialEnergy());

		         	
		               
		              }
		    	
		        	engeryConsumption += Et[i];
		        	
		    	} 
	   
	//	System.out.println("###### engery Consumption: "+engeryConsumption+" ######");
		return engeryConsumption;
	}
	
	///////////////////////////////////////////
	/*Node Based Algorithm---2*/
	
	public void NodeBasedAlgo()
	{
		
		SensorNode max= Node[0];  // assuming the first node has the highest energy
		SensorNode generator=Node[0];  // assuming the first node has the highest energy
		double energy=0;
		   // to offload one data item once and use while loop to get it done
		double remainEnergy=0;
		 for(int i=0;i<Node.length;i++)
		    {
		    	remainEnergy+=Node[i].getInitialEnergy();
		    }
		
		
	    while(numOfDataItem>0)
	    {
	    	for(int i=0;i<Node.length;i++)   // go over the array and find out the generator Node
	    	{
			if(Node[i].getStorageCapacity()==0)
				generator=Node[i];
			
	    	}
		
	    	for(int i=0;i<Node.length;i++)   // go over the array and find out the real Max engery Node
	     	{
			if(Node[i].getInitialEnergy()>max.getInitialEnergy() && max.getStorageCapacity()>0)
				max=Node[i];
			
	    	}
	    	System.out.println("**** the max engery NODE is: "+max+"****");
	    	// get one of the a data items and off load it to the max Node
	    	int dateCapNo=max.getStorageCapacity();
	    	if(max.getStorageCapacity()>0)
	    	{
	    		max.setStorageCapacity(dateCapNo-1);
	    		max.setNumOfDataItem(max.getNumOfDataItem()+1);
	    	}
	    	System.out.println("&&&&&&&&&&==> "+max.getStorageCapacity());
	    	// Update the energy levels of all the nodes involved
	    	// get the shortest path from the Generator to the destination
	         energy+=computingEnergyConsumption(generator.getID(),max.getID());
	    	 System.out.println("the engery consumed: "+energy);
	    	
	    	numOfDataItem--;
		
	    }
		
	     remainEnergy-=energy;
	    
    	 System.out.println("****** engery Resilience level: "+remainEnergy+" ******");
		
	}
	
	 /*data based Algorithm---3*/
		public void DataBasedAlgo()
		{
			double energy=0;
			SensorNode generator=Node[0];
			double remainEnergy=0;
			 for(int i=0;i<Node.length;i++)
			    {
			    	remainEnergy+=Node[i].getInitialEnergy();
			    }
			
			for(int i=0;i<Node.length;i++)   // go over the array and find out the generator Node
	    	{
			if(Node[i].getStorageCapacity()==0)
				generator=Node[i];
			
	    	}
			PriorityQueue<SensorNode> myQueue= new PriorityQueue<SensorNode>();	
			for(int i=0;i<Node.length;i++)   // go over the Node List;
			{
				if(Node[i].getStorageCapacity()>0)   // check the capacity, if it is not zero, add to the priority queue
                    myQueue.add(Node[i]);
				
			
			}
			
			System.out.println("The priority Queue"+myQueue.toString());
			System.out.println();
			System.out.println("***************************************");
			//numOfDataItem's iteration, 
			for(int i=0;i<numOfDataItem;i++)
			{   int newCapacity=myQueue.peek().getStorageCapacity();
			    SensorNode aNode;
				
			    aNode=myQueue.poll();   // get the element with the highest energy
			    aNode.setStorageCapacity(newCapacity-1);   // capacity--
			    aNode.setNumOfDataItem(aNode.getNumOfDataItem()+1);  // number of data Item++
			    energy+=computingEnergyConsumption(generator.getID(), aNode.getID());    // get total energy consumed
			    System.out.println("target IDDDDDDDDDDDDDDDDDDDDDDDD: "+aNode.getID());
			    if(aNode.getStorageCapacity()>0 && aNode.getInitialEnergy()>0)
			    	myQueue.add(aNode);
		    	System.out.println("====>the engery consumed: "+energy);
			}
				
			  remainEnergy-=energy;
			    
		   	 System.out.println("****** engery Resilience level: "+remainEnergy+" ******");	
				
		}
	
	
	
	 /*Network based Algorithm*/
	public void NetworkBasedAlgo()
	{
		double energy=0;
		int count=numOfDataItem;
		SensorNode generator=Node[0];
		for(int i=0;i<Node.length;i++)   // go over the array and find out the generator Node
    	{
		if(Node[i].getStorageCapacity()==0)
			generator=Node[i];
		
    	}
		PriorityQueue<SensorNode> myQueue= new PriorityQueue<SensorNode>();	
		for(int i=0;i<Node.length;i++)   // go over the Node List;
		{
			if(Node[i].getStorageCapacity()>0)   // check the capacity, if it is not zero, add to the priority queue
                myQueue.add(Node[i]);
			
		
		}
		
		System.out.println("The priority Queue"+myQueue.toString());
		System.out.println();
		System.out.println("***************************************");
		//numOfDataItem's iteration, 
		while(count>0)
		{   int newCapacity=myQueue.peek().getStorageCapacity();
		    int newDataItemNo=myQueue.peek().getNumOfDataItem();
		    SensorNode aNode;
			
		    aNode=myQueue.poll();   // get the element with the highest energy
		    aNode.setStorageCapacity(0);   // to off load the data to available space 
		    aNode.setNumOfDataItem(newCapacity);  // number of occupied data item increase 
		    count-=newCapacity-newDataItemNo;  //  deduct the offloaded data item number
		    energy+=computingEnergyConsumption(generator.getID(), aNode.getID());    // get total energy consumed
//		    System.out.println("target NNNNNNNNNNNN: "+aNode.getID());
//	    	System.out.println("====>the engery consumed: "+energy);
		    
		}
			
		double remainEnergy=0;
		 for(int i=0;i<Node.length;i++)
		    {
		    	remainEnergy+=Node[i].getInitialEnergy();
		    }	
		 remainEnergy-=energy;
		    
	   	 System.out.println("****** engery Resilience level: "+remainEnergy+" ******");		
			
			
	}
		
		
		 /*Benifit based Algorithm*/
		public void BenefitBasedAlgo()
		{
			double energy=0;
			int count=numOfDataItem;
			SensorNode generator=Node[0],betaPeek;
			for(int i=0;i<Node.length;i++)   // go over the array and find out the generator Node
	    	{
			if(Node[i].getStorageCapacity()==0)
				generator=Node[i];
			
	    	}
			PriorityQueue<SensorNode> betaQueue= new PriorityQueue<SensorNode>();	
			
			for(int i=0;i<numOfDataItem;i++)   // go over the Node items;
			{
				for(int k=0;k<Node.length;k++)   // go over the Node List;
				{
					if(Node[k].getStorageCapacity()>0)   // check the capacity, if it is not zero, add to the priority queue
						betaQueue.add(Node[k]);
					
				
				}
				int peekCap= betaQueue.peek().getStorageCapacity();
				int peekDI= betaQueue.peek().getNumOfDataItem();
				    betaPeek=betaQueue.poll();
				betaQueue.peek().setStorageCapacity(peekCap-1);
				betaQueue.peek().setNumOfDataItem(peekDI-1);
				
		       energy+=computingEnergyConsumption(generator.getID(),betaPeek.getID());    // get total energy consumed
		       
		       if(peekCap>0)
		    	   betaQueue.add(betaPeek);
		       
//		        System.out.println("target NNNNNNNNNNNN: "+betaPeek.getID());
//		    	System.out.println("====>the engery consumed: "+energy);
			
			}
			
			
			
			double remainEnergy=0;
			 for(int i=0;i<Node.length;i++)
			    {
			    	remainEnergy+=Node[i].getInitialEnergy();
			    }	
			 remainEnergy-=energy;
			    
		   	 System.out.println("****** engery Resilience level: "+remainEnergy+" ******");		
						
			
			
		}
	
	///////////////////////////////////////////
	
	
	
	
	
	
	
}
