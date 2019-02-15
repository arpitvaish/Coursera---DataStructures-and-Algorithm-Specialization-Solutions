import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;


class APlusB {
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
	
	
	
	static FastScanner sc = new FastScanner(System.in);
	public static void main(String args[]) throws IOException {

		
		FlowGraph graph = readGraph();
		System.out.println(maxFlow(graph, 0, graph.size() - 1));
	}

	private static int maxFlow(FlowGraph graph, int from, int to) {
		int flow = 0;
		int flowNew = 0;
		while (true) {
			flowNew = BFS(graph, from, to);
			if (flowNew <= 0) {
				break;
			}
			flow += flowNew;

		}
		//System.out.println(flow);
		return flow;
	}

	static int BFS(FlowGraph graph, int from, int to) {
		int flow = Integer.MAX_VALUE;
		boolean[] visited = new boolean[graph.size()];
		Queue<Integer> q = new LinkedList<>();
		List<Integer> list = new ArrayList<>();
		q.offer(from);
		Map<Integer, Integer> mapper = new HashMap<>();
		while (!q.isEmpty()) {
			int v = q.poll();
			if (v == to) {
				// mapper.put(v, value)
				break;
			}
			visited[v] = true;
			List<Integer> neighbours = graph.getIds(v);
			for (int edge : neighbours) {
				if (visited[graph.getEdge(edge).to] == false
						&& (graph.getEdge(edge).capacity - graph.getEdge(edge).flow > 0)) {
					q.offer(graph.getEdge(edge).to);
					mapper.put(graph.getEdge(edge).to, edge);

				}
			}
		}

		int node = to;
		//System.out.println();
		while (node != from) {
			//System.out.print(node + " -> ");
			if(mapper.get(node) == null)
				return 0;
			Edge edgeId = (null != graph.getEdge(mapper.get(node)) ? graph.getEdge(mapper.get(node)) : null );
			if (edgeId == null)
				return 0;
			list.add(mapper.get(node));
			node = edgeId.from;
			flow = Math.min(flow, edgeId.capacity - edgeId.flow);

		}

		for (int edgeno : list) {
			graph.addFlow(edgeno, flow);
		}

		return flow;
	}

	static FlowGraph readGraph() throws IOException {
		int vertex_count = sc.nextInt();
		int edge_count = sc.nextInt();
		FlowGraph graph = new FlowGraph(vertex_count);

		for (int i = 0; i < edge_count; ++i) {
			int from = sc.nextInt() - 1, to = sc.nextInt() - 1, capacity = sc.nextInt();
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

		public String toString() {
			return "from: " + this.from + " to: " + this.to + " capacity:  " + this.capacity + " flow:  " + this.flow;
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
}
