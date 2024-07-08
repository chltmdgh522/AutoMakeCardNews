package amcn.amcn.member.web.password;


import amcn.amcn.mail.NaverMailIdService;
import amcn.amcn.mail.NaverMailPasswordService;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.domain.password.ChangePassword;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.service.password.PasswordService;
import amcn.amcn.member.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PasswordController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordService passwordService;
    private final NaverMailPasswordService mailService;
    private final MemberRepository memberRepository;

    @GetMapping("/change-password")
    public String getChangePassword(@ModelAttribute("password") ChangePassword password,
                                    @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                    Member loginMember,
                                    Model model) {

        if(loginMember.getRoleType().equals(RoleType.OAUTH_USER)){
            return "redirect:/";
        }
        return "member/password/change-password";
    }

    @PostMapping("/change-password")
    public String postChangePassword(@Validated
                                     @ModelAttribute("password") ChangePassword password,
                                     BindingResult bindingResult,
                                     HttpServletRequest request,
                                     Model model,
                                     @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                     Member loginMember) {

        Member findMember = passwordService.findByMemberId(loginMember);

        if(!Objects.equals(password.getLoginId(),findMember.getLoginId())){
            bindingResult.reject("err", "기존 아이디가 일치하지 않습니다");
            return "member/password/change-password";

        }

        if (!bCryptPasswordEncoder.matches(password.getOriginalPassword(), findMember.getPassword())) {
            bindingResult.reject("err", "기존 비밀번호가 일치하지 않습니다");
            return "member/password/change-password";
        }

        if(!Objects.equals(password.getNewPassword(),password.getNewReturnPassword())){
            bindingResult.reject("err", "새 비밀번호가 일치하지 않습니다");
            return "member/password/change-password";
        }

        if (bCryptPasswordEncoder.matches(password.getNewPassword(), findMember.getPassword())) {
            bindingResult.reject("err", "새 비밀번호가 기존 비밀번호랑 일치합니다.");
            return "member/password/change-password";
        }

        passwordService.updatePassword(findMember.getMemberId(),password.getNewPassword());

        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }

        return "redirect:/login";

    }

    @GetMapping("/forgot-password")
    public String getForgotId(@ModelAttribute Member member
            , Model model) {
        model.addAttribute("member", member);
        return "member/password/forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String postForgotId(@Validated
                               @ModelAttribute Member member,
                               BindingResult bindingResult,
                               HttpServletRequest request
    ) throws Exception {
        Optional<Member> findMember = memberRepository.findByEmail(member);
        if (findMember.isPresent()) {
            Member member1 = findMember.get();
            if (!Objects.equals(member1.getEmail(), member.getEmail())) {
                bindingResult.reject("err", "일치하는 계정이 없습니다.");
                return "member/password/forgotPassword";
            }
        } else {
            bindingResult.reject("err", "일치하는 계정이 없습니다.");
            return "member/password/forgotPassword";
        }

        String code = mailService.sendSimpleMessage(member);
        memberRepository.updatePassword(findMember.get().getMemberId(),bCryptPasswordEncoder.encode(code));
        // HttpSession session= request.getSession();
        // session.setAttribute(SessionConst.TEM_MEMBER, member);

        return "redirect:/login?mailSent=true";


    }
}
