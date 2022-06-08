package com.trablock.web.service.location.mapper;

import com.trablock.web.dto.location.RestaurantDto;
import com.trablock.web.dto.location.RestaurantDto.RestaurantDtoBuilder;
import com.trablock.web.entity.location.type.Restaurant;
import com.trablock.web.entity.location.type.Restaurant.RestaurantBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-08T10:04:57+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public RestaurantDto toDto(Restaurant e) {
        if ( e == null ) {
            return null;
        }

        RestaurantDtoBuilder restaurantDto = RestaurantDto.builder();

        restaurantDto.popularMenu( e.getPopularMenu() );
        restaurantDto.openTime( e.getOpenTime() );
        restaurantDto.packing( e.isPacking() );
        restaurantDto.parking( e.isParking() );
        restaurantDto.restDate( e.getRestDate() );
        restaurantDto.menu( e.getMenu() );

        return restaurantDto.build();
    }

    @Override
    public Restaurant toEntity(RestaurantDto d) {
        if ( d == null ) {
            return null;
        }

        RestaurantBuilder restaurant = Restaurant.builder();

        restaurant.popularMenu( d.getPopularMenu() );
        restaurant.openTime( d.getOpenTime() );
        restaurant.packing( d.isPacking() );
        restaurant.parking( d.isParking() );
        restaurant.restDate( d.getRestDate() );
        restaurant.menu( d.getMenu() );

        return restaurant.build();
    }

    @Override
    public void updateFromDto(RestaurantDto dto, Restaurant entity) {
        if ( dto == null ) {
            return;
        }
    }
}
