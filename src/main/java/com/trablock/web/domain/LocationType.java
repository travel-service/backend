package com.trablock.web.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LocationType {
    ATTRACTION("Attraction"),
    CULTURE("Culture"),
    FESTIVAL("Festival"),
    LEPORTS("Leports"),
    LODGE("Lodge"),
    RESTAURANT("Restaurant");

    private String type;

    @JsonCreator
    public static LocationType fromValue(String value) {
        for (LocationType type : LocationType.values()) {
            if (type.getType().equals(value)) {
                return type;
            }
        }
        return null;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    LocationType(String type) {
        this.type = type;
    }
}
