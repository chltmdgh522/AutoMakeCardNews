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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class adminMemberController {

    private final MemberRepository memberRepository;
    private final AdminService adminService;


    @GetMapping("/api/system/member")
    @ResponseBody // 이 애너테이션은 응답을 JSON으로 변환합니다.
    public ResponseEntity<Page<Member>> getAdminMember(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @ModelAttribute("memberSearchCond") MemberSearch cond,
            @RequestParam(value = "page", defaultValue = "0") int page) {

//        // MASTER가 아닌 경우 403 Forbidden 반환
//        if (!loginMember.getRoleType().equals(RoleType.MASTER)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
//        // 멤버를 찾지 못한 경우 404 Not Found 반환
//        if (!findMember.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }

        Page<Member> list = adminService.getList(cond.getName(), page);
        // 멤버 리스트를 JSON으로 반환
        return ResponseEntity.ok(list);
    }

    @PostMapping("/api/system/{memberId}/point")
    public ResponseEntity<String> postPoint(@PathVariable String memberId,
                                            @RequestBody Map<String, Integer> requestBody) {
        Optional<Member> findMember = memberRepository.findMemberId(memberId);

        if (findMember.isPresent()) {
            Member member = findMember.get();
            Integer newPoint = requestBody.get("point");
            log.info("New point: " + newPoint);

            // 포인트 업데이트 로직
            member.setPoint(Long.valueOf(newPoint));
            memberRepository.updatePoint2(member);

            return ResponseEntity.ok("포인트가 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 멤버를 찾을 수 없습니다.");
        }
    }


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

    @PostMapping("/system/{memberId}/point")
    public String postPoint(@PathVariable String memberId,
                            @ModelAttribute Member pointMember){
        Optional<Member> findMember = memberRepository.findMemberId(memberId);

        log.info(String.valueOf(pointMember.getPoint()));
        if (findMember.isPresent()) {
            Member member = findMember.get();
            member.setPoint(pointMember.getPoint());
            memberRepository.updatePoint2(member);

        } else {
            return null;
        }

        return "redirect:/system/member";
    }

    @PostMapping("/system/{memberId}/delete")
    public String postDelete(@PathVariable String memberId,
                            @ModelAttribute Member pointMember){
        Optional<Member> findMember = memberRepository.findMemberId(memberId);

        log.info(String.valueOf(pointMember.getPoint()));
        if (findMember.isPresent()) {
            Member member = findMember.get();
            memberRepository.delete(member);

        } else {
            return null;
        }

        return "redirect:/system/member";
    }
}
