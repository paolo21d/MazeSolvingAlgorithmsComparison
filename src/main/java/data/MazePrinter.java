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
     * This method saves maze structure to file in special format, which can be read by app later.
     *
     * @param fileName String having name of file without extension
     * */
    void saveMazeStructureToFile(String fileName) throws IOException;
}
