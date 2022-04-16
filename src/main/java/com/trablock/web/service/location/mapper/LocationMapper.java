package com.trablock.web.service.location.mapper;

import com.trablock.web.config.MapStructMapperConfig;
import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.service.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapStructMapperConfig.class)
public interface LocationMapper extends GenericMapper<LocationDto, Location> {

}
