package com.wowapp.game.enums;


/**
 * GameType -enums contains  info of game user choises as rock, scissors and paper.
 * which takes from usr input from screen
 * TERMINATE - status of  quick game termination
 */
public enum PlayAttribute {
    ROCK("R"),
    SCISSOR("S"),
    PAPER("P"),
    TERMINATE("T");

    private String type;

    PlayAttribute(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
