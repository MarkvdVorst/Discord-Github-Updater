package org.example.githubdiscordupdater.api.model.util;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ReviewStates {
    CHANGES_REQUESTED,
    APPROVED,
    COMMENTED;

    @JsonCreator
    public static ReviewStates fromString(String state) {
        if(state == null) return null;
        try {
            return ReviewStates.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
