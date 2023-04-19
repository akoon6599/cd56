package mazeRecursion;

import java.util.*;
import java.util.List;
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
        Map mainMap = new Map(
                """
                m........c
                .####.####
                ......####
                #####.####
                ####..####
                ####..####
                .###.#####
                c....#####
                ###.######
                ###c######
                """
        );

        solve(mainMap);
    }

    private static void solve(Map mainMap) {
        // Edit here to change which cheese to find
        int num = 2;

        System.out.println("       # Character Map #");
        mainMap.print(false);
        System.out.println();
        System.out.printf("     # Solving:  Answer %d #%n", num);
        System.out.println("       #  Weights Map  #");
        mainMap.assignWeights(0, mainMap.endPositions.get(num));
        mainMap.print(true);

        HashMap<Integer, Integer[]> pathHistory = new HashMap<>();
        pathHistory.put(0, mainMap.startPosition);
        pathHistory = mainMap.solveMap(0, pathHistory);
        mainMap.map.get(mainMap.startPosition[0]).get(mainMap.startPosition[1]).idealPath = true;

        System.out.println();
        System.out.println("       #   Solved Map  #");
        mainMap.print(false);
        System.out.printf("     # %d Steps to Solve #", pathHistory.size()-1);
    }

}

class Map {
    public List<List<MapCell>> map = new ArrayList<>(10);
    public Integer[] startPosition;
    public HashMap<Integer, Integer[]> endPositions = new HashMap<>();
    public Map(String strMap) {
        strMap = strMap.strip();
        String[] splitMap = strMap.split("\n");
        for (String s : splitMap) {
            map.add(
                    Arrays.stream(
                                    s.split("")
                            )
                            .map(MapCell::new)
                            .collect(Collectors.toCollection(ArrayList::new))
            );
        }
        int endCount = 0;
        for (int i = 0; i<map.size(); i++) {
            for (int j = 0; j<map.get(i).size(); j++) {
                if (map.get(i).get(j).type == MapCell.CellType.START) {
                    startPosition = new Integer[] {i, j};
                }
                else if (map.get(i).get(j).type == MapCell.CellType.CHEESE) {
                    endPositions.put(endCount, new Integer[] {i,j});
                    endCount++;
                }
                map.get(i).get(j).position = new Integer[] {i,j};
            }
        }
    }

    public void print(boolean weights) {
        for (List<MapCell> row: map) {
            for (MapCell cell: row) {
                if (!weights) {cell.print();}
                else {cell.printWeightMap();}
            }
            System.out.println();
        }
    }

    public void assignWeights(int prevWeight, Integer[] startPosition) {
        ArrayList<MapCell> nearbyCells = getNearbyCells(startPosition);

        for (MapCell cell : nearbyCells) {
            if (cell.type != MapCell.CellType.WALL && (Objects.equals(cell.weight, "x") || Integer.parseInt(cell.weight) > prevWeight) && prevWeight <= 50) {
               cell.weight = Integer.toString(prevWeight+1);
                if (cell.type == MapCell.CellType.CHEESE) {
                    cell.weight = Integer.toString(-1);
                }
                if (cell.position != startPosition) {
                    assignWeights(prevWeight + 1, cell.position);
                }
            }
        }
    }

    public HashMap<Integer, Integer[]> solveMap(int depth, HashMap<Integer, Integer[]> pathHistory) {
        ArrayList<MapCell> nearbyCells = getNearbyCells(pathHistory.get(depth));

        int lowestAt = 0;
        for (int i=0; i<nearbyCells.size(); i++) {
            if (!nearbyCells.get(i).weight.equals("x")) {
                if (nearbyCells.get(lowestAt).weight.equals("x")) {lowestAt = i;}
                else {
                    lowestAt = Integer.parseInt(nearbyCells.get(i).weight) < Integer.parseInt(nearbyCells.get(lowestAt).weight) ? i : lowestAt;
                }
            }
        }

        depth++;
        pathHistory.put(depth, nearbyCells.get(lowestAt).position);
        nearbyCells.get(lowestAt).idealPath = true;

        if (nearbyCells.get(lowestAt).type == MapCell.CellType.CHEESE) {
            return pathHistory;
        }
        else {
            return solveMap(depth, pathHistory);
        }
    }

    private ArrayList<MapCell> getNearbyCells(Integer[] position) {
        ArrayList<MapCell> nearbyCells = new ArrayList<>();
        for (int i=-1;i<=1;i++) {
            for (int j=-1;j<=1;j++) {
                int newRow = position[0]+i;
                int newCol = position[1]+j;
                if ((newRow != -1 && newRow != map.size()) &&
                        (newCol != -1  && newCol != map.get(0).size())) {
                    nearbyCells.add(map.get(newRow).get(newCol));
                }
            }
        }
    return nearbyCells;
    }
}

class MapCell {
    public enum CellType {WALL, FLOOR, CHEESE, START}
    public CellType type;
    public char repr;
    public String weight = "x";
    public Integer[] position;
    public boolean idealPath = false;
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
        if (idealPath && type == CellType.START) {System.out.print("  +");}
        else if (idealPath && type == CellType.CHEESE) {System.out.print("  *");}
        else if (idealPath) {System.out.print("  O");}
        else {System.out.printf("  %s",repr);}
    }
    public void printWeightMap() {
        System.out.printf(" %s", weight.length()==1 ? " "+weight : weight);
    }
}
