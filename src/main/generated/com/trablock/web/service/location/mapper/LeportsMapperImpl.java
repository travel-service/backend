package com.trablock.web.service.location.mapper;

import com.trablock.web.dto.location.LeportsDto;
import com.trablock.web.dto.location.LeportsDto.LeportsDtoBuilder;
import com.trablock.web.entity.location.type.Leports;
import com.trablock.web.entity.location.type.Leports.LeportsBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-16T17:26:05+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class LeportsMapperImpl implements LeportsMapper {

    @Override
    public LeportsDto toDto(Leports e) {
        if ( e == null ) {
            return null;
        }

        LeportsDtoBuilder leportsDto = LeportsDto.builder();

        leportsDto.openPeriod( e.getOpenPeriod() );
        leportsDto.parking( e.isParking() );
        leportsDto.reservation( e.getReservation() );
        leportsDto.restDate( e.getRestDate() );
        leportsDto.fee( e.getFee() );
        leportsDto.useTime( e.getUseTime() );

        return leportsDto.build();
    }

    @Override
    public Leports toEntity(LeportsDto d) {
        if ( d == null ) {
            return null;
        }

        LeportsBuilder leports = Leports.builder();

        leports.openPeriod( d.getOpenPeriod() );
        leports.parking( d.isParking() );
        leports.reservation( d.getReservation() );
        leports.restDate( d.getRestDate() );
        leports.fee( d.getFee() );
        leports.useTime( d.getUseTime() );

        return leports.build();
    }

    @Override
    public void updateFromDto(LeportsDto dto, Leports entity) {
        if ( dto == null ) {
            return;
        }
    }
}
