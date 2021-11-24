package com.trablock.demo.repository.location;

import com.trablock.demo.domain.location.Location;
import com.trablock.demo.domain.location.MemberLocation;
import com.trablock.demo.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<MemberLocation, Long> {
    List<Location> findByMember(Member member);

}
