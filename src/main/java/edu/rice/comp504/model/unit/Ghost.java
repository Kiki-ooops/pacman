package edu.rice.comp504.model.unit;

import edu.rice.comp504.model.ghoststrategy.IGhostBehaviour;
import edu.rice.comp504.model.command.IGameCmd;

import java.awt.*;
import java.beans.PropertyChangeEvent;

import static edu.rice.comp504.model.util.Constants.GHOSTS;
import static edu.rice.comp504.model.util.Constants.GRID_SIZE;

/**
 * Ghost class.
 */
public class Ghost extends GameUnit {
    private IGhostBehaviour ghostBehaviour;
    private int color;
    private int waitExitTime;
    private int state; //0 normal, 1 dark blue, 2 flashing, 3 eaten by pacMan.
    /**
     * Ghost constructor.
     *
     * @param loc            location.
     * @param vel            velocity.
     * @param direction      direction.
     * @param ghostBehaviour ghost behaviour.
     * @param color          ghost color.
     */
    public Ghost(Point loc, int vel, int[] direction, IGhostBehaviour ghostBehaviour, int color, int waitExitTime) {
        super(loc, vel, direction);
        this.ghostBehaviour = ghostBehaviour;
        this.color = color;
        this.waitExitTime = waitExitTime;
        this.state = 0;
    }

    /**
     * Get color.
     *
     * @return color.
     */
    public int getColor() {
        return color;
    }

    /**
     * Get ghost behaviour.
     *
     * @return Ghost behaviour.
     */
    public IGhostBehaviour getGhostBehaviour() {
        return ghostBehaviour;
    }

    /**
     * Set ghost behaviour.
     *
     * @param ghostBehaviour new ghost behaviour.
     */
    public void setGhostBehaviour(IGhostBehaviour ghostBehaviour) {
        this.ghostBehaviour = ghostBehaviour;
    }

    /**
     * Get the waiting time of the ghost to be released.
     * @return the waiting time.
     */
    public int getWaitExitTime() {
        return waitExitTime;
    }

    /**
     * Set the waiting time for the ghost to come out from the block.
     * @param waitExitTime the waiting time period.
     */
    public void setWaitExitTime(int waitExitTime) {
        this.waitExitTime = waitExitTime;
    }

    /**
     * Get the state of the ghost.
     * @return the state of the ghost.
     */
    public int getState() {
        return state;
    }

    /**
     * Set the state of of the ghost.
     * @param state the state of the ghost.
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Check whether the ghost is in the center of a grid.
     * @return the indicator of whether the ghost is in the center.
     */
    public boolean isCenter() {
        return this.getLoc().x % GRID_SIZE == 0 && this.getLoc().y % GRID_SIZE == 0;
    }

    /**
     * Fix the location of the ghost to be on the center of the grid.
     */
    public void fixLocation() {
        int fixedX = (getLoc().x / GRID_SIZE) * GRID_SIZE;
        int fixedY = (getLoc().y / GRID_SIZE) * GRID_SIZE;
        this.setLoc(new Point(fixedX, fixedY));
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GHOSTS)) {
            ((IGameCmd) evt.getNewValue()).execute(this);
        }
    }
}
