package com.trablock.demo.domain.location;


public interface Location {
    /** 주석으로 규격을 정하고 필요한 메소드만 정의하는 방법은 어떨까요? 회의 필요 */
    String name = "name";
    String address1 = "address1";
    String address2 = "address2";

    Coords coords = new Coords();
    byte[] locationImg = new byte[512];
    Information information = new Information();
    LocationType locationType = LocationType.TourSpot;

    /** Location Entity 완성 시 필요한 공통 메소드 회의 필요 */
}
