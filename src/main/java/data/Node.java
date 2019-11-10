package data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Node {
    private Integer x;
    private Integer y;
    private List<Node> neighbours;

    public Node() {
        neighbours = new ArrayList<>();
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        neighbours = new ArrayList<>();
    }

    public void addNeighbour(Node n) {
        if (!neighbours.contains(n))
            neighbours.add(n);
    }
}
