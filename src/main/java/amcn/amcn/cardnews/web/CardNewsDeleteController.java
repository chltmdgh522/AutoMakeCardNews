package amcn.amcn.cardnews.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class CardNewsDeleteController {

    private final CardNewsRepository cardNewsRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/cardnews/fake/{id}")
    public String fakeCardNews(@PathVariable("id") Long id) {
        Optional<CardNews> findCardNews = cardNewsRepository.findCardNewsId(id);
        if (findCardNews.isPresent()) {
            CardNews cardNews = findCardNews.get();
            cardNews.setTrash("O");
            cardNewsRepository.updateTrash(cardNews);
        } else {
            return null;
        }

        return "redirect:/trash";
    }

    @GetMapping("/trash")
    public String getTrash(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                           Member loginMember,
                           Model model) {
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        List<CardNews> findCardNews = cardNewsRepository.findTrashAll(loginMember);
        model.addAttribute("cardnews", findCardNews);

        return "trash/trash";
    }

    @PostMapping("/cardnews/delete")
    public String deleteCardNews(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                 Member loginMember) {
        log.info("딜리드");
        cardNewsRepository.findTrashAllDelete(loginMember);
        return "redirect:/trash";
    }

    @PostMapping("/cardnews/restore")
    public String restoreCardNews(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                  Member loginMember) {
        cardNewsRepository.findTrashAllRestore(loginMember);
        return "redirect:/cardnews/project";
    }

    @PostMapping("/cardnews/select/delete")
    public String selectDeleteCardNews(@RequestParam("selectedIds") String selectedIdsJson) {
        // JSON 문자열을 List로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> selectedIds = new ArrayList<>();


        try {
            //의존성 2개 추가해야됨
            selectedIds = objectMapper.readValue(selectedIdsJson, new TypeReference<List<Long>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }

        cardNewsRepository.findTrashSelectDelete(selectedIds);

        return "redirect:/trash";
    }

    @PostMapping("/cardnews/select/restore")
    public String selectRestoreCardNews(@RequestParam("selectedIdss") String selectedIdsJson) {
        // JSON 문자열을 List로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> selectedIds = new ArrayList<>();


        try {
            selectedIds = objectMapper.readValue(selectedIdsJson, new TypeReference<List<Long>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }


        cardNewsRepository.findTrashSelectRestore(selectedIds);

        return "redirect:/cardnews/project";
    }

}
