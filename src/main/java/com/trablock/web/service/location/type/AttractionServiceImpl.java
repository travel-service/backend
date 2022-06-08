package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.AttractionDto;
import com.trablock.web.entity.location.type.Attraction;
import com.trablock.web.repository.location.TypeLocationRepository;
import com.trablock.web.service.location.mapper.AttractionMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final TypeLocationRepository repository;
    private final AttractionMapper mapper = Mappers.getMapper(AttractionMapper.class);

    @Override
    public Long create(AttractionDto dto) {
        return repository.saveAttraction(mapper.toEntity(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public AttractionDto read(Long locationId) {
        Optional<Attraction> target = repository.findAttractionByLocationId(locationId);
        return target.map(mapper::toDto).orElse(null);
    }

    @Override
    public AttractionDto update(AttractionDto dto, Long locationId) {
        repository.saveAttraction(mapper.toEntity(dto));
        Optional<Attraction> updated = repository.findAttractionByLocationId(locationId);

        return updated.map(mapper::toDto).orElse(null);
    }

    @Override
    public void delete(Long locationId) {
        Optional<Attraction> target = repository.findAttractionByLocationId(locationId);
        target.ifPresent(repository::deleteAttraction);
    }
}
