/*
Input : 
2 3
4 3 1
3 1 0

Output: 
6


*/

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		int matrix[][] = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				matrix[i][j] = scanner.nextInt();
			}
		}
		int sum = 0;
		int j = 0;
		int[] a = getMax(matrix);
		int k = 0;
		for (int i = 0; i < matrix.length; i++) {
			sum += matrix[i][a[k]];
			k++;
		}
		System.out.println(sum);
	}

	static int[] getMax(int w[][]) {
		final int n = w.length, m = w[0].length, PHI = -1, NOL = -2;
		boolean[] x[] = new boolean[n][m], ss = new boolean[n], st = new boolean[m];
		int[] u = new int[n], v = new int[m], p = new int[m], ls = new int[n], lt = new int[m], a = new int[n];
		int f = 0;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				f = Math.max(f, w[i][j]);

		Arrays.fill(u, f);
		Arrays.fill(p, Integer.MAX_VALUE);
		Arrays.fill(lt, NOL);
		Arrays.fill(ls, PHI);
		Arrays.fill(a, -1);

		while (true) {
			f = -1;
			for (int i = 0; i < n && f == -1; i++)
				if (ls[i] != NOL && !ss[i])
					f = i;

			if (f != -1) {
				ss[f] = true;
				for (int j = 0; j < m; j++)
					if (!x[f][j] && u[f] + v[j] - w[f][j] < p[j]) {
						lt[j] = f;
						p[j] = u[f] + v[j] - w[f][j];
					}
			} else {
				for (int i = 0; i < m && f == -1; i++)
					if (lt[i] != NOL && !st[i] && p[i] == 0)
						f = i;

				if (f == -1) {
					int d1 = Integer.MAX_VALUE, d2 = Integer.MAX_VALUE, d;
					for (int i : u)
						d1 = Math.min(d1, i);

					for (int i : p)
						if (i > 0)
							d2 = Math.min(d2, i);

					d = Math.min(d1, d2);

					for (int i = 0; i < n; i++)
						if (ls[i] != NOL)
							u[i] -= d;

					for (int i = 0; i < m; i++) {
						if (p[i] == 0)
							v[i] += d;
						if (p[i] > 0 && lt[i] != NOL)
							p[i] -= d;
					}

					if (d2 >= d1)
						break;
				} else {
					st[f] = true;
					int s = -1;

					for (int i = 0; i < n && s == -1; i++)
						if (x[i][f])
							s = i;

					if (s == -1) {
						for (int l, r;; f = r) {
							r = f;
							l = lt[r];

							if (r >= 0 && l >= 0)
								x[l][r] = !x[l][r];
							else
								break;

							r = ls[l];
							if (r >= 0 && l >= 0)
								x[l][r] = !x[l][r];
							else
								break;
						}

						Arrays.fill(p, Integer.MAX_VALUE);
						Arrays.fill(lt, NOL);
						Arrays.fill(ls, NOL);
						Arrays.fill(ss, false);
						Arrays.fill(st, false);

						for (int i = 0; i < n; i++) {
							boolean ex = true;
							for (int j = 0; j < m && ex; j++)
								ex = !x[i][j];
							if (ex)
								ls[i] = PHI;
						}
					} else
						ls[s] = f;
				}
			}
		}

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (x[i][j])
					a[j] = i;
		return a;
	}
}
