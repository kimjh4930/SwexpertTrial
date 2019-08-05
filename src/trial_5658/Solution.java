package trial_5658;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Solution {
	
	static List<String> combiSet = new ArrayList<>();
	static int windowSize;
	
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		
		int T = scan.nextInt();
		
		for(int test_case=1; test_case<=T; test_case++) {
			int N = scan.nextInt();
			int K = scan.nextInt();
			scan.nextLine();
			
			char[] temp1 = scan.nextLine().toCharArray();
			char[] temp2 = new char[temp1.length];
			
			windowSize = N/4;	//이게 회전하는 횟수를 의미함.
			
			//최소 2번에서 7번 회전한다.
			for(int i=0; i<windowSize; i++) {
				if(i % 2 ==0) {
					for(int j=1; j<N; j++) {
						temp2[j] = temp1[j-1];
					}
					temp2[0] = temp1[N-1];
					checkNumber(temp2);
				}else {
					for(int j=1; j<N; j++) {
						temp1[j] = temp2[j-1];
					}
					temp1[0] = temp2[N-1];
					checkNumber(temp1);
				}
			}
			
			for(String s : combiSet) {
				System.out.println(s);
			}
			
		}
		
		scan.close();
	}
	
	static void checkNumber (char[] arr) {
		for(int i=0; i<=windowSize; i++) {
			
			String number = "";
			
			for(int j=i*windowSize; j<i*windowSize+windowSize; j++) {
				number += arr[j];
			}
			
			if(!combiSet.contains(number)) {
				combiSet.add(number);
			}
		}
	}
	
	static void stringTonNumber () {
		
	}
}

class AscendingList implements Comparator<Integer>{

	@Override
	public int compare(Integer o1, Integer o2) {
		return o1 > o2 ? 1 : -1;
	}
	
}



