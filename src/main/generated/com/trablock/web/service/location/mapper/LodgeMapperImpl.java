package com.trablock.web.service.location.mapper;

import com.trablock.web.dto.location.LodgeDto;
import com.trablock.web.dto.location.LodgeDto.LodgeDtoBuilder;
import com.trablock.web.entity.location.type.Lodge;
import com.trablock.web.entity.location.type.Lodge.LodgeBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-18T00:30:09+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
@Component
public class LodgeMapperImpl implements LodgeMapper {

    @Override
    public LodgeDto toDto(Lodge e) {
        if ( e == null ) {
            return null;
        }

        LodgeDtoBuilder lodgeDto = LodgeDto.builder();

        lodgeDto.checkInTime( e.getCheckInTime() );
        lodgeDto.checkOutTime( e.getCheckOutTime() );
        lodgeDto.cooking( e.isCooking() );
        lodgeDto.parking( e.isParking() );
        if ( e.getNumOfRooms() != null ) {
            lodgeDto.numOfRooms( e.getNumOfRooms().longValue() );
        }
        lodgeDto.reservationUrl( e.getReservationUrl() );
        lodgeDto.subFacility( e.getSubFacility() );

        return lodgeDto.build();
    }

    @Override
    public Lodge toEntity(LodgeDto d) {
        if ( d == null ) {
            return null;
        }

        LodgeBuilder lodge = Lodge.builder();

        lodge.checkInTime( d.getCheckInTime() );
        lodge.checkOutTime( d.getCheckOutTime() );
        lodge.cooking( d.isCooking() );
        lodge.parking( d.isParking() );
        if ( d.getNumOfRooms() != null ) {
            lodge.numOfRooms( d.getNumOfRooms().intValue() );
        }
        lodge.reservationUrl( d.getReservationUrl() );
        lodge.subFacility( d.getSubFacility() );

        return lodge.build();
    }

    @Override
    public void updateFromDto(LodgeDto dto, Lodge entity) {
        if ( dto == null ) {
            return;
        }
    }
}
