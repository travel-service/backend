package com.trablock.web.service.location.mapper;

import com.trablock.web.dto.location.FestivalDto;
import com.trablock.web.dto.location.FestivalDto.FestivalDtoBuilder;
import com.trablock.web.entity.location.type.Festival;
import com.trablock.web.entity.location.type.Festival.FestivalBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-18T00:30:09+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
@Component
public class FestivalMapperImpl implements FestivalMapper {

    @Override
    public FestivalDto toDto(Festival e) {
        if ( e == null ) {
            return null;
        }

        FestivalDtoBuilder festivalDto = FestivalDto.builder();

        festivalDto.endDate( e.getEndDate() );
        festivalDto.homepage( e.getHomepage() );
        festivalDto.place( e.getPlace() );
        festivalDto.startDate( e.getStartDate() );
        festivalDto.placeInfo( e.getPlaceInfo() );
        festivalDto.playTime( e.getPlayTime() );
        festivalDto.program( e.getProgram() );
        festivalDto.fee( e.getFee() );

        return festivalDto.build();
    }

    @Override
    public Festival toEntity(FestivalDto d) {
        if ( d == null ) {
            return null;
        }

        FestivalBuilder festival = Festival.builder();

        festival.endDate( d.getEndDate() );
        festival.homepage( d.getHomepage() );
        festival.place( d.getPlace() );
        festival.startDate( d.getStartDate() );
        festival.placeInfo( d.getPlaceInfo() );
        festival.playTime( d.getPlayTime() );
        festival.program( d.getProgram() );
        festival.fee( d.getFee() );

        return festival.build();
    }

    @Override
    public void updateFromDto(FestivalDto dto, Festival entity) {
        if ( dto == null ) {
            return;
        }
    }
}
