package com.washu.pacman.model.command;

import com.washu.pacman.model.util.ControlUtil;
import com.washu.pacman.model.GameInfo;
import com.washu.pacman.model.MazeMap;
import com.washu.pacman.model.ghoststrategy.DeathBehaviour;
import com.washu.pacman.model.ghoststrategy.FrightenedBehaviour;
import com.washu.pacman.model.ghoststrategy.ScatterBehaviour;
import com.washu.pacman.model.unit.GameUnit;
import com.washu.pacman.model.unit.Ghost;
import com.washu.pacman.model.unit.PacMan;

import java.awt.*;
import java.beans.PropertyChangeListener;

import static com.washu.pacman.model.util.Constants.*;

/**
 * Update command class.
 */
public class UpdateCmd implements IGameCmd {
    private PropertyChangeListener[] ghosts;
    private PropertyChangeListener[] pacMan;
    private GameInfo gameInfo;
    private MazeMap mazeMap;

    /**
     * Constructor.
     *
     * @param ghosts   ghost
     * @param gameInfo game info
     * @param mazeMap  maze map
     */
    public UpdateCmd(PropertyChangeListener[] ghosts, PropertyChangeListener[] pacMan,
                     GameInfo gameInfo, MazeMap mazeMap) {
        this.ghosts = ghosts;
        this.pacMan = pacMan;
        this.gameInfo = gameInfo;
        this.mazeMap = mazeMap;
    }

    /**
     * Update the game.
     * @param unit gameUnit.
     */
    public void execute(GameUnit unit) {
        if (unit instanceof PacMan) {
            if (!((PacMan) unit).isVisible() || !detectPacManGhostsCollision((PacMan) unit)) {
                updatePacMan((PacMan) unit);
            } else {
                for (PropertyChangeListener pcl : ghosts) {
                    if (detectPacManGhostCollision((PacMan) unit, (Ghost) pcl)) {
                        Ghost ghost = (Ghost) pcl;
                        if (ghost.getState() == GHOST_STATE_FLASHING || ghost.getState() == GHOST_STATE_DARK_BLUE) {
                            ghost.setGhostBehaviour(new DeathBehaviour(ghost.getGrid(), mazeMap.getMaze(), ghost, (PacMan) pacMan[0]));
                            gameInfo.gainBonus();
                        } else if (ghost.getState() == GHOST_STATE_NORMAL) {
                            gameInfo.getKilled();
                            gameInfo.setLoseLifeWaitCounter(LOSE_LIFE_WAIT_TIME);
                        }
                    }
                }
            }
        } else if (unit instanceof Ghost) {
            //update the position of the ghosts (1. wall collision 2. next location)
            updateGhost((Ghost) unit);
        }
    }

    /**
     * Update the position of the pacMan (1) direction change (2) move to next location.
     *  (3) move on to the food by updating maze and score (4) wall collision .
     *
     * @param pacMan pacMan
     */
    private void updatePacMan(PacMan pacMan) {
        pacMan.updateDirection(mazeMap.getMaze());
        pacMan.updateLocation(mazeMap.getMaze());
        if (pacMan.isVisible()) {
            updateMaze(pacMan.getGrid());
        } else {
            if (gameInfo.getTofuDuration() <= 0) {
                for (PropertyChangeListener ghost : ghosts) {
                    ControlUtil.resetGhostBehavior((Ghost) ghost, pacMan);
                }
                pacMan.setVisible(true);
            }
        }
    }

    /**
     * Update maze map.
     *
     * @param hitGrid hit grid.
     */
    private void updateMaze(int[] hitGrid) {
        if (gameInfo.getFruitDisappearTime() == 0) {
            mazeMap.setFruit();
        }
        int curGridVal = mazeMap.getMaze()[hitGrid[0]][hitGrid[1]];
        mazeMap.getMaze()[hitGrid[0]][hitGrid[1]] = EMPTY;
        if (curGridVal == PILL) {
            for (PropertyChangeListener ghost : ghosts) {
                ((Ghost) ghost).setGhostBehaviour(new FrightenedBehaviour((PacMan) pacMan[0], gameInfo, ((Ghost) ghost)));
            }
            gameInfo.resetBonus();
            gameInfo.setPillDuration(GAME_INFO_PILL_DURATION / gameInfo.getLevel());
            gameInfo.gainScore(PILL_VALUE);
            gameInfo.decreaseDots();
        } else if (curGridVal == FOOD) {
            gameInfo.gainScore(FOOD_VALUE);
            gameInfo.decreaseDots();
        } else if (curGridVal == FRUIT) {
            gameInfo.gainScore(FRUIT_VALUE);
            gameInfo.resetFruitDisappearTime();
        } else if (curGridVal == TOFU) {
            ((PacMan) pacMan[0]).setVisible(false);
            for (PropertyChangeListener ghost : ghosts) {
                ((Ghost) ghost).setGhostBehaviour(ScatterBehaviour.getScatterBehavior());
            }
            gameInfo.setTofuDuration(GAME_INFO_TOFU_DURATION / gameInfo.getLevel());
            gameInfo.gainScore(TOFU_VALUE);
            gameInfo.decreaseDots();
        }
    }

    /**
     * Update ghost.
     *
     * @param ghost ghost
     */
    private void updateGhost(Ghost ghost) {
        ghost.getGhostBehaviour().behave(ghost, mazeMap);
    }

    /**
     * Detect collision between pacMan and ghost.
     *
     */
    private boolean detectPacManGhostsCollision(PacMan pacMan) {
        for (PropertyChangeListener pcl : ghosts) {
            if (detectPacManGhostCollision(pacMan, (Ghost) pcl)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Detect whether there is a collision happens between a pacMan and a ghost.
     * @param g1 pacMan object
     * @param g2 ghost object
     * @return the indicator of whether they collide.
     */
    private boolean detectPacManGhostCollision(PacMan g1, Ghost g2) {
        return distance(g1.getLoc(), g2.getLoc()) < 200 && g2.getState() != GHOST_STATE_EATEN;
    }

    /**
     * Calculate the distance between two position.
     * @param p1 position one
     * @param p2 position two
     * @return the distance of the two position
     */
    private int distance(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }
}
