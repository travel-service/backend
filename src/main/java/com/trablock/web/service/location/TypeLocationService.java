package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.TypeLocationSaveRequestDto;

public interface TypeLocationService {
    Object getLocationDetails(Long locationId, LocationType type);

    Object createTypeLocation(TypeLocationSaveRequestDto typeLocationSaveRequestDto, Long locationId, LocationType type);
}
