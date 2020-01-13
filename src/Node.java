
public class Node {

	private String label;
	private String value[];
	private int child[];
	private int parent[];
	private int ccount, pcount;
	private float p[];
	
	public Node(String label, String[] value, int[] child, int[] parent, float[] p) {
		super();
		this.label = label;
		this.value = value;
		this.child = child;
		this.ccount = child.length;
		this.parent = parent;
		this.pcount = parent.length;
		this.p = p;
	}
	public String getLabel() {
		return label;
	}
	public String[] getValue() {
		return value;
	}
	public int[] getChild() {
		return child;
	}
	public int childSize() {
		return ccount;
	}
	public boolean isLeaf() {
		return ccount == 0;
	}
	public int[] getParent() {
		return parent;
	}
	public int parentSize() {
		return pcount;
	}
	public float[] getP() {
		return p;
	}
	private int remove(int e, int[] arr, int count) {
		boolean found = false;
		for(int i = 0;i < count; i++) {
			found = found || arr[i] == e;
			if(found && i != count-1)
				arr[i] = arr[i+1];
		}
		return found?count-1:count;
	}
	public void removeConnections(int e) {
		this.ccount = remove(e, child, ccount);
		this.pcount = remove(e, parent, pcount);
	}
	public boolean isChild() {
		return pcount != 0;
	}
	public void updateIndexes(int e) {
		update(e, child);
		update(e, parent);
	}
	private void update(int e, int arr[]) {
		for(int i = 0; i < arr.length; i++)
			if(arr[i] >= e)
				arr[i]--;
	}
}
