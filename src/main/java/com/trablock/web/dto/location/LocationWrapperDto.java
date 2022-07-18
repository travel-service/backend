package com.trablock.web.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trablock.web.dto.location.save.InformationRequestDto;
import com.trablock.web.dto.location.save.LocationRequestDto;
import com.trablock.web.dto.location.save.MemberLocationRequestDto;
import lombok.*;

import static lombok.AccessLevel.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class LocationWrapperDto {

    @JsonProperty("location")
    private LocationRequestDto location;

    @JsonProperty("information")
    private InformationRequestDto information;

    @JsonProperty("memberLocation")
    private MemberLocationRequestDto memberLocation;

    @JsonProperty("typeLocation")
    private TypeLocationRequestDto typeLocation;

}
