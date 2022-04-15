package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.LeportsDto;

public interface LeportsService {

    Long create(LeportsDto dto);

    LeportsDto read(Long locationId);

    LeportsDto update(LeportsDto dto, Long locationId);

    void delete(Long locationId);
}
