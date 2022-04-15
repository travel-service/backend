package com.trablock.web.repository.location;

import com.trablock.web.entity.location.type.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;


@Repository
public class TypeLocationRepository {

    @PersistenceContext
    private EntityManager em;

    public Long saveAttraction(Attraction attraction) {
        em.persist(attraction);
        return attraction.getId();
    }

    public void deleteAttraction(Attraction attraction) {
        em.remove(attraction);
    }

    public Optional<Attraction> findAttractionByLocationId(Long locationId) {
        try {
            return Optional.of(em.createQuery("select a from Attraction a where a.locationId =:locationId", Attraction.class)
                    .setParameter("locationId", locationId)
                    .getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    public Long saveRestaurant(Restaurant restaurant) {
        em.persist(restaurant);
        return restaurant.getId();
    }

    public void deleteRestaurant(Restaurant restaurant) {
        em.remove(restaurant);
    }

    public Optional<Restaurant> findRestaurantByLocationId(Long locationId) {
        try {
            return Optional.of(
                    em.createQuery("select r from Restaurant r where r.locationId =:locationId"
                                    , Restaurant.class)
                            .setParameter("locationId", locationId)
                            .getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    public Long saveCulture(Culture culture) {
        em.persist(culture);
        return culture.getId();
    }

    public void deleteCulture(Culture culture) {
        em.remove(culture);
    }

    public Optional<Culture> findCultureByLocationId(Long locationId) {
        try {
            return Optional.of(em.createQuery("select c from Culture c where c.locationId =:locationId", Culture.class)
                    .setParameter("locationId", locationId)
                    .getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    public Long saveFestival(Festival festival) {
        em.persist(festival);
        return festival.getId();
    }

    public void deleteFestival(Festival festival) {
        em.remove(festival);
    }

    public Optional<Festival> findFestivalByLocationId(Long locationId) {
        try {
            return Optional.of(em.createQuery("select f from Festival f where f.locationId =:locationId", Festival.class)
                    .setParameter("locationId", locationId)
                    .getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    public Long saveLodge(Lodge lodge) {
        em.persist(lodge);
        return lodge.getId();
    }

    public void deleteLodge(Lodge lodge) {
        em.remove(lodge);
    }

    public Optional<Lodge> findLodgeByLocationId(Long locationId) {
        try {
            return Optional.of(em.createQuery("select l from Lodge l where l.locationId =:locationId", Lodge.class)
                    .setParameter("locationId", locationId)
                    .getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    public Long saveLeports(Leports leports) {
        em.persist(leports);
        return leports.getId();
    }

    public void deleteLeports(Leports leports) {
        em.remove(leports);
    }

    public Optional<Leports> findLeportsByLocationId(Long locationId) {
        try {
            return Optional.of(em.createQuery("select l from Leports l where l.locationId =:locationId", Leports.class)
                    .setParameter("locationId", locationId)
                    .getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

}
