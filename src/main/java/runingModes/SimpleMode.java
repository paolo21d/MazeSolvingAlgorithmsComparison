package runingModes;

import comparators.MazeSolvingComparator;
import data.Maze;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class SimpleMode {
    static boolean JVMReady = false;

    public static void main(String[] args) {
        prepareJavaMachine();
        /*System.out.println("Size 10");
        MazeSolvingComparator.averageComparator(10, 10, 150);
        System.out.println("Size 25");
        MazeSolvingComparator.averageComparator(25, 25, 150);
        System.out.println("Size 50");
        MazeSolvingComparator.averageComparator(50, 50, 150);
        System.out.println("Size 100");
        MazeSolvingComparator.averageComparator(100, 100, 100);

        System.out.println("Size 200");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(200, 200, 100, true, true, false);
        System.out.println("Size 500");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(500, 500, 100, true, true, false);
        System.out.println("Size 700");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(700, 700, 80, true, true, false);
        System.out.println("Size 1000");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(1000, 1000, 60, true, true, false);
        System.out.println("Size 1500");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(1500, 1500, 40, true, true, false);
        System.out.println("Size 2000");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(2000, 2000, 20, true, true, false);*/

        //Compare without IDFS
        /*System.out.println("Size 10");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(10, 10, 100, true, true, false);
        System.out.println("Size 25");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(25, 25, 100, true, true, false);
        System.out.println("Size 50");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(50, 50, 100, true, true, false);
        System.out.println("Size 100");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(100, 100, 100, true, true, false);
        System.out.println("Size 200");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(200, 200, 100, true, true, false);
        System.out.println("Size 500");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(500, 500, 100, true, true, false);
        System.out.println("Size 700");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(700, 700, 80, true, true, false);
        System.out.println("Size 1000");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(1000, 1000, 60, true, true, false);
        System.out.println("Size 1500");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(1500, 1500, 40, true, true, false);
        System.out.println("Size 2500");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(2500, 2500, 1, true, true, false);
        System.out.println("Size 3000");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(3000, 3000, 1, true, true, false);
        System.out.println("Size 3500");
        MazeSolvingComparator.averageComparatorChoosingAlgorithm(3500, 3500, 1, true, true, false);*/

        //MazeSolvingComparator.averageMultiThreadComparator(300, 300, 50);
        menu();
    }

    private static void prepareJavaMachine() {
        if (JVMReady)
            return;
        Maze maze = new Maze();
        maze.generateMazeStructure(150, 150);
        maze.IDFS();
        maze.DFS();
        maze.BFS();
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
        prepareJavaMachine();

        while (!exit) {
            if (mainMenu) {
                System.out.println(mainMenuMessage);
                option = in.nextLine();
                try {
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
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input parameter!");
                    mainMenu = true;
                    generatedMenu = false;
                    loadedMenu = false;
                }
            } else if (generatedMenu && maze != null) { //maze structure randomly generated
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

            } else if (loadedMenu && maze != null) { //maze structure loaded from file
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
