package com.trablock.web.controller.form;

import lombok.Getter;

import java.util.List;

@Getter
public class UserDirectoryForm {

    private List<Long> userDirectoryId;
    private String directoryName;
}
