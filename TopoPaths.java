// Rhami Thrower
// COP 3503
// Spring 2023
// rh2708888

// Imports bellow
import java.util.*;
import java.io.*;
// Classes bellow

// class to help store a graph
class Graph
{
    // variables
    public boolean [][] matrix;
    private int [] start; // plausable starts
    public int [] end; // plausable ends
    private int vertices;
    // constructors
    public Graph(String fileName) throws IOException
    {
        // variables
        File file = new File(fileName);
        Scanner read = new Scanner(file);
        Scanner readCol;
        int totalVertices;
        String row;
        int totalEdges;
        int edge;
        int vertice = 0;

        totalVertices = read.nextInt();
        vertices = totalVertices;
        matrix = new boolean[totalVertices][totalVertices];
        start = new int[totalVertices];
        end = new int[totalVertices];
        read.nextLine();
        while(read.hasNext())// iterate by row
        {
            row = read.nextLine(); // gets nxt row
            readCol = new Scanner(row);
            totalEdges = readCol.nextInt();
            end[vertice] = totalEdges;
            for (int e = 0; e < totalEdges; e++)
            {
                edge = readCol.nextInt(); // connected vertice
                matrix[vertice][edge-1] = true;
                start[edge-1]++;
            }
            vertice++;
        }
    }

    public int[] getStart()
    {
        return start;
    }
    public int getNumVertices()
    {
        return vertices;
    }
}

public class TopoPaths
{
    public static int countTopoPaths(String filename) throws IOException
    {
        // variables
        Graph graph = new Graph(filename);
        boolean [] visited = new boolean[graph.getNumVertices()];
        int [] incomingEdges = graph.getStart();
        int countTP = 0;

        for (int s = 0; s < graph.getNumVertices(); s++)
        {
            if (incomingEdges[s] == 0)
            {
                visited[s] = true;
                countTP += countTopoPaths(s, visited, incomingEdges, graph);
            }  
        }
        return countTP;
    }

    private static int countTopoPaths(int last, boolean [] visited, 
    int [] incomingEdges, Graph graph) throws IOException
    {
        int count = 0;
        boolean valid = true;

        for (int i = 0; i < graph.getNumVertices(); i++)
        {
            if (visited[i] == false)
            {
                valid = false;
                break;
            }
        }
        // base case

        if (valid) // reached end
        {
            return (count + 1);
        }

        // change in-edges
        for (int i = 0; i < graph.getNumVertices(); i++)
        {
            if (graph.matrix[last][i] == true)
                incomingEdges[i]--;
        }

        for (int i = 0; i < graph.getNumVertices(); i++)
        {
            if (graph.matrix[last][i] == true && incomingEdges[i] <= 0 && !visited[i])
            {
                visited[i] = true;
                count += countTopoPaths(i, visited, incomingEdges, graph);
                visited[i] = false;
            }
        }
        for (int i = 0; i < graph.getNumVertices(); i++)
        {
            if (graph.matrix[last][i] == true)
                incomingEdges[i]++;
        }
        return count;
    }
    public static double difficultyRating()
    {
        return 2.5;
    }
    public static double hoursSpent()
    {
        return 8.5;
    }
}