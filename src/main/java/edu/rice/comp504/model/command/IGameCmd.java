package edu.rice.comp504.model.command;

import edu.rice.comp504.model.unit.GameUnit;

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
