package com.washu.pacman.model.ghoststrategy;

import com.washu.pacman.model.MazeMap;
import com.washu.pacman.model.unit.Ghost;
import com.washu.pacman.model.unit.PacMan;
import com.washu.pacman.model.util.Constants;
import com.washu.pacman.model.util.ControlUtil;

import java.util.*;

/**
 * DeathBehaviour class defines the strategy used by ghost when the ghost eaten by the pacMan.
 */
public class DeathBehaviour implements IGhostBehaviour {

    private Stack<int[]> path;
    private PacMan pacMan;

    static class Node {
        private int[] pos;
        private Node pre;

        Node(int[] pos, Node pre) {
            this.pos = pos;
            this.pre = pre;
        }
    }

    /**
     * DeathBehaviour class constructor
     *
     * @param startGrid starting grid location
     * @param mazeMap maze map
     * @param ghost ghost object
     * @param pacMan pacman object
     */
    public DeathBehaviour(int[] startGrid, int[][] mazeMap, Ghost ghost, PacMan pacMan) {
        this.pacMan = pacMan;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        boolean[][] seen = new boolean[mazeMap.length][mazeMap[0].length];
        int[] endGrid = new int[]{Constants.GHOST_INIT_POS_X / Constants.GRID_SIZE, Constants.GHOST_INIT_POS_Y / Constants.GRID_SIZE};
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(startGrid, null));
        seen[startGrid[0]][startGrid[1]] = true;
        boolean isFind = false;
        Node endNode = null;
        while(!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0 ; i < n ; i++) {
                Node cur = queue.poll();
                if (cur.pos[0] == endGrid[0] && cur.pos[1] == endGrid[1]) {
                    isFind = true;
                    endNode = cur;
                    break;
                }
                for (int j = 0 ; j < 4 ; j++) {
                    int nx = cur.pos[0] + dx[j];
                    int ny = cur.pos[1] + dy[j];
                    if (isValid(nx, ny, mazeMap) && mazeMap[nx][ny] > Constants.WALL && !seen[nx][ny]) {
                        Node next = new Node(new int[]{nx, ny}, cur);
                        seen[nx][ny] = true;
                        queue.offer(next);
                    }
                }
            }
            if (isFind) {
                break;
            }
        }
        this.path = new Stack<>();
        while(endNode.pre != null) {
            path.push(endNode.pos);
            endNode = endNode.pre;
        }
        ghost.setVel(Constants.HIGH_SPEED);
        ghost.setState(Constants.GHOST_STATE_EATEN);
        ghost.fixLocation();
    }

    private boolean isValid(int x, int y, int[][] mazeMap) {
        return x >= 0 && x < mazeMap.length && y >= 0 && y < mazeMap[0].length;
    }

    /**
     * This function get the name of the death behavior.
     * @return The behavior name
     */
    @Override
    public String getName() {
        return Constants.DEATH_BEHAVIOR;
    }

    /**
     * Execute this behavior on the target ghost.
     * @param ghost The ghost running with this behavior
     * @param mazeMap The maze map
     */
    @Override
    public void behave(Ghost ghost, MazeMap mazeMap) {
        if (!path.isEmpty()) {
            int[] curPathGrid = path.peek();
            int[] hitGrid = ghost.getGrid();
            if (curPathGrid[0] != hitGrid[0] || curPathGrid[1] != hitGrid[1]) {
                updateDirection(ghost, curPathGrid, hitGrid);
            } else {
                path.pop();
            }
            ghost.updateLocation(mazeMap.getMaze());
        } else {
            ghost.setState(Constants.GHOST_STATE_NORMAL);
            ghost.setVel(Constants.NORMAL_SPEED);
            ControlUtil.resetGhostBehavior(ghost, pacMan);
        }
    }

    private void updateDirection(Ghost ghost, int[] cpg, int[] hg) {
        if (cpg[0] == hg[0] && cpg[1] == hg[1] + 1) {
            ghost.setDirection(Constants.RIGHT);
        } else if (cpg[0] == hg[0] && cpg[1] == hg[1] - 1) {
            ghost.setDirection(Constants.LEFT);
        } else if (cpg[0] == hg[0] + 1 && cpg[1] == hg[1]) {
            ghost.setDirection(Constants.DOWN);
        } else if (cpg[0] == hg[0] - 1 && cpg[1] == hg[1]) {
            ghost.setDirection(Constants.UP);
        }
    }
}
