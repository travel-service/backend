package com.trablock.web.service.location.type;

import com.trablock.web.dto.location.FestivalDto;
import com.trablock.web.entity.location.type.Festival;
import com.trablock.web.repository.location.CustomTypeRepositoryImpl;
import com.trablock.web.service.location.mapper.FestivalMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalServiceImpl implements FestivalService {

    private final CustomTypeRepositoryImpl repository;
    private final FestivalMapper mapper = Mappers.getMapper(FestivalMapper.class);

    @Override
    public Long create(FestivalDto dto) {
        return repository.saveFestival(mapper.toEntity(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public FestivalDto read(Long locationId) {
        Optional<Festival> target = repository.findFestivalByLocationId(locationId);

        return target.map(festival -> mapper.toDto(festival)).orElse(null);
    }

    @Override
    public FestivalDto update(FestivalDto dto, Long locationId) {
        repository.saveFestival(mapper.toEntity(dto));
        Optional<Festival> updated = repository.findFestivalByLocationId(locationId);

        return updated.map(mapper::toDto).orElse(null);
    }

    @Override
    public void delete(Long locationId) {
        Optional<Festival> target = repository.findFestivalByLocationId(locationId);
        target.ifPresent(repository::deleteFestival);
    }
}
