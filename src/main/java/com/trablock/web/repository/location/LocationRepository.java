package com.trablock.web.repository.location;

import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select l from Location l where l.name = :name")
    Optional<Location> findByName(@Param("name") String name);

    @Query("select l from Location l where l.name like %:name%")
    Optional<Location> findByNameContaining(@Param("name") String name);

    @Query("select l from Location l where l.type = :type")
    Optional<Location> findLocationByType(@Param("type") LocationType type);

}
