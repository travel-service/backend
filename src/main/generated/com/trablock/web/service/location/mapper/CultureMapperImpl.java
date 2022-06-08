package com.trablock.web.service.location.mapper;

import com.trablock.web.dto.location.CultureDto;
import com.trablock.web.dto.location.CultureDto.CultureDtoBuilder;
import com.trablock.web.entity.location.type.Culture;
import com.trablock.web.entity.location.type.Culture.CultureBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-18T00:30:09+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
@Component
public class CultureMapperImpl implements CultureMapper {

    @Override
    public CultureDto toDto(Culture e) {
        if ( e == null ) {
            return null;
        }

        CultureDtoBuilder cultureDto = CultureDto.builder();

        cultureDto.parking( e.isParking() );
        cultureDto.restDate( e.getRestDate() );
        cultureDto.fee( e.getFee() );
        cultureDto.useTime( e.getUseTime() );
        cultureDto.spendTime( e.getSpendTime() );

        return cultureDto.build();
    }

    @Override
    public Culture toEntity(CultureDto d) {
        if ( d == null ) {
            return null;
        }

        CultureBuilder culture = Culture.builder();

        culture.parking( d.isParking() );
        culture.restDate( d.getRestDate() );
        culture.fee( d.getFee() );
        culture.useTime( d.getUseTime() );
        culture.spendTime( d.getSpendTime() );

        return culture.build();
    }

    @Override
    public void updateFromDto(CultureDto dto, Culture entity) {
        if ( dto == null ) {
            return;
        }
    }
}
