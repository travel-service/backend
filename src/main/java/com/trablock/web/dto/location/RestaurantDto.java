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
public class RestaurantDto {
    private String popularMenu;
    private String openTime;
    private boolean packing;
    private boolean parking;
    private String restDate;
    private String menu;

}
