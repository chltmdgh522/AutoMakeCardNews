package amcn.amcn.other.web;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.other.repository.OthersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
            if (member.getRoleType().name().equals("USER")) {
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
            if (member.getRoleType().name().equals("USER")) {
                return "redirect:/";
            }
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }


        return "game/baseball";
    }


    @GetMapping("/game/number")
    public String getNumber(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                            Member loginMember,
                            Model model) {
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            if (member.getRoleType().name().equals("USER")) {
                return "redirect:/";
            }
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }


        return "game/number";
    }


    @GetMapping("/game/swipe")
    public String getSwipe(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                           Member loginMember,
                           Model model) {
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            if (member.getRoleType().name().equals("USER")) {
                return "redirect:/";
            }
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }


        return "game/swipe";
    }

    @ResponseBody
    @PostMapping("/game/point")
    public String updatePoint(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                              Member loginMember) {
        loginMember.setPoint(100L);
        memberRepository.updatePoint(loginMember);

        return "sucess";
    }

    @ResponseBody
    @PostMapping("/game/point2")
    public ResponseEntity<String> updatePoint2(@RequestBody Map<String, Integer> data,
                                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        loginMember.setPoint(Long.valueOf(data.get("score")));
        memberRepository.updatePoint(loginMember);

        return ResponseEntity.ok("점수 저장 성공");
    }

}
