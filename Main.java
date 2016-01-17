import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static Graph readFile(BufferedReader file) throws IOException {
        int lineCount = Integer.parseInt(file.readLine().replaceAll("\\s+",""));
        double[][] matrix = new double[lineCount][lineCount];

        // read matrix
        for (int i = 0; i < lineCount; i++) {
            String[] line = file.readLine().split("\\s+");
            for (int j = 0; j < lineCount; j++) {
                double val = Integer.parseInt(line[j]);
                matrix[i][j] = val >= 999 ? Double.POSITIVE_INFINITY : val;
            }
        }
        // translate to graph
        Graph graph = new Graph(matrix);
        return graph;
    }

    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Usage: java Main [file.dat...]");
            return;
        }
        try {
            for (String filePath : args) {
                BufferedReader file = new BufferedReader(new FileReader(filePath));
                Graph graph = readFile(file);
                for (Map.Entry<Graph.Direction, Graph.Path> e :
                        graph.shortestPaths().entrySet()) {
                    Graph.Direction direction = e.getKey();
                    Graph.Path path = e.getValue();
                    System.out.println(direction + " " + path);
                }
            }
        } catch(IOException e){
            System.out.println("Unable to read file: "+e);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
