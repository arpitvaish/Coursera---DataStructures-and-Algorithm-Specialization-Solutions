/*
Task. A tornado is approaching the city, and we need to evacuate the people quickly. There are several
roads outgoing from the city to the nearest cities and other roads going further. The goal is to evacuate
everybody from the city to the capital, as it is the only other city which is able to accomodate that
many newcomers. We need to evacuate everybody as fast as possible, and your task is to find out
what is the maximum number of people that can be evacuated each hour given the capacities of all
the roads.
Input Format. The first line of the input contains integers 𝑛 and 𝑚 — the number of cities and the number
of roads respectively. Each of the next 𝑚 lines contains three integers 𝑢, 𝑣 and 𝑐 describing a particular
road — start of the road, end of the road and the number of people that can be transported through
this road in one hour. 𝑢 and 𝑣 are the 1-based indices of the corresponding cities.
The city from which people are evacuating is the city number 1, and the capital city is the city number
𝑛.
Note that all the roads are given as one-directional, that is, you cannot transport people
from 𝑣 to 𝑢 using a road that connects 𝑢 to 𝑣. Also note that there can be several roads
connecting the same city 𝑢 to the same city 𝑣, there can be both roads from 𝑢 to 𝑣 and
from 𝑣 to 𝑢, or there can be only roads in one direction, or there can be no roads between
a pair of cities. Also note that there can be roads going from a city 𝑢 to itself in the
input.
When evacuating people, they cannot stop in the middle of the road or in any city other than the
capital. The number of people per hour entering any city other than the evacuating city 1 and the
capital city 𝑛 must be equal to the number of people per hour exiting from this city. People who left
a city 𝑢 through some road (𝑢, 𝑣, 𝑐) are assumed to come immediately after that to the city 𝑣. We
are interested in the maximum possible number of people per hour leaving the city 1 under the above
restrictions.
Constraints. 1 ≤ 𝑛 ≤ 100; 0 ≤ 𝑚 ≤ 10 000; 1 ≤ 𝑢, 𝑣 ≤ 𝑛; 1 ≤ 𝑐 ≤ 10 000. It is guaranteed that
𝑚 · EvacuatePerHour ≤ 2 · 108, where EvacuatePerHour is the maximum number of people that can
be evacuated from the city each hour — the number which you need to output.
Output Format. Output a single integer — the maximum number of people that can be evacuated from
the city number 1 each hour.

Sample 1.
Input:
5 7
1 2 2
2 5 5
1 3 6
3 4 2
4 5 1
3 2 3
2 4 1
Output:
6


Sample 2.
Input:
4 5
1 2 10000
1 3 10000
2 3 1
3 4 10000
2 4 10000
Output:
20000

*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class MainClass {
	private static FastScanner in;

	public static void main(String[] args) throws IOException {
		in = new FastScanner();

		FlowGraph graph = readGraph();
		System.out.println(maxFlow(graph, 0, graph.size() - 1));
	}

	private static int maxFlow(FlowGraph graph, int from, int to) {
		int flow = 0;
		int level[] = new int[graph.graph.length];
		while (BFS(graph, level, from, to)) {
			long maxflow = 0;
			while ((maxflow = DFS(graph, from, to, level, Long.MAX_VALUE)) != 0) {
				flow += maxflow;
				System.out.println(flow);
			}
		}
		return flow;
	}

	private static long DFS(FlowGraph graph, int from, int to, int[] level, long flow) {

		if (from == to) {
			return flow;
		}
		for (int neighbour : graph.graph[from]) {
			if (level[graph.edges.get(neighbour).to] == level[graph.edges.get(neighbour).from] + 1
					&& graph.edges.get(neighbour).capacity > 0
					&& graph.edges.get(neighbour).flow != graph.edges.get(neighbour).capacity) {
				long minFlow = DFS(graph, graph.edges.get(neighbour).to, to, level,
						Math.min(flow, graph.edges.get(neighbour).capacity - graph.edges.get(neighbour).flow));
				if (minFlow > 0) {
					graph.addFlow(neighbour, (int) minFlow);
					return minFlow;
				}
			}
		}
		return 0;
	}

	private static boolean BFS(FlowGraph graph, int[] level, int source, int sink) {
		Arrays.fill(level, -1);
		boolean visisted[] = new boolean[level.length];
		Queue<Integer> q = new LinkedList<>();
		q.offer(source);
		level[source] = 0;
		while (!q.isEmpty()) {
			int node = q.poll();
			for (int edgeNumber : graph.graph[node]) {
				if (level[graph.edges.get(edgeNumber).to] == -1
						&& graph.edges.get(edgeNumber).flow != graph.edges.get(edgeNumber).capacity) {
					level[graph.edges.get(edgeNumber).to] = level[node] + 1;
					q.offer(graph.edges.get(edgeNumber).to);
				}
			}
		}
		return level[sink] != -1;
	}

	static FlowGraph readGraph() throws IOException {
		int vertex_count = in.nextInt();
		int edge_count = in.nextInt();
		FlowGraph graph = new FlowGraph(vertex_count);
		for (int i = 0; i < edge_count; ++i) {
			int from = in.nextInt() - 1, to = in.nextInt() - 1, capacity = in.nextInt();
			graph.addEdge(from, to, capacity);
		}
		return graph;
	}

	static class Edge {
		int from, to, capacity, flow;

		public Edge(int from, int to, int capacity) {
			this.from = from;
			this.to = to;
			this.capacity = capacity;
			this.flow = 0;
		}
	}

	/*
	 * This class implements a bit unusual scheme to store the graph edges, in
	 * order to retrieve the backward edge for a given edge quickly.
	 */
	static class FlowGraph {
		/* List of all - forward and backward - edges */
		private List<Edge> edges;

		/*
		 * These adjacency lists store only indices of edges from the edges list
		 */
		private List<Integer>[] graph;

		public FlowGraph(int n) {
			this.graph = (ArrayList<Integer>[]) new ArrayList[n];
			for (int i = 0; i < n; ++i)
				this.graph[i] = new ArrayList<>();
			this.edges = new ArrayList<>();
		}

		public void addEdge(int from, int to, int capacity) {
			/*
			 * Note that we first append a forward edge and then a backward
			 * edge, so all forward edges are stored at even indices (starting
			 * from 0), whereas backward edges are stored at odd indices.
			 */
			Edge forwardEdge = new Edge(from, to, capacity);
			Edge backwardEdge = new Edge(to, from, 0);
			graph[from].add(edges.size());
			edges.add(forwardEdge);
			graph[to].add(edges.size());
			edges.add(backwardEdge);
		}

		public int size() {
			return graph.length;
		}

		public List<Integer> getIds(int from) {
			return graph[from];
		}

		public Edge getEdge(int id) {
			return edges.get(id);
		}

		public void addFlow(int id, int flow) {
			/*
			 * To get a backward edge for a true forward edge (i.e id is even),
			 * we should get id + 1 due to the described above scheme. On the
			 * other hand, when we have to get a "backward" edge for a backward
			 * edge (i.e. get a forward edge for backward - id is odd), id - 1
			 * should be taken.
			 *
			 * It turns out that id ^ 1 works for both cases. Think this
			 * through!
			 */
			edges.get(id).flow += flow;
			edges.get(id ^ 1).flow -= flow;
		}
	}

	static class FastScanner {
		private BufferedReader reader;
		private StringTokenizer tokenizer;

		public FastScanner() {
			reader = new BufferedReader(new InputStreamReader(System.in));
			tokenizer = null;
		}

		public String next() throws IOException {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				tokenizer = new StringTokenizer(reader.readLine());
			}
			return tokenizer.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
}
