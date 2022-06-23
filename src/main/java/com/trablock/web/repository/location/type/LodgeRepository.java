package com.trablock.web.repository.location.type;

import com.trablock.web.entity.location.type.Lodge;
import com.trablock.web.entity.location.type.TypeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LodgeRepository extends JpaRepository<Lodge, Long> {
}
