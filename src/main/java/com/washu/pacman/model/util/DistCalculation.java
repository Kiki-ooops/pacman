package com.washu.pacman.model.util;

import com.washu.pacman.model.MazeMap;
import com.washu.pacman.model.unit.Ghost;
import com.washu.pacman.model.unit.PacMan;


/**
 * Distance Calculation Util for Ghost Behaviours.
 */
public class DistCalculation {
    private static int[][] distanceMap;
    private static final int MaxDistanceInGrid = 65;

    /**
     * Return the Manhattan Distance between two location in a grid.
     * @param loc1 location 1.
     * @param loc2 location 2.
     * @return distance.
     */
    public static int getManhattanDist(final int[] loc1, final int[] loc2) {
        return Math.abs(loc1[0] - loc2[0]) + Math.abs(loc1[1] - loc2[1]);
    }

    /**
     * Util Function: Set Distance by BFS.
     * @param i index of x-axis.
     * @param j index of y-axis.
     */
    public static void bfsSetValue(final int i, final int j) {
        if (distanceMap[i][j] < 0) {
            return;
        }


        int valueOfUp   =
                distanceMap[i][j - 1] > 0 ? distanceMap[i][j - 1] : MaxDistanceInGrid;
        int valueOfLeft =
                distanceMap[i - 1][j] > 0 ? distanceMap[i - 1][j] : MaxDistanceInGrid;
        int valueOfRight =
                distanceMap[i + 1][j] > 0 ? distanceMap[i + 1][j] : MaxDistanceInGrid;
        int valueOfDown =
                distanceMap[i][j + 1] > 0 ? distanceMap[i][j + 1] : MaxDistanceInGrid;

        int minNeighborhoodDist = Math.min(Math.min(valueOfUp, valueOfDown),
                                    Math.min(valueOfLeft, valueOfRight));
        if (distanceMap[i][j] == 0 || distanceMap[i][j] > minNeighborhoodDist) {
            distanceMap[i][j] = minNeighborhoodDist + 1;
        }

        if (distanceMap[i][j - 1] == 0
                || distanceMap[i][j - 1] > distanceMap[i][j]) {
            bfsSetValue(i, j - 1);
        }
        if (distanceMap[i][j + 1] == 0
                || distanceMap[i][j + 1] > distanceMap[i][j]) {
            bfsSetValue(i, j + 1);
        }
        if (distanceMap[i - 1][j] == 0
                || distanceMap[i - 1][j] > distanceMap[i][j]) {
            bfsSetValue(i - 1, j);
        }
        if (distanceMap[i + 1][j] == 0
                || distanceMap[i + 1][j] > distanceMap[i][j]) {
            bfsSetValue(i + 1, j);
        }
//        System.out.println(i+" "+j);
    }


    /**
     * Make distance map by nullify information of food or pill (value > 0) as 0.
     * @param mazeMap current mazeMap
     * @return null distance map
     */
    public static int[][] getModifiedMaze(final MazeMap mazeMap) {
        int height = mazeMap.getHeight();
        int width = mazeMap.getWidth();
        int[][] maze = mazeMap.getMaze();
        int[][] modifiedMaze = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int value = maze[i][j];
                modifiedMaze[i][j] = value > 0 ? 0 : value;
            }
        }

        return modifiedMaze;
    }

    /**
     *
     * @param mazeMap maze map
     * @param pacMan pacman
     * @return distance map
     */
    public static int[][] getDistMap(final MazeMap mazeMap, final PacMan pacMan) {
        distanceMap = getModifiedMaze(mazeMap);
        int[] currentPos = pacMan.getGrid();

        distanceMap[currentPos[0]][currentPos[1]] = 1;
//        bfsSetValue(currentPos[0], currentPos[1]);
        bfsSetValue(currentPos[0] - 1, currentPos[1]);
        bfsSetValue(currentPos[0] + 1, currentPos[1]);
        bfsSetValue(currentPos[0], currentPos[1] - 1);
        bfsSetValue(currentPos[0], currentPos[1] + 1);

        return distanceMap;
    }

    /**
     * A* Distance Map: add heuristic value to actual distance map.
     * @param pacMan Pacman instance.
     * @param front value of {1, 0} determining front/back for block/chase.
     * @return A* distance map.
     */
    public static int[][] addHeuristicToDist(final PacMan pacMan, final Ghost ghost, final int front) {
        int height = distanceMap.length;
        int width = distanceMap[0].length;
        int[][] heuristic = new int[height][width];
        int[] direction = pacMan.getDirection();
        int[] currentPos = ghost.getGrid();


        if (direction[1] == 0) {
            for (int i = 0; i < height; i++) {
                int value = (i - currentPos[0]) * direction[0] >= 0 ? 1 - front : front;
                for (int j = 0; j < width; j++) {
                    heuristic[i][j] = value + distanceMap[i][j];
                }
            }
        } else if (direction[0] == 0) {
            for (int j = 0; j < width; j++) {
                int value = (j - currentPos[1]) * direction[1] >= 0 ? 1 - front : front;
                for (int i = 0; i < height; i++) {
                    heuristic[i][j] = value + distanceMap[i][j];
                }
            }
        }

        return heuristic;
    }
}
