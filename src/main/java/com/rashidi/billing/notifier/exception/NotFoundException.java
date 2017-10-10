package com.rashidi.billing.notifier.exception;

/**
 * An exception for NotFound.
 *
 * @author Mina Rashidi
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
