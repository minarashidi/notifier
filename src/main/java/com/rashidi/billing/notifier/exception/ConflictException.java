package com.rashidi.billing.notifier.exception;

/**
 * An exception for business conflicts.
 *
 * @author Mina Rashidi
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message, Throwable t) {
        super(message, t);
    }
}
