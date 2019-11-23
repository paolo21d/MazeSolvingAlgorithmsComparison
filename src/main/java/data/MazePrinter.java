package data;

import java.io.IOException;

public interface MazePrinter {

    /**
     * This method returns simplified view of maze
     * @return String contains x - maze columns, y - maze rows and structure which represents whole structure of maze
     */
    String getSimplifiedMazeStructure();


    /**
     * This method returns simplified view of maze with solution path
     * x - path
     * B - start of maze
     * E - end of maze
     * @return String contains x - maze columns, y - maze rows and structure which represents structure of maze
     */
    String getSimplifiedMazeSolution();

    /**
     * This method save maze structure to file in special format, which can be read by app later
     * */
    boolean saveMazeStructureToFile(String path) throws IOException;
}
