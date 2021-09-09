package edu.rice.comp504.model;

import junit.framework.TestCase;

public class MazeMapTest extends TestCase {

    public void testNumberOfDots() {
        MazeMap mazeMap = MazeMap.getInstance();
        int count = 0;
        for (int i = 0 ; i < mazeMap.getHeight() ; i++) {
            for (int j = 0 ; j < mazeMap.getWidth() ; j++) {
                if (mazeMap.getMaze()[i][j] > 0) {
                    count += 1;
                }
            }
        }
        System.out.println(count);
    }
}
