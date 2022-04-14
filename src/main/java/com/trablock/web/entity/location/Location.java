package com.trablock.web.entity.location;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Location {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "LOCATION_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address1;

    @Column(nullable = false)
    private String address2;

    @Embedded
    private Coords coords;

    @Column
    private Byte image;

    @Column
    private LocationType type;

    @Builder
    public Location(String name, String address1, String address2,
                    Coords coords, Byte image, LocationType type) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.coords = coords;
        this.image = image;
        this.type = type;
    }

    @Getter
    public static class SimpleLocDto {
        private String name;
        private Coords coords;
        private LocationType type;

        @Builder
        public SimpleLocDto(String name, Coords coords, LocationType type) {
            this.name = name;
            this.coords = coords;
            this.type = type;
        }
    }

    @Getter
    public static class LocationSaveRequestDto {
        private final String name;
        private final String address1;
        private final String address2;
        private final Coords coords;
        private final Byte image;
        private final LocationType type;

        @Builder
        public LocationSaveRequestDto(String name, String address1, String address2,
                                      Coords coords, Byte image, LocationType type) {
            this.name = name;
            this.address1 = address1;
            this.address2 = address2;
            this.coords = coords;
            this.image = image;
            this.type = type;
        }

        public Location toEntity() {
            return Location.builder()
                    .name(name)
                    .address1(address1)
                    .address2(address2)
                    .coords(coords)
                    .image(image)
                    .type(type)
                    .build();
        }

    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public String changeName(String name) {
        return this.name = name;
    }

}
