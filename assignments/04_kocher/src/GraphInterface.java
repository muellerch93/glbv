/**
 *  A simple interface to specify the input to the APSP problem
 */
public interface GraphInterface {
  /**
   *  Initialize the graph with the given number of nodes.
   * 
   *  @param  numberOfNodes The number of nodes
	 */
	public void setNumberOfNodes (int numberOfNodes);

  /**
   *  Add an undirected edge to the graph.
   * 
   *  @param  nodeA One node incident at the edge
   *  @param  nodeB The other node incident at the edge
   */
	public void addEdge (int nodeA, int nodeB);

  /**
   *  Solve the APSP problem.
   * 
   *  @return A matrix A[i,j] with the next hop on the shortest path from node i
   *          to node j
  */
	public int[][] APSP ();

  /**
   *  Get the base index for the node IDs.
   *
   *  @return Either 0 or 1, the index of the first node
   */
  public int getBase ();
}
