package com.trablock.web.converter;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Converter {

    @Data
    @AllArgsConstructor
    public static class UserPlan<T> {
        private T planForm;
    }

    @Data
    @AllArgsConstructor
    public static class UserDay<T> {
        private T dayForm;
    }
}
