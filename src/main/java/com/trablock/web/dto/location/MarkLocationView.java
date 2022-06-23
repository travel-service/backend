package com.trablock.web.dto.location;


import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Spring Data JPA - Projection
 */
public interface MarkLocationView {

    Long getId();

    String getName();

    Coords getCoords();

    LocationType getType();

}
