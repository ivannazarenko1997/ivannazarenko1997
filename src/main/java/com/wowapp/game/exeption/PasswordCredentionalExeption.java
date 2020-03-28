package com.wowapp.game.exeption;

/*
 PasswordCredentionalExeption class   throws  exeption then inputed
  password  contains wrong symbols.
 */
public class PasswordCredentionalExeption extends RuntimeException {
    public PasswordCredentionalExeption(String message) {
        super(message);
    }
}
