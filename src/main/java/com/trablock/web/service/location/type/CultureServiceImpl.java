package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.CultureDto;
import com.trablock.web.entity.location.type.Culture;
import com.trablock.web.repository.location.TypeRepositoryCustomImpl;
import com.trablock.web.service.location.mapper.CultureMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CultureServiceImpl implements CultureService {

    private final TypeRepositoryCustomImpl repository;
    private final CultureMapper mapper = Mappers.getMapper(CultureMapper.class);

    @Override
    public Long create(CultureDto dto) {
        return repository.saveCulture(mapper.toEntity(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public CultureDto read(Long locationId) {
        Optional<Culture> target = repository.findCultureByLocationId(locationId);
        return target.map(mapper::toDto).orElse(null);
    }

    @Override
    public CultureDto update(CultureDto dto, Long locationId) {
        repository.saveCulture(mapper.toEntity(dto));
        Optional<Culture> updated = repository.findCultureByLocationId(locationId);

        return updated.map(mapper::toDto).orElse(null);
    }

    @Override
    public void delete(Long locationId) {
        Optional<Culture> target = repository.findCultureByLocationId(locationId);
        target.ifPresent(repository::deleteCulture);
    }
}
