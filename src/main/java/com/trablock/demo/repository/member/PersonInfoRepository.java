package com.trablock.demo.repository.member;

import com.trablock.demo.domain.member.PersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/** 미래를 위해 생성만 해둔 Repository, 안 쓸거같으면 날릴 예정*/
@Repository
public interface PersonInfoRepository extends JpaRepository<PersonInfo, Long> {
}
