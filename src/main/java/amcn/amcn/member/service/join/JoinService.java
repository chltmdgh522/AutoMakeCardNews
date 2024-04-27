package amcn.amcn.member.service.join;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public String save(Member member) {
//        //아이디 중복 방지
//        Optional<Member> fmember = memberRepository.findByLoginId(member.getLoginId());
//        if (fmember.isPresent()) {
//            return "loginId";
//        }
//
//        //이메일 중복 방지
//        Optional<Member> emember = memberRepository.findByEmail(member.getEmail());
//        if (emember.isPresent()) {
//            return "email";
//        }

//        member.setProfile("basic.jpg");
        member.setMemberId(UUID.randomUUID().toString());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);

        return null;
    }
}
