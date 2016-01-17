import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

public class Graph {
    protected List<Vertex> vertices = new ArrayList<Vertex>();
    protected Set<Edge> edges = new HashSet<Edge>();

    public class Vertex {
        public String toString(){
            return "" + vertices.indexOf(this);
        }
    }

    public class Edge {
        public Edge(Vertex from, double cost, Vertex to){
            this.to = to;
            this.cost = cost;
            this.from = from;
        }
        public final Vertex to;
        public final Vertex from;
        public final double cost;
        public String toString(){
            return from + " -(" + cost + ")> " + to;
        }
    }

    public class Path {
        public Path(List<Vertex> verticesToPass, double totalCost){
            this.verticesToPass = verticesToPass;
            this.totalCost = totalCost;
        }
        public final List<Vertex> verticesToPass;
        public final double totalCost;
        public String toString(){
            return totalCost + " " + verticesToPass;
        }
    }

    public class Direction{
        public final Vertex from;
        public final Vertex to;
        public Direction(Vertex from, Vertex to){
            this.from = from;
            this.to = to;
        }
        public String toString(){
            return from + " -> " + to;
        }
    }

    public Graph(double[][] matrix){
        for (int i = 0; i < matrix.length; ++i) {
            vertices.add(new Vertex());
        }
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                if(matrix[i][j] != Double.POSITIVE_INFINITY){
                    Vertex from = vertices.get(i);
                    Vertex to = vertices.get(j);
                    double cost = matrix[i][j];
                    edges.add(new Edge(from, cost, to));
                }
            }
        }
    }

    public String toString(){
        String result = "";
        for (Edge e: edges) {
            result += e.toString() + "\n";
        }
        return result;
    }

    public Map<Direction, Path> shortestPaths(){
        Map<Direction, Path> result = new HashMap<Direction, Path>();
        for (Vertex v : vertices) {
            for(Map.Entry<Vertex, Path> e : pathesFrom(v).entrySet()){
                result.put(new Direction(v, e.getKey()),
                        e.getValue());
            }
        }
        return result;
    }

    protected Map<Vertex, Path> pathesFrom(Vertex origin){
        List<Vertex> subgraph = new ArrayList<Vertex>();
        Map<Vertex, Path> pathes = new HashMap<Vertex, Path>();

        // the origin has cost 0 from itself
        subgraph.add(origin);
        pathes.put(origin, new Path(new ArrayList<Vertex>(), 0));

        while(subgraph.size() < vertices.size()){
            Edge bestEdge = null;
            for(Edge e : edges)
                if(subgraph.contains(e.from) && !subgraph.contains(e.to) &&
                        (bestEdge == null || e.cost < bestEdge.cost)){
                    bestEdge = e;
                        }

            // if we can't find any edge, we're finished here
            if(bestEdge == null)
                break;

            // add new path to dictionnary
            Path oldPath = pathes.get(bestEdge.from);

            List<Vertex> newPathVertices = new ArrayList<Vertex>(oldPath.verticesToPass);
            newPathVertices.add(bestEdge.to);

            double newPathCost = oldPath.totalCost + bestEdge.cost;
            Path newPath = new Path(newPathVertices, newPathCost);

            pathes.put(bestEdge.to, newPath);
            subgraph.add(bestEdge.to);
        }
        return pathes;
    }
}
