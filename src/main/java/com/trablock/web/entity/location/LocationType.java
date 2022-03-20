package com.trablock.web.entity.location;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A Guide to Java Enums
 * https://www.baeldung.com/a-guide-to-java-enums
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LocationType {
    MEMBER(0) {
        @Override
        public boolean isMember() {
            return true;
        }
    },
    LODGE(1) {
        @Override
        public boolean isLodge() {
            return true;
        }
    },
    RESTAURANT(2) {
        @Override
        public boolean isRestaurant() {
            return true;
        }
    },
    ATTRACTION(3) {
        @Override
        public boolean isAttraction() {
            return true;
        }
    },
    CULTURE(4) {
        @Override
        public boolean isCulture() {
            return true;
        }
    },
    FESTIVAL(5) {
        @Override
        public boolean isFestival() {
            return true;
        }
    },
    LEPORTS(6) {
        @Override
        public boolean isLeports() {
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


    @JsonProperty("type")
    private int getType() {
        return type;
    }

    private LocationType(int type) {
        this.type = type;
    }

}
