package amcn.amcn;

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
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final CardNewsRepository cardNewsRepository;


    @GetMapping("/")
    public String home(Model model,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                       Member loginMember){

        List<CardNews> newAll = cardNewsRepository.findNewAll();

        if(loginMember == null){
            model.addAttribute("cardnews",newAll);
        return "home/noLoginHome";
    }

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if(findMember.isPresent()){
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member",member);
            model.addAttribute("cardnews",newAll);
        }else {
            return null;
        }
        return "home/loginHome";
    }
}
