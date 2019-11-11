package runingModes;

import data.Maze;

public class SimpleMode {
    public static void main(String[] args){
        Maze maze = new Maze();
        maze.generateMazeStructure(15, 20);
        maze.DFS();
        System.out.println(maze.getSimplifiedMazeSolution());
        maze.IDFS();
        System.out.println(maze.getSimplifiedMazeSolution());
    }
}
