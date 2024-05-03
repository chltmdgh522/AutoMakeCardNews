package amcn.amcn.member.service.password;


import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member findByMemberId(Member member) {
        return memberRepository.findMemberId(member.getMemberId()).orElse(null);
    }

    public void updatePassword(String memberId, String password) {
        memberRepository.updatePassword(memberId, bCryptPasswordEncoder.encode(password));
    }
}
