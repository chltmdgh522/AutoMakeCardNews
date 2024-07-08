package amcn.amcn.admin.repository.member;

import amcn.amcn.member.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository <Member, String> {

    Page<Member> findAll(Pageable pageable);

    Page<Member> findByNameContaining(String name, Pageable pageable);
}
