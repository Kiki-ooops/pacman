package com.washu.pacman.model.unit;

import java.awt.*;
import java.beans.PropertyChangeListener;

import static com.washu.pacman.model.util.Constants.*;

/**
 * Game unit abstract class.
 */
public abstract class GameUnit implements PropertyChangeListener {
    private Point loc;
    private int vel;
    private int[] direction;
    protected String unitType;

    /**
     * Constructor.
     *
     * @param loc       location.
     * @param vel       velocity.
     * @param direction direction.
     */
    public GameUnit(Point loc, int vel, int[] direction) {
        this.loc = loc;
        this.vel = vel;
        this.direction = direction;
    }

    /**
     * Update unit location if not collide.
     *
     * @param maze maze map.
     */
    public void updateLocation(int[][] maze) {
        if (!detectCollision(maze, direction)) {
            this.loc.x += vel * direction[0];
            this.loc.y += vel * direction[1];
        }
    }

    /**
     * Get the location of the game unit.
     * @return the location of the game unit.
     */
    public Point getLoc() {
        return loc;
    }

    /**
     * Set the location of the game unit.
     * @param loc the location of the game unit.
     */
    public void setLoc(Point loc) {
        this.loc = loc;
    }

    /**
     * Set the unit type.
     * @param unitType the unit type.
     */
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    /**
     * Get unit type.
     *
     * @return unit type.
     */
    public String getUnitType() {
        return unitType;
    }

    /**
     * Get velocity.
     *
     * @return velocity.
     */
    public int getVel() {
        return vel;
    }

    /**
     * Set velocity.
     *
     * @param vel velocity.
     */
    public void setVel(int vel) {
        this.vel = vel;
    }

    /**
     * Get direction.
     *
     * @return direction.
     */
    public int[] getDirection() {
        return direction;
    }

    /**
     * Set direction.
     *
     * @param direction new direction.
     */
    public void setDirection(int[] direction) {
        this.direction = direction;
    }

    /**
     * Check collision based on the wall and direction.
     *
     * @param maze      maze map.
     * @param direction direction.
     * @return true if has collision.
     */
    public boolean detectCollision(int[][] maze, int[] direction) {
        int[] grid = getGrid();
        if (direction == UP) {
            return loc.x % GRID_SIZE == 0 && maze[grid[0] - 1][grid[1]] <= WALL;
        } else if (direction == LEFT) {
            return loc.y % GRID_SIZE == 0 && maze[grid[0]][grid[1] - 1] <= WALL;
        } else if (direction == DOWN) {
            return loc.x % GRID_SIZE == 0 && maze[grid[0] + 1][grid[1]] <= WALL;
        } else if (direction == RIGHT) {
            return loc.y % GRID_SIZE == 0 && maze[grid[0]][grid[1] + 1] <= WALL;
        }
        return false;
    }

    /**
     * Check is is center of grid.
     *
     * @return isCenter.
     */
    private boolean isCenter() {
        return loc.x % GRID_SIZE == 0 && loc.y % GRID_SIZE == 0;
    }

    /**
     * Get grid location.
     *
     * @return grid location.
     */
    public int[] getGrid() {
        return new int[]{loc.x / GRID_SIZE, loc.y / GRID_SIZE};
    }

}
