/**
 *  Assignment 04, Advanced Algorithms & Data Structures, Summer term 2016.
 *  Department of Computer Sciences, University of Salzburg.
 *
 *  @author Christian Mueller, 1123410
 *  @author Daniel Kocher, 0926293
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Graph implements GraphInterface {
  /**
   * Initialize the graph with the given number of nodes.
   * 
   * @param numberOfNodes The number of nodes
   */
  public void setNumberOfNodes (int numberOfNodes) {
    this.numberOfNodes = numberOfNodes + 1;
    A = new int[this.numberOfNodes][this.numberOfNodes];
  }

  /**
   * Add an undirected edge to the graph.
   * 
   * @param nodeA One node incident at the edge
   * @param nodeB The other node incident at the edge
   */
  public void addEdge (int nodeA, int nodeB) {
    A[nodeA][nodeB] = A[nodeB][nodeA] = 1;
  }

  /**
   *  Get the base index for the node IDs.
   *
   *  @return Either 0 or 1, the index of the first node
   */
  public int getBase () {
    return 1;
  }

  /**
   * Solve the APSP problem.
   * 
   * @return  A matrix A[i,j] with the next hop on the shortest path from node i
   *          to node j
   */
  public int[][] APSP () {
    int[][] D = APD(A);

    Map<Integer, int[][]> Ds = new HashMap<Integer, int[][]>() {{
      put(0, new int[D.length][D.length]);
      put(1, new int[D.length][D.length]);
      put(2, new int[D.length][D.length]);
    }};
    Map<Integer, int[][]> Ws = new HashMap<Integer, int[][]>() {{
      put(0, new int[D.length][D.length]);
      put(1, new int[D.length][D.length]);
      put(2, new int[D.length][D.length]);
    }};

    int j = 0, k = 0;
    for (int s = 0; s <= 2; ++s) {
      for (k = 1; k < D.length; ++k) {
        for (j = 1; j < D.length; ++j) {
          if ((D[k][j] + 1) % 3 == s) {
            Ds.get(s)[k][j] = 1;
          }
        }
      }

      Ws.put(s, BPWM(A, Ds.get(s)));
    }

    /*
    // debug - print BPWMs
    for (Map.Entry<Integer, int[][]> ws: Ws.entrySet()) {
      printMatrix(ws.getValue(), "Ws(" + ws.getKey() + ")");
    }
    */

    int[][] S = new int[D.length][D.length];
    for (int i = 1; i < S.length; ++i) {
      for (j = 1; j < S.length; ++j) {
        S[i][j] = Ws.get(D[i][j] % 3)[i][j];
        if (i == j) {
          S[i][j] = 0 ;
        }
      }
    }

    return S;
  }

  /**
   *  Solve the APD problem
   *
   *  @param A  The adjacency matrix of the graph
   *
   *  @return A matrix D[i, j] with the distances between all pairs of nodes
   */
  public int[][] APD (int[][] A) {
    int[][] D = new int[A.length][A.length];
    int[][] Z = matrixMultiply(A, A, 1);

    // A[i][j] = 1 <=> es existiert Weg der Laenge 1 oder 2 von i nach j
    int[][] A_ = new int[A.length][A.length];
    int j = 0;
    for (int i = 1; i < A_.length; ++i) {
      for (j = 1; j < A_.length; ++j) {
        if (i != j && (A[i][j] == 1 || Z[i][j] > 0)) {
          A_[i][j] = 1;
        }
      }
    }

    boolean terminate = true;
    for (int i = 1; i < A_.length; ++i) {
      for (j = 1; j < A_.length; ++j) {
        if (i != j && A_[i][j] != 1) {
          terminate = false;
          i = j = A_.length; // terminate loops asap
        }
      }
    }

    if (terminate) {
      for (int i = 1; i < D.length; ++i) {
        for (j = 1; j < D.length; ++j) {
          D[i][j] = 2 * A_[i][j] - A[i][j];
        }
      }

      return D;
    }

    int[][] D_ = APD(A_);
    int[][] S = matrixMultiply(A, D_, 1);

    for (int i = 1; i < D.length; ++i) {
      for (j = 1; j < D.length; ++j) {
        D[i][j] = 2 * D_[i][j];
        
        if (S[i][j] < D_[i][j] * Z[i][i]) {
          D[i][j] -= 1;
        }
      }
    }

    return D;
  }

  /**
   *  Multiplies two given integer matrices A and B, taking a factor into account.
   *
   *  @param  A       The first matrix of the multiplication
   *  @param  B       The second matrix of the multiplication
   *  @param  factor  The factor to be applied after the multiplication
   *
   *  @return The resulting matrix of factor * (A * B)
   */
  public int[][] matrixMultiply (int[][] A, int[][] B, int factor) {
    int[][] C = new int[A.length][A.length];
   
    int j = 0, k = 0;
    for (int i = 1; i < A.length; ++i) {
      for (j = 1; j < A.length; ++j) {
        for (k = 1; k < A.length; ++k) {
          C[i][j] += A[i][k] * B[k][j];
        }

        C[i][j] *= factor;
      }
    }

    return C;
  }

  /**
   *  Computes a boolean product witness matrix (BPWM) for the product of two
   *  boolean matrices A and B, i.e., P = A * B, using the randomized algorithm.
   *  Expected time complexity: O(MM(n) * log^2(n)).
   *
   *  @param  A The first boolean matrix
   *  @param  B The second boolean matrix
   *
   *  @return A witness matrix for P = A * B
   */
  public int[][] BPWM (int[][] A, int[][] B) {
    // W[i][j] < 0 <=> witness for P[i][j] still to be found
    int[][] W = matrixMultiply(A, B, -1);

    int i = 0, j = 0, r = 0, u = 0, v = 0, k = 0;
    int[][] Z = null;
    Random random = new Random();
    double bound = Math.log10(A.length) / Math.log10(2); // log2(n)
    // test all r = 2^t between 1 and n
    for (int t = 0; t <= Math.floor(bound); ++t) {
      r = ((int)Math.pow(2, t));
      for (u = 0; u < Math.ceil(4 * bound); ++u) {
        int[][] AR = new int[A.length][A.length];
        int[][] BR = new int[A.length][A.length];
        int[] Rk = new int[A.length];

        // fill vector Rk
        for (i = 1; i < r; ++i) {
          k = random.nextInt(A.length - 1) + 1;
          Rk[k] = 1;
        }

        // compute AR
        for (i = 1; i < A.length; ++i) {
          for (k = 1; k < Rk.length; ++k) {
            AR[i][k] = k * Rk[k] * A[i][k];
          }
        }

        // compute BR
        for (k = 1; k < Rk.length; ++k) {
          for (j = 1; j < B.length; ++j) {
            BR[k][j] = Rk[k] * B[k][j];
          }
        }

        // Z = AR * BR contains witnesses for all entries in P = A * B, which
        // have a unique witness in R
        Z = matrixMultiply(AR, BR, 1);

        for (i = 1; i < W.length; ++i) {
          for (j = 1; j < W.length; ++j) {
            // Z[i][j] is a witness of P = A * B iff
            // A[i][k] = 1 and B[k][j] = 1, where k = Z[i][j]
            if (W[i][j] < 0 && Z[i][j] > 0 && Z[i][j] < W.length &&
                A[i][Z[i][j]] == 1 && B[Z[i][j]][j] == 1)
            {
              W[i][j] = Z[i][j];
            }
          }
        }
      }
    }

    // trivial algorithm to find remaining witnesses
    // for all (i, j) in {1, ..., n}^2 where W[i][j] < 0,
    // test all k in {1, ..., n}
    for (i = 1; i < W.length; ++i) {
      for (j = 1; j < W.length; ++j) {
        if (W[i][j] < 0) {
          for (k = 1; k < W.length; ++k) {
            if (A[i][k] == 1 && B[k][j] == 1) {
              W[i][j] = k;
              break;
            }
          }
        }
      }
    }

    return W;
  }

  /**
   *  Prints a given integer matrix with the corresponding name upfront.
   *
   *  @param  A     The integer matrix to be printed
   *  @param  name  The name of the matrix (printed before the matrix)
   */
  public void printMatrix (int[][] A, String name) {
    System.out.println(name + ":");
    for (int i = 0; i < A.length; ++i) {
      for (int j = 0; j < A.length; ++j) {
        System.out.print(" " + A[i][j]);
      }
      System.out.println();
    }
  }

  /**
   *  Computes the boolean product witness matrix (BPWM) for the product of two
   *  boolean matrices A and B, i.e., P = A * B, using the trival algorithm.
   *  Time complexity: O(n^3).
   *
   *  @param  A The first boolean matrix
   *  @param  B The second boolean matrix
   *
   *  @param  A witness matrix for P = A * B
   */
  public int[][] trivialBPWM (int[][] A, int[][] B) {
    int[][] W = new int[A.length][A.length];

    int j = 0, k = 0;
    // for all (i, j) in {1, ..., n}^2, test all k in {1, ..., n}
    for (int i = 1; i < W.length; ++i) {
      for (j = 1; j < W.length; ++j) {
        for (k = 1; k < W.length; ++k) {
          if (A[i][k] == 1 && B[k][j] == 1) {
            W[i][j] = k;
            break;
          }
        }
      }
    }

    return W;
  }

  private int numberOfNodes = 0;
  private int[][] A = null; // adjacency matrix
}
