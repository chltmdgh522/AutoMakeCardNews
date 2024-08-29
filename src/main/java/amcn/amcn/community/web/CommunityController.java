package amcn.amcn.community.web;

import amcn.amcn.community.domain.board.Board;
import amcn.amcn.community.domain.board.BoardSearchCond;
import amcn.amcn.community.repository.BoardRepository;
import amcn.amcn.community.service.BoardService;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommunityController {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping("/community")
    public String getCommunity(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                               Member loginMember, Model model) {

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        Board board = new Board();

        model.addAttribute("it", boardRepository.boardList("IT"));
        model.addAttribute("sports", boardRepository.boardList("스포츠"));
        model.addAttribute("eco", boardRepository.boardList("경제"));
        model.addAttribute("art", boardRepository.boardList("예술"));
        model.addAttribute("science", boardRepository.boardList("과학"));

        model.addAttribute("it1", boardRepository.boardList("IT").getFirst());
        model.addAttribute("sports1", boardRepository.boardList("스포츠").getFirst());
        model.addAttribute("eco1", boardRepository.boardList("경제").getFirst());
        model.addAttribute("art1", boardRepository.boardList("예술").getFirst());
        model.addAttribute("science1", boardRepository.boardList("과학").getFirst());


        return "community/community";
    }

    @GetMapping("/community/more")
    public String getCommunityMore(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                   Member loginMember, Model model) {

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        model.addAttribute("all", boardService.titleContentALLSub());

        model.addAttribute("it", boardService.titleContentCategorySub("IT"));
        model.addAttribute("sport", boardService.titleContentCategorySub("스포츠"));

        model.addAttribute("art", boardService.titleContentCategorySub("예술"));

        model.addAttribute("sci", boardService.titleContentCategorySub("과학"));
        model.addAttribute("eco", boardService.titleContentCategorySub("경제"));

        return "community/communitymore";
    }


    @GetMapping("/community/search")
    public String getCommunitySearch(@RequestParam(value = "query", required = false) String query,
                                     @ModelAttribute("boardSearchCond") BoardSearchCond boardSearchCond,
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

        if (query != null && !query.isEmpty()) {
            model.addAttribute("board", boardService.searchService(query));
        } else {
            model.addAttribute("board", boardService.searchService(boardSearchCond.getTitle()));

        }


        return "community/communitysearch";
    }

}
