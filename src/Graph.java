
public class Graph {

	protected Node node[];
	protected int count;

	public Graph(int total) {
		node = new Node[total];
		count = 0;
	}
	
	public Graph(Node nodes[], int count) {
		this.node = new Node[count];
		System.arraycopy(nodes, 0, this.node, 0, count);
		this.count = count;
	}
	
	public Node getNode(int index) {
		return node[index];
	}
	
	public Node[] getNodes() {
		return node;
	}

	public void addNode(Node node) {
		this.node[count] = node;
		count++;
	}
	
	public int totalNodes() {
		return count;
	}
	
	public Graph getAncestralSubgraph(int[] x, int[] z, int [] y) {

		SetInt w = new SetInt(x.length+z.length+y.length);
		w.add(x);
		w.add(y);
		w.add(z);
		System.out.print("\nW = { ");
		for(int i = 0; i < w.size(); i++)
			System.out.print(node[w.get(i)].getLabel() + " ");
		System.out.println("}.");
		
		System.out.print("\nTo build the Ancestral Graph we removed: {");
		//AG = G
		int agCount = count;
		Node agn[] = new Node[count];
		System.arraycopy(node, 0, agn, 0, count);
		
		boolean done = false;
		while(!done) {
			done = true;
			for(int i = 0; i < agCount && done; i++) {
				//remove node and links if leaf and not in W
				if(agn[i].isLeaf() && !w.contains(i)) {
					//removing connections of children and parents
					System.out.print(" " + agn[i].getLabel());
					int temp[] = agn[i].getChild();
					for(int j = 0; j < temp.length; j++)
						agn[temp[j]].removeConnections(i);
					temp = agn[i].getParent();
					for(int j = 0; j < temp.length; j++)
						agn[temp[j]].removeConnections(i);
					//removing the node
					for(int j = i; j < agCount-1; j++)
						agn[j] = agn[j+1];
					
					agCount--;
					//updating indexes
					for(int j = 0; j < agCount; j++) {
						agn[j].updateIndexes(i);
					}
					w.update(i);
					for(int j = 0; j < x.length; j++)
						x[j] = x[j] > i? x[j]-1: x[j];
					for(int j = 0; j < z.length; j++)
						z[j] = z[j] > i? z[j]-1: z[j];
					for(int j = 0; j < y.length; j++)
						y[j] = y[j] > i? y[j]-1: y[j];
					done = false;
				}
			}
		}
		System.out.println(" }.");
		return new Graph(agn, agCount);
	}
}
