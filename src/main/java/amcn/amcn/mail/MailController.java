package amcn.amcn.mail;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final NaverMailService mailService;

    private final MemberRepository memberRepository;

    @GetMapping("/email-auth")
    public String getEmail(
            Model model,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
            Member loginMember) throws Exception {
        String authPassword = mailService.sendSimpleMessage(loginMember.getEmail());
        loginMember.setAuthPassword(authPassword);

        Member member=new Member();
        model.addAttribute("member",member);
        return "email/email";
    }

    @PostMapping("/email-auth")
    public String postEmail(
            @Validated
            @ModelAttribute Member member,
            BindingResult bindingResult,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
            Member loginMember) {
        if(!Objects.equals(member.getAuthPassword(),loginMember.getAuthPassword())){
            bindingResult.reject("err", "이메일 인증번호가 일치하지 않습니다");
            return "email/email";
        }

        memberRepository.updateRoleType(loginMember);
        return "redirect:/mypage";
    }
}
