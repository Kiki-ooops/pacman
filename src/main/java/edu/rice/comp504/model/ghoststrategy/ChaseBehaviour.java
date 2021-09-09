package edu.rice.comp504.model.ghoststrategy;

import edu.rice.comp504.model.MazeMap;
import edu.rice.comp504.model.unit.Ghost;
import edu.rice.comp504.model.unit.PacMan;
import edu.rice.comp504.model.util.ControlUtil;
import edu.rice.comp504.model.util.DistCalculation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static edu.rice.comp504.model.util.Constants.*;
import static edu.rice.comp504.model.util.ControlUtil.ableToMove;
import static edu.rice.comp504.model.util.ControlUtil.availableDirections;

/**
 * ChaseBehaviour define the strategy used by the ghost to chase the pacMan.
 */
public class ChaseBehaviour implements IGhostBehaviour {
    private PacMan pacMan;

    /**
     * Behaviour constructor.
     *
     * @param pacMan PacMan Object
     */
    ChaseBehaviour(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    /**
     * This function get the name of this behaviour.
     *
     * @return behavior name
     */
    @Override
    public String getName() {
        return CHASE_BEHAVIOR;
    }

    public PacMan getPacMan() {
        return pacMan;
    }

    /**
     * Execute the chase behavior.
     *
     * @param ghost   The ghost object running with this behavior
     * @param mazeMap The maze map
     */
    @Override
    public void behave(Ghost ghost, MazeMap mazeMap) { }


    /**
     * Greedy Search: find min manhattan distance position away from pacman.
     * @param ghost a ghost.
     * @param mazeMap current maze map.
     */
    public void setMinManhattanChaseDirection(Ghost ghost, MazeMap mazeMap) {
        int[] currentPos = ghost.getGrid();
        int minDist = mazeMap.getHeight() + mazeMap.getWidth() + 1;

        //set default as going left
        int[] minDistDirection = {0, -1};
        List<int[]> availableDirection = ControlUtil.availableDirections(ghost, mazeMap);
        
        for (int[] direction : availableDirection) {
            int[] tempPos = {currentPos[0], currentPos[1]};
            tempPos[0] += direction[0];
            tempPos[1] += direction[1];

            int tempDist = DistCalculation.getManhattanDist(tempPos, pacMan.getGrid());

            // choose a direction with lower manhattan dist
            // or randomly pick a direction with the same manhattan dist
            if (tempDist < minDist) {
                minDist = tempDist;
                minDistDirection = direction;
            }

        }

        ghost.setDirection(minDistDirection);
    }

    public void setAStarDirection(Ghost ghost, List<int[]> availableDirection, int front, MazeMap mazeMap, PacMan pacMan) {
        // get A* distance matrix
        int[][] astar_dist_map = DistCalculation.addHeuristicToDist(pacMan, ghost, front);

        // init
        int[] currentPos = ghost.getGrid();
        int max_dist_decrease = -1;
        int[] max_dist_decrease_direction = {-1, 0};

        for (int[] direction : availableDirection) {
            int[] tempPos = {currentPos[0], currentPos[1]};
            tempPos[0] += direction[0];
            tempPos[1] += direction[1];

            int tempDistDecrease = astar_dist_map[currentPos[0]][currentPos[1]] -
                                        astar_dist_map[tempPos[0]][tempPos[1]];

            // choose a direction with lower manhattan dist
            // or randomly pick a direction with the same manhattan dist
            if (tempDistDecrease > max_dist_decrease) {
                max_dist_decrease = tempDistDecrease;
                max_dist_decrease_direction = direction;
            }

        }
        ghost.setDirection(max_dist_decrease_direction);
    }
}
