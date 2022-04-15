package com.trablock.web.repository.location;

import com.trablock.web.entity.location.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InformationRepository extends JpaRepository<Information, Long> {
    @Query("select i from Information i where i.locationId = :locationId")
    Information findInformationByLocationId(@Param("locationId") Long locationId);
}
