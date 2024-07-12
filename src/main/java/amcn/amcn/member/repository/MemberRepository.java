package amcn.amcn.member.repository;

import amcn.amcn.member.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface MemberRepository{
    void save(Member member);
    void update(Member member);

    void updatePassword(String memberId,String password);

    void updateRoleType(Member member);

    void updateUrl(Member member);

    void updateHello(Member member);

    void updatePoint(Member member);

    void updatePoint2(Member member);

    Optional<Member> findMemberId(String memberId);

    Optional<Member> findByLoginId(Member member);

    Optional<Member> findByEmail(Member member);

    List<Member> findAll();

    void delete(Member member);


}
