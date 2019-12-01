package comparators;

import data.Maze;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    protected List<Maze> mazes;
    protected List<Long> durations;
    protected List<Long> steps;
    @Getter
    protected Double averageDuration;
    @Getter
    protected Double averageSteps;

    public Runner() {
        durations = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public Runner(List<Maze> m) {
        mazes = new ArrayList<>(m);
        durations = new ArrayList<>();
        steps = new ArrayList<>();
    }

}
