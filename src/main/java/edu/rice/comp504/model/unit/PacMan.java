package edu.rice.comp504.model.unit;

import edu.rice.comp504.model.command.IGameCmd;

import java.awt.*;
import java.beans.PropertyChangeEvent;

import static edu.rice.comp504.model.util.Constants.*;

/**
 * PacMan class.
 */
public class PacMan extends GameUnit {
    private int[] nextDirection;
    private boolean isVisible;

    /**
     * PacMan constructor.
     *
     * @param loc       location.
     * @param vel       velocity.
     * @param direction direction.
     */
    public PacMan(Point loc, int vel, int[] direction) {
        super(loc, vel, direction);
        this.isVisible = true;
    }

    /**
     * Get next direction.
     *
     * @return next direction.
     */
    public int[] getNextDirection() {
        return nextDirection;
    }

    /**
     * Set next direction from user's command.
     *
     * @param nextDirection next direction.
     */
    public void setNextDirection(int[] nextDirection) {
        this.nextDirection = nextDirection;
    }

    /**
     * Detect the collision when there are a current direction and a next direction.
     * @param maze the maze map
     * @param direction the current direction
     * @param nextDirection the next direction
     * @return the state of whether there is a collision
     */
    private boolean detectTurnCollision(int[][] maze, int[] direction, int[] nextDirection) {
        int[] grid = getGrid();
        Point loc = getLoc();
        if (direction[0] + nextDirection[0] == 0 && direction[1] + nextDirection[1] == 0) {
            return false;
        }
        if (loc.x % GRID_SIZE == 0 && loc.y % GRID_SIZE == 0) {
            if ((nextDirection == UP && maze[grid[0] - 1][grid[1]] > WALL)
                    || (nextDirection == LEFT && maze[grid[0]][grid[1] - 1] > WALL)
                    || (nextDirection == DOWN && maze[grid[0] + 1][grid[1]] > WALL)
                    || (nextDirection == RIGHT && maze[grid[0]][grid[1] + 1] > WALL)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether the pacMan is visible or not.
     * @return the visible status of the
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Set the visible state for the pacMan.
     * @param visible the visible state.
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Update direction if not collide.
     *
     * @param maze maze map.
     */
    public void updateDirection(int[][] maze) {
        if (nextDirection != null && !detectTurnCollision(maze, getDirection(), nextDirection)) {
            super.setDirection(nextDirection);
            nextDirection = null;
        }
    }

    /**
     * Update the location of the pacMan to implement the relocation from one position to another position.
     * @param maze maze map.
     */
    public void updateLocation(int[][] maze) {
        super.updateLocation(maze);
        int[] hitGrid = this.getGrid();
        if (hitGrid[0] == 15 && hitGrid[1] == 1) {
            this.setLoc(new Point(15 * GRID_SIZE, 28 * GRID_SIZE));
        } else if (hitGrid[0] == 15 && hitGrid[1] == 28) {
            this.setLoc(new Point(15 * GRID_SIZE, 2 * GRID_SIZE));
        }
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PACMAN)) {
            ((IGameCmd) evt.getNewValue()).execute(this);
        }
    }
}
