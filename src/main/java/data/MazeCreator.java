package data;

public interface MazeCreator {

    /**
     * This method reads maze structure from file
     * File structure
     * ###O##
     * ##OO##
     * #OO###
     * #O####
     *
     * # - wall (impassible)
     * O - empty space (passable)
     */
    void readMazeStructureFromFile();

    /**
     * This method generates random maze structure
     * @param x Integer size of maze x axis
     * @param y Integer size of maze y axis
     */
    void generateMazeStructure(Integer x, Integer y);
}
