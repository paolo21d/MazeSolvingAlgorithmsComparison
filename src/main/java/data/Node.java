package data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Node {
    private Integer x;
    private Integer y;
    private List<Node> neighbours;
}
