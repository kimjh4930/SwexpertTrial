package trial_5656;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

	static int[][] map = new int[15][12];

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { -1, 0, 1, 0 };

	static Queue<Point> queue = new LinkedList<>();
	static Queue<Point> tempQueue = new LinkedList<>();

	static int N, W, H, depth = 0;
	static int min = 0x7FFFFFFF;

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);

		int T = scan.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {

			N = scan.nextInt();
			W = scan.nextInt();
			H = scan.nextInt();

			depth = 0;
			min = 0x7FFFFFFF;

			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					map[i][j] = scan.nextInt();
				}
			}

			dfs(map);

			System.out.println("#" + test_case + " " + min);

		}

		scan.close();
	}

	static void dfs(int[][] formerMap) {

		int[][] tempMap = new int[H][W];

		// 깊이 1 증가
		depth++;

		if (depth > N) {
//			System.out.println("========result=======");
			int count = 0;
			for (int y = 0; y < H; y++) {
				for (int x = 0; x < W; x++) {
					if (formerMap[y][x] != 0) {
//						System.out.print(formerMap[y][x] + " ");
						count++;
					}
				}
//				System.out.println("");
			}

			if (min > count) {
				min = count;
			}

			return;
		}

		// 맵을 복사한다.
		

		for (int x = 0; x < W; x++) { // x
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					tempMap[i][j] = formerMap[i][j];
				}
			}
			for (int y = 0; y < H; y++) { // y
				if (tempMap[y][x] != 0) {
					if (tempMap[y][x] == 1) {
						tempMap[y][x] = -1;
						arrangeMap(tempMap);
					} else {
						queue.add(new Point(x, y, map[y][x]));
						bfs(tempMap);
					}
					break;
				}
			}
			// 다시 dfs로 들어간다.
			dfs(tempMap);
			depth--;
		}

	}

	static void bfs(int[][] tempMap) {

		int temp_x, temp_y;
		Point p;

		boolean[][] visited = new boolean[15][12];

		while (!queue.isEmpty()) {
			p = queue.poll();
			visited[p.y][p.x] = true;

			for (int n = 0; n < tempMap[p.y][p.x]; n++) {
				for (int i = 0; i < 4; i++) {
					temp_x = p.x + dx[i] * n;
					temp_y = p.y + dy[i] * n;

					// 범위를 모두 탐색한다.
					if (temp_x >= 0 && temp_x < W && temp_y >= 0 && temp_y < H) {
						if (tempMap[temp_y][temp_x] > 1) {
							// 폭탄이 2 이상인 경우에만 true로 표시
							visited[temp_y][temp_x] = true;
							tempQueue.add(new Point(temp_x, temp_y, tempMap[temp_y][temp_x]));
						} else if (tempMap[temp_y][temp_x] == 1) {
							// 깨진 벽을 -1로 표시.
							tempMap[temp_y][temp_x] = -1;
						}
					}
				}
			}
			// 현재 범위를 깨고 지금 폭탄을 -1로 변경한다.
			tempMap[p.y][p.x] = -1;

			if (queue.isEmpty()) {
				queue.addAll(tempQueue);
				tempQueue.clear();
			}
		}

		// 깨진 벽돌을 정리한다.
		arrangeMap(tempMap);
	}

	// 정리한다.
	static void arrangeMap(int[][] tempMap) {

		List<Integer> numList = new ArrayList<>();

		for (int x = 0; x < W; x++) {
			for (int y = H - 1; y >= 0; y--) {
				if (tempMap[y][x] > 0) {
					numList.add(tempMap[y][x]);
				}

				if (tempMap[y][x] == 0) {
					break;
				}
			}

			// clear
			for (int y = 0; y < H; y++) {
				tempMap[y][x] = 0;
			}

			int i = H - 1;
			for (Integer num : numList) {
				tempMap[i][x] = num;
				i--;
			}

			numList.clear();
		}

	}
}

class Point {
	int x, y, num;

	public Point(int x, int y, int num) {
		this.x = x;
		this.y = y;
		this.num = num;
	}
}
