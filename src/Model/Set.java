package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Set<T extends Answer> implements Serializable, Cloneable {
	private ArrayList<T> arr;

	public Set() {
		arr = new ArrayList<>();
	}

	public boolean add(T temp) {
		if (arr.contains(temp))
			return false;
		if(arr.size()<2)
			arr.add(0, temp);//stack comparable
		else 
			arr.add(arr.size()-2,temp);
		return true;
	}

	public boolean update(int index, T temp) {
		int length = arr.size();
		if (length <= 3 || index > length)
			return false;
		if (arr.contains(temp))
			return false;
		else
			arr.set(index, temp);
		return true;
	}

	public boolean update(T replace) {
		for (int x=0;x<arr.size();x++)
			if (replace.equals(arr.get(x))) {
				arr.set(x, replace);
				return true;
			}
		return false;
	}

	public boolean delete(int index) {
		int length = arr.size();
		if (length <= 3 || index > length)
			return false;
		arr.remove(index);
		return true;
	}

	public int Length() {
		return arr.size();
	}

	public T get(int index) {
		return arr.get(index);
	}

	public ArrayList<T> setToArrayList() {
		return arr;
	}
	
	@Override
	public String toString() {
		if(arr.size()==0)return "no "+arr.getClass().getSimpleName();
		StringBuilder buff = new StringBuilder();
		int counter = 0;
		for(T saved: arr) {
			buff.append("\t" + (++counter) + ") " + saved.getAnswer() + "\n");
		}
		return  buff.toString();
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
