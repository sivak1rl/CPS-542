import java.util.Scanner;

/**
 * Do not run this program with input greater than 32, as it's to slow to
 * reasonably run. You can do it at your own risk, but more than likely it'll
 * take long enough you won't want to wait for it. Alternatively, you can
 * comment out The BaseCutRod call in the main method to run with much larger
 * inputs. At n = 34, it takes my laptop approximately 1 minute.
 * 
 * @author Richard Sivak
 *
 */
public class RodCut {

	public static int[] memoizedCosts;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Give a number (rod length)");
		int size = sc.nextInt();
		memoizedCosts = new int[size + 1];
		int[] costs = size < 4 ? new int[5] : new int[size + 1];
		costs[0] = 0;
		costs[1] = 5;
		costs[2] = 6;
		costs[3] = 8;
		costs[4] = 13;
		for (int i = 5; i <= size; i++) {
			costs[i] = Integer.MIN_VALUE;
		}
		long startTime = System.currentTimeMillis();
		// Base
		int q = BaseCutRod(costs, size);
		System.out.println("base cut rod took " + (System.currentTimeMillis() - startTime) + " milliseconds");
		System.out.println("Answer: " + q);
		// Bottom up
		startTime = System.currentTimeMillis();
		q = BottomUpCutRod(costs, size);
		System.out.println("bottom up rod took " + (System.currentTimeMillis() - startTime) + " milliseconds");
		System.out.println("Answer: " + q);
		// Top down
		startTime = System.currentTimeMillis();
		q = TopDownCutRod(costs, size);
		System.out.println("top down cut rod took " + (System.currentTimeMillis() - startTime) + " milliseconds");
		System.out.println("Answer: " + q);
	}

	/**
	 * This can't handle very big input. Don't run it with a number bigger than
	 * ~50
	 * 
	 * @param p
	 *            The list of base costs
	 * @param n
	 *            The size of the rod we were given
	 * @return The maximum value of a rod we can get.
	 */
	public static int BaseCutRod(int[] p, int n) {
		if (n == 0) {
			return 0;
		}
		int q = Integer.MIN_VALUE;
		for (int i = 1; i <= n; i++) {
			q = Math.max(q, BaseCutRod(p, n - i) + p[i]);
		}
		return q;
	}

	public static int BottomUpCutRod(int[] p, int n) {
		int[] r = new int[n + 1];
		r[0] = 0;
		for (int i = 1; i <= n; i++) {
			int q = Integer.MIN_VALUE;
			for (int j = 1; j <= i; j++) {
				q = Math.max(q, p[j] + r[i - j]);
			}
			r[i] = q;
		}
		return r[n];
	}

	/**
	 * Basically just calls the other TopDown function, but don't try skipping
	 * this one...
	 * 
	 * @param p
	 *            Base costs and other stuff. length n + 1 (goes zero to n)
	 * @param n
	 *            Size of rod
	 * @return
	 */
	public static int TopDownCutRod(int[] p, int n) {
		int[] r = new int[n + 1];
		for (int i = 0; i < n + 1; i++) {
			r[i] = Integer.MIN_VALUE;
		}
		return TopDownCutRodGet(p, n, r);
	}

	/**
	 * This actually does the stuff. Also, I can only go so deep, so after a
	 * while, you're gonna get a stack overflow error.
	 * 
	 * @param p
	 *            an array of size n + 1 (from 0 to n) [0..n] which holds all of
	 *            the costs we've calculated
	 * @param n
	 *            the size of the rod we want to find the value of
	 * @param r
	 *            an array of calculated best costs.
	 * @return the best value for a rod of length n
	 */
	public static int TopDownCutRodGet(int[] p, int n, int[] r) {
		if (r[n] > 0) {
			return r[n];
		}
		int q;
		if (n == 0) {
			q = 0;
		} else {
			q = Integer.MIN_VALUE;
			for (int i = 1; i < n + 1; i++) {
				q = Math.max(q, TopDownCutRodGet(p, n - i, r) + p[i]);
			}
			r[n] = q;
		}
		return q;
	}
}
