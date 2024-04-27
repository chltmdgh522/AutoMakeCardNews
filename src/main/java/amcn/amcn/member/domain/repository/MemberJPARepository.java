package amcn.amcn.member.domain.repository;

import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Transactional
@Slf4j
public class MemberJPARepository implements MemberRepository {
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
        try {
            Member findMember = em.createQuery("select m from Member m where m.loginId = :loginId ", Member.class)
                    .setParameter("loginId", member.getLoginId())
                    .getSingleResult();
            return Optional.of(findMember);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByEmail(Member member) {

        try {
            Member findMember = em.createQuery("select m from Member m where m.email = :email ", Member.class)
                    .setParameter("email", member.getEmail())
                    .getSingleResult();
            return Optional.of(findMember);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public void delete() {

    }
}
