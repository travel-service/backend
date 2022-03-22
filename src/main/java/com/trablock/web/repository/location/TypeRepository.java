package com.trablock.web.repository.location;

import com.trablock.web.entity.location.type.*;
import com.trablock.web.repository.location.type.AttractionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 이거 안됨. Signle Inheritance Table에서만 작동한다.
 * 현재 TypeLocations는 Single Inheritance table X
 *
 * @param <T>
 */
@NoRepositoryBean
public interface TypeRepository<T extends TypeLocation> extends AttractionRepository<Attraction, Long> {

    @Query("select t from #{#entityName} t where t.locationId =: locationId")
    Optional<T> findByLocationId(@Param("locationId") Long locationId);

//    @Query("select t from T t where t.locationId = :locationId")
//    List<T> findAllByType(@Param("locationId") Long locationId);

    @Query("select t from #{#entityName} t where t.Id =: Id")
    Optional<T> findById(@Param("Id") Long Id);
}
