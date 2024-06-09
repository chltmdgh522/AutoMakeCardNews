package amcn.amcn.member.service.login;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<Member> loginIdCheck(Member member) {
        return memberRepository.findByLoginId(member);
    }


    public Member passwordCheck(Member member) {
        return memberRepository.findByLoginId(member)
                .filter(m -> bCryptPasswordEncoder.matches(member.getPassword(), m.getPassword()))
                .orElse(null);
    }

    public void logoutService(Member member){
        memberRepository.updateHello(member);
    }
}
