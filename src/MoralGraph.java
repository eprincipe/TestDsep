import java.util.Iterator;
import java.util.LinkedList;

public class MoralGraph extends Graph {

	private Edge edge[];
	private int ecount;
	private LinkedList<Integer> adj[];
	
	public MoralGraph (int total) {
		super(total);
	}
	
	public MoralGraph (Graph g) {
		super(g.getNodes(), g.count);
		
		//initializing the edges
		int epsize = 0, ecsize = 0;;
		for(int i = 0; i < count; i++) {
			ecsize = ecsize + node[i].parentSize() + node[i].childSize();
			if(node[i].isChild())
				epsize += (node[i].parentSize()) *
				(node[i].parentSize()-1) / 2;
		}
		edge = new Edge[epsize + ecsize/2];
		
		
		ecount = 0;
		String output = "";
		for(int i = 0; i < count; i++) {
			if(node[i].isChild()) {
				//generating pair-wise connection between parents
				output += " " + node[i].getLabel();
				for(int j = 1; j < node[i].parentSize(); j++)
					for(int k = 0; k < j; k++) {
						edge[ecount] = new Edge(node[i].getParent()[k], node[i].getParent()[j]);
						ecount++;
					}
			}
			
			//changing the old directed into new undirected connections
			for(int j = 0; j < node[i].childSize(); j++) {
				edge[ecount] = new Edge(i, node[i].getChild()[j]);
				ecount++;
			}
		}

		if(output.equalsIgnoreCase(""))
			output = "\nNo children found in the Graph.\n";
		else
			output = "\nAG Nodes that are children:" + output + ". Connecting their parents pair-wise.\n"; 
		System.out.println(output);
	}

	public boolean uSeparation(int[] x, int[] z, int[] y) {
		//remove Z and its edges
		for(int i = 0; i < z.length; i++) {
			for(int e = 0; e <ecount; e++) {
				if(edge[e].getV1() == z[i] || edge[e].getV2() == z[i]) {
					//z is present on the edge, remove it
					edge[e] = edge[ecount-1];
					ecount--;
					e--;
				} else {
					// update indexes if necessary
					if(edge[e].getV1() > z[i])
						edge[e].updateV1();
					if(edge[e].getV2() > z[i])
						edge[e].updateV2();
				}
			}
			//remove z
			for(int n = z[i] + 1; n < count; n++) {
				node[n-1] = node[n];
			}
			for(int j = 0; j < x.length; j++)
				x[j] = x[j] > z[i]? x[j]-1: x[j];
			for(int j = 0; j < y.length; j++)
				y[j] = y[j] > z[i]? y[j]-1: y[j];
			count--;
		}
		
		for(int i =0; i < x.length; i++)
			for(int j =0; j < y.length; j++) {
				//build X+ with all the reachable nodes
				boolean visited[] = checkXPlus(x[i]);
				if(visited[y[j]]) {
					System.out.print("Path is u-opened for " +
							node[x[i]].getLabel() + " & " +
							node[y[j]].getLabel() + ".");
					return visited[y[j]];
				}
			}
		System.out.print("Path is u-blocked for all possible combinations of X and Y.");
		
		return false;
	}

	private boolean[] checkXPlus(int x) {
		boolean visited[] = new boolean[count];
		adj = new LinkedList[count];
		for(int i = 0; i < count; i++)
			adj[i] = new LinkedList<Integer>();
		for(int e = 0; e < ecount; e++) {
			adj[edge[e].getV1()].add(edge[e].getV2());
			adj[edge[e].getV2()].add(edge[e].getV1());
		}
		DFS(x, visited);
		
		return visited;
	}

	private void DFS(int x, boolean[] visited) {
		visited[x] = true;
		Iterator<Integer> it = adj[x].iterator();
		while(it.hasNext()) {
			int n = it.next();
			if(!visited[n])
				DFS(n, visited);
		}
	}
}
