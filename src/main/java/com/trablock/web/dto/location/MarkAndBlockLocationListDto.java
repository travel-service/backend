package com.trablock.web.dto.location;

import lombok.*;

import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MarkAndBlockLocationListDto {

    private Map<String, List<MarkLocationDto>> markLocations;
    private Map<String, List<BlockLocationDto>> blockLocations;

}
