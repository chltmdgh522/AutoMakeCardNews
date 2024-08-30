package amcn.amcn.other.web;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.other.repository.OthersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final MemberRepository memberRepository;
    private final OthersRepository othersRepository;

    @GetMapping("/game")
    public String getListGame(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                              Member loginMember,
                              Model model) {
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

        model.addAttribute("pointMember", othersRepository.memberPointList());


        return "game/game";
    }

    @GetMapping("/game/baseball")
    public String getBaseBall(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                              Member loginMember,
                              Model model) {
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


        return "game/baseball";
    }

    @ResponseBody
    @PostMapping("/game/point")
    public String updatePoint(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                  Member loginMember){
        loginMember.setPoint(100L);
        memberRepository.updatePoint(loginMember);

        return "sucess";
    }


}
