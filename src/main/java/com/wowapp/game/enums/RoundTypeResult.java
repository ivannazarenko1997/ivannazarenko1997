package com.wowapp.game.enums;

/**
 * RoundTypeResult -enum contains  info of game winner,
 * where WIN -win  of human, LOSE -win  of computer,DRAW -none wins.
 * which takes from usr input from screen
 * TERMINATE - status of  quick game termination
 */
public enum RoundTypeResult {
    WIN("win"),
    LOSE("lose"),
    DRAW("draw"),
    TERMINATE("terminate");

    private String type;

    RoundTypeResult(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}