package com.trablock.web.converter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class Converter {

    // error 상태 반환
    @Data
    @AllArgsConstructor
    public static class Error<T> {
        private int httpStatus;
        private String message;
    }

    // plan 생성후 responsebody에 정보를 담아 보내기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class CreatePlan<T> {
        private int httpStatus;
        private String message;
        private Long planId;
    }


    // plan 을 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class UserPlan<T> {
        private int httpStatus;
        private String message;
        private T planForm;
    }

    // concept 수정 후 상태코드와 메시지를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class UpdateConcept<T> {
        private int httpStatus;
        private String message;
    }

    // concept 수정 후 상태코드와 메시지를 반환하기 위한 클래스

    // day 생성후 상태코드와 메시지를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class CreateDay<T> {
        private int httpStatus;
        private String message;
    }

    // day 을 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class UserDay<T> {
        private int httpStatus;
        private String message;
        private T dayForm;
    }

    // day 수정 후 상태코드와 메시지를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class GetDay<T> {
        private int httpStatus;
        private String message;
    }

    // Plan 수정 후 상태코드와 메시지를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class UpdatePlan<T> {
        private int httpStatus;
        private String message;
    }

    // SelectedLocation 수정 후 상태코드와 메시지를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class UpdateSelectedLocation<T> {
        private int httpStatus;
        private String message;
    }



    // 메인 디렉터리를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class MainDirectory<T> {
        private int httpStatus;
        private String message;
        private int planCount;
        private T mainDirectory;
    }

    // main 디렉터리의 user 디렉터리를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class MainUserDirectory<T> {
        private int httpStatus;
        private String message;
        private List<Integer> planCount;
        private T mainUserDirectory;
    }

    // trash 디렉터리를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class TrashDirectory<T> {
        private int httpStatus;
        private String message;
        private int trashPlanCount;
        private T trashDirectory;
    }

    // user 디렉터리를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class ShowUserDirectory<T> {
        private int httpStatus;
        private String message;
        private T userDirectory;
    }

    // 플래이 메인에서 휴지통으 정상적으로 이동 후 상태코드와 메시지를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class PlanMoveMainToTrash<T> {
        private int httpStatus;
        private String message;
    }

    // 플래을 휴지통에 로서 메인으로 정상적으로 이동 후 상태코드와 메시지를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class PlanMoveTrashToMain<T> {
        private int httpStatus;
        private String message;
    }

    // 플래을 휴지통에서 완전 삭제된 후 상태코드와 메시지를 반환하기 위한 클래스
    @Data
    @AllArgsConstructor
    public static class PlanDelete<T> {
        private int httpStatus;
        private String message;
    }

    // user dir 생성 후 상태코드를 반환하기 클래스
    @Data
    @AllArgsConstructor
    public static class CreateUserDirectory<T> {
        private int httpStatus;
        private String message;
        private Long UserDirectoryId;
    }

    // user dir 삭제 후 상태코드와 메시지를 반환하기 클래스
    @Data
    @AllArgsConstructor
    public static class DeleteUserDirectory<T> {
        private int httpStatus;
        private String message;
    }

    // user dir 삭제 후 상태코드와 메시지를 반환하기 클래스
    @Data
    @AllArgsConstructor
    public static class PlanMoveToUserDirectory<T> {
        private int httpStatus;
        private String message;
    }

    // user dir 이름 변경 후 상태코드와 메시지를 반환하기 클래스
    @Data
    @AllArgsConstructor
    public static class UpdatePlanName<T> {
        private int httpStatus;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class DeletePlans<T> {
        private int httpStatus;
        private String message;
    }
}
