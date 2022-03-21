package com.trablock.web.repository.location;

import com.trablock.web.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    
}
