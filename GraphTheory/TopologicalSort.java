import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

class TopologicalSort {

	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		FastScanner(InputStream stream) {
			try {
				br = new BufferedReader(new InputStreamReader(stream));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}
	}

	private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
		int used[] = new int[adj.length];
		ArrayList<Integer> order = new ArrayList<Integer>();
		for (int i = 0; i < adj.length; i++) {
			if (used[i] != 1) {
				dfs(adj, used, order, i);
			}
		}
		Collections.reverse(order);
		return order;
	}

	private static void dfs(ArrayList<Integer>[] adj, int[] used, ArrayList<Integer> order, int s) {

		used[s] = 1;
		for (int i : adj[s]) {
			if (used[i] != 1) {
				dfs(adj, used, order, i);

			}
		}
		order.add(s);

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
		ArrayList<Integer> order = toposort(adj);
		for (int x : order) {
			System.out.print((x + 1) + " ");
		}

	}

}
