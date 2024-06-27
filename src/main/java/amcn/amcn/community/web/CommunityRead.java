package amcn.amcn.community.web;

import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.comment.repository.CommentRepository;
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

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommunityRead {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/community/{id}")
    public String getCommunityRead(@PathVariable Long id,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                               Member loginMember, Model model){

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        Optional<Board> findBoard = boardRepository.findBoardId(id);

        Comment comment=new Comment();

        if(findBoard.isPresent()){
            Board board = findBoard.get();
            int i= board.getComments().size();
            model.addAttribute("commentsize",i);
            model.addAttribute("board",board);
            model.addAttribute("comment",comment);
        }else{
            return null;
        }

        List<Comment> commentBoardId = commentRepository.findCommentBoardId(id);



        model.addAttribute("listComment",commentBoardId);
        return "community/communityread";
    }

}
