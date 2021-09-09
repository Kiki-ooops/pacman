package edu.rice.comp504.model.ghoststrategy;

import edu.rice.comp504.model.MazeMap;
import edu.rice.comp504.model.unit.Ghost;
import edu.rice.comp504.model.unit.PacMan;
import edu.rice.comp504.model.util.DistCalculation;

import java.util.List;

import static edu.rice.comp504.model.util.Constants.RED_CHASE_BEHAVIOR;
import static edu.rice.comp504.model.util.ControlUtil.ableToMove;
import static edu.rice.comp504.model.util.ControlUtil.availableDirections;

public class RedChaseBehaviour extends ChaseBehaviour {

    public RedChaseBehaviour(PacMan pacMan) {
        super(pacMan);
    }

    public String getName() {
        return RED_CHASE_BEHAVIOR;
    }

    @Override
    public void behave(Ghost ghost, MazeMap mazeMap) {
        super.behave(ghost, mazeMap);
        if (ableToMove(ghost)) {
            if (ghost.isCenter()) {
                List<int[]> availableDirection = availableDirections(ghost, mazeMap);
                DistCalculation.getDistMap(mazeMap, getPacMan());
                setAStarDirection(ghost, availableDirection, 0, mazeMap, getPacMan());
            }
            ghost.updateLocation(mazeMap.getMaze());
        }
    }
}
