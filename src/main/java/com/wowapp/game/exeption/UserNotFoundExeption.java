package com.wowapp.game.exeption;

/*
 UserNotFoundExeption class   throws  exeption then usr not found
 */
public class UserNotFoundExeption extends RuntimeException {
    public UserNotFoundExeption(String message) {
        super(message);
    }
}
