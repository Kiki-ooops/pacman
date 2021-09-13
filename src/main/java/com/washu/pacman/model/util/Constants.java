package com.washu.pacman.model.util;


public class Constants {
    public static final int BLOCK = -2; // ghost room
    public static final int WALL = -1;
    public static final int EMPTY = 0;
    public static final int FOOD = 1;
    public static final int PILL = 2;
    public static final int FRUIT = 3;
    public static final int TOFU = 4;

    public static final int[] UP = {-1, 0};
    public static final int[] LEFT = {0, -1};
    public static final int[] DOWN = {1, 0};
    public static final int[] RIGHT = {0, 1};
    public static final int[][] allDirections = {UP, LEFT, DOWN, RIGHT};


    public static final int RED = 1;
    public static final int PINK = 2;
    public static final int CYAN = 3;
    public static final int ORANGE = 4;

    public static final int GRID_SIZE = 16;

    public static final int FRUIT_VALUE = 100;
    public static final int PILL_VALUE = 50;
    public static final int FOOD_VALUE = 10;
    public static final int TOFU_VALUE = 50;

    public static final int LOW_SPEED = 2;
    public static final int NORMAL_SPEED = 4;
    public static final int HIGH_SPEED = 8;


    public static final int GHOST_INIT_POS_X = 192;
    public static final int GHOST_INIT_POS_Y = 232;
    public static final int GHOST_WAIT_EXIT_TIME_DURATION = 100;

    public static final int LOSE_LIFE_WAIT_TIME = 20;


    public static final String PACMAN = "thePacMan";
    public static final String GHOSTS = "theGhost";
    public static final String MAZE = "mazeMap";
    public static final String GAMEINFO = "gameInfo";

    public static final int GHOST_STATE_EATEN = 3;
    public static final int GHOST_STATE_FLASHING = 2;
    public static final int GHOST_STATE_DARK_BLUE = 1;
    public static final int GHOST_STATE_NORMAL = 0;

    public static final String FRIGHTEN_BEHAVIOR = "frightenBehavior";
    public static final String SCATTER_BEHAVIOR = "scatterBehavior";
    public static final String DEATH_BEHAVIOR = "deathBehavior";
    public static final String CHASE_BEHAVIOR = "chaseBehavior";
    public static final String CYAN_CHASE_BEHAVIOR = "cyanChaseBehavior";
    public static final String RED_CHASE_BEHAVIOR = "redChaseBehavior";
    public static final String ORANGE_CHASE_BEHAVIOR = "orangeChaseBehavior";
    public static final String PINK_CHASE_BEHAVIOR = "pinkChaseBehavior";



    public static final int GAME_INFO_PILL_DURATION = 300;
    public static final int GAME_INFO_TOFU_DURATION = 100;
    public static final int GAME_INFO_GHOST_EATEN_INITIAL_BONUS = 200;
    public static final int GAME_INFO_TOTAL_DOTS = 245;
    public static final int GAME_INFO_RANDOM_CHERRY_PERIOD = 100;

    public static final int GAME_INFO_FRUIT_GRID_X = 18;
    public static final int GAME_INFO_FRUIT_GRID_Y = 16;

    public static final int TOFU_MODE = 1;
    public static final int PILL_MODE = 0;
}
