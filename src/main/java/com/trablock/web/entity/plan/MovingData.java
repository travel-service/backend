package com.trablock.web.entity.plan;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovingData {

    private String vehicle;
    private String movingTime;
    private String stayTime;
    private String startTime;
    private String arriveTime;
}
