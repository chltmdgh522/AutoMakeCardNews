package amcn.amcn.member.web.mypage;

import amcn.amcn.file.FileStore;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.mypage.MyPageMember;
import amcn.amcn.member.service.MyPageService;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;
    private final FileStore fileStore;

    @GetMapping("/{memberId}")
    public String getMyPage(
            @PathVariable String memberId,
            Model model,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        myPageService.memberIdCheck(memberId)
                .ifPresent(findMember -> model.addAttribute("member", findMember));

        model.addAttribute("loginMember", loginMember);
        log.info("gd");

        return "member/mypage";

    }

    @GetMapping("/edit")
    public String editGetMyPageHome(
            Model model,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        model.addAttribute("loginMember", loginMember);

        myPageService.memberIdCheck(loginMember.getMemberId())
                .ifPresent(findMember -> model.addAttribute("member", findMember));


        return "member/mypage-edit";
    }

    @PostMapping("/edit")
    public String editPostMyPage(
            @Validated
            @ModelAttribute("member") MyPageMember myPageMember,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) throws IOException {
        model.addAttribute("member",loginMember);
        if (bindingResult.hasErrors()) {
            return "member/mypage-edit";
        }
        Member member=new Member();
        log.info("============================================");
        // 프로필사진
        String uploadImage = fileStore.storeFile(myPageMember.getProfile());
        member.setProfile(uploadImage);

        if (uploadImage == null) {
            Optional<Member> fmember = myPageService.memberIdCheck(loginMember.getMemberId());
            member.setProfile(fmember.get().getProfile());
        }

        // 이름
        member.setName(myPageMember.getName());

        // 이메일
        member.setEmail(myPageMember.getEmail());

        //멤버 고유 id
        member.setMemberId(loginMember.getMemberId());

        myPageService.updateMyPage(member);

        redirectAttributes.addAttribute("memberId",loginMember.getMemberId());

        return "redirect:/mypage/{memberId}";

    }

}
