package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.LodgeDto;

public interface LodgeService {

    Long create(LodgeDto dto);

    LodgeDto read(Long locationId);

    LodgeDto update(LodgeDto dto, Long locationId);

    void delete(Long locationId);
}
