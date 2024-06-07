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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class CardNewsDeleteController {

    private final CardNewsRepository cardNewsRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/cardnews/fake/{id}")
    public String fakeCardNews(@PathVariable("id") Long id) {
        Optional<CardNews> findCardNews = cardNewsRepository.findCardNewsId(id);
        if (findCardNews.isPresent()) {
            CardNews cardNews = findCardNews.get();
            cardNews.setTrash("O");
            cardNewsRepository.updateTrash(cardNews);
        } else {
            return null;
        }

        return "redirect:/trash";
    }

    @GetMapping("/trash")
    public String getTrash(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
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

        List<CardNews> findCardNews = cardNewsRepository.findTrashAll(loginMember);
        model.addAttribute("cardnews",findCardNews);

        return "trash/trash";
    }

    @PostMapping("/cardnews/delete")
    public String deleteCardNews() {
        cardNewsRepository.findTrashAllDelete();
        return "redirect:/trash";
    }

}
