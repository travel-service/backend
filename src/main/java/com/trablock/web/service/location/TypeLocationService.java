package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;

public interface TypeLocationService {
    Object getLocationDetails(Long locationId, LocationType type);
}
