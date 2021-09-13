package com.washu.pacman.model.command;

import com.washu.pacman.model.unit.GameUnit;
import com.washu.pacman.model.unit.PacMan;
import com.washu.pacman.model.util.Constants;

/**
 * Switch direction command.
 */
public class SwitchDirectionCmd implements IGameCmd {
    private int[] direction;

    /**
     * Constructor.
     *
     * @param type direction type.
     */
    public SwitchDirectionCmd(String type) {
        switch (type) {
            case "LEFT":
                direction = Constants.LEFT;
                break;
            case "RIGHT":
                direction = Constants.RIGHT;
                break;
            case "UP":
                direction = Constants.UP;
                break;
            case "DOWN":
                direction = Constants.DOWN;
                break;
            default:
                break;
        }
    }

    @Override
    public void execute(GameUnit unit) {
        ((PacMan) unit).setNextDirection(direction);
    }
}
