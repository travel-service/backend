package com.trablock.web.entity.location;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.BlockLocationDto;
import com.trablock.web.dto.location.MarkLocationDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Location {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer areaCode;

    @Column(nullable = false)
    private String address1;

    @Column
    private String address2;

    @Embedded
    private Coords coords;

    @Column
    private String image;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private LocationType type;

    @Column
    private Boolean isMember;

    public void setType(LocationType type) {
        this.type = type;
    }

    public LocationType getType() {
        return this.type;
    }

    public String changeName(String name) {
        return this.name = name;
    }


    public BlockLocationDto toBlockLocationDto() {
        return BlockLocationDto.builder()
                .locationId(id)
                .name(name)
                .address1(address1)
                .address2(address2)
                .image(image)
                .type(type)
                .build();
    }

    public MarkLocationDto toMarkLocationDto() {
        return MarkLocationDto.builder()
                .locationId(id)
                .name(name)
                .coords(coords)
                .type(type)
                .build();
    }

}
