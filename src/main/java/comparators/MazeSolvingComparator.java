package comparators;

import data.Maze;

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
        System.out.println("BFS:\t"+getDurationOfBFS(maze)+"ms");
        //DFS
        System.out.println("BFS:\t"+getDurationOfDFS(maze)+"ms");
        //IDFS
        System.out.println("BFS:\t"+getDurationOfIDFS(maze)+"ms");
    }

    public static void averageComparator(Integer sizeX, Integer sizeY, Integer quantityOfTries) {
        Maze maze = new Maze();
        List<Long> durationsOfBFS = new ArrayList<>();
        List<Long> durationsOfDFS = new ArrayList<>();
        List<Long> durationsOfIDFS = new ArrayList<>();

        for(Integer i=0; i<quantityOfTries; i++){
            maze.generateMazeStructure(sizeX, sizeY);
            durationsOfBFS.add(getDurationOfBFS(maze));
            durationsOfDFS.add(getDurationOfDFS(maze));
            durationsOfIDFS.add(getDurationOfIDFS(maze));
        }

        double averageDurationOfBFS = durationsOfBFS.stream().mapToDouble(val -> val).average().orElse(0.0);
        double averageDurationOfDFS = durationsOfDFS.stream().mapToDouble(val -> val).average().orElse(0.0);
        double averageDurationOfIDFS = durationsOfIDFS.stream().mapToDouble(val -> val).average().orElse(0.0);

        System.out.println("BFS:\t"+averageDurationOfBFS+"ms");
        System.out.println("DFS:\t"+averageDurationOfDFS+"ms");
        System.out.println("IDFS:\t"+averageDurationOfIDFS+"ms");
    }

    public static void averageMultiThreadComparator(Integer sizeX, Integer sizeY, Integer quantityOfTries) {
        List<Maze> mazesBFS = new ArrayList<>();
        List<Maze> mazesDFS = new ArrayList<>();
        List<Maze> mazesIDFS = new ArrayList<>();
        try {
            for(int i=0; i<quantityOfTries; i++) {
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
        System.out.println("BFS:\t"+bfsRunner.getAverageDuration()+"ms");
        System.out.println("DFS:\t"+dfsRunner.getAverageDuration()+"ms");
        System.out.println("IDFS:\t"+idfsRunner.getAverageDuration()+"ms");
    }

    public static Long getDurationOfBFS(Maze maze) {
        long start = System.currentTimeMillis();
        maze.BFS();
        long end = System.currentTimeMillis();
        return end-start;
    }

    public static Long getDurationOfDFS(Maze maze) {
        long start = System.currentTimeMillis();
        maze.DFS();
        long end = System.currentTimeMillis();
        return end-start;
    }

    public static Long getDurationOfIDFS(Maze maze) {
        long start = System.currentTimeMillis();
        maze.IDFS();
        long end = System.currentTimeMillis();
        return end-start;
    }
}
