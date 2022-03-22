package com.trablock.web.repository.location.type;

import com.trablock.web.entity.location.type.Attraction;
import com.trablock.web.entity.location.type.Restaurant;
import com.trablock.web.entity.location.type.TypeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    @Query("select a from Attraction a where a.locationId =: locationId")
    Optional<Attraction> findByLocationId(@Param("locationId") Long locationId);
}
