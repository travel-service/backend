package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.FestivalDto;

public interface FestivalService {

    Long create(FestivalDto dto);

    FestivalDto read(Long locationId);

    FestivalDto update(FestivalDto dto, Long locationId);

    void delete(Long locationId);
}
