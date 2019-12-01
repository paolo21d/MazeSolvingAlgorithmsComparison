package data;


import exceptions.ReadFileException;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.*;

@Getter
@Setter
public class Maze implements MazeCreator, MazeSolver, MazePrinter, Cloneable {
    private List<Node> mazeStructure;
    private Node beginOfMaze, endOfMaze;
    private Integer sizeX, sizeY;
    private List<Node> pathSolution;
    private Long lastRunSteps;

    //Maze Creator
    @Override
    public void readMazeStructureFromFile(String path) throws IOException {
        readFromFile(path);
    }

    @Override
    public void generateMazeStructure(Integer x, Integer y) throws InvalidParameterException {
        if (x <= 1 || y <= 1)
            throw new InvalidParameterException();
        Random random = new Random();

        sizeX = x;
        sizeY = y;

        List<Boolean> visited = prepareVisitedList();
        Stack<Node> nodeStack = new Stack<>();

        prepareNodes();
        generateRandomStartAndEnd();

        Integer startX = random.nextInt(x);
        Integer startY = random.nextInt(y);

        Node startNode = getNode(startX, startY);
        nodeStack.push(startNode);

        while (!nodeStack.empty()) {
            Node currentNode = nodeStack.peek();
            setVisitedFromCoordinates(visited, currentNode.getX(), currentNode.getY(), true);
            ArrayList<Node> potentialNodes = new ArrayList<>();

            if (checkIfNodeFitsToMazeSize(currentNode.getX() + 1, currentNode.getY()) && !isVisitedFromCoordinates(visited, currentNode.getX() + 1, currentNode.getY())) {
                potentialNodes.add(getNode(currentNode.getX() + 1, currentNode.getY()));
            }
            if (checkIfNodeFitsToMazeSize(currentNode.getX() - 1, currentNode.getY()) && !isVisitedFromCoordinates(visited, currentNode.getX() - 1, currentNode.getY())) {
                potentialNodes.add(getNode(currentNode.getX() - 1, currentNode.getY()));
            }
            if (checkIfNodeFitsToMazeSize(currentNode.getX(), currentNode.getY() + 1) && !isVisitedFromCoordinates(visited, currentNode.getX(), currentNode.getY() + 1)) {
                potentialNodes.add(getNode(currentNode.getX(), currentNode.getY() + 1));
            }
            if (checkIfNodeFitsToMazeSize(currentNode.getX(), currentNode.getY() - 1) && !isVisitedFromCoordinates(visited, currentNode.getX(), currentNode.getY() - 1)) {
                potentialNodes.add(getNode(currentNode.getX(), currentNode.getY() - 1));
            }

            if (!potentialNodes.isEmpty()) {
                Node randomNode = potentialNodes.get(random.nextInt(potentialNodes.size()));

                currentNode.addNeighbour(randomNode);
                randomNode.addNeighbour(currentNode);

                nodeStack.push(getNode(randomNode.getX(), randomNode.getY()));
            } else {
                nodeStack.pop();
            }
        }
    }

    //Maze Printer
    @Override
    public void saveMazeStructureToFile(String fileName) throws IOException {
        saveToFile(fileName);
    }

    @Override
    public String getSimplifiedMazeStructure() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sizeY; i++) {
            // draw the north edge
            for (int j = 0; j < sizeX; j++) {
                stringBuilder.append((!checkIfNodeFitsToMazeSize(j, i - 1) || !getNode(j, i).isNeighbour(getNode(j, i - 1))) ? "+---" : "+   ");
            }
            stringBuilder.append("+\n");
            // draw the west edge
            for (int j = 0; j < sizeX; j++) {
                stringBuilder.append((!checkIfNodeFitsToMazeSize(j - 1, i) || !getNode(j, i).isNeighbour(getNode(j - 1, i))) ? "|   " : "    ");
            }
            stringBuilder.append("|\n");
        }
        // draw the bottom line
        for (int j = 0; j < sizeX; j++) {
            stringBuilder.append("+---");
        }
        stringBuilder.append("+\n");

        return stringBuilder.toString();
    }

    @Override
    public String getSimplifiedMazeSolution() {
        if (pathSolution == null) {
            BFS();
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sizeY; i++) {
            // draw the north edge
            for (int j = 0; j < sizeX; j++) {
                stringBuilder.append((!checkIfNodeFitsToMazeSize(j, i - 1) || !getNode(j, i).isNeighbour(getNode(j, i - 1))) ? "+----" : "+    ");
            }
            stringBuilder.append("+\n");
            // draw the west edge
            for (int j = 0; j < sizeX; j++) {
                if (pathSolution.contains(getNode(j, i))) {
                    if (getNode(j, i).equals(beginOfMaze)) {
                        stringBuilder.append((!checkIfNodeFitsToMazeSize(j - 1, i) || !getNode(j, i).isNeighbour(getNode(j - 1, i))) ? "| B  " : "  B  ");
                    } else if (getNode(j, i).equals(endOfMaze)) {
                        stringBuilder.append((!checkIfNodeFitsToMazeSize(j - 1, i) || !getNode(j, i).isNeighbour(getNode(j - 1, i))) ? "| E  " : "  E  ");
                    } else {
                        stringBuilder.append((!checkIfNodeFitsToMazeSize(j - 1, i) || !getNode(j, i).isNeighbour(getNode(j - 1, i))) ? "| x  " : "  x  ");
                    }
                } else {
                    stringBuilder.append((!checkIfNodeFitsToMazeSize(j - 1, i) || !getNode(j, i).isNeighbour(getNode(j - 1, i))) ? "|    " : "     ");
                }
            }
            stringBuilder.append("|\n");
        }
        // draw the bottom line
        for (int j = 0; j < sizeX; j++) {
            stringBuilder.append("+----");
        }
        stringBuilder.append("+\n");

        return stringBuilder.toString();
    }

    //MazeSolver
    @Override
    public List<Node> BFS() {
        List<Boolean> visited = prepareVisitedList();
        List<Node> path = new ArrayList<>();
        Queue<Node> nodeQueue = new LinkedList<>();
        Long steps = 0L;
        nodeQueue.add(beginOfMaze);

        List<Node> backtrackList = new ArrayList<>();
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                backtrackList.add(new Node(x, y));
            }
        }

        while (!nodeQueue.isEmpty()) {
            steps++;
            Node node = nodeQueue.poll();
            setVisitedFromCoordinates(visited, node.getX(), node.getY(), true);
            if (checkMazeEnd(node))
                break;
            for (Node neighbour : node.getNeighbours()) {
                if (isVisitedFromCoordinates(visited, neighbour.getX(), neighbour.getY()))
                    continue;

                backtrackList.set(neighbour.getY() * sizeX + neighbour.getX(), node);
                nodeQueue.add(neighbour);
            }
        }

        Node backtrack = endOfMaze;
        while (!backtrack.equals(beginOfMaze)) {
            path.add(backtrack);
            backtrack = getNodeFromList(backtrackList, backtrack.getX(), backtrack.getY());
        }
        path.add(beginOfMaze);

        lastRunSteps = steps;
        pathSolution = path;
        return pathSolution;
    }

    @Override
    public List<Node> DFS() {
        lastRunSteps = 0L;
        pathSolution = DFSSetDepth(sizeX * sizeY);
        return pathSolution;
    }

    @Override
    public List<Node> IDFS() {
        lastRunSteps = 0L;
        for (int depth = 1; depth < sizeX * sizeY; depth++) {
            pathSolution = DFSSetDepth(depth);

            if (!pathSolution.isEmpty()) {
                break;
            }
        }
        return pathSolution;
    }

    @Override
    public Maze clone() throws CloneNotSupportedException {
        return (Maze) super.clone();
    }

    //Private Methods
    private void generateRandomStartAndEnd() {
        beginOfMaze = generateRandomPoint();
        endOfMaze = generateRandomPoint();

        while (beginOfMaze.equals(endOfMaze)) {
            endOfMaze = generateRandomPoint();
        }
    }

    private Node generateRandomPoint() {
        Random random = new Random();

        switch (random.nextInt(4)) {
            case 0:
                return getNode(random.nextInt(sizeX), 0);
            case 1:
                return getNode(random.nextInt(sizeX), sizeY - 1);
            case 2:
                return getNode(0, random.nextInt(sizeY));
            case 3:
                return getNode(sizeX - 1, random.nextInt(sizeY));
            default:
                return getNode(0, 0);
        }
    }

    private Node getNodeFromList(List<Node> list, Integer x, Integer y) {
        return list.get(y * sizeX + x);
    }

    /**
     * Return from nodes list node with coordinates (x,y)
     *
     * @param x - X coordinate of Node
     * @param y - Y coordinate of Node
     * @return Node which have got coordinates (x,y)
     */
    private Node getNode(Integer x, Integer y) {
        return mazeStructure.get(y * sizeX + x);
    }

    private List<Boolean> prepareVisitedList() {
        List<Boolean> visited = Arrays.asList(new Boolean[getSizeX() * getSizeY()]);

        for (int i = 0; i < visited.size(); i++) {
            visited.set(i, false);
        }

        return visited;
    }

    private Boolean isVisitedFromCoordinates(List<Boolean> visited, Integer x, Integer y) {
        return visited.get(y * sizeX + x);
    }

    private void setVisitedFromCoordinates(List<Boolean> visited, Integer x, Integer y, Boolean value) {
        visited.set(y * sizeX + x, value);
    }

    private Boolean checkMazeEnd(Node node) {
        return endOfMaze == node;
    }

    private void prepareNodes() {
        mazeStructure = new ArrayList<>();
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                mazeStructure.add(new Node(x, y));
            }
        }
    }

    private void saveToFile(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(fileName + ".txt", StandardCharsets.UTF_8);
        List<Boolean> visited = prepareVisitedList();

        writer.println(sizeX + " " + sizeY);
        writer.println(beginOfMaze.getX() + " " + beginOfMaze.getY());
        writer.println(endOfMaze.getX() + " " + endOfMaze.getY());

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                Node currentNode = getNode(i, j);
                setVisitedFromCoordinates(visited, i, j, true);

                for (Node neighbour : currentNode.getNeighbours()) {
                    if (!isVisitedFromCoordinates(visited, neighbour.getX(), neighbour.getY())) {
                        writer.println(currentNode.getX() + " " + currentNode.getY() + " " +
                                neighbour.getX() + " " + neighbour.getY());
                    }
                }
            }
        }
        writer.close();
    }

    private void readFromFile(String path) throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(path));

        //read & analyze size of maze
        String line = reader.readLine();
        List<String> parts = Arrays.asList(line.split(" "));
        sizeX = Integer.valueOf(parts.get(0));
        sizeY = Integer.valueOf(parts.get(1));
        if (sizeX <= 0 || sizeY <= 0) {
            throw new ReadFileException("Incorrect maze size");
        }

        prepareNodes();

        //read begin and end of maze
        line = reader.readLine();
        parts = Arrays.asList(line.split(" "));
        Integer beginX = Integer.valueOf(parts.get(0));
        Integer beginY = Integer.valueOf(parts.get(1));
        line = reader.readLine();
        parts = Arrays.asList(line.split(" "));
        Integer endX = Integer.valueOf(parts.get(0));
        Integer endY = Integer.valueOf(parts.get(1));
        if (!checkIfNodeFitsToMazeSize(beginX, beginY) || !checkIfNodeFitsToMazeSize(endX, endY)) {
            throw new ReadFileException("Incorrect maze begin or end");
        }
        beginOfMaze = getNode(beginX, beginY);
        endOfMaze = getNode(endX, endY);

        //read maze structure - nodes and theirs neighbours
        line = reader.readLine();
        while (line != null) {
            //System.out.println(line);
            parts = Arrays.asList(line.split(" "));
            Integer fromNodeX = Integer.valueOf(parts.get(0));
            Integer fromNodeY = Integer.valueOf(parts.get(1));
            Integer toNodeX = Integer.valueOf(parts.get(2));
            Integer toNodeY = Integer.valueOf(parts.get(3));

            if (!checkIfNodeFitsToMazeSize(fromNodeX, fromNodeY) || !checkIfNodeFitsToMazeSize(toNodeX, toNodeY)) {
                throw new ReadFileException("Incorrect node's neighbour");
            }

            Node fromNode = getNode(fromNodeX, fromNodeY);
            Node toNode = getNode(toNodeX, toNodeY);
            fromNode.addNeighbour(toNode);
            toNode.addNeighbour(fromNode);

            // read next line
            line = reader.readLine();
        }
        reader.close();
    }

    /**
     * Check if node with coordinates (x,y) fits to maze size
     *
     * @param nodeX
     * @param nodeY
     */
    private boolean checkIfNodeFitsToMazeSize(Integer nodeX, Integer nodeY) {
        return nodeX >= 0 && nodeX < sizeX && nodeY >= 0 && nodeY < sizeY;
    }

    private List<Node> DFSSetDepth(Integer depth) {
        List<Boolean> visited = prepareVisitedList();
        Stack<Node> nodeStack = new Stack<Node>();
        List<Node> path = new ArrayList<>();
        Long steps = 0L;

        nodeStack.push(beginOfMaze);

        while (!nodeStack.empty()) {
            steps++;
            Node currentNode = nodeStack.peek();
            setVisitedFromCoordinates(visited, currentNode.getX(), currentNode.getY(), true);
            if (checkMazeEnd(currentNode)) {
                break;
            }

            if (nodeStack.size() - 1 == depth) {
                nodeStack.pop();
                continue;
            }

            for (Node neighbour : currentNode.getNeighbours()) {
                if (!isVisitedFromCoordinates(visited, neighbour.getX(), neighbour.getY())) {
                    nodeStack.push(neighbour);
                    break;
                }
            }

            if (nodeStack.peek() == currentNode) {
                nodeStack.pop();
            }

        }

        while (!nodeStack.empty()) {
            path.add(nodeStack.pop());
        }

        lastRunSteps += steps;
        return path;
    }
}
