package amcn.amcn.home;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.socket.chatservice.ChatService;
import io.github.cdimascio.dotenv.Dotenv;
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
    private final Dotenv dotenv;
    private final ChatService chatService;

    @GetMapping("/")
    public String home(Model model,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                       Member loginMember) {


        List<CardNews> newAll = cardNewsRepository.findNewAll();
        List<CardNews> popAll = cardNewsRepository.findPopAll();

        if (loginMember == null) {
            model.addAttribute("cardnews", newAll);
            model.addAttribute("cardPop", popAll);
            return "home/noLoginHome";
        }

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);

            // 인기테마
            model.addAttribute("cardnews", popAll);
            // 내가 만든거 및 없으면 최근 테마
            List<CardNews> myCard = cardNewsRepository.findMyCard(loginMember);
            String exist="O";
            if(myCard.isEmpty()){
                exist="X";
                model.addAttribute("exist",exist);
            }
            model.addAttribute("chatCount",chatService.findCountChat(member));
            model.addAttribute("myCardnews",myCard);
            model.addAttribute("cardPop", newAll);


        } else {
            return null;
        }
        return "home/loginHome";
    }
}
