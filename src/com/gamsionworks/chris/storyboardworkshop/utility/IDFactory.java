package com.gamsionworks.chris.storyboardworkshop.utility;

import java.util.ArrayList;
import java.util.List;

public class IDFactory {
	public static long count = 0;
	private static List<Long> forbiddenNumbers = new ArrayList<Long>();
	public static long getUID(){
		while(forbiddenNumbers.contains(count)){
			++count;
		}
		return count++;
	}
	
	public static void forbidNumber(long numb){
		forbiddenNumbers.add(numb);
	}
//	
//	
//	public static void main(String[] args){
//		IDFactory.forbidNumber(459);
//		IDFactory.forbidNumber(1337);
//		IDFactory.forbidNumber(42);
//		IDFactory.forbidNumber(8001);
//		IDFactory.forbidNumber(101010);
//		for(int i=0;i<100;i++){
//			System.out.println(IDFactory.getUID());
//		}
//	}
	
}
