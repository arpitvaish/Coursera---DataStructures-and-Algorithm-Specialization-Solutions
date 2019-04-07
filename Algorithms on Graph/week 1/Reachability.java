/*
Count the no. of ways reachable to x,y

4 4
1 2
3 2
4 3
1 4
1 4

output : 1


4 2
1 2
3 2
1 4

output : 0

*/


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reachability {

	private static int numberOfComponents(ArrayList<Integer>[] adj, int x, int y) {
		List<List<Integer>> list = new ArrayList<>();
		DFS(adj, 0, list);
		int counter = 0;
		for (List<Integer> l1 : list) {
			if (l1.contains(x) && l1.contains(y)) {
				counter++;
			}
		}
		return counter;
	}

	private static void DFS(List<Integer>[] adj, int u, List<List<Integer>> list) {
		boolean visited[] = new boolean[adj.length];
		for (int i = 0; i < adj.length; i++) {
			if (visited[i] == false) {
				List<Integer> l1 = new ArrayList<>();
				DFSVISIT(adj, i, visited, l1);
				list.add(l1);
			}
		}
	}

	private static void DFSVISIT(List<Integer>[] adj, int u, boolean[] visited, List<Integer> l1) {
		visited[u] = true;
		l1.add(u);
		for (int node : adj[u]) {
			if (visited[node] == false)
				DFSVISIT(adj, node, visited, l1);
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
		for (int i = 0; i < n; i++) {
			adj[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < m; i++) {
			int x, y;
			x = scanner.nextInt();
			y = scanner.nextInt();
			adj[x - 1].add(y - 1);
			adj[y - 1].add(x - 1);
		}
		int x = scanner.nextInt() - 1;
		int y = scanner.nextInt() - 1;
		System.out.println(numberOfComponents(adj, x, y));
	}
}
