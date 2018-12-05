import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import  java.util.Map;
import java.util.Set;
import  java.util.PriorityQueue;
import  java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Kellen Haynes
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     * <p>
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     * <p>
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     * <p>
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if {@code start} doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                                         Graph<T> graph) {
        List<Vertex<T>> returnList = new LinkedList<>();
        List<VertexDistance<T>> distance;
        Queue<Vertex<T>> q = new LinkedList<>();
        int size = 1;
        returnList.add(start);
        q.add(start);

        Vertex<T> currentVertex;
        while (size < graph.getVertices().size() && q.peek() != null) {
            currentVertex = q.remove();
            distance = graph.getAdjList().get(currentVertex);
            for (VertexDistance item : distance) {
                if (!returnList.contains(item.getVertex())) {
                    returnList.add(item.getVertex());
                    q.add(item.getVertex());
                    size++;
                }
            }
        }
        return returnList;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     * <p>
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     * <p>
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     * <p>
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if {@code start} doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                                       Graph<T> graph) {

        List<Vertex<T>> returnList = new LinkedList<>();
        HashSet vS = new HashSet();
        return recursive(start, graph, returnList, vS);

    }

    /**

     * recursivley performs dfs
     * @param <T>   the generic typing of the data
     * @param v the vertex to begin the recursion on
     * @param g the graph to search
     * @param returnList list t return
     * @param vS list of verticies already visited
     * @return list of vertices in visited order**/
    private static <T> List<Vertex<T>> recursive(Vertex<T> v,
                                                 Graph<T> g,
                                                 List<Vertex<T>> returnList,
                                                 HashSet vS) {
        if (!vS.contains(v)) {
            returnList.add(v);
            vS.add(v);
            for (VertexDistance item : g.getAdjList().get(v)) {
                if (!vS.contains(item.getVertex())) {
                    recursive(item.getVertex(), g, returnList, vS);
                }

            }
        }
        return returnList;
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {

        ArrayList<VertexDistance<T>> q = new ArrayList<>();
        Map<Vertex<T>, Integer> distances = new HashMap<>();
        List<VertexDistance<T>> adj;
        int size = 0;
        q.add(new VertexDistance<>(start, 0));
        VertexDistance<T> currentVertex = q.get(0);
        int smallestIndex = 0;
        HashSet<Vertex<T>> vS = new HashSet();

        while (vS.size() < graph.getVertices().size() && !q.isEmpty()) {
            currentVertex = q.get(smallestIndex);
            adj = graph.getAdjList().get(currentVertex.getVertex());
            //System.out.print(currentVertex.getVertex()+ "\t");
            //System.out.println(VS.size());
            for (VertexDistance<T> item : adj) {
                if (item != null && !vS.contains(item.getVertex())
                        && !q.contains(item)) {
                    q.add(new VertexDistance<>(item.getVertex(),
                            item.getDistance() + currentVertex.getDistance()));
                }

            }
            if (!vS.contains(currentVertex.getVertex())) {
                distances.putIfAbsent(currentVertex.getVertex(),
                        currentVertex.getDistance());
                vS.add(currentVertex.getVertex());
            }
            size++;
            q.remove(smallestIndex);
            smallestIndex = 0;
            for (VertexDistance<T> thing : q) {
                if (thing != null) {
                    if (thing.getDistance()
                            < q.get(smallestIndex).getDistance()) {
                        smallestIndex = q.indexOf(thing);
                    }
                }
            }
        }
        return distances;

    }


    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     * <p>
     * You should NOT allow self-loops or parallel edges into the MST.
     * <p>
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        PriorityQueue<Edge<T>> pQ = new PriorityQueue<>(graph.getEdges());
        DisjointSet<Vertex<T>> dS = new DisjointSet(graph.getVertices());
        Set<Edge<T>> mST = new HashSet<>();
        int size = 0;
        Edge<T> currentEdge;
        while (!pQ.isEmpty() && mST.size()
                < 2 * (graph.getVertices().size() - 1)) {
            currentEdge = pQ.remove();
            if (!dS.find(currentEdge.getU())
                    .equals(dS.find(currentEdge.getV()))) {
                dS.union(currentEdge.getU(), currentEdge.getV());
                mST.add(currentEdge);
                mST.add((new Edge<>(currentEdge.getV(),
                        currentEdge.getU(), currentEdge.getWeight())));
            }
        }
        System.out.print(mST.size() + "\t");
        System.out.println(graph.getVertices().size() - 1);
        if (mST.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return mST;
    }
}
