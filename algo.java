import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class algo {

    public static Path UniformCostSearch(String sourceAirportID, String destinationAirportID, ArrayList<String[]> map) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashSet<String> closedList = new HashSet<>();

        System.out.println("About to do the search on the problem: ");
        Node initialNode = new Node(sourceAirportID);
        openList.add(initialNode);

        while (openList.size() > 0) {
            Node node = openList.poll();

            if (destinationAirportID.equals(node.getState())) {
                System.out.println("Yay, found a solution!");
                return node.path();
            } else {
                closedList.add(node.getState());
                System.out.println("Expanding node: " + node);

                try {
                    ArrayList<String> neighbouringAirports = new ArrayList<>();

                    for (String[] routeArray: map) {
                        if (!(routeArray[3] == "" && routeArray[5] == "")) {
                            if (node.getState().equals(routeArray[3])) {
                                neighbouringAirports.add(routeArray[5]);
                            }
                        }
                    }

                    for (String airportID: neighbouringAirports) {
                        Node child = new Node(airportID, node, node.getPathCost() + 1);

                        if (!closedList.contains(child.getState()) && !openList.contains(child)) {
                            openList.add(child);
                        } else if (openList.contains(child)) {
                            int cost = openList.peek().getPathCost();
                            if (cost > child.getPathCost()) {
                                openList.poll();
                                openList.add(child);
                            }
                        }
                    }
                } catch (NullPointerException ne) {
                    System.out.println(ne.getMessage());
                }
            }
        }
        System.out.println("After the rather frustrating search, I couldn't find any valid route.");
        return null;
    }
}

class Node implements Comparable<Node> {
    private String state;
    private Node parent;
    private int pathCost;

    public Node(String state) {
        this.state = state;
        this.parent = null;
        this.pathCost = 0;
    }

    /**
     *
     * @param state the airport Id
     * @param parent the entire node of the calling object
     * @param pathCost number of flights taken to get this state
     */
    public Node(String state, Node parent, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.pathCost = pathCost;
    }

    public String getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public int getPathCost() {
        return pathCost;
    }

    @Override
    public String toString() {
        return "Node{" +
                "state='" + state + '\'' +
                ", parent=" + parent +
                ", pathCost=" + pathCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return getState().equals(node.getState());
    }

    @Override
    public int compareTo(Node o) {
//        if (this.getPathCost() > o.getPathCost()) {
//            return 1;
//        }
//        else if (this.getPathCost() < o.getPathCost()) {
//            return -1;
//        }
//        else {
//            return 0;
//        }
        return Double.compare(this.getPathCost(), o.getPathCost());
    }

    public Path path() {
        ArrayList<String> actionSequence = new ArrayList<>();
        actionSequence.add(0, this.state);
        Integer pathCost = this.getPathCost();

        Node node = this.parent;
        while (!(node == null)) {
            actionSequence.add(0, node.getState());
            node = node.getParent();
        }

        return new Path(actionSequence, pathCost);
    }
}

class Path implements Comparable<Path> {
    ArrayList<String> actionSequence;
    int pathCost;

    public Path(ArrayList<String> actionSequence, int pathCost) {
        this.actionSequence = actionSequence;
        this.pathCost = pathCost;
    }

    public ArrayList<String> getActionSequence() {
        return actionSequence;
    }

    public int getPathCost() {
        return pathCost;
    }

    @Override
    public int compareTo(Path o) {
        return Double.compare(this.getPathCost(), o.getPathCost());
    }
}