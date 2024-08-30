package amcn.amcn.cardnews.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.domain.searchcond.CardNewsSearchCond;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.cardnews.repository.CardNewsSpringDataRepository;
import amcn.amcn.like.repository.LikeRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CardNewsProjectController {

    private final CardNewsRepository cardNewsRepository;
    private final MemberRepository memberRepository;
    private final CardNewsSpringDataRepository springDataRepository;
    @GetMapping("/cardnews/project")
    public String getHomeCard(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                              Member loginMember,
                              Model model,
                              @ModelAttribute("cardNewsSearchCond") CardNewsSearchCond cardNewsSearchCond) {
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            if(member.getRoleType().name().equals("USER")){
                return "redirect:/";
            }
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        //검색
        List<CardNews> searchCardNews = springDataRepository.findSearchProject
                (loginMember.getMemberId(),cardNewsSearchCond.getTitle(), cardNewsSearchCond.getCategory(), cardNewsSearchCond.getSelected());

        model.addAttribute("cardnews",searchCardNews);

        return "cardNews/cardnewsproject";
    }
}
