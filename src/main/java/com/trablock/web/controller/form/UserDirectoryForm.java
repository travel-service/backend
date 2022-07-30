package com.trablock.web.controller.form;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDirectoryForm {

    private List<Long> userDirectoryId;
    private String directoryName;
}
