package amcn.amcn.other.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.other.repository.OthersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MembersController {

    private final MemberRepository memberRepository;

    private final OthersRepository othersRepository;
    @GetMapping("/members")
    public String getMembers(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                 Member loginMember,
                             Model model){
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        // 공감
        List<CardNews> heartCardNews = othersRepository.findHeartCardNews(loginMember);
        model.addAttribute("heartCardNews",heartCardNews);

        //포크
        List<CardNews> forkCardNews = othersRepository.findForkCardNews(loginMember);
        model.addAttribute("forkCardNews",forkCardNews);

        return "members/members";
    }
}
