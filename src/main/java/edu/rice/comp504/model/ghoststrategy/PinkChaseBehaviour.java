package edu.rice.comp504.model.ghoststrategy;

import edu.rice.comp504.model.MazeMap;
import edu.rice.comp504.model.unit.Ghost;
import edu.rice.comp504.model.unit.PacMan;
import edu.rice.comp504.model.util.DistCalculation;

import java.util.List;

import static edu.rice.comp504.model.util.Constants.PINK_CHASE_BEHAVIOR;
import static edu.rice.comp504.model.util.ControlUtil.ableToMove;
import static edu.rice.comp504.model.util.ControlUtil.availableDirections;

public class PinkChaseBehaviour extends ChaseBehaviour {

    public PinkChaseBehaviour(PacMan pacMan) {
        super(pacMan);
    }

    public String getName() {
        return PINK_CHASE_BEHAVIOR;
    }

    @Override
    public void behave(Ghost ghost, MazeMap mazeMap) {
        super.behave(ghost, mazeMap);
        if (ableToMove(ghost)) {
            if (ghost.isCenter()) {
                List<int[]> availableDirection = availableDirections(ghost, mazeMap);
                DistCalculation.getDistMap(mazeMap, getPacMan());
                setAStarDirection(ghost, availableDirection, 1, mazeMap, getPacMan());
            }
            ghost.updateLocation(mazeMap.getMaze());
        }

    }
}
