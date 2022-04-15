package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.RestaurantDto;
import com.trablock.web.entity.location.type.Restaurant;
import com.trablock.web.repository.location.TypeLocationRepository;
import com.trablock.web.service.location.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final TypeLocationRepository repository;
    private final RestaurantMapper mapper = Mappers.getMapper(RestaurantMapper.class);

    @Override
    public Long create(RestaurantDto dto) {
        return repository.saveRestaurant(mapper.toEntity(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantDto read(Long locationId) {
        Optional<Restaurant> target = repository.findRestaurantByLocationId(locationId);
        return target.map(mapper::toDto).orElse(null);
    }

    @Override
    public RestaurantDto update(RestaurantDto dto, Long locationId) {
        repository.saveRestaurant(mapper.toEntity(dto));
        Optional<Restaurant> updated = repository.findRestaurantByLocationId(locationId);

        return updated.map(mapper::toDto).orElse(null);
    }

    @Override
    public void delete(Long locationId) {
        Optional<Restaurant> target = repository.findRestaurantByLocationId(locationId);
        target.ifPresent(repository::deleteRestaurant);
    }
}
