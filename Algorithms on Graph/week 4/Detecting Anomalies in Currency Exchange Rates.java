import java.util.ArrayList;
import java.util.Scanner;

public class Solutions {
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        int[] dist = new int[adj.length];
        dist[0] = 0;

        for (int i = 0; i < adj.length - 1; i++) {
            for (int u = 0; u < adj.length; u++) {
                ArrayList<Integer> u_adj = adj[u];
                for (int v : u_adj) {
                    int v_index = adj[u].indexOf(v);
                    if (dist[v] > dist[u] + cost[u].get(v_index)) {
                        dist[v] = dist[u] + cost[u].get(v_index);
                    }
                }
            }
        }

        for (int u = 0; u < adj.length; u++) {
            ArrayList<Integer> u_adj = adj[u];
            for (Integer v : u_adj) {
                int v_index = adj[u].indexOf(v);

                if (dist[v] > dist[u] + cost[u].get(v_index)) {
                    return 1;
                }
            }
        }

        return 0;
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
        System.out.println(negativeCycle(adj, cost));
    }
}
