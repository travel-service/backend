package com.trablock.demo.repository.location;

import com.trablock.demo.domain.location.Location;
import com.trablock.demo.domain.location.MemberLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl {

    private final EntityManager em;

}
