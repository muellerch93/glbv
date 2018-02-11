import java.util.Arrays;

public class Binom {
  private static int[][] coeff = null;

  public static void main (String[] args) {
    int threshold = 20;
    int i = 0;
    for (int n = 0; n < threshold; ++n) {
      for (i = 0; i < threshold; ++i) {
        System.out.println("n = " + n + ", i = " + i + ": binom1 = " + binom(n, i));
	System.out.println("n = " + n + ", i = " + i + ": binom2 = " + binom2(n, i));
      }
    }
  }

  public static int binom (final int n, final int i) {
    coeff = new int[n + 1][i + 1];

    int l = 0, k = 0;
    for (k = 0; k <= n; ++k) {
      for (l = 0; l <= i; ++l) {
        coeff[k][l] = Integer.MAX_VALUE;
      }
    }

    for (k = 0; k <= n; ++k) {
      coeff[k][0] = 1;
    }

    for (l = 1; l <= i; ++l) {
        coeff[0][l] = 0;
    }

    return lookupBinom(n, i);
  }

	public static int lookupBinom (final int n, final int i) {
		if (coeff[n][i] == Integer.MAX_VALUE)
			coeff[n][i] = lookupBinom(n - 1, i) + lookupBinom(n - 1, i - 1);
		return coeff[n][i];
	}


	public static int binom2(int n, int i){
		if(i>n)
			return 0;
		int[] b=new int[n+1];
		b[0]=1;
		//after k iterations b[] contains the k-th row of the bin triangle
		//=> after n iterations b[] contains the n-th row of the bin triangle
		// just return the i-th element of the array to obtain the value of n "over" i.
		for (int k=1;k<=n;k++){
			b[k]=1;
			for(int j=k-1;j>0;j--)
				b[j]+=b[j-1];
			System.out.println(Arrays.toString(b));
		}
		return b[i];

	}

}
