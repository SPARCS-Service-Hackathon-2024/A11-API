package com.partybbangbbang.member.infra.persistence;

import com.partybbangbbang.member.domain.HearReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HearReportRepository extends JpaRepository<HearReport, Long> {
}
