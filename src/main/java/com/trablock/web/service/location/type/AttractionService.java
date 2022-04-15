package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.AttractionDto;

public interface AttractionService {

    Long create(AttractionDto dto);

    AttractionDto read(Long locationId);

    AttractionDto update(AttractionDto dto, Long locationId);

    void delete(Long locationId);
}
