package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.LodgeDto;
import com.trablock.web.entity.location.type.Lodge;
import com.trablock.web.repository.location.TypeLocationRepository;
import com.trablock.web.service.location.mapper.LodgeMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LodgeServiceImpl implements LodgeService {

    private final TypeLocationRepository repository;
    private final LodgeMapper mapper = Mappers.getMapper(LodgeMapper.class);

    @Override
    public Long create(LodgeDto dto) {
        return repository.saveLodge(mapper.toEntity(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public LodgeDto read(Long locationId) {
        Optional<Lodge> target = repository.findLodgeByLocationId(locationId);
        return target.map(mapper::toDto).orElse(null);
    }

    @Override
    public LodgeDto update(LodgeDto dto, Long locationId) {
        repository.saveLodge(mapper.toEntity(dto));
        Optional<Lodge> updated = repository.findLodgeByLocationId(locationId);
        return updated.map(mapper::toDto).orElse(null);
    }

    @Override
    public void delete(Long locationId) {
        Optional<Lodge> target = repository.findLodgeByLocationId(locationId);
        target.ifPresent(repository::deleteLodge);
    }
}
