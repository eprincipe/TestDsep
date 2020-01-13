public class TestDsep {
	
	private static Graph bn;

	public static void main(String[] args) {
		
		System.out.println("Welcome to D-Separation Test!\n");
		
		bn = IO.getInput(args[0]);
		int xyz[][] = IO.getXYZ(args[1], bn.getNodes());
		
		System.out.println(dSeparated(xyz[0], xyz[2], xyz[1]));
	}

	private static String dSeparated(int[] x, int[] z, int[] y) {
		
		String ret = " Therefore, < ";
		if(x.length > 1)
			ret += "{ ";
		for(int i = 0; i < x.length; i ++)
			ret += bn.getNode(x[i]).getLabel()+ " ";
		if(x.length > 1)
			ret += "} ";
		ret += "| ";

		if(z.length > 1)
			ret += "{ ";
		for(int i = 0; i < z.length; i ++)
			ret += bn.getNode(z[i]).getLabel()+ " ";
		if(z.length > 1)
			ret += "} ";
		ret += "| ";

		if(y.length > 1)
			ret += "{ ";
		for(int i = 0; i < y.length; i ++)
			ret += bn.getNode(y[i]).getLabel()+ " ";
		if(y.length > 1)
			ret += "} ";
		ret += ">G ";
		
		//preparing the ancestral subgraph
		Graph AG = bn.getAncestralSubgraph(x, z, y);
		
		//preparing the moral graph
		MoralGraph MG = new MoralGraph(AG);
		
		//testing U-separation
		boolean block =  MG.uSeparation(x, z, y);
		
		if(block)
			ret += "does NOT hold.";
		else ret += "holds."; 
		
		return ret;
	}
}
