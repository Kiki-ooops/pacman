package com.washu.pacman.model.ghoststrategy;

import com.washu.pacman.model.GameInfo;
import com.washu.pacman.model.MazeMap;
import com.washu.pacman.model.unit.Ghost;
import com.washu.pacman.model.unit.PacMan;
import com.washu.pacman.model.util.Constants;
import com.washu.pacman.model.util.ControlUtil;
import com.washu.pacman.model.util.DistCalculation;

import java.util.List;

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
        ghost.setVel(Constants.LOW_SPEED);
        ghost.setState(Constants.GHOST_STATE_DARK_BLUE);
    }

    /**
     * This function gets the name of the frightened behavior.
     *
     * @return The behavior name
     */
    @Override
    public String getName() {
        return Constants.FRIGHTEN_BEHAVIOR;
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
            ghost.setState(Constants.GHOST_STATE_NORMAL);
            ghost.setVel(Constants.NORMAL_SPEED);
            ghost.fixLocation();
            ControlUtil.resetGhostBehavior(ghost, pacMan);
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
            ghost.setState(Constants.GHOST_STATE_FLASHING);
        } else {
            ghost.setState(Constants.GHOST_STATE_DARK_BLUE);
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
