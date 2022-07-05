package com.trablock.web.converter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class Converter {

    // plan 을 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class UserPlan<T> {
        private T planForm;
    }

    // day 을 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class UserDay<T> {
        private T dayFom;
    }

    // 메인 디렉터리를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class MainDirectory<T> {
        private int planCount;
        private T mainDirectory;
    }

    // main 디렉터리의 user 디렉터리를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class MainUserDirectory<T> {
        private List<Integer> planCount;
        private T mainUserDirectory;
    }

    // trash 디렉터리를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class TrashDirectory<T> {
        private int trashPlanCount;
        private T trashDirectory;
    }

    // user 디렉터리를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class ShowUserDirectory<T> {
        private T userDirectory;
    }
}
