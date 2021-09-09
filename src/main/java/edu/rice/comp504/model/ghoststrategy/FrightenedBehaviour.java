package edu.rice.comp504.model.ghoststrategy;

import edu.rice.comp504.model.GameInfo;
import edu.rice.comp504.model.MazeMap;
import edu.rice.comp504.model.unit.Ghost;
import edu.rice.comp504.model.unit.PacMan;
import edu.rice.comp504.model.util.ControlUtil;
import edu.rice.comp504.model.util.DistCalculation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static edu.rice.comp504.model.util.Constants.*;
import static edu.rice.comp504.model.util.ControlUtil.availableDirections;
import static edu.rice.comp504.model.util.ControlUtil.resetGhostBehavior;

/**
 * FrightenedBehaviour class defines the strategy used by ghost after the panMan eats the pill.
 */
public class FrightenedBehaviour implements IGhostBehaviour {
    private PacMan pacMan;
    private GameInfo gameInfo;

    /**
     * FrightenedBehaviour constructor.
     *
     * @param pacMan pacMan
     */
    public FrightenedBehaviour(PacMan pacMan, GameInfo gameInfo, Ghost ghost) {
        this.pacMan = pacMan;
        this.gameInfo = gameInfo;
        ghost.setVel(LOW_SPEED);
        ghost.setState(GHOST_STATE_DARK_BLUE);
    }

    /**
     * This function gets the name of the frightened behavior.
     *
     * @return The behavior name
     */
    @Override
    public String getName() {
        return FRIGHTEN_BEHAVIOR;
    }

    /**
     * Execute the behavior on the target ghost.
     *
     * @param ghost   The ghost object running with this behavior
     * @param mazeMap The maze map
     */
    @Override
    public void behave(Ghost ghost, MazeMap mazeMap) {

        if (gameInfo.getPillDuration() <= 0) {
            gameInfo.resetBonus();
            ghost.setState(GHOST_STATE_NORMAL);
            ghost.setVel(NORMAL_SPEED);
            ghost.fixLocation();
            resetGhostBehavior(ghost, pacMan);
        } else {
            updateState(ghost, gameInfo);
            if (ghost.isCenter()) {
                setMaxManhattanChaseDirection(ghost, mazeMap);
            }
            ghost.updateLocation(mazeMap.getMaze());
        }
    }

    /**
     * Update the state of the ghost based on the pill duration.
     * @param ghost the ghost object
     * @param gameInfo the game information
     */
    private void updateState(Ghost ghost, GameInfo gameInfo) {
        if (gameInfo.getPillDuration() < 70) {
            ghost.setState(GHOST_STATE_FLASHING);
        } else {
            ghost.setState(GHOST_STATE_DARK_BLUE);
        }
    }

    /**
     * Greedy Search: find max manhattan distance position away from pacman.
     * @param ghost a ghost.
     * @param mazeMap current maze map.
     */
    public void setMaxManhattanChaseDirection(Ghost ghost, MazeMap mazeMap) {
        int[] currentPos = ghost.getGrid();
        //set default as stopping
        int[] maxDistDirection = {0, 0};
        List<int[]> availableDirection = ControlUtil.availableDirections(ghost, mazeMap);

        // save the max distance
        int maxDist = 0;

        for (int[] direction : availableDirection) {
            int[] tempPos = {currentPos[0], currentPos[1]};
            tempPos[0] += direction[0];
            tempPos[1] += direction[1];

            int tempDist = DistCalculation.getManhattanDist(tempPos, pacMan.getGrid());
            // choose a direction with larger manhattan dist
            // or randomly pick a direction with the same manhattan dist
            if (tempDist > maxDist) {
                maxDist = tempDist;
                maxDistDirection = direction;
            }

        }

        ghost.setDirection(maxDistDirection);
    }
}
