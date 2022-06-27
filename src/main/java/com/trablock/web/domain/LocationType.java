package com.trablock.web.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LocationType {
    ATTRACTION(0) {
        @Override
        public boolean isAttraction() {
            return true;
        }
    },
    CULTURE(1) {
        @Override
        public boolean isCulture() {
            return true;
        }
    },
    FESTIVAL(2) {
        @Override
        public boolean isFestival() {
            return true;
        }
    },
    LEPORTS(3) {
        @Override
        public boolean isLeports() {
            return true;
        }
    },
    LODGE(4) {
        @Override
        public boolean isLodge() {
            return true;
        }
    },
    RESTAURANT(5) {
        @Override
        public boolean isRestaurant() {
            return true;
        }
    };

    private int type;

    public boolean isMember() {
        return false;
    }

    public boolean isLodge() {
        return false;
    }

    public boolean isRestaurant() {
        return false;
    }

    public boolean isAttraction() {
        return false;
    }

    public boolean isCulture() {
        return false;
    }

    public boolean isFestival() {
        return false;
    }

    public boolean isLeports() {
        return false;
    }

    @JsonCreator
    public static LocationType fromValue(String value) {
        switch (value) {
            case "ATTRACTION":
                return ATTRACTION;
            case "CULTURE":
                return CULTURE;
            case "FESTIVAL":
                return FESTIVAL;
            case "LEPORTS":
                return LEPORTS;
            case "LODGE":
                return LODGE;
            case "RESTAURANT":
                return RESTAURANT;
        }
        return null;
    }

    @JsonProperty("type")
    private int getType() {
        return type;
    }

    private LocationType(int type) {
        this.type = type;
    }

}