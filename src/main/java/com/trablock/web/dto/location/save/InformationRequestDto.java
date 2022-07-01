package com.trablock.web.dto.location.save;

import com.trablock.web.entity.location.Information;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InformationRequestDto {

    private String summary;
    private String report;
    private String image1;
    private String image2;
    private String tel;

    public Information toEntity(Long locationId) {
        return Information.builder()
                .locationId(locationId)
                .summary(summary)
                .report(report)
                .image1(image1)
                .image2(image2)
                .tel(tel)
                .build();
    }
}
