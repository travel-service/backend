package com.trablock.web.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DirectoryNameUpdateDto {

    private String directoryName;
}
