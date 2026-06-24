import java.util.*;

class Edge3 {
    int to, fare, time;

    Edge(int to, int fare, int time) {
        this.to = to;
        this.fare = fare;
        this.time = time;
    }
}

class Label {
    int node, fare, time;

    Label(int node, int fare, int time) {
        this.node = node;
        this.fare = fare;
        this.time = time;
    }
}

public class MOSP {

    static boolean dominates(Label a, Label b) {
        return (a.fare <= b.fare && a.time <= b.time) &&
               (a.fare < b.fare || a.time < b.time);
    }

    public static void main(String[] args) {

        int n = 5;
        List<Edge>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Sample graph
        graph[0].add(new Edge(1, 5, 10));
        graph[0].add(new Edge(2, 8, 5));

        graph[1].add(new Edge(3, 4, 8));
        graph[2].add(new Edge(3, 2, 12));

        graph[3].add(new Edge(4, 3, 4));

        List<Label>[] pareto = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            pareto[i] = new ArrayList<>();
        }

        Queue<Label> queue = new LinkedList<>();

        Label start = new Label(0, 0, 0);
        pareto[0].add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {

            Label current = queue.poll();

            for (Edge e : graph[current.node]) {

                Label next = new Label(
                        e.to,
                        current.fare + e.fare,
                        current.time + e.time
                );

                boolean dominated = false;

                for (Label l : pareto[e.to]) {
                    if (dominates(l, next)) {
                        dominated = true;
                        break;
                    }
                }

                if (dominated)
                    continue;

                List<Label> newFrontier = new ArrayList<>();

                for (Label l : pareto[e.to]) {
                    if (!dominates(next, l)) {
                        newFrontier.add(l);
                    }
                }

                newFrontier.add(next);
                pareto[e.to] = newFrontier;

                queue.offer(next);
            }
        }

        int destination = 4;

        System.out.println("Pareto-optimal routes to node " + destination + ":");

        for (Label l : pareto[destination]) {
            System.out.println(
                    "Fare = ₹" + l.fare +
                    ", Time = " + l.time + " mins"
            );
        }
    }
}