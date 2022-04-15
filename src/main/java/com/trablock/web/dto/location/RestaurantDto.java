package com.trablock.web.dto.location;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class RestaurantDto {
    private String popularMenu;
    private String openTime;
    private boolean packing;
    private boolean parking;
    private String restDate;
    private String menu;

    @Builder
    public RestaurantDto(String popularMenu, String openTime, boolean packing, boolean parking, String restDate, String menu) {
        this.popularMenu = popularMenu;
        this.openTime = openTime;
        this.packing = packing;
        this.parking = parking;
        this.restDate = restDate;
        this.menu = menu;
    }
}
