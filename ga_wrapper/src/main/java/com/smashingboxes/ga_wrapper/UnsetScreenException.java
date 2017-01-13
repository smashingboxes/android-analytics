package com.smashingboxes.ga_wrapper;

/**
 * Created by Tyler McCraw on 5/19/16.
 *
 * Exception to be thrown when activities that do not set the screen name
 * for GA screen view events
 */
public class UnsetScreenException extends Exception {

    public UnsetScreenException(String message) {
        super(message);
    }
}