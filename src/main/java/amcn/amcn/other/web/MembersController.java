package amcn.amcn.other.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.community.domain.board.Board;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.other.repository.OthersRepository;
import amcn.amcn.other.service.OthersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MembersController {

    private final MemberRepository memberRepository;

    private final OthersService othersService;

    @GetMapping("/members")
    public String getMembers(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                 Member loginMember,
                             Model model){
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();

            if(member.getRoleType().name().equals("USER")){
                return "redirect:/";
            }

            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }


        //게시글
        model.addAttribute("postBoard", othersService.postBoard(loginMember));

        //게시글 공감
        model.addAttribute("likeBoards",othersService.likeBoard(loginMember));

        //게시글 댓글
        model.addAttribute("commentBoard",othersService.commentBoard(loginMember));

        //뉴스 스크랩
        model.addAttribute("likeNews",othersService.newsScrap(loginMember));

        //뉴스
        model.addAttribute("postNews",othersService.postNews(loginMember));

        // 카드뉴스 공감
        List<CardNews> heartCardNews = othersService.findHeartCardNewsService(loginMember);
        model.addAttribute("heartCardNews",heartCardNews);

        // 카드뉴스 포크
        List<CardNews> forkCardNews = othersService.findForkCardNewsService(loginMember);
        model.addAttribute("forkCardNews",forkCardNews);

        return "members/members";
    }

}
