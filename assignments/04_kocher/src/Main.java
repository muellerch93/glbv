/**
 *  Assignment 04, Advanced Algorithms & Data Structures, Summer term 2016.
 *  Department of Computer Sciences, University of Salzburg.
 *
 *  @author Christian Mueller, 1123410
 *  @author Daniel Kocher, 0926293
 */
public class Main {
  public static void main (String[] args) {
    Graph g = new Graph();
    int numberOfNodes = 11;

    g.setNumberOfNodes(numberOfNodes);

    g.addEdge(1, 2);
    g.addEdge(1, 4);
    g.addEdge(2, 3);
    g.addEdge(2, 4);
    g.addEdge(2, 5);
    g.addEdge(3, 4);
    g.addEdge(4, 10);
    g.addEdge(5, 6);
    g.addEdge(5, 9);
    g.addEdge(6, 7);
    g.addEdge(6, 8);
    g.addEdge(7, 11);
    g.addEdge(10, 11);

    int[][] apsp = g.APSP();
    g.printMatrix(apsp, "APSP");
  }
}
