package com.trablock.web.repository.location.type;

import com.trablock.web.entity.location.type.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
    @Query("select f from Festival f where f.locationId = :locationId")
    Festival findByLocationId(@Param("locationId") Long locationId);
}
