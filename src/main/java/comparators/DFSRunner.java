package comparators;

import data.Maze;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DFSRunner extends Runner implements Runnable {
    public DFSRunner(List<Maze> mazes) {
        super(mazes);
    }

    @Override
    public void run() {
        for (Maze maze : mazes) {
            Long time = MazeSolvingComparator.getDurationOfDFS(maze);
            durations.add(time);
        }
        averageDuration = durations.stream().mapToDouble(val -> val).average().orElse(0.0);
        //System.out.println("DFS: " + averageDuration);
    }
}
