
public class SetInt {

	int element[];
	int count;
	
	public SetInt(int size) {
		count = 0;
		element = new int[size];
	}
	
	public void add(int e) {
		for(int i = 0; i < count; i++)
			if(this.element[i] == e)
				return;
		element[count] = e;
		count++;
	}
	
	public void add(int e[]) {
		for(int i = 0; i < e.length; i++)
			add(e[i]);
	}
	
	public int size() {
		return count;
	}
	
	public boolean contains(int element) {
		for(int i = 0; i < count; i++)
			if(this.element[i] == element)
				return true;
		return false;
	}
	
	public int get(int index) {
		return element[index];
	}
	
	public void update(int e) {
		for(int i = 0; i < element.length; i++)
			if(element[i] >= e)
				element[i]--;
	}
}
