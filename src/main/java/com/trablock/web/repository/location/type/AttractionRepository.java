package com.trablock.web.repository.location.type;

import com.trablock.web.entity.location.type.Attraction;
import com.trablock.web.entity.location.type.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
}
