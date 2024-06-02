package amcn.amcn.cardnews.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.domain.searchcond.CardNewsSearchCond;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.cardnews.repository.CardNewsSpringDataRepository;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.like.repository.LikeRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.news.domain.searchcond.NewsSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CardNewsPageController {
    private final CardNewsRepository cardNewsRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final CardNewsSpringDataRepository springDataRepository;


    @GetMapping("/cardnews")
    public String getHomeCard(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                              Member loginMember,
                              Model model,
                              @ModelAttribute("cardNewsSearchCond") CardNewsSearchCond cardNewsSearchCond) {
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }
        
        //검색
        List<CardNews> searchCardNews = springDataRepository.findSearchCardNews(cardNewsSearchCond.getTitle(), cardNewsSearchCond.getCategory(), cardNewsSearchCond.getSelected());

        model.addAttribute("cardnews",searchCardNews);

        return "cardNews/cardnewshome";
    }


    @GetMapping("/cardnews/{id}")
    public String getCardMore(@PathVariable("id") Long id, Model model,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                              Member loginMember) {

        Likes likes = new Likes();
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        Optional<CardNews> cardNewsId = cardNewsRepository.findCardNewsId(id);
        if (cardNewsId.isPresent()) {
            CardNews cardNews = cardNewsId.get();

            likes.setCardNews(cardNews);
            likes.setMember(loginMember);

            model.addAttribute("cardNews", cardNews);
            model.addAttribute("id", id);
        } else {
            return null;
        }

        String correct = likeRepository.findByCardNewsLike(likes);
        model.addAttribute("cardlike",correct);


        int newsLike = likeRepository.findByBookmarkCardNewsLike(likes).size();
        model.addAttribute("cardNewsLike", newsLike);





        return "cardNews/cardnewsmore";
    }

}
