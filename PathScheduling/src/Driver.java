
import java.awt.List;
import java.util.LinkedList;

public class Driver {

	public static void main(String[] args) {
		
		GsensorNetwork sn = new GsensorNetwork();
		
		/* Output 1: Plot sensor network field, with all sensor nodes (including Dgs) */
		sn.buildNetwork(); 
		
		sn.creatingAdjacencyList();
		
		
		sn.isConnected();
		boolean isConnect = sn.isConnected();
		System.out.println("isConnect : " + isConnect);
		sn.DataBasedAlgo();
//		sn.NodeBasedAlgo();
//		sn.NetworkBasedAlgo();
//		sn.BenefitBasedAlgo();
		

	}

}
