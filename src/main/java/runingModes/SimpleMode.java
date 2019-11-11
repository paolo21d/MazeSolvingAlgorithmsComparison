package runingModes;

import data.Maze;

public class SimpleMode {
    public static void main(String[] args) {
        Maze maze = new Maze();
        maze.generateMazeStructure(25, 25);
        maze.BFS();
        System.out.println(maze.getSimplifiedMazeSolution());
        maze.IDFS();
        System.out.println(maze.getSimplifiedMazeSolution());
        maze.DFS();
        System.out.println(maze.getSimplifiedMazeSolution());
    }
}
