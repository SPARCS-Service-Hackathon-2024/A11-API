package com.partybbangbbang.couple.infra.persistence;

import com.partybbangbbang.couple.domain.Couple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.partybbangbbang.couple.domain.QCouple.couple;

@Repository
@RequiredArgsConstructor
public class CoupleQueryRepository {

    private final JPAQueryFactory query;

    public Couple findCoupleByMemberId(Long memberId) {
        return query.selectFrom(couple)
                .where(couple.husband.id.eq(memberId)
                        .or(couple.wife.id.eq(memberId)))
                .fetchOne();
    }
}
