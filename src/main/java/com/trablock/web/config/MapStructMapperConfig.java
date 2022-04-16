package com.trablock.web.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public class MapStructMapperConfig {

}
