package com.hevlar.accounting.model;

/**
 * Recurrence - used for specifying recurring journal entries
 */
public enum Recurrence {
    /**
     * Yearly
     */
    Y("Yearly"),

    /**
     * Monthly
     */
    M("Monthly"),

    /**
     * Fortnightly, once in two weeks
     */
    F("Fortnightly"),

    /**
     * Weekly
     */
    W("Weekly"),

    /**
     * Daily
     */
    D("Daily"),

    /**
     * None
     */
    N("None");

    /**
     * Label to display recurrence
     */
    public final String label;

    /**
     * Default constructor
     * @param label label of the recurrence
     */
    Recurrence(String label) {
        this.label = label;
    }
}
