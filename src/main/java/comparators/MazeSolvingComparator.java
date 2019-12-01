package comparators;

import data.Maze;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class MazeSolvingComparator {

    public static void simpleComparator(Integer sizeX, Integer sizeY) {
        Maze maze = new Maze();
        maze.generateMazeStructure(sizeX, sizeY);
        simpleComparator(maze);
    }

    public static void simpleComparator(Maze maze) {
        //BFS
        System.out.println("BFS:\t" + getDurationOfBFS(maze) + "ms\t" + maze.getLastRunSteps() + "steps");
        //DFS
        System.out.println("DFS:\t" + getDurationOfDFS(maze) + "ms\t" + maze.getLastRunSteps() + "steps");
        //IDFS
        System.out.println("IDFS:\t" + getDurationOfIDFS(maze) + "ms\t" + maze.getLastRunSteps() + "steps");
    }

    public static void averageComparator(Integer sizeX, Integer sizeY, Integer quantityOfTries) throws InvalidParameterException {
        averageComparatorChoosingAlgorithm(sizeX, sizeY, quantityOfTries, true, true, true);
    }

    public static void averageComparatorChoosingAlgorithm(Integer sizeX, Integer sizeY, Integer quantityOfTries,
                                                          boolean doBFS, boolean doDFS, boolean doIDFS) throws InvalidParameterException {
        Maze maze = new Maze();
        List<Long> durationsOfBFS = new ArrayList<>();
        List<Long> durationsOfDFS = new ArrayList<>();
        List<Long> durationsOfIDFS = new ArrayList<>();

        List<Long> stepsOfBFS = new ArrayList<>();
        List<Long> stepsOfDFS = new ArrayList<>();
        List<Long> stepsOfIDFS = new ArrayList<>();

        for (int i = 0; i < quantityOfTries; i++) {
            maze.generateMazeStructure(sizeX, sizeY);
            if (doBFS) {
                durationsOfBFS.add(getDurationOfBFS(maze));
                stepsOfBFS.add(maze.getLastRunSteps());
            }
            if (doDFS) {
                durationsOfDFS.add(getDurationOfDFS(maze));
                stepsOfDFS.add(maze.getLastRunSteps());
            }
            if (doIDFS) {
                durationsOfIDFS.add(getDurationOfIDFS(maze));
                stepsOfIDFS.add(maze.getLastRunSteps());
            }
        }
        double averageDurationOfBFS, averageDurationOfDFS, averageDurationOfIDFS;
        double averageStepsOfBFS, averageStepsOfDFS, averageStepsOfIDFS;
        if (doBFS) {
            averageDurationOfBFS = getAverage(durationsOfBFS);
            averageStepsOfBFS = getAverage(stepsOfBFS);
            System.out.println("BFS:\t" + averageDurationOfBFS + " ms\t" + averageStepsOfBFS + " steps");
        }

        if (doDFS) {
            averageDurationOfDFS = getAverage(durationsOfDFS);
            averageStepsOfDFS = getAverage(stepsOfDFS);
            System.out.println("DFS:\t" + averageDurationOfDFS + " ms\t" + averageStepsOfDFS + " steps");
        }
        if (doIDFS) {
            averageDurationOfIDFS = getAverage(durationsOfIDFS);
            averageStepsOfIDFS = getAverage(stepsOfIDFS);
            System.out.println("IDFS:\t" + averageDurationOfIDFS + " ms\t" + averageStepsOfIDFS + " steps");
        }
    }

    /**
     * Experimental function, it should be use only on processors with minimum 3 cores
     * It runs measuring time of each algorithm simultaneously
     */
    public static void averageMultiThreadComparator(Integer sizeX, Integer sizeY, Integer quantityOfTries) {
        List<Maze> mazesBFS = new ArrayList<>();
        List<Maze> mazesDFS = new ArrayList<>();
        List<Maze> mazesIDFS = new ArrayList<>();
        try {
            for (int i = 0; i < quantityOfTries; i++) {
                Maze maze = new Maze();
                maze.generateMazeStructure(sizeX, sizeY);
                mazesBFS.add(maze.clone());
                mazesDFS.add(maze.clone());
                mazesIDFS.add(maze.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        BFSRunner bfsRunner = new BFSRunner(mazesBFS);
        DFSRunner dfsRunner = new DFSRunner(mazesDFS);
        IDFSRunner idfsRunner = new IDFSRunner(mazesIDFS);

        Thread bfsThread = new Thread(bfsRunner);
        Thread dfsThread = new Thread(dfsRunner);
        Thread idfsThread = new Thread(idfsRunner);

        bfsThread.start();
        dfsThread.start();
        idfsThread.start();

        try {
            bfsThread.join();
            dfsThread.join();
            idfsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("BFS:\t" + bfsRunner.getAverageDuration() + " ms\t" + bfsRunner.getAverageSteps() + " steps");
        System.out.println("DFS:\t" + dfsRunner.getAverageDuration() + " ms\t" + dfsRunner.getAverageSteps() + " steps");
        System.out.println("IDFS:\t" + idfsRunner.getAverageDuration() + " ms\t" + idfsRunner.getAverageSteps() + " steps");
    }

    public static Long getDurationOfBFS(Maze maze) {
        long start = System.currentTimeMillis();
        maze.BFS();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static Long getDurationOfDFS(Maze maze) {
        long start = System.currentTimeMillis();
        maze.DFS();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static Long getDurationOfIDFS(Maze maze) {
        long start = System.currentTimeMillis();
        maze.IDFS();
        long end = System.currentTimeMillis();
        return end - start;
    }

    private static double getAverage(List<Long> list) {
        return list.stream().mapToDouble(val -> val).average().orElse(0.0);
    }
}
