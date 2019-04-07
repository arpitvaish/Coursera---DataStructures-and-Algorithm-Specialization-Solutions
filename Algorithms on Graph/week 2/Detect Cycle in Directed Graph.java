


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
public class Acyclicity {

	private static int acyclic(ArrayList<Integer>[] adj) {
		Set<Integer> whiteSet = new HashSet<>();
		Set<Integer> graySet = new HashSet<>();
		Set<Integer> blackSet = new HashSet<>();

		for (int i = 0; i < adj.length; i++) {
			whiteSet.add(i);
		}

		while (whiteSet.size() > 0) {
			int current = whiteSet.iterator().next();
			if (DFS(current, adj, whiteSet, graySet, blackSet)) {
				return 1;
			}
		}
		return 0;
	}

	private static boolean DFS(int u, ArrayList<Integer>[] adj, Set<Integer> whiteSet, Set<Integer> graySet,
			Set<Integer> blackSet) {

		whiteSet.remove(u);
		graySet.add(u);
		for (int node : adj[u]) {

			if (blackSet.contains(node)) {
				continue;
			}
			if (graySet.contains(node)) {
				return true;
			}
			if (DFS(node, adj, whiteSet, graySet, blackSet)) {
				return true;
			}

		}
		graySet.remove(u);
		blackSet.add(u);
		return false;
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
		}
		System.out.println(acyclic(adj));

	}
}
