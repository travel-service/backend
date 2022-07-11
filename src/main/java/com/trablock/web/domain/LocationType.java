package com.trablock.web.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LocationType {
    ATTRACTION("ATTRACTION"),
    CULTURE("CULTURE"),
    FESTIVAL("FESTIVAL"),
    LEPORTS("LEPORTS"),
    LODGE("LODGE"),
    RESTAURANT("RESTAURANT");

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