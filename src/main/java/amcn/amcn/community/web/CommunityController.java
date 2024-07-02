package amcn.amcn.community.web;

import amcn.amcn.community.domain.board.Board;
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

        Board board=new Board();

        model.addAttribute("it", boardRepository.boardListIt());
        model.addAttribute("sports", boardRepository.boardListSports());
        model.addAttribute("eco", boardRepository.boardListEco());
        model.addAttribute("art", boardRepository.boardListArt());
        model.addAttribute("science", boardRepository.boardListScience());



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

        model.addAttribute("all",boardService.titleContentALLSub());

        model.addAttribute("it",boardRepository.boardListItmore());
        model.addAttribute("sport",boardRepository.boardListSportsmore());

        model.addAttribute("art",boardRepository.boardListArtmore());

        model.addAttribute("sci",boardRepository.boardListSciencemore());
        model.addAttribute("eco",boardRepository.boardListEcomore());





        return "community/communitymore";
    }

}
