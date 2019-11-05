package data;

import java.util.ArrayList;
import java.util.List;

public interface MazeSolver {

    /**
     * This method resolve maze using BFS algorithm
     * @return List<Node> This is maze structure with marked nodes which are in the found path
     */
    List<Node> BFS();

    /**
     * This method resolve maze using DFS algorithm
     * @return List<Node> This is maze structure with marked nodes which are in the found path
     */
    List<Node> DFS();

    /**
     * This method resolve maze using IDFS algorithm
     * @return List<Node> This is maze structure with marked nodes which are in the found path
     */
    List<Node> IDFS();
}
