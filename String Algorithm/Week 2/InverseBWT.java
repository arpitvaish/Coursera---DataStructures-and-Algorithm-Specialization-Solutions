import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {
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

    String inverseBWT(String bwt) {
		  StringBuilder result = new StringBuilder();

      Integer ints[] = new Integer[bwt.length()];
      for (int i = 0; i < ints.length; i++) {
        ints[i] = i;
      }
		  Arrays.sort(ints, new StringSort(bwt));

      int startIndex = 0;
      for (; bwt.charAt(startIndex) != '$'; startIndex++)
        ;

      for(int i=0;i<ints.length;i++){
        startIndex = ints[startIndex];
        result.append(bwt.charAt(startIndex));
		}

		return result.toString();
	}

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
}
