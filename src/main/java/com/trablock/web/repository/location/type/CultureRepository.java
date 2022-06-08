package com.trablock.web.repository.location.type;

import com.trablock.web.entity.location.type.Culture;
import com.trablock.web.entity.location.type.Restaurant;
import com.trablock.web.entity.location.type.TypeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CultureRepository extends JpaRepository<Culture, Long> {
    @Query("select c from Culture c where c.locationId = :locationId")
    Culture findByLocationId(@Param("locationId") Long locationId);
}
