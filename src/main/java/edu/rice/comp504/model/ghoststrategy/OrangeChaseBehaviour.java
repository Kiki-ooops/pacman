package edu.rice.comp504.model.ghoststrategy;

import edu.rice.comp504.model.MazeMap;
import edu.rice.comp504.model.unit.Ghost;
import edu.rice.comp504.model.unit.PacMan;

import java.util.List;

import static edu.rice.comp504.model.util.Constants.ORANGE_CHASE_BEHAVIOR;
import static edu.rice.comp504.model.util.ControlUtil.ableToMove;
import static edu.rice.comp504.model.util.ControlUtil.availableDirections;

public class OrangeChaseBehaviour extends ChaseBehaviour {

    public OrangeChaseBehaviour(PacMan pacMan) {
        super(pacMan);
    }

    public String getName() {
        return ORANGE_CHASE_BEHAVIOR;
    }

    @Override
    public void behave(Ghost ghost, MazeMap mazeMap) {
        super.behave(ghost, mazeMap);
        if (ableToMove(ghost)) {
            if (ghost.isCenter()) {
                setMinManhattanChaseDirection(ghost, mazeMap);
            }
            ghost.updateLocation(mazeMap.getMaze());
        }
    }
}
