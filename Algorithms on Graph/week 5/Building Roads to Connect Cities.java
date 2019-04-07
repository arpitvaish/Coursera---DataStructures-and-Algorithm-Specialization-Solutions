
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class Graph {
    int V, E;
    Edge[] edges;

    public Graph(int v, int e) {
        V = v;
        E = e;
        edges = new Edge[E];
        for (int i = 0; i < v; i++) {
            edges[i] = new Edge(0, 0, 0);
        }
    }

    public static double Weight(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    class Edge implements Comparable<Edge> {
        int source, destination;
        Double weight;

        public Edge(int source, int destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge e) {
            return this.weight.compareTo(e.weight);
        }

        @Override
        public String toString() {
            return "Edge [source=" + source + ", destination=" + destination + ", weight=" + weight + "]";
        }
    }


}

class DisjointSet {
    int parent, rank;

    @Override
    public String toString() {
        return "DisjointSet [parent=" + parent + ", rank=" + rank + "]";
    }


    public static int find(DisjointSet set[], int u) {
        if (set[u].parent != u) {
            set[u].parent = find(set, set[u].parent);
        }

        return set[u].parent;
    }

    public static void union(DisjointSet set[], int u, int v) {
        int uRoot = find(set, u);
        int vRoot = find(set, v);

        if (set[uRoot].rank < set[vRoot].rank) {
            set[uRoot].parent = vRoot;
        } else if (set[uRoot].rank > set[vRoot].rank) {
            set[vRoot].parent = uRoot;
        } else {
            set[vRoot].parent = uRoot;
            set[uRoot].rank++;
        }
    }

    public static void makeSet(int i, DisjointSet set[]) {
        DisjointSet ds = new DisjointSet();
        ds.parent = i;
        ds.rank = 0;
        set[i] = ds;
    }
}

public class Solutions {
    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        DisjointSet[] set = new DisjointSet[x.length];
        for (int i = 0; i < x.length; i++) {
            DisjointSet.makeSet(i, set);
        }
        PriorityQueue<Graph.Edge> q = new PriorityQueue<>();
        Graph g = new Graph(x.length, x.length);
        for (int i = 0; i < x.length; i++) {
            for (int j = i + 1; j < x.length; j++) {
                Graph.Edge edge = g.new Edge(i, j, Graph.Weight(x[i], y[i], x[j], y[j]));
                q.offer(edge);
            }
        }
        while (!q.isEmpty()) {
            Graph.Edge edge = q.poll();
            int u = edge.source;
            int v = edge.destination;
            if (DisjointSet.find(set, u) != DisjointSet.find(set, v)) {
                result += edge.weight;
                DisjointSet.union(set, u, v);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        // Graph g = new Graph(n, n);
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(minimumDistance(x, y));
    }
}
