package com.wowapp.game.exeption;

/*
 IncorrectLoginExeption class  throws exeption then incorrect login of user input.
 */
public class IncorrectLoginExeption extends RuntimeException {
    public IncorrectLoginExeption(String message) {
        super(message);
    }
}
