package amcn.amcn.member.service;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.repository.MemberRepository;
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
}
