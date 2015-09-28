// Copy paste this Java Template and save it as "HospitalTour.java"

import java.util.*;
import java.io.*;

// write your matric number here:
// write your name here:
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class HospitalTour {
    private int V; // number of vertices in the graph (number of rooms in the hospital)
    private int[][] AdjMatrix; // the graph (the hospital)
    private int[] RatingScore; // the weight of each vertex (rating score of each room)

    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    private int[] visited;
    private int[] parent;

    public HospitalTour() {
        // Write necessary code during construction
        //
        // write your answer here


    }

    int Query() {
        int ans = 0;

        // You have to report the rating score of the important room (vertex)
        // with the lowest rating score in this hospital
        //
        // or report -1 if that hospital has no important room
        //
        // write your answer here

        // Initialize all values of visited to be zero and parent to be -1
        visited = new int[V];
        parent = new int[V];

        ans = getLowestRating(getArticulationPoints());

        return ans;
    }

    // You can add extra function if needed
    // --------------------------------------------

    public void DFS(int[][] matrix, int vertex) {
        visited[vertex] = 1;
        for (int i = 0; i < V; i++) {
            if (matrix[vertex][i] == 1) {
                if (visited[i] == 0) {
                    parent[i] = vertex;
                    DFS(matrix, i);
                }
            }
        }
    }

    // Reinitialize the visited and parent arrays after every run of DFS
    public void initArrays() {
        for (int i = 0; i < V; i++) {
            visited[i] = 0;
            parent[i] = -1;
        }
    }

    public int getLowestRating(ArrayList<Integer> articulationPoints) {
        if (articulationPoints.isEmpty()) {
            return -1;
        } else {
            int lowestRating = RatingScore[articulationPoints.get(0)];
            for (int i = 1; i < articulationPoints.size(); i++) {
                if (RatingScore[articulationPoints.get(i)] < lowestRating) {
                    lowestRating = RatingScore[articulationPoints.get(i)];
                }
            }
            return lowestRating;
        }
    }

    public int[][] duplicateMatrix(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    public boolean isStillConnected(int[] original, int[] visited, int deletedVertex) {
        boolean connected = true;
        for (int i = 0; i < V; i++) {
            if ((i != deletedVertex) && (original[i] != visited[i])) {
                connected = false;
            }
        }
        return connected;
    }

    public ArrayList<Integer> getArticulationPoints() {
        ArrayList<Integer> articulationPoints = new ArrayList<Integer>();

        if (V != 1) {
            // Run a preliminary DFS to get the connected graph in visited
            DFS(AdjMatrix, 0);

            int originalVisited[] = new int[V];
            for (int i = 0; i < V; i++) {
                originalVisited[i] = visited[i];
            }

            // Run a loop through every vertex
            for (int i = 0; i < V; i++) {
                // Make a temporary copy to do deletions
                int[][] tempMatrix = duplicateMatrix(AdjMatrix);
                // For every value of i, remove this value from the Adjacency Matrix
                // and keep it in a temporary variable
                for (int j = 0; j < V; j++) {
                    if (j == i) {
                        // Replace the entire ith row with 0
                        for (int l = 0; l < V; l++) {
                            tempMatrix[i][l] = 0;
                        }
                    } else {
                        // Replace just the ith column
                        tempMatrix[j][i] = 0;
                    }
                }
                initArrays();
                if (i == 0) {
                    DFS(tempMatrix, 1);
                } else {
                    DFS(tempMatrix, i - 1);
                }

                if (!isStillConnected(originalVisited, visited, i)) {
                    articulationPoints.add(i);
                }
            }
        }

        return articulationPoints;
    }

    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print('\n');
        }
        System.out.println('\n');
    }

    // --------------------------------------------

    void run() throws Exception {
        // for this PS3, you can alter this method as you see fit

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int TC = Integer.parseInt(br.readLine()); // there will be several test cases
        while (TC-- > 0) {
            br.readLine(); // ignore dummy blank line
            V = Integer.parseInt(br.readLine());

            StringTokenizer st = new StringTokenizer(br.readLine());
            // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
            RatingScore = new int[V];
            for (int i = 0; i < V; i++)
                RatingScore[i] = Integer.parseInt(st.nextToken());

            // clear the graph and read in a new graph as Adjacency Matrix
            AdjMatrix = new int[V][V];
            for (int i = 0; i < V; i++) {
                st = new StringTokenizer(br.readLine());
                int k = Integer.parseInt(st.nextToken());
                while (k-- > 0) {
                    int j = Integer.parseInt(st.nextToken());
                    AdjMatrix[i][j] = 1; // edge weight is always 1 (the weight is on vertices now)
                }
            }

            pr.println(Query());
        }
        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        HospitalTour ps3 = new HospitalTour();
        ps3.run();
    }
}


class IntegerPair implements Comparable<IntegerPair> {
    Integer _first, _second;

    public IntegerPair(Integer f, Integer s) {
        _first = f;
        _second = s;
    }

    public int compareTo(IntegerPair o) {
        if (!this.first().equals(o.first()))
            return this.first() - o.first();
        else
            return this.second() - o.second();
    }

    Integer first() {
        return _first;
    }

    Integer second() {
        return _second;
    }
}