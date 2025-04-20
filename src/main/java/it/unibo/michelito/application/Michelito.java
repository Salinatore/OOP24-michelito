package it.unibo.michelito.application;

import it.unibo.michelito.controller.maincontroller.impl.MainControllerImpl;

/**
 * Entry class for the Michelito application.
 */
public final class Michelito {
    /**
     *  Private constructor preventing instantiation.
     */
    private Michelito() { }

    /**
     * Entry method to start the Michelito application.
     *
     * @param args is ignored.
     */
    public static void main(final String[] args) {
        new MainControllerImpl().start();
    }
}
