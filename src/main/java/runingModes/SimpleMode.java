package runingModes;

import comparators.MazeSolvingComparator;
import data.Maze;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class SimpleMode {
    static boolean JVMReady = false;
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
        //prepareJavaMachine();
        //MazeSolvingComparator.averageComparator(10, 10, 20);


        //MazeSolvingComparator.averageMultiThreadComparator(300, 300, 50);
        menu();
    }

    private static void prepareJavaMachine() {
        if(JVMReady)
            return;
        Maze maze = new Maze();
        maze.generateMazeStructure(200, 200);
        maze.IDFS();
        JVMReady = true;
    }

    private static void menu() {
        String mainMenuMessage = "Choose option:\n" +
                "1\tRead maze structure from file\n" +
                "2\tGenerate maze structure\n" +
                "3\tPrint stats\n" +
                "0\tExit";
        String loadedMenuMessage = "Choose option:\n" +
                "1\tPrint maze structure\n" +
                "2\tShow maze solution\n" +
                "3\tPrint stats\n" +
                "0\tMain menu";
        String generatedMenuMessage = "Choose option:\n" +
                "1\tPrint maze structure\n" +
                "2\tShow maze solution\n" +
                "3\tPrint stats\n" +
                "4\tSave structure to file\n" +
                "0\tMain menu";

        boolean exit = false;
        boolean mainMenu = true;
        boolean generatedMenu = false;
        boolean loadedMenu = false;
        Scanner in = new Scanner(System.in);
        String option;
        Integer sizeX, sizeY, quantityOfTries;
        Maze maze = null;

        while (!exit) {
            if (mainMenu) {
                System.out.println(mainMenuMessage);
                option = in.nextLine();
                switch (option) {
                    case "1":  //read from file
                        System.out.println("Enter file path");
                        String path = in.nextLine();
                        maze = new Maze();
                        try {
                            maze.readMazeStructureFromFile(path);
                        } catch (IOException e) {
                            System.out.println(e.toString());
                            maze = null;
                            continue;
                        }
                        loadedMenu = true;
                        mainMenu = false;
                        break;
                    case "2":  //generate maze
                        System.out.println("Maze size X:");
                        sizeX = Integer.parseInt(in.nextLine());
                        System.out.println("Maze size Y:");
                        sizeY = Integer.parseInt(in.nextLine());
                        maze = new Maze();
                        try {
                            maze.generateMazeStructure(sizeX, sizeY);
                        } catch (InvalidParameterException e) {
                            System.out.println("Invalid parameters!");
                            maze = null;
                            continue;
                        }
                        generatedMenu = true;
                        mainMenu = false;
                        break;
                    case "3": //Print stats
                        System.out.println("Maze size X:");
                        sizeX = Integer.parseInt(in.nextLine());
                        System.out.println("Maze size Y:");
                        sizeY = Integer.parseInt(in.nextLine());
                        System.out.println("Quantity of tries:");
                        quantityOfTries = Integer.parseInt(in.nextLine());
                        System.out.println("Calculating...");
                        try {
                            prepareJavaMachine();
                            MazeSolvingComparator.averageComparator(sizeX, sizeY, quantityOfTries);
                        } catch (InvalidParameterException e) {
                            System.out.println("Invalid parameter!");
                        }
                        break;
                    case "0": //close app
                        exit = true;
                        break;
                }

            }
            else if (generatedMenu && maze != null) { //maze structure randomly generated
                System.out.println(generatedMenuMessage);
                option = in.nextLine();
                switch (option) {
                    case "1":  //print structure
                        System.out.println(maze.getSimplifiedMazeStructure());
                        break;
                    case "2":  //show solution
                        System.out.println(maze.getSimplifiedMazeSolution());
                        break;
                    case "3":  //print stats
                        MazeSolvingComparator.simpleComparator(maze);
                        break;
                    case "4":  //save structure to file
                        System.out.println("Enter name of file:");
                        String fileName = in.nextLine();
                        try {
                            maze.saveMazeStructureToFile(fileName);
                        } catch (IOException e) {
                            System.out.println(e.toString());
                        }
                        break;
                    case "0":  //back to main menu
                        mainMenu = true;
                        generatedMenu = false;
                        break;
                }

            }
            else if (loadedMenu && maze != null) { //maze structure loaded from file
                System.out.println(loadedMenuMessage);
                option = in.nextLine();
                switch (option) {
                    case "1":  //print structure
                        System.out.println(maze.getSimplifiedMazeStructure());
                        break;
                    case "2":  //show solution
                        System.out.println(maze.getSimplifiedMazeSolution());
                        break;
                    case "3":  //print stats
                        MazeSolvingComparator.simpleComparator(maze);
                        break;
                    case "0":  //back to main menu
                        loadedMenu = false;
                        mainMenu = true;
                        break;
                }
            }
        }

    }
}
