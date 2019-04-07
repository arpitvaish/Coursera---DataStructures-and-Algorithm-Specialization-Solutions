
/*
Input:
4 2 
1 2
3 2
Output:2


*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectedComponents {

	private static int numberOfComponents(ArrayList<Integer>[] adj) {
		int result = 0;
		result = DFS(adj, 0);
		return result;
	}

	private static int DFS(List<Integer>[] adj, int u) {
		int result = 0;
		boolean visited[] = new boolean[adj.length];
		for (int i = 0; i < adj.length; i++) {
			if (visited[i] == false) {
				DFSVISIT(adj, i, visited);
				result += 1;
			}
		}
		return result;
	}

	private static void DFSVISIT(List<Integer>[] adj, int u, boolean[] visited) {
		visited[u] = true;
		for (int node : adj[u]) {
			if (visited[node] == false)
				DFSVISIT(adj, node, visited);
		}
	}

	public static void main(String[] args) throws IOException {
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
		System.out.println(numberOfComponents(adj));
	}
}
