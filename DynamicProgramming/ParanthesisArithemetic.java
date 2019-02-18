/*
Maximum Value of an Arithmetic Expression
Problem Introduction
In this problem, your goal is to add parentheses to a given arithmetic
expression to maximize its value.

Sample 1.
Input:
1+5
Output:
6
Sample 2.
Input:
5-8+7*4-8+9
Output:
200
200 = (5 − ((8 + 7) × (4 − (8 + 9))))




*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


class ParanthesisArithemetic {
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

	

	private static long getMaximValue(String exp) {
		String[] values = exp.split("");
		List<Long> integer = new ArrayList<>();
		List<String> ops = new ArrayList<>();
		for (String val : values) {
			if (val.matches("\\d+")) {
				integer.add(Long.valueOf(val));
			} else {
				ops.add(val);
			}
		}

		long dpMin[][] = new long[integer.size()][integer.size()];
		long dpMax[][] = new long[integer.size()][integer.size()];
		for (int i = 0; i < integer.size(); i++) {
			dpMin[i][i] = integer.get(i);
			dpMax[i][i] = integer.get(i);

		}
		
		for(int j= 0; j< integer.size()-1; j++) {
			for (int i = 0; i < integer.size() - j -1; i++) {
				int k = i+j+1;
				MinMax(i,k, dpMin, dpMax, ops);

			}

		}
		return dpMax[0][integer.size()-1];
	}
	
	public static void MinMax(int i, int j, long dpMin[][], long dpMax[][], List<String> ops){
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		for(int k=i; k<j;k++){
			long a = eval(dpMax[i][k], dpMax[k+1][j], ops.get(k));
			long b = eval(dpMax[i][k], dpMin[k+1][j], ops.get(k));
			long c = eval(dpMin[i][k], dpMax[k+1][j], ops.get(k));
			long d = eval(dpMin[i][k], dpMin[k+1][j], ops.get(k));
			
			min = Math.min(min, Math.min(a, Math.min(b, Math.min(c, d))));
			max = Math.max(max, Math.max(a, Math.max(b, Math.max(c, d))));
		}
		
		dpMin[i][j] = min;
		dpMax[i][j] = max;
	}

	private static long eval(long a, long b, String op) {
		if (op.equals("+")) {
			return a + b;
		} else if (op.equals("-")) {
			return a - b;
		} else if (op.equals("*")) {
			return a * b;
		} else {
			assert false;
			return 0;
		}
	}

	public static void main(String[] args) {
		
		String exp = scanner.next();
		System.out.println(getMaximValue(exp));

	}

}
