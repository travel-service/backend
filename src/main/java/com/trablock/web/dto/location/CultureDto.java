package com.trablock.web.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CultureDto {
    private boolean parking;
    private String restDate;
    private String fee;
    private String useTime;
    private String spendTime;

}
