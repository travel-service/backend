package com.trablock.web.entity.location;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@Getter
public class Location {

    @Id @GeneratedValue
    @Column(name = "location_id")
    private Long id;

    public Location() {
    }
}
