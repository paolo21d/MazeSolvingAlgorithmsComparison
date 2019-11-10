package runingModes;

import data.Maze;

public class SimpleMode {
    public static void main(String[] args){
        Maze maze = new Maze();
        maze.generateMazeStructure(20, 20);
        maze.printMaze();
    }
}
