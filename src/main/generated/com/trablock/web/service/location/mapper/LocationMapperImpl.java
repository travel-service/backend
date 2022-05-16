package com.trablock.web.service.location.mapper;

import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.dto.location.LocationDto.LocationDtoBuilder;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.Location.LocationBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-16T17:26:04+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Override
    public LocationDto toDto(Location e) {
        if ( e == null ) {
            return null;
        }

        LocationDtoBuilder locationDto = LocationDto.builder();

        locationDto.id( e.getId() );
        locationDto.name( e.getName() );
        locationDto.address1( e.getAddress1() );
        locationDto.address2( e.getAddress2() );
        locationDto.image( e.getImage() );
        locationDto.type( e.getType() );

        return locationDto.build();
    }

    @Override
    public Location toEntity(LocationDto d) {
        if ( d == null ) {
            return null;
        }

        LocationBuilder location = Location.builder();

        location.id( d.getId() );
        location.name( d.getName() );
        location.address1( d.getAddress1() );
        location.address2( d.getAddress2() );
        location.image( d.getImage() );
        location.type( d.getType() );

        return location.build();
    }

    @Override
    public void updateFromDto(LocationDto dto, Location entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
    }
}
