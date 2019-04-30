
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    private static int prime;
    private static int multiplier;
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    public static List<Integer> getOccurrences(Data input) {
        List<Integer> occurrences = new ArrayList<Integer>();
        int n = input.text.length(), p = input.pattern.length();
        prime = nextPrime(n * p);
        multiplier = 31;
        int[] hashes = precomputeHashes(input.text, p, prime, multiplier);
        for (int i = 0; i <= n - p; i++) {
            String substr = input.text.substring(i, i + p);
            if (hash(substr) != hashes[i]) continue;
            boolean matching = true;
            for (int j = 0; j < p; j++) {
                if (substr.charAt(j) != input.pattern.charAt(j)) {
                    matching = false;
                    break;
                }
            }
            if (matching) occurrences.add(i);
        }
        return occurrences;
    }

    private static int[] precomputeHashes(
            String text,
            int pLength,
            int prime,
            int multiplier) {
        int tLength = text.length();
        int[] hashes = new int[tLength - pLength + 1];
        hashes[tLength - pLength] = hash(text.substring(tLength - pLength));
        int y = 1;
        for (int i = 1; i <= pLength; i++) {
            y = (y * multiplier) % prime;
        }
        for (int i = tLength - pLength - 1; i >= 0; i--) {
            int subtraction = text.charAt(i) - y * text.charAt(i + pLength);
            hashes[i] = ((hashes[i + 1] * multiplier) % prime +
                    (subtraction % prime + prime) % prime) % prime;
        }
        return hashes;
    }

    private static int nextPrime(int n) {
        int prime = n + 1;
        while (!isPrime(prime)) prime++;
        return prime;
    }

    private static boolean isPrime(int n) {
        if (n <= 1 || n % 2 == 0) return false;
        if (n == 2 || n == 3) return true;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * Compute rotating hashing value of a string.
     * h = (S[i] + h * x) % p;
     * Note: integer overflow -> store into long type
     *
     * @param str
     * @return
     */
    private static int hash(String str) {
        long hashval = 0;  // Prevent integer overflow
        for (int i = str.length() - 1; i >= 0; i--) {
            hashval = (str.charAt(i) + hashval * multiplier) % prime;
        }
        return (int) hashval;
    }

    private static String generateRandomString(int size) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append((char) (random.nextInt(75) + '0'));
        }
        return sb.toString();
    }

    static class Data {
        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
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

