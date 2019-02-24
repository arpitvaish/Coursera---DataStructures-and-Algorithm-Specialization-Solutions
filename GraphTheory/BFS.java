/*
Problem: Computing the Minimum Number of Flight Segments

Input Format. A graph is given in the standard format. The next line contains two vertices u and v.
Constraints. 2 ≤ n ≤ 105
, 0 ≤ m ≤ 105
, u 6= v, 1 ≤ u, v ≤ n.
Output Format. Output the minimum number of edges in a path from u to v, or −1 if there is no path.

Sample 1.
Input:
4 4
1 2
4 1
2 3
3 1
2 4
Output:
2


Sample 2.
Input:
5 4
5 2
1 3
3 4
1 4
3 5
Output:
-1


*/


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class BFS {

	

	private static int distance(ArrayList<Integer>[] adj, int s, int t) {
		int dist[] = new int[adj.length];
		int parent[] = new int[adj.length];
		Arrays.fill(parent, -1);
		bfs(adj, dist, parent, s, t);
		return (parent[t] == -1) ? -1 : dist[t];
	}

	private static void bfs(List<Integer>[] adj, int distance[], int parent[], int s, int t) {
		Queue<Integer> q = new LinkedList<>();
		q.offer(s);
		distance[s] = 0;
		parent[s] = 0;
		while (!q.isEmpty()) {
			int node = q.poll();
			for (int neighbours : adj[node]) {
				if (parent[neighbours] == -1) {
					q.add(neighbours);
					distance[neighbours] = distance[node] + 1;
					parent[neighbours] = node;
				}

			}

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
		System.out.println(distance(adj, x, y));
	}
}
