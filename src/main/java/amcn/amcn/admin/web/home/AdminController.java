package amcn.amcn.admin.web.home;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final MemberRepository memberRepository;

    @GetMapping("/system")
    public String getAdmin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                           Member loginMember, Model model) {
        if (!loginMember.getRoleType().equals(RoleType.MASTER)) {
            return "redirect:/";
        }
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        return "admin/admin";
    }

}