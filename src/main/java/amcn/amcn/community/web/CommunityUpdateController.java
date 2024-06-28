package amcn.amcn.community.web;


import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.community.domain.board.Board;
import amcn.amcn.community.repository.BoardRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommunityUpdateController {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @GetMapping("/community/edit/{id}")
    public String getCommunityEdit(@PathVariable Long id,
                                   @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                   Member loginMember, Model model) {
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        Optional<Board> findBoard = boardRepository.findBoardId(id);

        if (findBoard.isPresent()) {
            Board board = findBoard.get();
            model.addAttribute("board", board);
        } else {
            return null;
        }

        return "community/communityedit";

    }

    @PostMapping("/community/edit/{id}")
    public String postCommunityEdit(@PathVariable Long id,
                                    @ModelAttribute Board board,
                                    RedirectAttributes redirectAttributes) {
        board.setBoardId(id);
        boardRepository.update(board);

        redirectAttributes.addAttribute("id", id);
        return "redirect:/community/{id}";

    }


    @PostMapping("/community/delete/{id}")
    public String postCommunityDelete(@PathVariable Long id,
                                      RedirectAttributes redirectAttributes) {
        Optional<Board> findBoard = boardRepository.findBoardId(id);
        if (findBoard.isPresent()) {
            Board board1 = findBoard.get();
            boardRepository.delete(board1);
        } else {
            return null;
        }

        return "redirect:/community";

    }


}
