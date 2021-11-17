package com.trablock.demo.domain.location;

import static com.trablock.demo.domain.location.LocationType.TourSpot;

public interface Location {

    Long id = 0L;
    String name = "name";
    String address1 = "address1";
    String address2 = "address2";

    Coords coords = new Coords();
    byte[] locationImg = new byte[512];
    Information information = new Information();
    LocationType locationType = TourSpot;

}
