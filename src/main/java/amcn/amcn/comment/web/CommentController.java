package amcn.amcn.comment.web;


import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.comment.repository.CommentRepository;
import amcn.amcn.community.domain.board.Board;
import amcn.amcn.community.repository.BoardRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @PostMapping("/comment/{id}")
    public String postComment(@PathVariable Long id,
                              @ModelAttribute Comment comment,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                   Member loginMember,
                              RedirectAttributes redirectAttributes){

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            comment.setMember(member);
        } else {
            return null;
        }


        Optional<Board> findBoard = boardRepository.findBoardId(id);
        if(findBoard.isPresent()){
            Board board = findBoard.get();
              comment.setBoard(board);
        }else{
            return null;
        }
        commentRepository.save(comment);

        redirectAttributes.addAttribute("id",id);

        return "redirect:/community/{id}";
    }

    @PostMapping("/comment/delete/{commentId}")
    @ResponseBody // JSON으로 응답하기 위해 추가
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long commentId) {
        Optional<Comment> comment = commentRepository.findComment(commentId);
        Map<String, String> response = new HashMap<>();

        if (comment.isPresent()) {
            Comment findComment = comment.get();
            commentRepository.delete(findComment);

            // 리다이렉트할 URL 설정
            String redirectUrl = "/community/" + findComment.getBoard().getBoardId();
            response.put("redirectUrl", redirectUrl);

            return ResponseEntity.ok(response);
        } else {
            // 실패한 경우 404 상태 코드 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
