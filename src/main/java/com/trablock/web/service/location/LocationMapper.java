package com.trablock.web.service.location;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper extends GenericMapper(LocationDto,Location){
        }
