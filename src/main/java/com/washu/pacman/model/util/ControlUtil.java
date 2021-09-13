package com.washu.pacman.model.util;

import com.washu.pacman.model.MazeMap;
import com.washu.pacman.model.ghoststrategy.CyanChaseBehaviour;
import com.washu.pacman.model.ghoststrategy.OrangeChaseBehaviour;
import com.washu.pacman.model.ghoststrategy.PinkChaseBehaviour;
import com.washu.pacman.model.ghoststrategy.RedChaseBehaviour;
import edu.rice.comp504.model.ghoststrategy.*;
import com.washu.pacman.model.unit.Ghost;
import com.washu.pacman.model.unit.PacMan;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.washu.pacman.model.util.Constants.*;

public class ControlUtil {

    /**
     * Get the available directions based on the ghost current position.
     * @param ghost the ghost object
     * @param mazeMap the maze map
     * @return the list of available directions
     */
    public static List<int[]> availableDirections(Ghost ghost, MazeMap mazeMap) {
        List<int[]> availableDirections = new ArrayList<>();
        for (int[] direction : allDirections) {
            if (!ghost.detectCollision(mazeMap.getMaze(), direction)) {
                availableDirections.add(direction);
            }
        }
        return availableDirections;
    }

    /**
     * Check whether the ghost is able to move.
     * @param ghost the ghost object.
     * @return the indicator of whether the ghost is able to move.
     */
    public static boolean ableToMove(Ghost ghost) {
        if (ghost.getWaitExitTime() > 0) {
            ghost.setWaitExitTime(ghost.getWaitExitTime() - 1);
            return false;
        } else {
            if (ghost.getWaitExitTime() == 0) {
                ghost.setLoc(new Point(GHOST_INIT_POS_X, GHOST_INIT_POS_Y));
                ghost.setWaitExitTime(-1);
            }
            return true;
        }
    }

    /**
     * Reset the behavior of the ghost based on the color of the ghosts.
     * @param ghost the ghost object
     * @param pacMan the pacMan object
     */
    public static void resetGhostBehavior(Ghost ghost, PacMan pacMan) {
        ghost.setDirection(LEFT);
        if (ghost.getColor() == RED) {
            ghost.setGhostBehaviour(new RedChaseBehaviour(pacMan));
        } else if (ghost.getColor() == PINK) {
            ghost.setGhostBehaviour(new PinkChaseBehaviour(pacMan));
        } else if (ghost.getColor() == CYAN) {
            ghost.setGhostBehaviour(new CyanChaseBehaviour(pacMan));
        } else if (ghost.getColor() == ORANGE) {
            ghost.setGhostBehaviour(new OrangeChaseBehaviour(pacMan));
        }

    }
}
