package amcn.amcn.member.service.join;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public String save(Member member) {

        // 이메일 합치기
        String F_email = member.getEmailF() + "@" + member.getEmailType().getEmailCode();
        member.setEmail(F_email);

        // 이메일 형식
        if (member.getEmailF().contains("@")) {
            return "emailF";
        }

        //아이디 중복 방지
        Optional<Member> fmember = memberRepository.findByLoginId(member);
        if (fmember.isPresent()) {
            return "loginId";
        }

        //비밀번호 체크
        if (!Objects.equals(member.getPassword(), member.getPasswordCheck())) {
            return "password";
        }

        //이메일 중복 방지
        Optional<Member> emember = memberRepository.findByEmail(member);
        if (emember.isPresent()) {
            return "email";
        }

        //관리자 부여
        if (member.getLoginId().equals("chltmdgh522")) {
            member.setRoleType(RoleType.ADMIN);
        } else {
            member.setRoleType(RoleType.USER);
        }

//        member.setProfile("basic.jpg");


        member.setMemberId(UUID.randomUUID().toString());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);

        return null;
    }
}
