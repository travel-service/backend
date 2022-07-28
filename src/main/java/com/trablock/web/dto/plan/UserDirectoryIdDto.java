package com.trablock.web.dto.plan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserDirectoryIdDto {

    @JsonIgnore
    private Long planId;
    private Long userDirectoryId;

    public UserDirectoryIdDto(Long planId, Long userDirectoryId) {
        this.planId = planId;
        this.userDirectoryId = userDirectoryId;
    }
}
