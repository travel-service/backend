package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.CultureDto;

public interface CultureService {

    Long create(CultureDto dto);

    CultureDto read(Long locationId);

    CultureDto update(CultureDto dto, Long locationId);

    void delete(Long locationId);
}
