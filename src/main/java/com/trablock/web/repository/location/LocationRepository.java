package com.trablock.web.repository.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select l from Location l where l.type = :type and l.isMember = false")
    HashSet<Location> findAllByTypeAndIsMemberFalse(@Param("type") LocationType locationType);

    @Query("select l from Location l where l.isMember = false")
    List<Location> findAllByIsMemberFalse();

    @Query("select l from Location l where l.name = :name")
    Optional<Location> findByName(@Param("name") String name);

    @Query("select l from Location l where l.name like %:name%")
    Optional<Location> findByNameContaining(@Param("name") String name);

    @Query("select l from Location l where l.type = :type")
    List<Location> findLocationByType(@Param("type") LocationType type);

    @Query("select l from Location l where l.id = :locationId")
    Optional<Location> findLocationById(@Param("locationId") Long locationId);
}
