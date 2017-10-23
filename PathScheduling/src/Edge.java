

import static java.lang.Math.sqrt;

public class Edge {
	private int length;
	private SensorNode startNode;
	private SensorNode endNode;
	private boolean isExist;
	
	public Edge(SensorNode n1, SensorNode n2) {
		this.startNode = n1;
		this.endNode = n2;
		this.length = (int) sqrt((n1.getxPosition()-n2.getxPosition())*(n1.getxPosition()-n2.getxPosition())
				+ (n1.getyPosition()-n2.getyPosition())* (n1.getyPosition()-n2.getyPosition()));
		
		if (length <= n1.getTransmissionRange()) {
			this.isExist = true;
		} else {
			this.isExist = false;
		}
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public SensorNode getStartNode() {
		return startNode;
	}

	public void setStartNode(SensorNode startNode) {
		this.startNode = startNode;
	}

	public SensorNode getEndNode() {
		return endNode;
	}

	public void setEndNode(SensorNode endNode) {
		this.endNode = endNode;
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
	



	
	

}
