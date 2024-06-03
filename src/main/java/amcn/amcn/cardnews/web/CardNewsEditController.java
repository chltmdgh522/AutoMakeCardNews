package amcn.amcn.cardnews.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class CardNewsEditController {

    private final CardNewsRepository cardNewsRepository;
    private final MemberRepository memberRepository;
    @GetMapping("/cardnews/edit/{id}")
    public String getEdit(@PathVariable("id") Long id,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
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

        Optional<CardNews> findCardNews = cardNewsRepository.findCardNewsId(id);
        if (findCardNews.isPresent()) {
            CardNews cardNews = findCardNews.get();
            model.addAttribute("cardNews", cardNews);
            model.addAttribute("id", id);
        } else {
            return null;
        }

        return "cardNews/cardnewsedit";
    }
}
