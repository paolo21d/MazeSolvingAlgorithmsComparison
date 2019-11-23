package data;

import java.io.IOException;

public interface MazeCreator {

    /**
     * This method reads maze structure from file
     * File structure:
     * mazeSizeX mazeSizeY
     * mazeBeginx mazeBeginY
     * mazeEndX mazeEndY
     * curves between nodes, each in new line eg: 0 0 1 1
     *
     */
    void readMazeStructureFromFile(String path) throws IOException;

    /**
     * This method generates random maze structure
     * @param x Integer size of maze x axis
     * @param y Integer size of maze y axis
     */
    void generateMazeStructure(Integer x, Integer y);
}
