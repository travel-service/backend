package com.trablock.web.repository.location.type;

import com.trablock.web.entity.location.type.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    @Query("select a from Attraction a where a.locationId =: locationId")
    Attraction findByLocationId(@Param("locationId") Long locationId);
}
