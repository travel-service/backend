package com.trablock.web.entity.plan;

import com.trablock.web.dto.plan.DayDto;
import com.trablock.web.entity.location.Location;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Day {

    @Id
    @GeneratedValue
    @Column(name = "day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Embedded
    private MovingData movingData;

    private Integer days;
    private String copyLocationId;

    public DayDto toDto() {
        return DayDto.builder()
                .locationId(location.getId())
                .copyLocationId(copyLocationId)
                .movingData(movingData)
                .days(days)
                .build();
    }
}
