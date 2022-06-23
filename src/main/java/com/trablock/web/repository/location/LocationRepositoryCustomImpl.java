package com.trablock.web.repository.location;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trablock.web.dto.location.*;
import com.trablock.web.entity.location.QInformation;
import com.trablock.web.entity.location.QLocation;
import com.trablock.web.entity.location.type.*;

import javax.persistence.EntityManager;

public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LocationRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public AttractionDto findAttractionByLocationId(Long locationId) {
        QLocation location = QLocation.location;
        QInformation information = QInformation.information;
        QAttraction attraction = QAttraction.attraction;

        return queryFactory.select(Projections.constructor(AttractionDto.class,
                        location.id.as("locationId"),
                        location.name,
                        location.areaCode,
                        location.address1,
                        location.address2,
                        location.image,
                        information.tel,
                        information.summary,
                        information.report,
                        location.type,
                        attraction.parking,
                        attraction.restDate
                )).from(location)
                .leftJoin(attraction).on(location.id.eq(attraction.locationId))
                .leftJoin(information).on(location.id.eq(information.locationId))
                .where(location.id.eq(locationId))
                .fetchOne();
    }

    @Override
    public CultureDto findCultureByLocationId(Long locationId) {
        QLocation location = QLocation.location;
        QInformation information = new QInformation("information");

        QCulture culture = new QCulture("culture");

        return queryFactory.select(Projections.constructor(CultureDto.class,
                        location.id.as("locationId"),
                        location.name,
                        location.areaCode,
                        location.address1,
                        location.address2,
                        location.image,
                        information.tel,
                        information.summary,
                        information.report,
                        location.type,
                        culture.parking,
                        culture.restDate,
                        culture.fee,
                        culture.useTime,
                        culture.spendTime
                )).from(location)
                .leftJoin(culture).on(location.id.eq(culture.locationId))
                .leftJoin(information).on(location.id.eq(information.locationId))
                .where(location.id.eq(locationId))
                .fetchOne();
    }

    @Override
    public FestivalDto findFestivalByLocationId(Long locationId) {
        QLocation location = QLocation.location;
        QInformation information = new QInformation("information");

        QFestival festival = new QFestival("festival");

        return queryFactory.select(Projections.constructor(FestivalDto.class,
                        location.id.as("locationId"),
                        location.name,
                        location.areaCode,
                        location.address1,
                        location.address2,
                        location.image,
                        location.type,
                        information.tel,
                        information.summary,
                        information.report,
                        location.type,
                        festival.homepage,
                        festival.place,
                        festival.placeInfo,
                        festival.startDate,
                        festival.endDate,
                        festival.playTime,
                        festival.program,
                        festival.fee
                )).from(location)
                .leftJoin(festival).on(location.id.eq(festival.locationId))
                .leftJoin(information).on(location.id.eq(information.locationId))
                .where(location.id.eq(locationId))
                .fetchOne();
    }

    @Override
    public LeportsDto findLeportByLocationId(Long locationId) {
        QLocation location = QLocation.location;
        QInformation information = new QInformation("information");
        QLeports leports = new QLeports("leports");

        return queryFactory.select(Projections.constructor(LeportsDto.class,
                        location.id.as("locationId"),
                        location.name,
                        location.areaCode,
                        location.address1,
                        location.address2,
                        location.image,
                        information.tel,
                        information.summary,
                        information.report,
                        location.type,
                        leports.openPeriod,
                        leports.parking,
                        leports.reservation,
                        leports.restDate,
                        leports.fee,
                        leports.useTime
                )).from(location)
                .leftJoin(leports).on(location.id.eq(leports.locationId))
                .leftJoin(information).on(location.id.eq(information.locationId))
                .where(location.id.eq(locationId))
                .fetchOne();
    }

    @Override
    public LodgeDto findLodgeByLocationId(Long locationId) {
        QLocation location = QLocation.location;
        QInformation information = new QInformation("information");
        QLodge lodge = new QLodge("lodge");

        return queryFactory.select(Projections.constructor(LodgeDto.class,
                        location.id.as("locationId"),
                        location.name,
                        location.areaCode,
                        location.address1,
                        location.address2,
                        location.image,
                        information.tel,
                        information.summary,
                        information.report,
                        location.type,
                        lodge.checkInTime,
                        lodge.checkOutTime,
                        lodge.cooking,
                        lodge.parking,
                        lodge.numOfRooms,
                        lodge.reservationUrl,
                        lodge.subFacility
                )).from(location)
                .leftJoin(lodge).on(location.id.eq(lodge.locationId))
                .leftJoin(information).on(location.id.eq(information.locationId))
                .where(location.id.eq(locationId))
                .fetchOne();
    }

    @Override
    public RestaurantDto findRestaurantByLocationId(Long locationId) {
        QLocation location = QLocation.location;
        QInformation information = new QInformation("information");
        QRestaurant restaurant = new QRestaurant("restaurant");

        return queryFactory.select(Projections.constructor(RestaurantDto.class,
                        location.id.as("locationId"),
                        location.name,
                        location.areaCode,
                        location.address1,
                        location.address2,
                        location.image,
                        information.tel,
                        information.summary,
                        information.report,
                        location.type,
                        restaurant.popularMenu,
                        restaurant.openTime,
                        restaurant.packing,
                        restaurant.parking,
                        restaurant.restDate,
                        restaurant.menu
                )).from(location)
                .leftJoin(restaurant).on(location.id.eq(restaurant.locationId))
                .leftJoin(information).on(location.id.eq(information.locationId))
                .where(location.id.eq(locationId))
                .fetchOne();
    }
}