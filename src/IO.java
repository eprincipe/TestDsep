import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class IO {

	public static Graph getInput (String fileName) {
		
		Graph bn = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("Could not find the BN input file.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			int nodes = Integer.parseInt(st.nextToken());
			bn = new Graph(nodes);
			System.out.print("Input Variables: ");
			
			for(int i = 0; i < nodes; i++) {
				//blank line
				reader.readLine();
				
				//cardinality
				st = new StringTokenizer(reader.readLine());
				int card = Integer.parseInt(st.nextToken());
				
				//label
				st = new StringTokenizer(reader.readLine());
				String label = st.nextToken();
				
				//variable names
				String value[] = new String[card];
				for(int j = 0; j < card; j++) {
					st = new StringTokenizer(reader.readLine());
					value[j] = st.nextToken();
				}
				
				//children
				st = new StringTokenizer(reader.readLine());
				int nChildren = Integer.parseInt(st.nextToken());
				int child[] = new int[nChildren];
				for(int j = 0; j < nChildren; j++)
					child[j] = Integer.parseInt(st.nextToken());
				
				//parents
				st = new StringTokenizer(reader.readLine());
				int nParents = Integer.parseInt(st.nextToken());
				int parent[] = new int[nParents];
				for(int j = 0; j < nParents; j++)
					parent[j] = Integer.parseInt(st.nextToken());
				
				
				//ignoring coordinates
				reader.readLine();
				
				//probabilities
				st = new StringTokenizer(reader.readLine());
				int nProb = Integer.parseInt(st.nextToken());
				float p[] = new float[nProb];
				st = new StringTokenizer(reader.readLine());
				
				int k = 0;
				while(k < nProb) {
					if(!st.hasMoreTokens())
						st = new StringTokenizer(reader.readLine());
					p[k] = Float.parseFloat(st.nextToken());
					k++;
				}
				
				//adding node to the BN
				bn.addNode(new Node(label, value, child, parent, p));
				if(i == nodes-1)
					System.out.print(label + ".");
				else System.out.print(label + ", ");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Problems occurred while reading the BN file.");
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println();
		System.out.println();
		return bn;
	}
	
	public static int[][] getXYZ(String fileName, Node[] n) {
		
		int xyz[][] = new int[3][];
		String label = "XYZ";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("Could not find the XYZ file.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			StringTokenizer st;
			for(int i = 0; i < 3; i++) {
				System.out.print(label.charAt(i)+ ": ");
				st = new StringTokenizer(reader.readLine());
				xyz[i] = new int[st.countTokens()];
				for(int j = 0; st.hasMoreTokens(); j++) {
					String temp = st.nextToken();
					if(temp.equalsIgnoreCase("{}"))
						xyz[i] = new int[0];
					else
						xyz[i][j] = getIndex(n, temp);
					System.out.print(temp + " ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.err.println("Problems occurred while reading the XYZ file.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		return xyz; 
	}

	private static int getIndex(Node[] n, String temp) {
		for(int i = 0; i < n.length; i++)
			if(n[i].getLabel().equalsIgnoreCase(temp))
				return i;
		System.out.println("Please, verify the XYZ file. One of the variables there is not part of the BN.");
		System.exit(-1);
		return -1;
	} 
}
