package comparators;

import data.Maze;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    protected List<Maze> mazes;
    protected List<Long> durations;
    @Getter
    protected Double averageDuration;

    public Runner() {
        durations = new ArrayList<>();
    }

    public Runner(List<Maze> m) {
        mazes = new ArrayList<>(m);
        durations = new ArrayList<>();
    }

}
