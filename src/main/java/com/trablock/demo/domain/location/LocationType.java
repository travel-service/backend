package com.trablock.demo.domain.location;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** 정확한 Location Entity 구현 후 수정 필요 */
@Getter
@RequiredArgsConstructor
public enum LocationType {
    TourSpot, CultureFacility, Performance, Leports, Accommodation, Restaurant
}
