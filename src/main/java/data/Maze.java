package data;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Maze implements MazeCreator, MazeSolver, MazePrinter{
    private List<List<Node>> mazeStructure;
    private Integer sizeX, sizeY;

    public void readMazeStructureFromFile() {

    }

    public void generateMazeStructure(Integer x, Integer y) {

    }

    public String getSimplifiedMazeStructure() {
        return null;
    }

    public List<Node> BFS() {
        return null;
    }

    public List<Node> DFS() {
        return null;
    }

    public List<Node> IDFS() {
        return null;
    }






    private Node getNode(Integer x, Integer y) {
        return mazeStructure.get(x).get(y);
    }


}
