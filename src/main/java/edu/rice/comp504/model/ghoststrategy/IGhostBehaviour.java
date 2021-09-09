package edu.rice.comp504.model.ghoststrategy;

import edu.rice.comp504.model.MazeMap;
import edu.rice.comp504.model.unit.Ghost;

/**
 * The ghost behaviour interface.
 */
public interface IGhostBehaviour {
    /**
     * This function gets the name of the strategy.
     *
     * @return IGhostBehaviour name.
     */
    public String getName();

    /**
     * Execute the behavior.
     *
     * @param ghost  The ghost object running with the strategy
     * @param object The maze map
     */
    public void behave(Ghost ghost, MazeMap object);
}
