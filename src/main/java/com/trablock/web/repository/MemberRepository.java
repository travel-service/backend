package com.trablock.web.repository;

import com.trablock.web.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
