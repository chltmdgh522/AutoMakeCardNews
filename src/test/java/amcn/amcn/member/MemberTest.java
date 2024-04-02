package amcn.amcn.member;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.domain.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest

public class MemberTest {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Test
    public void test() {
        Member member = new Member();
        memberRepository.save(member);
    }
}
