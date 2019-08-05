package trial_4014;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	
	static int[][] map = new int[20][20];
	static List<Stair> stairList = new ArrayList<>();
	
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		
		int T = scan.nextInt();
		
		for(int test_case=1; test_case<=T; test_case++) {
			
			int N = scan.nextInt();	// map의 크기
			int X = scan.nextInt();	// 경사로의 가로 길이
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					map[i][j] = scan.nextInt();
				}
			}
			
			//상하좌우 탐색해서. 단차가 1이 나는 지형의 개수가 X개 이상인 경우를 찾는다.
			//왼쪽에서 오른쪽으로 탐색
			
			int result = 0;
			
			//좌우 비교.
			result += confirmY (N, X);
			result += confirmX (N, X);
			
			System.out.println("#" + test_case + " " + result);
			
		}
		
		scan.close();
	}
	
	static int confirmY (int n, int slope) {
		
		int result = 0;
		
		for(int y=0; y<n; y++) {
			int old = -1;
			int length = 0;
			
			for(int x=0; x<n; x++) {
				
				if(old == -1) {
					old = map[y][x];
					length++;
					continue;
				}
				
				//같은경우
				if(old == map[y][x]) {
					length++;
				}else {
					//다른경우.
					stairList.add(new Stair(old, length));
					
					//새로운 높이 등장.
					old = map[y][x];
					length = 1;
				}
				if(x == n-1) {
					stairList.add(new Stair(old, length));
				}
			}
			
			if(analyzeList(slope)) {
				result ++;
			}
			//계단 초기
			stairList.clear();
		}
		
//		System.out.println("result : " + result);
		
		return result;
	}
	
	static int confirmX (int n, int slope) {
		int result = 0;
		
		for(int x=0; x<n; x++) {
			int old = -1;
			int length = 0;
			
			for(int y=0; y<n; y++) {
				
				if(old == -1) {
					old = map[y][x];
					length++;
					continue;
				}
				
				//같은경우
				if(old == map[y][x]) {
					length++;
				}else {
					//다른경우.
					stairList.add(new Stair(old, length));
					
					//새로운 높이 등장.
					old = map[y][x];
					length = 1;
				}
				if(y == n-1) {
					stairList.add(new Stair(old, length));
				}
			}
			
			if(analyzeList(slope)) {
				result ++;
			}
			
			//계단 초기
			stairList.clear();
		}
		
//		System.out.println("result : " + result);
		
		return result;
	}
	
	static boolean analyzeList (int slope) {
		int size = stairList.size();
		//length가 1인 경우는 무조건 통과
		
		if(stairList.size() == 1) {
			return true;
		}else {
			//2 이상인 경우에는 확인해 봐야 함.
			//앞뒤값이 1차이 나면서 작은쪽 length가 slope길이보다 긴 경우는 통과.
			boolean isEnable = true;
			
			for(int i=1; i<size; i++) {
				
				int diff = stairList.get(i).height - stairList.get(i-1).height;
				
				//단차가 1 나는 경우.
				if(diff == 1) {
					//뒤가 더 높은경우.
					int remainLength = stairList.get(i-1).length;
					
					if(stairList.get(i-1).slope == true) {
						remainLength -= slope;
					}
					
					if(remainLength < slope) {
						isEnable = false;
					}else {
						stairList.get(i-1).slope = true;
					}
				}else if(diff == -1){
					//앞이 더 높은 경우.
					int remainLength = stairList.get(i).length;
					
					if(stairList.get(i).slope == true) {
						remainLength -= slope;
					}
					
					if(remainLength < slope) {
						isEnable = false;
					}else {
						stairList.get(i).slope = true;
					}
				}else {
					isEnable = false;
				}
			}
			
			return isEnable;
		}
	}

}

class Stair {
	int height;
	int length;
	boolean slope = false;
	
	public Stair(int height, int length) {
		this.height = height;
		this.length = length;
	}
}
