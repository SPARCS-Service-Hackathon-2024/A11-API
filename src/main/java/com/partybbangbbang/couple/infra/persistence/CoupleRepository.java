package com.partybbangbbang.couple.infra.persistence;

import com.partybbangbbang.couple.domain.Couple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CoupleRepository extends JpaRepository<Couple, Long> {
}
