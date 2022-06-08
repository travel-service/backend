package com.trablock.web.repository.location.type;

import com.trablock.web.entity.location.type.Restaurant;
import com.trablock.web.entity.location.type.TypeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select r from Restaurant r where r.locationId = ?1")
    Restaurant findByLocationId(Long locationId);
}
