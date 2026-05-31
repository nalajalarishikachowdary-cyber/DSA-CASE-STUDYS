import java.util.*;

public class TarjanSCC {

    private int V;
    private List<List<Integer>> adj;

    private int time;
    private int[] disc;
    private int[] low;
    private boolean[] onStack;
    private Stack<Integer> stack;

    public TarjanSCC(int V) {

        this.V = V;

        adj = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        disc = new int[V];
        low = new int[V];
        onStack = new boolean[V];
        stack = new Stack<>();

        Arrays.fill(disc, -1);
        time = 0;
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }

    private void tarjanDFS(int u) {

        disc[u] = low[u] = ++time;

        stack.push(u);
        onStack[u] = true;

        for (int v : adj.get(u)) {

            if (disc[v] == -1) {

                tarjanDFS(v);

                low[u] = Math.min(low[u], low[v]);
            }
            else if (onStack[v]) {

                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {

            System.out.print("SCC: ");

            while (true) {

                int node = stack.pop();
                onStack[node] = false;

                System.out.print("m" + (node + 1) + " ");

                if (node == u)
                    break;
            }

            System.out.println();
        }
    }

    public void findSCCs() {

        for (int i = 0; i < V; i++) {

            if (disc[i] == -1) {
                tarjanDFS(i);
            }
        }
    }

    public static void main(String[] args) {

        TarjanSCC g = new TarjanSCC(8);

        g.addEdge(0, 1); // m1→m2
        g.addEdge(1, 2); // m2→m3

        g.addEdge(2, 0); // m3→m1
        g.addEdge(2, 3); // m3→m4

        g.addEdge(3, 4); // m4→m5
        g.addEdge(4, 5); // m5→m6

        g.addEdge(5, 3); // m6→m4
        g.addEdge(5, 6); // m6→m7

        g.addEdge(6, 7); // m7→m8
        g.addEdge(7, 6); // m8→m7

        System.out.println("Strongly Connected Components:");

        g.findSCCs();
    }
}