/*ab
ab
0

short
ports
3

editing
distance
5*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


class EditDistance {
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
	
	
	
	static FastScanner scanner = new FastScanner(System.in);

	

	public static int EditDistance(String s, String t) {
	    String[] s1 = s.split("");
	    String[] t1 = t.split("");
	    int dp[][] = new int[s1.length+1][t1.length+1];
	    for(int i=0; i<=s1.length;i++){
	    	dp[i][0] = i;
	    }
	    
	    for(int i=0; i<=t1.length;i++){
	    	dp[0][i] = i;
	    }
	    
	    for(int i = 1;i<=s1.length;i++){
	    	for(int j=1;j<=t1.length;j++){
	    		if(s1[i-1].equals(t1[j-1])){
	    			dp[i][j] = dp[i-1][j-1];
	    		}
	    		else{
	    			dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1])) +1;
	    		}
	    	}
	    }
	    return dp[s1.length-1][t1.length-1];
	  }

	public static void main(String[] args) {
		FastScanner scanner = new FastScanner(System.in);
		String s = scanner.next();
	    String t = scanner.next();

	    System.out.println(EditDistance(s, t));

	}
}
