package amcn.amcn.news.web.newhome;


import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.news.domain.News;
import amcn.amcn.news.domain.searchcond.NewsSearchCond;
import amcn.amcn.news.repository.NewsRepository;
import amcn.amcn.news.repository.NewsSpringDataRepository;
import amcn.amcn.news.service.NewsHomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class NewsHomeController {
    private final MemberRepository memberRepository;
    private final NewsHomeService newsHomeService;
    private final NewsRepository newsRepository;

    @GetMapping("/news")
    public String getNewsHome(Model model,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                              @ModelAttribute("newsSearchCond") NewsSearchCond newsSearchCond,
                              @RequestParam(value = "page", defaultValue = "0") int page) {

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        String title = newsSearchCond.getTitle();
        String category = newsSearchCond.getCategory();
        Page<News> paging = newsHomeService.getList(category, title, page);
        model.addAttribute("paging", paging);

        //model.addAttribute("news", newsRepository.findAll());
        return "news/newsHome";
    }
}
