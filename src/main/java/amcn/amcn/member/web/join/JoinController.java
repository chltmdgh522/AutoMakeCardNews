package amcn.amcn.member.web.join;

import amcn.amcn.member.domain.member.EmailType;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.service.join.JoinService;
import amcn.amcn.member.web.session.SessionConst;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @ModelAttribute("et")
    public List<EmailType> deliveryCodes(){
        List<EmailType> deliveryCodes=new ArrayList<>();
        deliveryCodes.add(new EmailType("naver.com"));
        deliveryCodes.add(new EmailType("nate.com"));
        deliveryCodes.add(new EmailType("google.com"));
        deliveryCodes.add(new EmailType("gs.anyang.ac.kr"));

        return deliveryCodes;
    }
    @GetMapping("/join")
    public String getJoin(@ModelAttribute Member member,
                          Model model,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                          Member loginMember) {
        if(loginMember !=null){
            return "redirect:/";
        }
        model.addAttribute("member",member);
        return "member/join";
    }
    @PostMapping("/join")
    public String postJoin(@Validated @ModelAttribute Member member, BindingResult bindingResult) {

        String save = joinService.save(member);
        if(Objects.equals(save,"loginId")){
            bindingResult.reject("joinFail","존재하는 아이디가 있습니다");
            return "member/join";
        }
        if(Objects.equals(save,"password")){
            bindingResult.reject("joinFail","비밀번호가 일치하지 않습니다");
            return "member/join";
        }

        if(Objects.equals(save,"email")){
            bindingResult.reject("joinFail","존재하는 이메일이 있습니다");
            return "member/join";
        }
        if(Objects.equals(save,"emailF")){
            bindingResult.reject("joinFail","올바른 이메일 형식이 아닙니다");
            return "member/join";
        }

        return "redirect:/login";
    }
}
