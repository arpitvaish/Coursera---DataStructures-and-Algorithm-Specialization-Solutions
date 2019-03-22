
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BurrowsWheelerTransform {
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

	String BWT(String text) {

		String[] str = new String[text.length()];
		for (int i = 0; i < str.length; i++) {
			str[i] = text.substring(i) + text.substring(0, i);
		}
		Arrays.sort(str);
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < str.length; i++) {
			builder.append(str[i].charAt(str[i].length() - 1));
		}

		return builder.toString();
	}

	static public void main(String[] args) throws IOException {
		new BurrowsWheelerTransform().run();
	}

	public void run() throws IOException {
		FastScanner scanner = new FastScanner();
		String text = scanner.next();
		System.out.println(BWT(text));
	}
}
