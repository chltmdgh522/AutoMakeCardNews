package amcn.amcn.member.web.join;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.service.join.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/join")
    public String getJoin(@ModelAttribute Member member,
                          Model model) {
        model.addAttribute("member",member);
        return "member/join";
    }
    @PostMapping("/join")
    public String postJoin(@ModelAttribute Member member) {
        joinService.save(member);
        member.setMemberSex(member.getMemberSex());
        return "member/join";
    }
}
