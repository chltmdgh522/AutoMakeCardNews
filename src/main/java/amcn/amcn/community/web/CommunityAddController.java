package amcn.amcn.community.web;

import amcn.amcn.community.domain.board.Board;
import amcn.amcn.community.repository.BoardRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommunityAddController {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @GetMapping("/community/add")
    public String getCommunityAdd(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                               Member loginMember, Model model){

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        Board board =new Board();
        model.addAttribute("board",board);
        return "community/communityadd";
    }

    @PostMapping("/community/add")
    public String postCommunityAdd(@ModelAttribute("board") Board board,
                                   @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                  Member loginMember,
                                   RedirectAttributes redirectAttributes){
        board.setMember(loginMember);

        Long id = boardRepository.save(board);

        redirectAttributes.addAttribute("id", id);
        return "redirect:/community/{id}";
    }
}
