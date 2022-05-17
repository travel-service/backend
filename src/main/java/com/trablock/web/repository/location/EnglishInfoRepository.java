package com.trablock.web.repository.location;

import com.trablock.web.entity.location.EnglishInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnglishInfoRepository extends JpaRepository<EnglishInfo, Long> {

    @Query("select e from EnglishInfo e where e.locationId = :locationId")
    EnglishInfo findEnglishInfoByLocationId(@Param("locationId") Long locationId);
}
