
/*
Maximum Amount of Gold
Problem Introduction
You are given a set of bars of gold and your goal is to take as much gold as possible into
your bag. There is just one copy of each bar and for each bar you can either take it or not
(hence you cannot take a fraction of a bar).
Problem Description
Task. Given 𝑛 gold bars, find the maximum weight of gold that fits into a bag of capacity 𝑊.
Input Format. The first line of the input contains the capacity 𝑊 of a knapsack and the number 𝑛 of bars
of gold. The next line contains 𝑛 integers 𝑤0,𝑤1, . . . ,𝑤𝑛−1 defining the weights of the bars of gold.
Constraints. 1 ≤ 𝑊 ≤ 104; 1 ≤ 𝑛 ≤ 300; 0 ≤ 𝑤0, . . . ,𝑤𝑛−1 ≤ 105.
Output Format. Output the maximum weight of gold that fits into a knapsack of capacity 𝑊.


Input:
10 3
1 4 8
Output:
9

Input:
20 4
5 7 12 18

Correct output:
19



*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

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

	static int optimalWeight(int W, int[] w) {
		// write you code here

		int[][] dp = new int[w.length + 1][W + 1];
		for (int j = 1; j <= w.length; j++) {

			for (int i = 1; i <= W; i++) {

				if (w[j - 1] <= i) {
					dp[j][i] = Math.max(w[j - 1] + dp[j - 1][i - w[j - 1]], dp[j - 1][i]);
				} else {
					dp[j][i] = Math.max(dp[j - 1][i], Math.max(dp[j][i - 1], dp[j - 1][i - 1]));
				}

			}

		}

		return dp[w.length][W];

	}

	public static void main(String[] args) {
		FastScanner scanner = new FastScanner(System.in);
		int W, n;
		W = scanner.nextInt();
		n = scanner.nextInt();
		int[] w = new int[n];
		for (int i = 0; i < n; i++) {
			w[i] = scanner.nextInt();
		}
		System.out.println(optimalWeight(W, w));

	}

}
