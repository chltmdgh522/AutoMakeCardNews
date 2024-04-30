package amcn.amcn.member.web.mypage;

import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.service.MyPageService;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;
    @GetMapping("/{memberId}")
    public String getMyPage(
            @PathVariable String memberId,
            Model model,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        myPageService.memberIdCheck(memberId)
                .ifPresent(findMember->model.addAttribute("member",findMember));

        model.addAttribute("loginMember",loginMember);
        log.info("gd");

        return "member/mypage";

    }
}
