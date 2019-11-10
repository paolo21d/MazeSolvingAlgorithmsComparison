package runingModes;

import data.Maze;

public class SimpleMode {
    public static void main(String[] args){
        Maze maze = new Maze();
        maze.generateMazeStructure(1000, 1000);
        System.out.println(maze.getSimplifiedMazeStructure());
    }
}
