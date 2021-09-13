package com.washu.pacman.model.ghoststrategy;

import com.washu.pacman.model.MazeMap;
import com.washu.pacman.model.unit.Ghost;

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
