package com.washu.pacman.model;

import com.washu.pacman.model.ghoststrategy.FrightenedBehaviour;
import com.washu.pacman.model.unit.Ghost;
import com.washu.pacman.model.unit.PacMan;
import junit.framework.TestCase;

import java.awt.*;
import java.beans.PropertyChangeListener;

import static com.washu.pacman.model.util.Constants.*;

public class ModelTest extends TestCase {

    public void testStartGame() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();

        //test initial gameInfo
        assertEquals("the number of lives should be 3", 3, GameInfo.getInstance().getNumOfLives());
        assertEquals("the pill duration should be 0 at the beginning", 0, GameInfo.getInstance().getPillDuration());
        assertEquals("the tofu duration should be 0 at the beginning", 0, GameInfo.getInstance().getTofuDuration());
        assertEquals("the level should be 1", 1, GameInfo.getInstance().getLevel());

        //test mazeMap
        assertEquals("the height of the map should be 33", 33, MazeMap.getInstance().getHeight());
        assertEquals("the width of the map should be 30", 30, MazeMap.getInstance().getWidth());

        //test pacMan
        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        assertEquals("the number of the pacMan should be one", 1, da.getPcs().getPropertyChangeListeners(PACMAN).length);
        assertEquals("the position of the pacMan should be (384, 256)", new Point(384, 256), pacMan.getLoc());

        //test Ghosts
        PropertyChangeListener[] pcls = da.getPcs().getPropertyChangeListeners(GHOSTS);
        assertEquals("the number of the ghosts should be 4", 4, pcls.length);
    }

    public void testSwitchDirection() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();
        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];

        da.switchDirection("LEFT");
        assertEquals("the next direction of the pacMan should be left", LEFT, pacMan.getNextDirection());

        da.switchDirection("RIGHT");
        assertEquals("the next direction of the pacMan should be right", RIGHT, pacMan.getNextDirection());

        da.switchDirection("UP");
        assertEquals("the next direction of the pacMan should be up", UP, pacMan.getNextDirection());

        da.switchDirection("DOWN");
        assertEquals("the next direction of the pacMan should be down", DOWN, pacMan.getNextDirection());
    }

    public void testSwitchMode() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();
        assertEquals("the current mode of the game is pill mode", PILL_MODE, GameInfo.getInstance().getMode());
        da.switchMode();
        assertEquals("the current mode of the game is tofu mode", TOFU_MODE, GameInfo.getInstance().getMode());
    }

    public void testResetGame() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();
        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        assertEquals("the position of the pacMan should be (384, 256)", new Point(384, 256), pacMan.getLoc());
        assertEquals("the red ghost position should be (192, 232)", new Point(192, 232), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[0]).getLoc());
        assertEquals("the pink ghost position should be (240, 208)", new Point(240, 208), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[1]).getLoc());
        assertEquals("the cyan ghost position should be (240, 240)", new Point(240, 240), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[2]).getLoc());
        assertEquals("the orange ghost position should be (240, 272)", new Point(240, 272), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[3]).getLoc());


        da.updateGame();
        assertNotSame("the position of the pacMan should not be (384, 256)", new Point(384, 256), pacMan.getLoc());
        assertNotSame("the red ghost position should be (192, 232)", new Point(192, 232), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[0]).getLoc());
        assertEquals("the pink ghost position should be (240, 208)", new Point(240, 208), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[1]).getLoc());
        assertEquals("the cyan ghost position should be (240, 240)", new Point(240, 240), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[2]).getLoc());
        assertEquals("the orange ghost position should be (240, 272)", new Point(240, 272), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[3]).getLoc());

        da.resetGame();
        pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        assertEquals("the position of the pacMan should be (384, 256)", new Point(384, 256), pacMan.getLoc());
        assertEquals("the red ghost position should be (192, 232)", new Point(192, 232), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[0]).getLoc());
        assertEquals("the pink ghost position should be (240, 208)", new Point(240, 208), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[1]).getLoc());
        assertEquals("the cyan ghost position should be (240, 240)", new Point(240, 240), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[2]).getLoc());
        assertEquals("the orange ghost position should be (240, 272)", new Point(240, 272), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[3]).getLoc());

        GameInfo.getInstance().setDots(0);

        da.resetGame();
        pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        assertEquals("the position of the pacMan should be (384, 256)", new Point(384, 256), pacMan.getLoc());
        assertEquals("the red ghost position should be (192, 232)", new Point(192, 232), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[0]).getLoc());
        assertEquals("the pink ghost position should be (240, 208)", new Point(240, 208), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[1]).getLoc());
        assertEquals("the cyan ghost position should be (240, 240)", new Point(240, 240), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[2]).getLoc());
        assertEquals("the orange ghost position should be (240, 272)", new Point(240, 272), ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[3]).getLoc());

    }

    public void testPacManCollideWithWall() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();

        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        pacMan.getLoc().x = 384;
        pacMan.getLoc().y = 112;
        pacMan.setNextDirection(UP);
        da.updateGame();
        assertEquals("the position of the pacMan should be (380, 112)", new Point(380, 112), pacMan.getLoc());
        assertEquals("the direction of the pacMan should be up", UP, pacMan.getDirection());
        assertNull("the next direction of the pacMan should be null", pacMan.getNextDirection());

        pacMan.getLoc().x = 384;
        pacMan.getLoc().y = 112;
        pacMan.setNextDirection(DOWN);
        da.updateGame();
        assertEquals("the position of the pacMan should be (388, 112)", new Point(388, 112), pacMan.getLoc());
        assertEquals("the direction of the pacMan should be down", DOWN, pacMan.getDirection());
        assertNull("the next direction of the pacMan should be null", pacMan.getNextDirection());

        pacMan.getLoc().x = 384;
        pacMan.getLoc().y = 112;
        pacMan.setNextDirection(RIGHT);
        da.updateGame();
        assertEquals("the position of the pacMan should be (384, 116)", new Point(384, 116), pacMan.getLoc());
        assertEquals("the direction of the pacMan should be right", RIGHT, pacMan.getDirection());
        assertNull("the next direction of the pacMan should be null", pacMan.getNextDirection());

        pacMan.getLoc().x = 384;
        pacMan.getLoc().y = 112;
        pacMan.setNextDirection(LEFT);
        da.updateGame();
        assertEquals("the position of the pacMan should be (384, 112)", new Point(384, 112), pacMan.getLoc());
        assertEquals("the direction of the pacMan should be left", LEFT, pacMan.getDirection());
        assertNull("the next direction of the pacMan should be null", pacMan.getNextDirection());

        pacMan.getLoc().x = 384;
        pacMan.getLoc().y = 116;
        pacMan.setNextDirection(UP);
        da.updateGame();
        assertEquals("the position of the pacMan should be (384, 112)", new Point(384, 112), pacMan.getLoc());
        assertEquals("the direction of the pacMan should be left", LEFT, pacMan.getDirection());
        assertEquals("the next direction of the pacMan should be up", UP, pacMan.getNextDirection());

    }

    public void testPacManInstantMovement() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();
        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        pacMan.getLoc().x = 240;
        pacMan.getLoc().y = 16;
        pacMan.setDirection(LEFT);
        da.updateGame();
        assertEquals("the position of the pacMan should be (240, 448)", new Point(240, 448), pacMan.getLoc());

        pacMan.getLoc().x = 240;
        pacMan.getLoc().y = 448;
        pacMan.setDirection(RIGHT);
        da.updateGame();
        assertEquals("the position of the pacMan should be (240, 32)", new Point(240, 32), pacMan.getLoc());
    }

    public void testPacManCollideWithPill() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();
        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        pacMan.getLoc().x = 64;
        pacMan.getLoc().y = 32;
        assertEquals("the red ghost position should be chasing", RED_CHASE_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[0]).getGhostBehaviour().getName());
        assertEquals("the pink ghost position should be chasing", PINK_CHASE_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[1]).getGhostBehaviour().getName());
        assertEquals("the cyan ghost position should be chasing", CYAN_CHASE_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[2]).getGhostBehaviour().getName());
        assertEquals("the orange ghost position should be chasing", ORANGE_CHASE_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[3]).getGhostBehaviour().getName());

        da.updateGame();
        assertEquals("the red ghost position should be frightened", FrightenedBehaviour.class, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[0]).getGhostBehaviour().getClass());
        assertEquals("the pink ghost position should be frightened", FrightenedBehaviour.class, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[1]).getGhostBehaviour().getClass());
        assertEquals("the cyan ghost position should be frightened", FrightenedBehaviour.class, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[2]).getGhostBehaviour().getClass());
        assertEquals("the orange ghost position should be frightened", FrightenedBehaviour.class, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[3]).getGhostBehaviour().getClass());
    }

    public void testPacManCollideWithFruit() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();
        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        pacMan.getLoc().x = GAME_INFO_FRUIT_GRID_X * 16;
        pacMan.getLoc().y = GAME_INFO_FRUIT_GRID_Y * 16 + 4;
        MazeMap.getInstance().setFruit();
        assertEquals("the grid position (18, 16) should be a fruit", FRUIT, MazeMap.getInstance().getMaze()[GAME_INFO_FRUIT_GRID_X][GAME_INFO_FRUIT_GRID_Y]);
        da.updateGame();
        assertEquals("the grid position (18, 16) should be empty", EMPTY, MazeMap.getInstance().getMaze()[GAME_INFO_FRUIT_GRID_X][GAME_INFO_FRUIT_GRID_Y]);
    }

    public void testPacManCollideWithTofu() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();
        da.switchMode();

        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        pacMan.getLoc().x = 68;
        pacMan.getLoc().y = 32;
        pacMan.setDirection(UP);
        assertEquals("the position (64, 32) should be a tofu", TOFU, MazeMap.getInstance().getMaze()[4][2]);
        assertTrue("the pacMan should be visible", pacMan.isVisible());

        da.updateGame();
        assertEquals("the position (64, 32) should be empty", EMPTY, MazeMap.getInstance().getMaze()[4][2]);
        assertFalse("the pacMan should be invisible", pacMan.isVisible());

        GameInfo.getInstance().setTofuDuration(1);
        da.updateGame();
        assertTrue("the pacMan should be visible", pacMan.isVisible());
    }

    public void testPacManCollideWithNormalGhost() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();

        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        Ghost red = (Ghost) (da.getPcs().getPropertyChangeListeners(GHOSTS)[0]);
        red.getLoc().x = pacMan.getLoc().x;
        red.getLoc().y = pacMan.getLoc().y;
        red.setWaitExitTime(-1);

        da.updateGame();
        assertEquals("the number of lives will be 2", 2, GameInfo.getInstance().getNumOfLives());
    }

    public void testPacManCollideWithFrightenGhost() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();

        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        pacMan.getLoc().x = 64;
        pacMan.getLoc().y = 32;
        pacMan.setDirection(DOWN);

        Ghost red = (Ghost) (da.getPcs().getPropertyChangeListeners(GHOSTS)[0]);
        red.getLoc().x = pacMan.getLoc().x + 20;
        red.getLoc().y = pacMan.getLoc().y;
        red.setWaitExitTime(-1);
        red.setDirection(UP);

        da.updateGame();
        assertEquals("the state of red ghost will be frightened", GHOST_STATE_DARK_BLUE, red.getState());
        da.updateGame();
        assertEquals("the state of red ghost will be eaten", GHOST_STATE_EATEN, red.getState());
        assertEquals("the behavior of red ghost will be deathbehavior", DEATH_BEHAVIOR, red.getGhostBehaviour().getName());

        Point home = new Point(GHOST_INIT_POS_X, GHOST_INIT_POS_Y);
        while (!red.getLoc().equals(home)) {
            da.updateGame();
        }
        da.updateGame();
    }

    public void testPillDurationDisappear() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();
        PacMan pacMan = (PacMan) da.getPcs().getPropertyChangeListeners(PACMAN)[0];
        pacMan.getLoc().x = 64;
        pacMan.getLoc().y = 32;
        da.updateGame();
        assertEquals("the red ghost position should be frightened", FRIGHTEN_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[0]).getGhostBehaviour().getName());
        assertEquals("the pink ghost position should be frightened", FRIGHTEN_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[1]).getGhostBehaviour().getName());
        assertEquals("the cyan ghost position should be frightened", FRIGHTEN_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[2]).getGhostBehaviour().getName());
        assertEquals("the orange ghost position should be frightened", FRIGHTEN_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[3]).getGhostBehaviour().getName());

        GameInfo.getInstance().setPillDuration(30);
        da.updateGame();

        GameInfo.getInstance().setPillDuration(0);
        da.updateGame();
        assertEquals("the red ghost position should be chasing", RED_CHASE_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[0]).getGhostBehaviour().getName());
        assertEquals("the pink ghost position should be scattering", PINK_CHASE_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[1]).getGhostBehaviour().getName());
        assertEquals("the cyan ghost position should be scattering", CYAN_CHASE_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[2]).getGhostBehaviour().getName());
        assertEquals("the orange ghost position should be scattering", ORANGE_CHASE_BEHAVIOR, ((Ghost)da.getPcs().getPropertyChangeListeners(GHOSTS)[3]).getGhostBehaviour().getName());
    }

    public void testScatterBehave() {
        DispatchAdapter da = new DispatchAdapter();
        da.resetGame();
        da.startGame();
        PropertyChangeListener[] pcls = da.getPcs().getPropertyChangeListeners(GHOSTS);
        for (PropertyChangeListener pcl : pcls) {
            ((Ghost) pcl).setWaitExitTime(0);
        }
        Ghost pink = (Ghost) (da.getPcs().getPropertyChangeListeners(GHOSTS)[1]);
        pink.getLoc().x = 32;
        pink.getLoc().y = 32;
        pink.setDirection(LEFT);
        da.updateGame();
    }
}
