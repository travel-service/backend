package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.RestaurantDto;

public interface RestaurantService {

    Long create(RestaurantDto dto);

    RestaurantDto read(Long locationId);

    RestaurantDto update(RestaurantDto dto, Long locationId);

    void delete(Long locationId);
}
