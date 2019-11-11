package runingModes;

import data.Maze;
import data.MazeSolvingComparator;

public class SimpleMode {
    public static void main(String[] args) {
        /*Maze maze = new Maze();
        maze.generateMazeStructure(25, 25);
        maze.BFS();
        System.out.println(maze.getSimplifiedMazeSolution());
        maze.IDFS();
        System.out.println(maze.getSimplifiedMazeSolution());
        maze.DFS();
        System.out.println(maze.getSimplifiedMazeSolution());*/

        //MazeSolvingComparator.simpleComparator(1000, 1000);
        prepareJavaMachine();
        MazeSolvingComparator.averageComparator(10, 10, 20);
    }

    private static void prepareJavaMachine() {
        Maze maze = new Maze();
        maze.generateMazeStructure(200, 200);
        maze.IDFS();
    }
}
