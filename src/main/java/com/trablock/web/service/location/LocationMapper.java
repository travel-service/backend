package com.trablock.web.service.location;

import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.service.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper extends GenericMapper<LocationDto, Location> {

}
