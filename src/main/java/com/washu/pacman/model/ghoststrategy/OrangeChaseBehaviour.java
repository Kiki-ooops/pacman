package com.washu.pacman.model.ghoststrategy;

import com.washu.pacman.model.MazeMap;
import com.washu.pacman.model.unit.Ghost;
import com.washu.pacman.model.unit.PacMan;
import com.washu.pacman.model.util.Constants;
import com.washu.pacman.model.util.ControlUtil;

public class OrangeChaseBehaviour extends ChaseBehaviour {

    public OrangeChaseBehaviour(PacMan pacMan) {
        super(pacMan);
    }

    public String getName() {
        return Constants.ORANGE_CHASE_BEHAVIOR;
    }

    @Override
    public void behave(Ghost ghost, MazeMap mazeMap) {
        super.behave(ghost, mazeMap);
        if (ControlUtil.ableToMove(ghost)) {
            if (ghost.isCenter()) {
                setMinManhattanChaseDirection(ghost, mazeMap);
            }
            ghost.updateLocation(mazeMap.getMaze());
        }
    }
}
