package com.trablock.web.repository.location.type;

import com.trablock.web.entity.location.type.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
