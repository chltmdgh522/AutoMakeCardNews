package amcn.amcn.community.web;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommunityController {

    private final MemberRepository memberRepository;
    @GetMapping("/community")
    public String getCommunity(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                 Member loginMember, Model model) {

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }


        return "community/community";
    }

    @GetMapping("/community/more")
    public String getCommunityMore(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                               Member loginMember, Model model) {

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }


        return "community/communitymore";
    }

}
