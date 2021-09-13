package com.washu.pacman.model;

import com.washu.pacman.model.command.UpdateCmd;
import com.washu.pacman.model.ghoststrategy.CyanChaseBehaviour;
import com.washu.pacman.model.ghoststrategy.OrangeChaseBehaviour;
import com.washu.pacman.model.ghoststrategy.PinkChaseBehaviour;
import com.washu.pacman.model.ghoststrategy.RedChaseBehaviour;
import com.washu.pacman.model.unit.Ghost;
import com.washu.pacman.model.unit.PacMan;
import com.washu.pacman.model.util.Constants;
import com.washu.pacman.model.ghoststrategy.*;
import com.washu.pacman.model.command.SwitchDirectionCmd;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * DispatchAdapter to control the game classes.
 */
public class DispatchAdapter {
    private PropertyChangeSupport pcs;

    /**
     * Constructor.
     */
    public DispatchAdapter() {
        pcs = new PropertyChangeSupport(this);
        MazeMap.getInstance().init();
        GameInfo.getInstance().init();
    }

    /**
     * Add listener to pcs.
     *
     * @param propertyName Can be "gameUnit", "mazeMap", "gameInfo"
     * @param pcl          PropertyChangeListener
     */
    private void addListener(String propertyName, PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(propertyName, pcl);
    }

    /**
     * Start the pacman game.
     *
     * @return all the propertyChangeListeners.
     */
    public PropertyChangeListener[] startGame() {
        GameInfo.getInstance().setStatus(1);
        return pcs.getPropertyChangeListeners();
    }

    /**
     * Create the pacMan object.
     * @return the pacMan object
     */
    private PacMan initPacMan() {
        PacMan pacMan = new PacMan(new Point(384, 256), Constants.NORMAL_SPEED, Constants.LEFT);
        addListener(Constants.PACMAN, pacMan);
        return pacMan;
    }

    /**
     * Create four ghost objects.
     * @param pacMan the pacMan object for the use of chasing behavior.
     */
    private void initGhosts(PacMan pacMan) {
        Ghost red = new Ghost(new Point(Constants.GHOST_INIT_POS_X, Constants.GHOST_INIT_POS_Y), Constants.NORMAL_SPEED, Constants.LEFT, new RedChaseBehaviour(pacMan), Constants.RED, 0);
        Ghost pink = new Ghost(new Point(240, 200), Constants.NORMAL_SPEED, Constants.LEFT, new PinkChaseBehaviour(pacMan), Constants.PINK, Constants.GHOST_WAIT_EXIT_TIME_DURATION);
        Ghost cyan = new Ghost(new Point(240, 232), Constants.NORMAL_SPEED, Constants.LEFT, new CyanChaseBehaviour(pacMan), Constants.CYAN, Constants.GHOST_WAIT_EXIT_TIME_DURATION * 2);
        Ghost orange = new Ghost(new Point(240, 264), Constants.NORMAL_SPEED, Constants.LEFT, new OrangeChaseBehaviour(pacMan), Constants.ORANGE, Constants.GHOST_WAIT_EXIT_TIME_DURATION * 3);

        addListener(Constants.GHOSTS, red);
        addListener(Constants.GHOSTS, pink);
        addListener(Constants.GHOSTS, cyan);
        addListener(Constants.GHOSTS, orange);
    }

    /**
     * Update the game.
     *
     * @return all the propertyChangeListeners.
     */
    public PropertyChangeListener[] updateGame() {
        GameInfo gameInfo = GameInfo.getInstance();
        if (gameInfo.isWin()) {
            gameInfo.addLevel();
            resetGame();
        } else if (gameInfo.isGameOver()) {
            gameInfo.setStatus(2);
        } else if (gameInfo.getStatus() == 1) {
            gameInfo.setPillDuration(gameInfo.getPillDuration() - 1);
            gameInfo.setTofuDuration(gameInfo.getTofuDuration() - 1);
            gameInfo.decreaseFruitDisappearanceTime();
            gameInfo.setLoseLifeWaitCounter(gameInfo.getLoseLifeWaitCounter() - 1);
            if (!gameInfo.isGameOver()) {
                if (gameInfo.getLoseLifeWaitCounter() == 0) {
                    resetGame();
                } else if (gameInfo.getLoseLifeWaitCounter() < 0) {
                    UpdateCmd cmd = new UpdateCmd(
                            pcs.getPropertyChangeListeners(Constants.GHOSTS),
                            pcs.getPropertyChangeListeners(Constants.PACMAN),
                            gameInfo,
                            MazeMap.getInstance()
                    );
                    pcs.firePropertyChange(Constants.GHOSTS, null, cmd);
                    pcs.firePropertyChange(Constants.PACMAN, null, cmd);
                }
            }
        }
        return pcs.getPropertyChangeListeners();
    }

    /**
     * Switch direction according REST post body.
     *
     * @param body REST post body.
     */
    public void switchDirection(String body) {
        pcs.firePropertyChange(Constants.PACMAN, null, new SwitchDirectionCmd(body));
    }

    /**
     * Reset all the objects of the game.
     */
    public void resetAll() {
        for (PropertyChangeListener pcl : pcs.getPropertyChangeListeners()) {
            pcs.removePropertyChangeListener(pcl);
        }
        GameInfo.getInstance().init();
        MazeMap.getInstance().init();
        addListener(Constants.MAZE, MazeMap.getInstance());
        addListener(Constants.GAMEINFO, GameInfo.getInstance());
        PacMan pacMan = initPacMan();
        initGhosts(pacMan);
    }

    /**
     * Switch the mode between the pill mode and tofu mode.
     * @return the mode given by the game.
     */
    public int switchMode() {
        int mode = GameInfo.getInstance().switchMode();
        MazeMap.getInstance().updateMaze(mode);
        return mode;
    }

    /**
     * Reset the game elements when pacMan is eaten or level up ( the game does not finish ).
     */
    void resetGame() {
        if (GameInfo.getInstance().isWin()) {
            GameInfo.getInstance().resetDots();
            MazeMap.getInstance().init();
            GameInfo.getInstance().resetFruitDisappearTime();
            MazeMap.getInstance().updateMaze(GameInfo.getInstance().getMode());
        }
        for (PropertyChangeListener pcl : pcs.getPropertyChangeListeners(Constants.PACMAN)) {
            pcs.removePropertyChangeListener(Constants.PACMAN, pcl);
        }
        for (PropertyChangeListener pcl : pcs.getPropertyChangeListeners(Constants.GHOSTS)) {
            pcs.removePropertyChangeListener(Constants.GHOSTS, pcl);
        }
        PacMan pacMan = initPacMan();
        initGhosts(pacMan);
    }

    /**
     * Get the pcs object for testing.
     * @return the pcs object.
     */
    PropertyChangeSupport getPcs() {
        return pcs;
    }
}
