/*
Task. Given an directed graph with positive edge weights and with n vertices and m edges as well as two
vertices u and v, compute the weight of a shortest path between u and v (that is, the minimum total
weight of a path from u to v).
Input Format. A graph is given in the standard format. The next line contains two vertices u and v.
Constraints. 1 ≤ n ≤ 103
, 0 ≤ m ≤ 105
, u 6= v, 1 ≤ u, v ≤ n, edge weights are non-negative integers not
exceeding 103
.
Output Format. Output the minimum weight of a path from u to v, or −1 if there is no path.


Sample 1.
Input:
4 4
1 2 1
4 1 2
2 3 2
1 3 5
1 3
Output:
3


Sample 2.
Input:
5 9
1 2 4
1 3 2
2 3 2
3 2 1
2 4 2
3 5 4
5 4 1
2 5 3
3 4 4
1 5
Output:
6

Sample 3.
Input:
3 3
1 2 7
1 3 5
2 3 2
3 2
Output:
-1

*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Dijstra {

	

	private static long distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {

		boolean visited[] = new boolean[adj.length];
		long distance[] = new long[adj.length];
		Arrays.fill(distance, Long.MAX_VALUE);
		int parent[] = new int[adj.length];
		Arrays.fill(parent, -1);
		Queue<Integer> q = new LinkedList<>();
		q.offer(s);
		distance[s] = 0;
		parent[s] = 0;

		while (!q.isEmpty()) {
			int node = q.poll();
			visited[node] = true;
			for (int i = 0; i < adj[node].size(); i++) {
				if (!visited[adj[node].get(i)] && distance[node] + cost[node].get(i) < distance[adj[node].get(i)]) {
					distance[adj[node].get(i)] = distance[node] + cost[node].get(i);
					parent[adj[node].get(i)] = node;
				}
			}

			int index = findMin(adj, cost, distance, visited);
			if (index != -1) {
				q.offer(index);
			}
		}
		return distance[t] == Long.MAX_VALUE ? -1 : distance[t];
	}

	private static int findMin(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, long distance[],
			boolean visited[]) {
		long min = Long.MAX_VALUE;
		int min_index = Integer.MAX_VALUE;
		for (int i = 0; i < distance.length; i++) {
			if (!visited[i] && distance[i] != Long.MAX_VALUE) {
				if (min > distance[i]) {
					min = distance[i];
					min_index = i;
				}
			}
		}

		return min_index == Integer.MAX_VALUE ? -1 : min_index;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
		ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];
		for (int i = 0; i < n; i++) {
			adj[i] = new ArrayList<Integer>();
			cost[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < m; i++) {
			int x, y, w;
			x = scanner.nextInt();
			y = scanner.nextInt();
			w = scanner.nextInt();
			adj[x - 1].add(y - 1);
			cost[x - 1].add(w);
		}
		int x = scanner.nextInt() - 1;
		int y = scanner.nextInt() - 1;
		System.out.println(distance(adj, cost, x, y));
	}
}
