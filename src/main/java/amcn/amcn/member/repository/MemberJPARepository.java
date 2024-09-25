package amcn.amcn.member.repository;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
public class MemberJPARepository implements MemberRepository {
    private final EntityManager em;


    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public void update(Member member) {
        Member findMember = em.find(Member.class, member.getMemberId());
        findMember.setEmail(member.getEmail());
        findMember.setName(member.getName());
        findMember.setProfile(member.getProfile());
        findMember.setRoleType(RoleType.valueOf(member.getRoleType().name()));
    }

    @Override
    public void updateHello(Member member) {
        Member findMember = em.find(Member.class, member.getMemberId());
        findMember.setHello(member.getHello());
    }

    @Override
    public void updatePoint(Member loginMember) {
        Member member = em.find(Member.class, loginMember.getMemberId());
        member.setPoint(member.getPoint()+loginMember.getPoint());
    }

    @Override
    public void updatePoint2(Member loginMember) {
        Member member = em.find(Member.class, loginMember.getMemberId());
        member.setPoint(loginMember.getPoint());
    }

    @Override
    public void updatePassword(String memberId ,String password) {
        Member findMember = em.find(Member.class, memberId);
        findMember.setPassword(password);
    }

    @Override
    public void updateRoleType(Member member) {
        Member findMember = em.find(Member.class, member.getMemberId());
        findMember.setRoleType(RoleType.AUTHUSER);
    }

    @Override
    public void updateUrl(Member member) {
        Member findMember = em.find(Member.class, member.getMemberId());
        findMember.setOriginalUrl(member.getOriginalUrl());
    }

    @Override
    public Optional<Member> findMemberId(String memberId) {
        try {
            Member findMember = em.createQuery("select m from Member m where m.memberId = :id ", Member.class)
                    .setParameter("id", memberId)
                    .getSingleResult();
            return Optional.of(findMember);
        } catch (NoResultException e) {
            return Optional.empty();
        }
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
            Member findMember = em.createQuery("select m from Member m where m.email = :email", Member.class)
                    .setParameter("email", member.getEmail())
                    .getSingleResult();
            log.info("repo:"+findMember.getEmail());
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
    public void delete(Member member) {
        Member findMember = em.find(Member.class, member.getMemberId());
        em.remove(findMember);
    }
}
