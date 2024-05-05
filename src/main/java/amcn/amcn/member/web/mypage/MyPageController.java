package amcn.amcn.member.web.mypage;

import amcn.amcn.file.FileStore;
import amcn.amcn.member.domain.member.EmailType;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.domain.mypage.MyPageMember;
import amcn.amcn.member.service.mypage.MyPageService;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;
    private final FileStore fileStore;


    @ModelAttribute("et")
    public List<EmailType> deliveryCodes(){
        List<EmailType> emalCodes=new ArrayList<>();
        emalCodes.add(new EmailType("naver.com"));
        emalCodes.add(new EmailType("google.com"));
        return emalCodes;
    }

    @GetMapping
    public String getMyPage(
            Model model,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        myPageService.memberIdCheck(loginMember.getMemberId())
                .ifPresent(findMember -> model.addAttribute("member", findMember));

        model.addAttribute("loginMember", loginMember);


        return "member/mypage";

    }

    @GetMapping("/edit")
    public String editGetMyPageHome(
            @ModelAttribute Member member,
            Model model,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        model.addAttribute("loginMember", loginMember);


        Optional<Member> findMember = myPageService.memberIdCheck(loginMember.getMemberId());
        if (findMember.isPresent()) {
            member = findMember.get();

            // 이메일 도메인 얻기
            String email = member.getEmail();
            int i = email.indexOf("@");
            String domain = email.substring(i + 1);
            model.addAttribute("emailDomain", domain);

            // 이메일 아이디 얻기
            String emailFirst = email.substring(0, i);
            member.setEmailF(emailFirst);

            model.addAttribute("member", member);
        } else {
            return null;
        }
        log.info(loginMember.getRoleType().name());
        model.addAttribute("type",loginMember.getRoleType().name());

        return "member/mypage-edit";
    }

    @PostMapping("/edit")
    public String editPostMyPage(
            @Validated
            @ModelAttribute("member") MyPageMember myPageMember,
            BindingResult bindingResult,
            Model model,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) throws IOException {
        model.addAttribute("member",loginMember);

        Member member=new Member();
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
        String Final_email = myPageMember.getEmailF() + "@" + myPageMember.getEmailType().getEmailCode();
        member.setEmail(Final_email);
        member.setEmailF(myPageMember.getEmailF());
        member.setDomain(myPageMember.getEmailType().getEmailCode());

        //멤버 고유 id
        member.setMemberId(loginMember.getMemberId());

        String email_msg = myPageService.email(member);

        if(!Final_email.equals(loginMember.getEmail())) {

            if (Objects.equals(email_msg, "email")) {
                model.addAttribute("error_email", "이메일이 존재합니다");
                log.info("이메일");
                model.addAttribute("member", member);
                model.addAttribute("emailDomain", member.getDomain());
                return "member/mypage-edit";
            }
            if (Objects.equals(email_msg, "emailF")) {
                model.addAttribute("error_email", "올바른 이메일 형식이 아닙니다");
                model.addAttribute("member", member);
                model.addAttribute("emailDomain", member.getDomain());

                return "member/mypage-edit";
            }
        }

        member.setRoleType(RoleType.USER);
        myPageService.updateMyPage(member);

        return "redirect:/mypage";

    }

}