package sample.java.collections;

import java.util.HashSet;

public class TestHashSet {
	
	public static void main(String[] args) {
		//HashSet内部存放Map
		HashSet<Integer> hashSet = new HashSet<>();
		
		hashSet.add(5);
		hashSet.add(1);
		hashSet.add(7);
		
		hashSet.add(null);
		
		hashSet.remove(null);
		
	}
}