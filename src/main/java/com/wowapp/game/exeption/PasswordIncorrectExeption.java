package com.wowapp.game.exeption;

/*
 PasswordIncorrectExeption class   throws  exeption then inputed
 incorect  password  for current user.
 */
public class PasswordIncorrectExeption extends RuntimeException {
    public PasswordIncorrectExeption(String message) {
        super(message);
    }
}
