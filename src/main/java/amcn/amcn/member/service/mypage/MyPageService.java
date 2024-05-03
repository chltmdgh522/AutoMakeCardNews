package amcn.amcn.member.service.mypage;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;

    public Optional<Member> memberIdCheck(String memberId) {
        return memberRepository.findMemberId(memberId);
    }


    public void updateMyPage(Member member){
        memberRepository.update(member);
    }

    public String email(Member member){
        // 이메일 형식
        if(member.getEmailF().contains("@")){
            return "emailF";
        }
        //이메일 중복 방지
        Optional<Member> emember = memberRepository.findByEmail(member);
        if (emember.isPresent()) {
            return "email";
        }

        return null;
    }
}
