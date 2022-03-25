package com.trablock.web.service.location;

import com.trablock.web.dto.LocationDto;

import java.util.List;

public interface LocationService {

    void create(LocationDto locationDto);

    LocationDto findLocation(Long locationId);

    LocationDto findLocationByName(String locationName);

    List<LocationDto> findAllLocationByMember(Long memberId);

    List<LocationDto> findAllPublicLocationByMember(Long memberId);

}
