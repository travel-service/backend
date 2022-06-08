package com.trablock.web.controller.form;

import lombok.Getter;

import java.util.List;

@Getter
public class MoveDirectoryForm {

    private Long userDirectoryId;
    private List<Long> planId;
}
