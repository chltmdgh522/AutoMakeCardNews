package amcn.amcn.member.web.password;


import amcn.amcn.mail.MailService;
import amcn.amcn.mail.NaverMailIdService;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ForgotIdController {

    private final MemberRepository memberRepository;

    private final NaverMailIdService naverMailIdService;
    @GetMapping("/forgot-id")
    public String getForgotId(@ModelAttribute Member member
            , Model model) {
        model.addAttribute("member", member);
        return "member/password/forgotId";
    }

    @PostMapping("/forgot-id")
    public String postForgotId(@Validated
                               @ModelAttribute Member member,
                               BindingResult bindingResult,
                               HttpServletRequest request
    ) throws Exception {
        Optional<Member> findMember = memberRepository.findByEmail(member);
        if (findMember.isPresent()) {
            Member member1 = findMember.get();
            if (!Objects.equals(member1.getName(), member.getName())) {
                bindingResult.reject("err", "일치하는 계정이 없습니다.");
                return "member/password/forgotId";
            }

            if (!Objects.equals(member1.getBirthday(), member.getBirthday())) {
                bindingResult.reject("err", "일치하는 계정이 없습니다.");
                return "member/password/forgotId";
            }
        } else {
            bindingResult.reject("err", "일치하는 계정이 없습니다.");
            return "member/password/forgotId";
        }

        naverMailIdService.sendSimpleMessage(member);
       // HttpSession session= request.getSession();
       // session.setAttribute(SessionConst.TEM_MEMBER, member);

        return "redirect:/login?mailSent=true";


    }

    //@GetMapping("/find-id")
    public String getForgot(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @SessionAttribute(name = SessionConst.TEM_MEMBER, required = false) Member temMember,
            Model model,
            HttpServletRequest request
    )  {
        if(loginMember !=null || temMember ==null ){
            return "redirect:/";
        }


        Optional<Member> findMember = memberRepository.findByEmail(temMember);

        if(findMember.isPresent()){
            model.addAttribute("member",findMember);
        }else{
            return null;
        }
        HttpSession session = request.getSession(false);
        if(session !=null){
            session.invalidate();
        }
        return "member/password/find";
    }

}
