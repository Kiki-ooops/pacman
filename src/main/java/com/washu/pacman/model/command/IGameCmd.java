package com.washu.pacman.model.command;

import com.washu.pacman.model.unit.GameUnit;

/**
 * Game command interface.
 */
public interface IGameCmd {
    /**
     * Execute command.
     *
     * @param unit gameUnit.
     */
    void execute(GameUnit unit);
}
