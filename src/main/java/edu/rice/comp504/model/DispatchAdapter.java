package edu.rice.comp504.model;

import edu.rice.comp504.model.command.UpdateCmd;
import edu.rice.comp504.model.ghoststrategy.*;
import edu.rice.comp504.model.unit.Ghost;
import edu.rice.comp504.model.unit.PacMan;
import edu.rice.comp504.model.command.SwitchDirectionCmd;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static edu.rice.comp504.model.util.Constants.*;

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
        PacMan pacMan = new PacMan(new Point(384, 256), NORMAL_SPEED, LEFT);
        addListener(PACMAN, pacMan);
        return pacMan;
    }

    /**
     * Create four ghost objects.
     * @param pacMan the pacMan object for the use of chasing behavior.
     */
    private void initGhosts(PacMan pacMan) {
        Ghost red = new Ghost(new Point(GHOST_INIT_POS_X, GHOST_INIT_POS_Y), NORMAL_SPEED, LEFT, new RedChaseBehaviour(pacMan), RED, 0);
        Ghost pink = new Ghost(new Point(240, 200), NORMAL_SPEED, LEFT, new PinkChaseBehaviour(pacMan), PINK, GHOST_WAIT_EXIT_TIME_DURATION);
        Ghost cyan = new Ghost(new Point(240, 232), NORMAL_SPEED, LEFT, new CyanChaseBehaviour(pacMan), CYAN, GHOST_WAIT_EXIT_TIME_DURATION * 2);
        Ghost orange = new Ghost(new Point(240, 264), NORMAL_SPEED, LEFT, new OrangeChaseBehaviour(pacMan), ORANGE, GHOST_WAIT_EXIT_TIME_DURATION * 3);

        addListener(GHOSTS, red);
        addListener(GHOSTS, pink);
        addListener(GHOSTS, cyan);
        addListener(GHOSTS, orange);
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
                            pcs.getPropertyChangeListeners(GHOSTS),
                            pcs.getPropertyChangeListeners(PACMAN),
                            gameInfo,
                            MazeMap.getInstance()
                    );
                    pcs.firePropertyChange(GHOSTS, null, cmd);
                    pcs.firePropertyChange(PACMAN, null, cmd);
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
        pcs.firePropertyChange(PACMAN, null, new SwitchDirectionCmd(body));
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
        addListener(MAZE, MazeMap.getInstance());
        addListener(GAMEINFO, GameInfo.getInstance());
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
        for (PropertyChangeListener pcl : pcs.getPropertyChangeListeners(PACMAN)) {
            pcs.removePropertyChangeListener(PACMAN, pcl);
        }
        for (PropertyChangeListener pcl : pcs.getPropertyChangeListeners(GHOSTS)) {
            pcs.removePropertyChangeListener(GHOSTS, pcl);
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
