/*
6
9 8 7 6 5 4
output : 15
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Inversions {

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

	
	private static long getNumberOfInversions(long[] a, int left, int right) {
		long numberOfInversions = 0;
		if(a.length == 1){
			return 0;
		}
		if (right <= left + 1) {
			return numberOfInversions+1;
		}
		numberOfInversions = mergeSort(a, left, right);
		return numberOfInversions;
	}

	private static long mergeSort(long[] a, int left, int right) {
		long numberOfInversions = 0;
		if (left < right) {
			int mid = (left + right) / 2;
			numberOfInversions += mergeSort(a, left, mid);
			numberOfInversions += mergeSort(a, mid + 1, right);
			numberOfInversions += merge(a, left, right, mid);
		}

		return numberOfInversions;

	}

	private static long merge(long[] a, int left, int right, int mid) {

		int l = mid - left + 1;
		int m = right - mid;
		long inversions = 0;
		long lArray[] = new long[l + 1];
		lArray[l] = Integer.MAX_VALUE;
		long rArray[] = new long[m + 1];
		rArray[m] = Integer.MAX_VALUE;

		for (int j = 0; j < l; j++) {
			lArray[j] = a[left + j];
		}

		for (int j = 0; j < m; j++) {
			rArray[j] = a[left + l + j];
		}

		int b = 0, k = 0, i = 0;
		for (i = left; i < (left + l + m); i++) {
			if (lArray[b] <= rArray[k]) {
				a[i] = lArray[b];
				b++;
			} else {
				a[i] = rArray[k];
				if(lArray[b] != Integer.MAX_VALUE)
					inversions += l - b;
				k++;
			}
		}

		return inversions;

	}

	public static void main(String[] args) {
		FastScanner scanner = new FastScanner(System.in);
		int n = scanner.nextInt();
		long[] a = new long[n];
		for (int i = 0; i < n; i++) {
			a[i] = Long.parseLong(scanner.next());
		}
		int[] b = new int[n];
		System.out.println(getNumberOfInversions(a, 0, a.length - 1));
	}
}

