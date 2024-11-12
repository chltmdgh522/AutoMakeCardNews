package amcn.amcn.cardnews.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.domain.searchcond.CardNewsSearchCond;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.cardnews.repository.CardNewsSpringDataRepository;
import amcn.amcn.file.FileStore2;
import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.like.repository.LikeRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CardNewsPageController {
    private final CardNewsRepository cardNewsRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final CardNewsSpringDataRepository springDataRepository;
private final FileStore2 fileStore2;

    @GetMapping("/cardnews")
    public String getHomeCard(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                              Member loginMember,
                              Model model,
                              @ModelAttribute("cardNewsSearchCond") CardNewsSearchCond cardNewsSearchCond) {
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
            if(member.getRoleType().name().equals("USER")){
                return "redirect:/";
            }
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        Optional<CardNews> cardNewsId = cardNewsRepository.findCardNewsId(id);
        if (cardNewsId.isPresent()) {
            CardNews cardNews = cardNewsId.get();

            // 포크 갯수
            Integer byCardNewsFork = cardNewsRepository.findByCardNewsFork(cardNews);
            model.addAttribute("forkcnt",byCardNewsFork);

            // 포크 원본 사용자 이름 불러오기
            Long fork = cardNews.getFork();
            if(fork !=0 ){
                Optional<CardNews> findforkCard = cardNewsRepository.findCardNewsId(fork);
                if(findforkCard.isPresent()){
                    CardNews cardNewsFork = findforkCard.get();
                    model.addAttribute("forkName",cardNewsFork);
                }
            }else{
                model.addAttribute("orginame",cardNews.getMember().getName());
            }

            likes.setCardNews(cardNews);
            likes.setMember(loginMember);

            model.addAttribute("cardNews", cardNews);
            model.addAttribute("id", id);
        } else {
            return null;
        }



        // 좋아요 확인
        String correct = likeRepository.findByCardNewsLike(likes);
        model.addAttribute("cardlike",correct);


        //좋아요 갯수
        int newsLike = likeRepository.findByBookmarkCardNewsLike(likes).size();
        model.addAttribute("cardNewsLike", newsLike);





        return "cardNews/cardnewsmore";
    }



    @PostMapping("/card/sound")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> playSound(@RequestBody Map<String, String> requestData) {
        String cardNewsId = requestData.get("cardNewsId");
        Map<String, Object> response = new HashMap<>();
        Optional<CardNews> findCardNews = cardNewsRepository.findCardNewsId(Long.valueOf(cardNewsId));
        if (findCardNews.isPresent()) {
            CardNews cardNews = findCardNews.get();

            String jsonContent="";
            try {
                // JSON 파일을 로드
                UrlResource jsonResource = new UrlResource("file:" + fileStore2.getFullPath2(cardNews.getJsonUrl()));
                try (InputStream inputStream = jsonResource.getInputStream()) {
                  jsonContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                }



                // JSON 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonContent);

                // "texts" 필드 내의 모든 "text" 값 추출
                List<String> textValues = new ArrayList<>();
                JsonNode textsNode = jsonNode.get("texts");

                if (textsNode.isArray()) {
                    for (JsonNode textNode : textsNode) {
                        // "text" 필드가 있는지 확인하고 값 추출
                        if (textNode.has("text")) {
                            log.info(textNode.get("text").asText());
                            textValues.add(textNode.get("text").asText());
                        }
                    }
                }

                response.put("texts", textValues);

            } catch (IOException e) {
                response.put("error", "JSON 파일을 읽는 중 오류가 발생했습니다.");
            }
        } else {
            response.put("error", "CardNews를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(response);
    }

}
