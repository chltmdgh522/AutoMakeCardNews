package amcn.amcn.member.domain.repository;

import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Transactional
@Slf4j
public class MemberJPARepository implements MemberRepository{
    private final EntityManager em;

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Member update(Member member) {
        return null;
    }

    @Override
    public Optional<Member> findByLoginId(Member member) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByEmail(Member member) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public void delete() {

    }
}
