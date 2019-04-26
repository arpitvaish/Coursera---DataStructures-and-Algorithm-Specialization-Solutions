
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Solution {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public class TreeHeight {
        int n;
        int parent[];
        int depth[];

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            parent = new int[n];
            depth = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = in.nextInt();
            }
        }

        int computeHeight() {
            for (int i = 0; i < n; i++) {
                computeDepth(parent, depth, i);
            }

            int height = 0;
            for (int i = 0; i < n; i++) {
                height = Math.max(height, depth[i]);
            }

            return height;
        }

        void computeDepth(int[] parent, int[] depth, int i) {

            if (depth[i] != 0) {
                return;
            }
            if (parent[i] == -1) {
                depth[i] = 1;
                return;
            }
            if (depth[i] == 0) {
                computeDepth(parent, depth, parent[i]);
            }

            depth[i] = depth[parent[i]] + 1;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new Solution().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}
