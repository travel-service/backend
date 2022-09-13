package com.trablock.web.repository.location;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trablock.web.dto.location.BlockLocationView;
import com.trablock.web.dto.location.type.*;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.QInformation;
import com.trablock.web.entity.location.QLocation;
import com.trablock.web.entity.location.QMemberLocation;
import com.trablock.web.entity.location.type.*;
import com.trablock.web.entity.plan.QSelectedLocation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final JPAQueryFactory queryFactory;

    public LocationRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Location> findLocationsByMemberId(Long memberId) {
        QLocation location = QLocation.location;
        QMemberLocation memberLocation = QMemberLocation.memberLocation;

        return queryFactory.selectFrom(location)
                .leftJoin(memberLocation).on(location.id.eq(memberLocation.locationId))
                .where(memberLocation.memberId.eq(memberId))
                .fetch();
    }


    @Override
    public List<BlockLocationView> findSelectedLocationByPlanId(Long planId) {
        QLocation location = QLocation.location;
        QSelectedLocation selectedLocation = QSelectedLocation.selectedLocation;

        return queryFactory.select(Projections.constructor(BlockLocationView.class,
                        location.id,
                        location.name,
                        location.address1,
                        location.address2,
                        location.image,
                        location.type)
                )
                .from(location)
                .join(selectedLocation.location, location)
                .on(selectedLocation.plan.id.eq(planId)).fetch();
    }

    @Override
    public Long saveAttraction(Attraction attraction) {
        em.persist(attraction);
        return attraction.getId();
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
                        attraction.restDate,
                        attraction.useTime
                )).from(location)
                .leftJoin(attraction).on(location.id.eq(attraction.locationId)).fetchJoin()
                .leftJoin(information).on(location.id.eq(information.locationId)).fetchJoin()
                .where(location.id.eq(locationId))
                .fetchOne();
    }

    @Override
    public void deleteAttraction(Attraction attraction) {
        em.remove(attraction);
    }

    @Override
    public Long saveCulture(Culture culture) {
        em.persist(culture);
        return culture.getId();
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
    public void deleteCulture(Culture culture) {
        em.remove(culture);
    }

    @Override
    public Long saveFestival(Festival festival) {
        em.persist(festival);
        return festival.getId();
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
    public void deleteFestival(Festival festival) {
        em.remove(festival);
    }

    @Override
    public Long saveLeports(Leports leports) {
        em.persist(leports);
        return leports.getId();
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
    public void deleteLeports(Leports leports) {
        em.remove(leports);
    }

    @Override
    public Long saveLodge(Lodge lodge) {
        em.persist(lodge);
        return lodge.getId();
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
                        lodge.checkInTime.as("checkInTime"),
                        lodge.checkOutTime.as("checkOutTime"),
                        lodge.cooking.as("cooking"),
                        lodge.parking.as("parking"),
                        lodge.numOfRooms.as("numOfRooms"),
                        lodge.reservationUrl.as("reservationUrl"),
                        lodge.subFacility
                )).from(location)
                .leftJoin(lodge).on(location.id.eq(lodge.locationId))
                .leftJoin(information).on(location.id.eq(information.locationId))
                .where(location.id.eq(locationId))
                .fetchOne();
    }

    @Override
    public void deleteLodge(Lodge lodge) {
        em.remove(lodge);
    }

    @Override
    public Long saveRestaurant(Restaurant restaurant) {
        em.persist(restaurant);
        return restaurant.getId();
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

    @Override
    public void deleteRestaurant(Restaurant restaurant) {
        em.remove(restaurant);
    }

    @Override
    public List<AttractionDto> findAttractionsByLocationIds(List<Long> locationIds) {
        QLocation location = QLocation.location;
        QAttraction attraction = QAttraction.attraction;
        QInformation information = QInformation.information;

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
                        attraction.restDate,
                        attraction.useTime
                )).from(location)
                .leftJoin(attraction).on(location.id.eq(attraction.locationId)).fetchJoin()
                .leftJoin(information).on(location.id.eq(information.locationId)).fetchJoin()
                .fetch();
    }

    @Override
    public List<CultureDto> findCulturesByLocationIds(List<Long> locationIds) {
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
                .where(location.id.in(locationIds))
                .fetch();
    }

    @Override
    public List<FestivalDto> findFestivalsByLocationIds(List<Long> locationIds) {
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
                .where(location.id.in(locationIds))
                .fetch();
    }

    @Override
    public List<LeportsDto> findLeportsByLocationIds(List<Long> locationIds) {
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
                .where(location.id.in(locationIds))
                .fetch();
    }

    @Override
    public List<LodgeDto> findLodgesByLocationIds(List<Long> locationIds) {
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
                        lodge.checkInTime.as("checkInTime"),
                        lodge.checkOutTime.as("checkOutTime"),
                        lodge.cooking.as("cooking"),
                        lodge.parking.as("parking"),
                        lodge.numOfRooms.as("numOfRooms"),
                        lodge.reservationUrl.as("reservationUrl"),
                        lodge.subFacility
                )).from(location)
                .leftJoin(lodge).on(location.id.eq(lodge.locationId))
                .leftJoin(information).on(location.id.eq(information.locationId))
                .where(location.id.in(locationIds))
                .fetch();
    }

    @Override
    public List<RestaurantDto> findRestaurantsByLocationIds(List<Long> locationIds) {
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
                .where(location.id.in(locationIds))
                .fetch();
    }

}
