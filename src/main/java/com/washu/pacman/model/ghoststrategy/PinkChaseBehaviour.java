package com.washu.pacman.model.ghoststrategy;

import com.washu.pacman.model.MazeMap;
import com.washu.pacman.model.unit.Ghost;
import com.washu.pacman.model.unit.PacMan;
import com.washu.pacman.model.util.Constants;
import com.washu.pacman.model.util.ControlUtil;
import com.washu.pacman.model.util.DistCalculation;

import java.util.List;

public class PinkChaseBehaviour extends ChaseBehaviour {

    public PinkChaseBehaviour(PacMan pacMan) {
        super(pacMan);
    }

    public String getName() {
        return Constants.PINK_CHASE_BEHAVIOR;
    }

    @Override
    public void behave(Ghost ghost, MazeMap mazeMap) {
        super.behave(ghost, mazeMap);
        if (ControlUtil.ableToMove(ghost)) {
            if (ghost.isCenter()) {
                List<int[]> availableDirection = ControlUtil.availableDirections(ghost, mazeMap);
                DistCalculation.getDistMap(mazeMap, getPacMan());
                setAStarDirection(ghost, availableDirection, 1, mazeMap, getPacMan());
            }
            ghost.updateLocation(mazeMap.getMaze());
        }

    }
}
