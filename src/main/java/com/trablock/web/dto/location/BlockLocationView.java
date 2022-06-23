package com.trablock.web.dto.location;

import com.trablock.web.domain.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

/**
 * https://knoc-story.tistory.com/114
 * 프로젝션
 */
public interface BlockLocationView {
    Long getId();

    String getName();

    String getAddress1();

    String getAddress2();

    String getImage();

    LocationType getType();
}
