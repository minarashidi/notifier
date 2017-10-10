package com.rashidi.billing.notifier.exception;

/**
 * An exception for duplicates.
 *
 * @author Mina Rashidi
 */
public class DuplicateException extends RuntimeException {

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable t) {
        super(message, t);
    }
}
