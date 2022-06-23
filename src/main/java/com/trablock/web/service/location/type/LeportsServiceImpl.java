package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.LeportsDto;
import com.trablock.web.entity.location.type.Leports;
import com.trablock.web.repository.location.TypeRepositoryCustomImpl;
import com.trablock.web.service.location.mapper.LeportsMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LeportsServiceImpl implements LeportsService {

    private final TypeRepositoryCustomImpl repository;
    private final LeportsMapper mapper = Mappers.getMapper(LeportsMapper.class);

    @Override
    public Long create(LeportsDto dto) {
        return repository.saveLeports(mapper.toEntity(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public LeportsDto read(Long locationId) {
        Optional<Leports> target = repository.findLeportsByLocationId(locationId);
        return target.map(mapper::toDto).orElse(null);
    }

    @Override
    public LeportsDto update(LeportsDto dto, Long locationId) {
        repository.saveLeports(mapper.toEntity(dto));
        Optional<Leports> updated = repository.findLeportsByLocationId(locationId);

        return updated.map(mapper::toDto).orElse(null);
    }

    @Override
    public void delete(Long locationId) {
        Optional<Leports> target = repository.findLeportsByLocationId(locationId);
        target.ifPresent(repository::deleteLeports);
    }
}
