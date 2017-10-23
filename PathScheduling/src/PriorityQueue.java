import java.util.Comparator;

public class PriorityQueue implements Comparator<SensorNode>
{

	@Override
	public int compare(SensorNode one, SensorNode two) {
		
		 if(one.getInitialEnergy()>one.getInitialEnergy())
			 return -1;
		 else if (one.getInitialEnergy()<one.getInitialEnergy())
		     return 1;
		 else 
			 return 0;
		
	} 

}
