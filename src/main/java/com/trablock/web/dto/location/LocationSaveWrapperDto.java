package com.trablock.web.dto.location;

import com.trablock.web.dto.location.save.InformationRequestDto;
import com.trablock.web.dto.location.save.LocationRequestDto;
import com.trablock.web.dto.location.save.MemberLocationRequestDto;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class LocationSaveWrapperDto {

    private LocationRequestDto location;

    private InformationRequestDto information;

    private MemberLocationRequestDto memberLocation;

    private TypeLocationRequestDto typeLocation;
}
