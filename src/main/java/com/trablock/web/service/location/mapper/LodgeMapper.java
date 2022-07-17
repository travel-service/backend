package com.trablock.web.service.location.mapper;

import com.trablock.web.config.MapStructMapperConfig;
import com.trablock.web.dto.location.TypeLocationRequestDto;
import com.trablock.web.entity.location.type.Lodge;
import com.trablock.web.service.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapStructMapperConfig.class)
public interface LodgeMapper extends GenericMapper<TypeLocationRequestDto, Lodge> {
}
