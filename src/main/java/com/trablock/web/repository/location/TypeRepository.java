package com.trablock.web.repository.location;

import com.trablock.web.entity.location.type.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;


@Repository
public class TypeRepository {

    @PersistenceContext
    private EntityManager em;

    public Attraction saveAttraction(Attraction attraction) {
        em.persist(attraction);
        return attraction;
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

    public Restaurant saveRestaurant(Restaurant restaurant) {
        em.persist(restaurant);
        return restaurant;
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

    public Culture saveCulture(Culture culture) {
        em.persist(culture);
        return culture;
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

    public Festival saveFestival(Festival festival) {
        em.persist(festival);
        return festival;
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

    public Lodge saveLodge(Lodge lodge) {
        em.persist(lodge);
        return lodge;
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

    public Leports saveLeports(Leports leports) {
        em.persist(leports);
        return leports;
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
