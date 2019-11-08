package data;


import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Getter
@Setter
public class Maze implements MazeCreator, MazeSolver, MazePrinter {
    private List<Node> mazeStructure;
    private Node beginOfMaze;
    private Node endOfMaze;
    private Integer sizeX, sizeY;

    public void readMazeStructureFromFile() {

    }

    public void generateMazeStructure(Integer x, Integer y) {

    }

    public String getSimplifiedMazeStructure() {
        return null;
    }

    public List<Node> BFS() {
        List<Boolean> visited = prepareVisitedList();
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(beginOfMaze);
        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();
            setVisitedFromCoordinates(visited, node.getX(), node.getY(), true);
            if (checkMazeEnd(node))
                break;
            for (Node neighbour : node.getNeighbours()) {
                if (isVisitedFromCoordinates(visited, neighbour.getX(), neighbour.getY()))
                    continue;
                nodeQueue.add(neighbour);
            }
        }

        //TODO Implement backtracking (for every node I have to know from which node I came)
        return null;
    }

    public List<Node> DFS() {
        return null;
    }

    public List<Node> IDFS() {
        return null;
    }

    /**
     * @param x - X coordinate of Node
     * @param y - Y coordinate of Node
     * @return Node which have got coordinates (x,y)
     */
    private Node getNode(Integer x, Integer y) {
        return mazeStructure.get(y * sizeX + x);
    }

    private List<Boolean> prepareVisitedList() {
        List<Boolean> visited = Arrays.asList(new Boolean[getSizeX() * getSizeY()]);
        visited.forEach(v -> v = false);
        return visited;
    }

    private Boolean isVisitedFromCoordinates(List<Boolean> visited, Integer x, Integer y) {
        return visited.get(y * sizeX + x);
    }

    private void setVisitedFromCoordinates(List<Boolean> visited, Integer x, Integer y, Boolean value) {
        Boolean v = visited.get(y * sizeX + x);
        v = value;
    }

    private Boolean checkMazeEnd(Node node) {
        return endOfMaze == node;
    }
}
