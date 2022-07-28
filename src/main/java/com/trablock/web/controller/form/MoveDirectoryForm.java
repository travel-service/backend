package com.trablock.web.controller.form;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MoveDirectoryForm {

    private Long userDirectoryId;
    private List<Long> planId;
}
