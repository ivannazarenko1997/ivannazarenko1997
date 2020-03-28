package com.wowapp.game.enums;


/**
 * GameType -enums contains  info of game types,
 * which takes from usr input from screen,
 * where AUTHOMATIC -authomatic play rounds between computer and "bad human input"
 * MANUAL -manual  play rounds between computer and human
 * TERMINATE - status of  quick game termination
 */
public enum GameType {
    AUTHOMATIC("authomatic"),
    MANUAL("manual"),
    TERMINATE("ternimate");

    private String type;

    GameType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}