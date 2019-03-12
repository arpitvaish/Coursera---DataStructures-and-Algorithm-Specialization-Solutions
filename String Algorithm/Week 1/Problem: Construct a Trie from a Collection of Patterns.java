/*
Sample 1.
Input:
1
ATA
Output:
0->1:A
2->3:A
1->2:T

Sample 2.
Input:
3
AT
AG
AC
Output:
0->1:A
1->4:C
1->3:G
1->2:T

Sample 3.
Input:
3
ATAGA
ATC
GAT
Output:
0->1:A
1->2:T
2->3:A
3->4:G
4->5:A
2->6:C
0->7:G
7->8:A
8->9:T



*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Trie {
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

    List<Map<Character, Integer>> buildTrie(String[] patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
        Map<Character, Integer> root = new HashMap<>();
        trie.add(root);
        for (String pattern : patterns) {
            Map<Character, Integer> currentNode = root;
            for (int i = 0; i < pattern.length(); i++) {
                Character currentSymbol = pattern.charAt(i);
                Set<Character> neighbours = currentNode.keySet();
                if (neighbours != null && neighbours.contains(currentSymbol)) {
                    currentNode = trie.get(currentNode.get(currentSymbol));
                } else {
                    Map<Character, Integer> newNode = new HashMap<>();
                    trie.add(newNode);
                    currentNode.put(currentSymbol, trie.size() - 1);
                    currentNode = newNode;
                }

            }
        }
        return trie;
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }
}
