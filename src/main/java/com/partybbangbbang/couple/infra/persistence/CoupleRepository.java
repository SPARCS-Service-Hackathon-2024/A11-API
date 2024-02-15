package com.partybbangbbang.couple.infra.persistence;

import com.partybbangbbang.couple.domain.Couple;
import com.partybbangbbang.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface CoupleRepository extends JpaRepository<Couple, Long> {

    Optional<Couple> findByHusbandOrWife(Member husband, Member wife);
}
