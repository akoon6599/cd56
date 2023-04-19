package mazeRecursion;

import java.util.*;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;


/*
 * Write a method that starts at the mouse's position
 * and finds a path to the cheese. The method should be recursive, and should pass visited
 * or unvisited cells as a parameter.
 * In the map below,
 * # = wall
 * . = walkable floor
 * m = mouse
 * c = cheese
 */

public class Interface {
    public static void main(String[] args) {
        Map map = new Map(
                """
                .........c
                .####.####
                ......####
                #####.####
                ####..####
                ####.m####
                .###.#####
                c....#####
                ###.######
                ###c######
                """
        );
        map.print();

        HashMap<Integer, Movement> moves;
        moves = map.solveMap(0, null);
        moves.values().forEach((movement) -> map.mapString.get(movement.absolutePosition[0]).set(movement.absolutePosition[1], "-"));

        for (List<String> row : map.mapString) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
//        map.mapArray.get(map.startPosition[0]).forEach((x) -> System.out.println(x.type));
    }

}

class Movement {
    int weight;
    int[] relativePosition;
    int[] absolutePosition;

    public Movement(int weight, int relRow, int relCol, int absRow, int absCol) {
        this.weight = weight;
        this.relativePosition = new int[] {relRow, relCol};
        this.absolutePosition = new int[] {absRow, absCol};
    }
    public int getWeight() {return weight;}
}

class Map {
    public List<List<String>> mapString = new ArrayList<>();

    public List<List<MapCell>> mapArray = new ArrayList<>();
    public int[] startPosition;
    public int[] curPosition;

    public HashMap<Integer, Movement> solveMap(int movementNumber, HashMap<Integer, Movement> movementChain) {
        curPosition = getLocationOf(MapCell.CellType.START);
        ArrayList<Movement> possibleMoves = checkNearbyCells(curPosition[0], curPosition[1]);

        for (Movement m : possibleMoves) {
            System.out.println(m.weight);
        }

        Integer[] weights = possibleMoves.stream().map(Movement::getWeight).toList().toArray(new Integer[1]);
        int maxWeightIndex = RandomGenerator.getDefault().nextInt(0,weights.length);
        for (int i=0; i<weights.length;i++) {
            System.out.println(Arrays.toString(weights));
            System.out.println(i);
            maxWeightIndex = weights[i] > weights[maxWeightIndex] ? i : maxWeightIndex;
        }


        MapCell oldCell = mapArray.get(curPosition[0]).get(curPosition[1]);
        oldCell.explored = true;
        oldCell.type = MapCell.CellType.FLOOR;
        Movement move = possibleMoves.get(maxWeightIndex);

        int newRow = curPosition[0] + move.relativePosition[0];
        int newCol = curPosition[1] + move.relativePosition[1];

        possibleMoves.forEach((x) -> System.out.println(Arrays.toString(x.relativePosition)));
        MapCell newCell = mapArray.get(newRow).get(newCol);

        if (Objects.nonNull(movementChain)) {movementChain.put(movementNumber, move);}
        else {
            movementChain = new HashMap<>();
            movementChain.put(movementNumber, move);
        }




        if (newCell.type == MapCell.CellType.CHEESE) {return movementChain;}
        else if (movementNumber >= 50) {return movementChain;}
        else {
            newCell.type = MapCell.CellType.START;
            return solveMap(movementNumber+1, movementChain);
        }
    }

    public int[] getLocationOf(MapCell.CellType type) {
        int rowNum = 0;
        int colNum = 0;
        for (List<MapCell> row : mapArray) {
            for (MapCell cell : row) {
                if (cell.type == type) {
                    rowNum = mapArray.indexOf(row);
                    colNum = row.indexOf(cell);
                }
            }
        }

        return new int[] {rowNum, colNum};
    }

    public Map(String strMap) {
        strMap = strMap.strip();
        String[] splitMap = strMap.split("\n");

        for (String s : splitMap) {
            String[] rowSplit = s.split("");
            mapArray.add(
                    Arrays.stream(rowSplit).map(MapCell::new).collect(Collectors.toList())
            );
            mapString.add(Arrays.stream(rowSplit).collect(Collectors.toList()));
        }

        startPosition = getLocationOf(MapCell.CellType.START);
    }

    public ArrayList<Movement> checkNearbyCells(int rowIndex, int columnIndex) {
        ArrayList<Movement> options = new ArrayList<>();

        for (int dRow=-1;dRow<=1;dRow++) {
            for (int dCol=-1;dCol<=1;dCol++) {
                int newRow = rowIndex+dRow;
                int newCol = columnIndex+dCol;

                if ((newRow < mapArray.size() && newRow > -1) && (newCol < mapArray.get(0).size() && newCol > -1)) {
                    MapCell cell = mapArray.get(newRow).get(newCol);

                    if (cell.type != MapCell.CellType.WALL && !(dRow == 0 && dCol == 0)) {
                        int weight = cell.explored ? -1 : 0;
                        weight += cell.type == MapCell.CellType.CHEESE ? 10 : 0;

                        options.add(new Movement(weight, dRow, dCol, newRow, newCol));
                    }
                }
            }
        }

        return options;
    }

    public void print() {
        for (List<MapCell> row: mapArray) {
            for (MapCell cell: row) {
                cell.print();
            }
            System.out.println();
        }
        System.out.println();
    }
}

class MapCell {
    public enum CellType {WALL, FLOOR, CHEESE, START}
    public boolean explored = false;
    public CellType type;
    public char repr;
    public MapCell(String mapChar) {
        repr = mapChar.charAt(0);
        switch (mapChar) {
            case "m" -> type = CellType.START;
            case "c" -> type = CellType.CHEESE;
            case "." -> type = CellType.FLOOR;
            case "#" -> type = CellType.WALL;
        }
    }
    public void print() {
        System.out.print(repr);
    }
}

