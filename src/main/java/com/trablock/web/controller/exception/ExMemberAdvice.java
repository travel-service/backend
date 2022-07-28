//package com.trablock.web.controller.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.io.FileNotFoundException;
//
///**
// * MemberController 에서 발생하는 예외 처리
// */
//@Slf4j
//@RestControllerAdvice
//public class ExMemberAdvice {
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ErrorResult illegalExHandle(IllegalArgumentException e) {
//        return new ErrorResult("BAD", e.getMessage());
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> MemberExHandle(MemberException e) {
//        String message = e.getMessage();
//        ErrorResult errorResult = new ErrorResult("MEMBER-EX", e.getMessage());
//
//        if (message.equals("Token-Error")) {
//                return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(FileNotFoundException.class)
//    public ErrorResult fileExHandle(FileNotFoundException e) {
//        return new ErrorResult("IMAGE EX", "일치하는 사진을 찾지 못했습니다.");
//    }
//
//
////    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
////    @ExceptionHandler
////    public ErrorResult exHandle(Exception e) {
////        return new ErrorResult("EX", "내부 로직 오류");
////    }
//}
