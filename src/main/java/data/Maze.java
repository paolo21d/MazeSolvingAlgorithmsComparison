package data;


import exceptions.ReadFileException;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@Getter
@Setter
public class Maze implements MazeCreator, MazeSolver, MazePrinter {
    private List<Node> mazeStructure;
    private Node beginOfMaze, endOfMaze;
    private Integer sizeX, sizeY;

    public void readMazeStructureFromFile() {
        try {
            //Scanner in = new Scanner(System.in);
            String path = "C:\\Users\\paolo\\Desktop\\PSZT\\mazeStructure1.txt";
            readFromFile(path);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void generateMazeStructure(Integer x, Integer y) {
        Random random = new Random();

        sizeX = x;
        sizeY = y;

        mazeStructure = new ArrayList<>();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                mazeStructure.add(new Node(j, i));
            }
        }

        Integer startX = random.nextInt(x);
        Integer startY = random.nextInt(y);

        Node startNode = getNode(startX, startY);
        MazeGenerationDFS(startNode);

    }

    private  void MazeGenerationDFS(Node startNode){
        Random random = new Random();
        List<Boolean> visited = prepareVisitedList();
        Stack<Node> previousNodes = new Stack<>();

        previousNodes.push(startNode);

        while(!previousNodes.empty()){
            Node currentNode = previousNodes.peek();
            setVisitedFromCoordinates(visited, currentNode.getX(), currentNode.getY(), true);
            ArrayList<Node> potentialNodes = new ArrayList<>();

            if(checkIfNodeFitsToMazeSize(currentNode.getX() + 1, currentNode.getY()) && !isVisitedFromCoordinates(visited, currentNode.getX() + 1, currentNode.getY())){
                potentialNodes.add(getNode(currentNode.getX() + 1, currentNode.getY()));
            }
            if(checkIfNodeFitsToMazeSize(currentNode.getX() - 1, currentNode.getY()) && !isVisitedFromCoordinates(visited, currentNode.getX() - 1, currentNode.getY())){
                potentialNodes.add(getNode(currentNode.getX() - 1, currentNode.getY()));
            }
            if(checkIfNodeFitsToMazeSize(currentNode.getX(), currentNode.getY() + 1) && !isVisitedFromCoordinates(visited, currentNode.getX(), currentNode.getY() + 1)){
                potentialNodes.add(getNode(currentNode.getX(), currentNode.getY() + 1));
            }
            if(checkIfNodeFitsToMazeSize(currentNode.getX(), currentNode.getY() - 1) && !isVisitedFromCoordinates(visited, currentNode.getX(), currentNode.getY() - 1)){
                potentialNodes.add(getNode(currentNode.getX(), currentNode.getY() - 1));
            }

            if(potentialNodes.size() > 0) {
                Node randomNode = potentialNodes.get(random.nextInt(potentialNodes.size()));

                currentNode.addNeighbour(randomNode);
                randomNode.addNeighbour(currentNode);

                previousNodes.push(getNode(randomNode.getX(), randomNode.getY()));
            }
            else{
                previousNodes.pop();
            }
        }

    }

    public String getSimplifiedMazeStructure() {
        return null;
    }

    public  void printMaze(){
        for (int i = 0; i < sizeY; i++) {
            // draw the north edge
            for (int j = 0; j < sizeX; j++) {
                System.out.print((!checkIfNodeFitsToMazeSize(j, i-1) || !getNode(j, i).isNeighbour(getNode(j, i-1))) ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < sizeX; j++) {
                System.out.print((!checkIfNodeFitsToMazeSize(j-1, i) || !getNode(j, i).isNeighbour(getNode(j-1, i)))? "|   " : "    ");
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < sizeX; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    public List<Node> BFS() {
        List<Boolean> visited = prepareVisitedList();
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(beginOfMaze);
        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();
            setVisitedFromCoordinates(visited, node.getX(), node.getY(), true);
            if (checkMazeEnd(node))
                break;
            for (Node neighbour : node.getNeighbours()) {
                if (isVisitedFromCoordinates(visited, neighbour.getX(), neighbour.getY()))
                    continue;
                nodeQueue.add(neighbour);
            }
        }

        //TODO Implement backtracking (for every node I have to know from which node I came)
        return null;
    }

    public List<Node> DFS() {
        return null;
    }

    public List<Node> IDFS() {
        return null;
    }

    /**
     * @param x - X coordinate of Node
     * @param y - Y coordinate of Node
     * @return Node which have got coordinates (x,y)
     */
    private Node getNode(Integer x, Integer y) {
        return mazeStructure.get(y * sizeX + x);
    }

    private List<Boolean> prepareVisitedList() {
        List<Boolean> visited = Arrays.asList(new Boolean[getSizeX() * getSizeY()]);
        //visited.forEach(v -> v = false);  nie działa
        for(int i = 0; i < visited.size(); i++){
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

        //prepare nodes
        mazeStructure = new ArrayList<>();
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                mazeStructure.add(new Node(x, y));
            }
        }

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
            System.out.println(line);
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
     * @param nodeX
     * @param nodeY
     */
    private boolean checkIfNodeFitsToMazeSize(Integer nodeX, Integer nodeY) {
        return nodeX >= 0 && nodeX < sizeX && nodeY >= 0 && nodeY < sizeY;
    }

    private List<Node> DFSSetDepth(Integer depth) {
        List<Boolean> visited = prepareVisitedList();
        Stack<Node> nodeStack = new Stack<Node>();
        nodeStack.push(beginOfMaze);
        Integer currentDepth = 1;
        while (!nodeStack.empty()){
            Node node = nodeStack.pop();
            setVisitedFromCoordinates(visited, node.getX(), node.getY(), true);
            if(checkMazeEnd(node)){
                break;
            }


        }

        return null;
    }
}
