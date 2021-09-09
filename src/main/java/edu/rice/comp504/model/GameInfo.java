package edu.rice.comp504.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static edu.rice.comp504.model.util.Constants.*;

/**
 * GameInfo class to store scores information.
 */
public class GameInfo implements PropertyChangeListener {
    private int scores;
    private int numOfLives;
    private int level;
    private int status;
    private int pillDuration;
    private int tofuDuration;
    private int ghostEatenBonus;
    private int loseLifeWaitCounter;
    private int leftDots;
    private int fruitDisappearTime;
    private int mode;
    private static GameInfo ONLY;

    /**
     * private constructor.
     */
    private GameInfo() { }

    /**
     * Initialize the gameInfo.
     *
     */
    public void init() {
        this.numOfLives = 3;
        this.pillDuration = 0;
        this.tofuDuration = 0;
        this.ghostEatenBonus = GAME_INFO_GHOST_EATEN_INITIAL_BONUS;
        this.scores = 0;
        this.level = 1;
        this.status = 0;
        this.leftDots = GAME_INFO_TOTAL_DOTS;
        this.fruitDisappearTime = GAME_INFO_RANDOM_CHERRY_PERIOD;
        this.mode = PILL_MODE;
        this.loseLifeWaitCounter = 0;
    }

    /**
     * Get singleton.
     *
     * @return GameInfo class.
     */
    public static GameInfo getInstance() {
        if (ONLY == null) {
            ONLY = new GameInfo();
        }
        return ONLY;
    }

    /**
     * Check if game is over.
     *
     * @return isGameOver.
     */
    public boolean isGameOver() {
        return numOfLives == 0;
    }

    /**
     * Gain scores.
     *
     * @param score scores.
     */
    public void gainScore(int score) {
        scores += score;
    }

    /**
     * Decrease the number of the dots.
     */
    public void decreaseDots() {
        leftDots -= 1;
    }

    /**
     * Check whether the player wins.
     * @return the winning state.
     */
    public boolean isWin() {
        return leftDots == 0;
    }

    /**
     * PacMan been killed.
     */
    public void getKilled() {
        numOfLives--;
    }

    /**
     * Set pill duration.
     *
     * @param duration duration.
     */
    public void setPillDuration(int duration) {
        this.pillDuration = duration;
    }

    /**
     * Get the number of lives.
     *
     * @return number of lives.
     */
    public int getNumOfLives() {
        return numOfLives;
    }

    /**
     * Get current level.
     *
     * @return level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get pill duration.
     *
     * @return pill duration.
     */
    public int getPillDuration() {
        return pillDuration;
    }

    /**
     * Get the waiting time to restart after the pacMan gets killed each time.
     * @return the waiting time to restart
     */
    public int getLoseLifeWaitCounter() {
        return loseLifeWaitCounter;
    }

    /**
     * The waiting time to restart after the pacMan gets killed each time.
     * @param loseLifeWaitCounter the waiting time to restart
     */
    public void setLoseLifeWaitCounter(int loseLifeWaitCounter) {
        this.loseLifeWaitCounter = loseLifeWaitCounter;
    }

    /**
     * Upgrade the level.
     */
    public void addLevel() {
        this.level += 1;
    }

    /**
     * Reset the number of dots to be initial value.
     */
    public void resetDots() {
        this.leftDots = GAME_INFO_TOTAL_DOTS;
    }

    /**
     * Gain the bonus of eating a frightened ghost.
     */
    public void gainBonus() {
        this.scores += ghostEatenBonus;
        this.ghostEatenBonus *= 2;
    }

    /**
     * Reset the bonus value after the round of the current round.
     */
    public void resetBonus() {
        this.ghostEatenBonus = GAME_INFO_GHOST_EATEN_INITIAL_BONUS;
    }

    /**
     * Decrease the disappearance time of the fruit.
     */
    public void decreaseFruitDisappearanceTime() {
        this.fruitDisappearTime -= 1;
    }

    /**
     * Get the disappearance time of the fruit.
     * @return the disappearance time of the fruit.
     */
    public int getFruitDisappearTime() {
        return this.fruitDisappearTime;
    }

    /**
     * Reset the disappearance time of the fruit.
     */
    public void resetFruitDisappearTime() {
        this.fruitDisappearTime = GAME_INFO_RANDOM_CHERRY_PERIOD;
    }

    /**
     * Get the tofu duration after pacMan eating a tofu.
     * @return the duration of the tofu
     */
    public int getTofuDuration() {
        return tofuDuration;
    }

    /**
     * Set the tofu duration.
     * @param tofuDuration the tofu duration.
     */
    public void setTofuDuration(int tofuDuration) {
        this.tofuDuration = tofuDuration;
    }

    /**
     * Get the mode of the game.
     * @return the mode
     */
    public int getMode() {
        return this.mode;
    }

    /**
     * Switch the mode of the game.
     * @return the mode of the game
     */
    public int switchMode() {
        this.mode = (this.mode + 1) % 2;
        return this.mode;
    }

    /**
     * Get the status of the game.
     * @return the status of the game
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set the status of the game.
     * @param status the status of the game.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Set the number of dots.
     * @param numDots the number of dots in the game.
     */
    public void setDots(int numDots) {
        this.leftDots = numDots;
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
