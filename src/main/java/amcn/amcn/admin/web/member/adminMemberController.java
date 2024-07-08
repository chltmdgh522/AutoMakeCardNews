package amcn.amcn.admin.web.member;

import amcn.amcn.admin.domain.member.MemberSearch;
import amcn.amcn.admin.service.AdminService;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class adminMemberController {

    private final MemberRepository memberRepository;
    private final AdminService adminService;
    @GetMapping("/system/member")
    public String getAdminMember(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                 Member loginMember,
                                 @ModelAttribute("memberSearchCond") MemberSearch cond,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 Model model) {
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

        Page<Member> list = adminService.getList(cond.getName(), page);
        model.addAttribute("loginMember",loginMember);
        model.addAttribute("paging", list);

        return "admin/member";
    }
}
