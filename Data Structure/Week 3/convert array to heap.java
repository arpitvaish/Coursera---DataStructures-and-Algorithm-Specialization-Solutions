

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
    private int[] data;
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new Solution().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
            data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
            out.println(swap.index1 + " " + swap.index2);
        }
    }

    private int parent(int pos) {
        return (int) Math.floor((pos - 1) / 2);
    }

    private int getLeftChild(int pos) {
        return 2 * pos + 1;
    }

    private int getRightChild(int pos) {
        return 2 * pos + 2;
    }

    private void shiftDown(int[] data, int pos) {
        int minIndex = pos;
        int left = getLeftChild(pos);
        int right = getRightChild(pos);
        if (left < data.length && data[left] < data[minIndex]) {
            minIndex = left;
        }

        if (right < data.length && data[right] < data[minIndex]) {
            minIndex = right;
        }

        if (pos != minIndex) {
            swaps.add(new Swap(pos, minIndex));
            int temp = data[pos];
            data[pos] = data[minIndex];
            data[minIndex] = temp;
            shiftDown(data, minIndex);

        }
    }

    private void generateSwaps() {
        swaps = new ArrayList<Swap>();
        // The following naive implementation just sorts
        // the given sequence using selection sort algorithm
        // and saves the resulting sequence of swaps.
        // This turns the given array into a heap,
        // but in the worst case gives a quadratic number of swaps.
        //
        // TODO: replace by a more efficient implementation
        for (int i = data.length / 2; i >= 0; i--) {
            shiftDown(data, i);
        }


    }


    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
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
