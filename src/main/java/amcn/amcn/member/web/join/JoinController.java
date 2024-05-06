package amcn.amcn.member.web.join;

import amcn.amcn.mail.NaverMailService;
import amcn.amcn.member.domain.member.EmailType;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.service.join.JoinService;
import amcn.amcn.member.service.login.LoginService;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @ModelAttribute("et")
    public List<EmailType> deliveryCodes() {
        List<EmailType> emalCodes = new ArrayList<>();
        emalCodes.add(new EmailType("naver.com"));
        emalCodes.add(new EmailType("gmail.com"));
        emalCodes.add(new EmailType("gs.anyang.ac.kr"));
        return emalCodes;
    }

    @GetMapping("/join")
    public String getJoin(@ModelAttribute Member member,
                          Model model,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                          Member loginMember) {
        if (loginMember != null) {
            return "redirect:/";
        }
        model.addAttribute("member", member);
        return "member/join";
    }

    @PostMapping("/join")
    public String postJoin(@Validated @ModelAttribute Member member, BindingResult bindingResult) {

        String save = joinService.save(member);
        if (Objects.equals(save, "loginId")) {
            bindingResult.reject("joinFail", "존재하는 아이디가 있습니다");
            return "member/join";
        }
        if (Objects.equals(save, "password")) {
            bindingResult.reject("joinFail", "비밀번호가 일치하지 않습니다");
            return "member/join";
        }

        if (Objects.equals(save, "email")) {
            bindingResult.reject("joinFail", "존재하는 이메일이 있습니다");
            return "member/join";
        }
        if (Objects.equals(save, "emailF")) {
            bindingResult.reject("joinFail", "올바른 이메일 형식이 아닙니다");
            return "member/join";
        }
        return "redirect:/login";
    }

    @GetMapping("/delete")
    public String getDelete(@Validated
                            @ModelAttribute Member member,
                            Model model) {
        model.addAttribute("member", member);

        return "member/memberdelete";
    }

    @PostMapping("/delete")
    public String postDelete(@Validated
                             @ModelAttribute Member member,
                             BindingResult bindingResult,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                             Member loginMember,
                             HttpServletRequest request) {
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            if (!Objects.equals(member.getLoginId(), loginMember.getLoginId())) {
                bindingResult.reject("err", "아이디가 계정이랑 일치하지 않습니다");
                return "member/memberdelete";
            }
            if (!bCryptPasswordEncoder.matches(member.getPassword(), loginMember.getPassword())) {
                bindingResult.reject("err", "비밀번호가 계정이랑 일치하지 않습니다");
                return "member/memberdelete";
            }

            if (!Objects.equals(member.getEmail(), loginMember.getEmail())) {
                bindingResult.reject("err", "이메일이 계정이랑 일치하지 않습니다");
                return "member/memberdelete";
            }

            if (!Objects.equals(member.getName(), loginMember.getName())) {
                bindingResult.reject("err", "이름이 계정이랑 일치하지 않습니다");
                return "member/memberdelete";
            }

        } else {
            return null;
        }

        HttpSession session = request.getSession(false);
        if(session !=null){
            session.invalidate();
        }
        memberRepository.delete(loginMember);

        return "redirect:/";
    }
}
