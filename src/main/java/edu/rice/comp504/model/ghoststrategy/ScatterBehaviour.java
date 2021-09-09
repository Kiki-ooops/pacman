package edu.rice.comp504.model.ghoststrategy;

import edu.rice.comp504.model.MazeMap;
import edu.rice.comp504.model.unit.Ghost;

import java.util.List;

import static edu.rice.comp504.model.util.Constants.*;
import static edu.rice.comp504.model.util.ControlUtil.ableToMove;
import static edu.rice.comp504.model.util.ControlUtil.availableDirections;

/**
 * ScatterBehaviour class defines the random moving strategy used by the ghost.
 */
public class ScatterBehaviour implements IGhostBehaviour {

    private static IGhostBehaviour scatterBehavior;

    private ScatterBehaviour() {

    }

    /**
     * This function get the singleton object of ScatterBehaviour.
     *
     * @return ScatterBehavior object
     */
    public static IGhostBehaviour getScatterBehavior() {
        if (scatterBehavior == null) {
            scatterBehavior = new ScatterBehaviour();
        }
        return scatterBehavior;
    }

    /**
     * This function get the name of this behavior.
     *
     * @return The behavior name
     */
    @Override
    public String getName() {
        return SCATTER_BEHAVIOR;
    }

    /**
     * Execute the behavior on the target ghost.
     *
     * @param ghost   The ghost object running with this behavior
     * @param mazeMap The maze map
     */
    @Override
    public void behave(Ghost ghost, MazeMap mazeMap) {
        if (ableToMove(ghost)) {
            if (ghost.detectCollision(mazeMap.getMaze(), ghost.getDirection())) {
                List<int[]> availableDirection = availableDirections(ghost, mazeMap);
                if (availableDirection.size() > 0) {
                    ghost.setDirection(availableDirection.get((int)(Math.random() * availableDirection.size())));
                }
            }
            ghost.updateLocation(mazeMap.getMaze());
        }
    }
}
