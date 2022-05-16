package com.trablock.web.service.location.mapper;

import com.trablock.web.dto.location.AttractionDto;
import com.trablock.web.dto.location.AttractionDto.AttractionDtoBuilder;
import com.trablock.web.entity.location.type.Attraction;
import com.trablock.web.entity.location.type.Attraction.AttractionBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-16T17:26:05+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class AttractionMapperImpl implements AttractionMapper {

    @Override
    public AttractionDto toDto(Attraction e) {
        if ( e == null ) {
            return null;
        }

        AttractionDtoBuilder attractionDto = AttractionDto.builder();

        attractionDto.parking( e.isParking() );
        attractionDto.restDate( e.getRestDate() );

        return attractionDto.build();
    }

    @Override
    public Attraction toEntity(AttractionDto d) {
        if ( d == null ) {
            return null;
        }

        AttractionBuilder attraction = Attraction.builder();

        attraction.parking( d.isParking() );
        attraction.restDate( d.getRestDate() );

        return attraction.build();
    }

    @Override
    public void updateFromDto(AttractionDto dto, Attraction entity) {
        if ( dto == null ) {
            return;
        }
    }
}
