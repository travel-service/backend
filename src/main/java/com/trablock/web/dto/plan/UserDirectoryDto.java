package com.trablock.web.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class UserDirectoryDto {

    private Long userDirectoryId;
    private String directoryName;
}
