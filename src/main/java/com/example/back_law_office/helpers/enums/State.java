package com.example.back_law_office.helpers.enums;

public enum State {
    ACTIVE("Active"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    ARCHIVED("Archived"); 

    private final String displayName;

    State(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
