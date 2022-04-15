package com.trablock.web.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class AttractionDto {
    private boolean parking;
    private String restDate;

    @Builder
    public AttractionDto(boolean parking, String restDate) {
        this.parking = parking;
        this.restDate = restDate;
    }
}
