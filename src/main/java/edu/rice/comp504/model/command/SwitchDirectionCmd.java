package edu.rice.comp504.model.command;

import edu.rice.comp504.model.unit.GameUnit;
import edu.rice.comp504.model.unit.PacMan;

import static edu.rice.comp504.model.util.Constants.*;

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
                direction = LEFT;
                break;
            case "RIGHT":
                direction = RIGHT;
                break;
            case "UP":
                direction = UP;
                break;
            case "DOWN":
                direction = DOWN;
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
