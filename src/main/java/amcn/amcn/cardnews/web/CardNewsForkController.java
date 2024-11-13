package amcn.amcn.cardnews.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CardNewsForkController {
    private final CardNewsRepository cardNewsRepository;
    private final MemberRepository memberRepository;

    private static String jsonname;
    @Value("${file.dir}")
    private String fileDir;

    @Value("${json.dir}")
    private String jsonDir;

    @GetMapping("/cardnews/fork/{id}")
    public String getEdit(@PathVariable("id") Long id,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                          Member loginMember,
                          Model model) {

        String memberId1="";
        String memberId2="";
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            memberId1=member.getMemberId();
            if(member.getRoleType().name().equals("USER")){
                return "redirect:/";
            }
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        Optional<CardNews> findCardNews = cardNewsRepository.findCardNewsId(id);
        if (findCardNews.isPresent()) {
            CardNews cardNews = findCardNews.get();
            memberId2=cardNews.getMember().getMemberId();
            cardNews.setTitle("");
            cardNews.setContent("");
            cardNews.setCategory("");

            model.addAttribute("cardNews", cardNews);
            model.addAttribute("id", id);
        } else {
            return null;
        }

        if(memberId1.equals(memberId2)){
            return "redirect:/";
        }

        return "cardNews/cardnewsfork";
    }

    @PostMapping("/cardnews/fork/{id}")
    public String saveImage(@RequestParam("imageData") String imageData,
                            @PathVariable("id") Long id,
                            @Validated
                            @ModelAttribute CardNews cardNews,
                            BindingResult bindingResult,
                            Model model,
                            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                            RedirectAttributes redirectAttributes) {

        if (cardNews.getTitle().isEmpty()) {
            bindingResult.reject("fail", "제목을 입력해주세요");
            model.addAttribute("member", loginMember);
            return "cardNews/cardnewsmake";
        }
        if (cardNews.getContent().isEmpty()) {
            bindingResult.reject("fail", "내용을 입력해주세요");
            model.addAttribute("member", loginMember);
            return "cardNews/cardnewsmake";
        }
        if (cardNews.getCategory() == null) {
            bindingResult.reject("fail", "카테고리를 선택해주세요");
            model.addAttribute("member", loginMember);
            return "cardNews/cardnewsmake";
        }


        try {
            // Base64 문자열에서 데이터 부분만 추출
            String base64Image = imageData.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // 바이트 배열을 BufferedImage로 변환
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));

            // 변환된 이미지가 null인지 확인
            if (img == null) {
                return "Failed to decode image.";
            }

            String fileName = UUID.randomUUID().toString() + ".png";
            ;
            Path destinationPath = Paths.get(fileDir, fileName);
            // 파일로 저장
            File outputfile = new File(fileName);
            ImageIO.write(img, "png", destinationPath.toFile());


            Optional<CardNews> cardNewsId = cardNewsRepository.findCardNewsId(id);
            if (cardNewsId.isPresent()) {
                CardNews cardNews2 = cardNewsId.get();
                cardNews.setOriginalUrl(cardNews2.getOriginalUrl());
            } else {
                return null;
            }

            cardNews.setJsonUrl(jsonname);
            cardNews.setImageUrl(fileName);
            cardNews.setMember(loginMember);

            cardNews.setTrash("X");
            cardNews.setEdit("O");
            cardNews.setFork(id);

            Long cardId = cardNewsRepository.save(cardNews);
            log.info(String.valueOf(cardId));
            redirectAttributes.addAttribute("id", cardId);
            return "redirect:/cardnews/{id}";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }


    @PostMapping("/ai-forkJson")
    @ResponseBody
    public String saveJsonData(@RequestBody Map<String, Object> jsonData,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                               Member loginMember) {
        try {
            log.info("JSON 데이터가 도착했습니다.");

            if (jsonData.containsKey("backgroundImage")) {
                //jsonData.put("backgroundImage", loginMember.getOriginalUrl());
            }

            jsonname = UUID.randomUUID().toString() + ".json";


            // 전송된 JSON 데이터를 파일로 저장
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(jsonDir + File.separator + jsonname), jsonData);


            // 성공적으로 처리되었을 경우 메시지 반환
            return "JSON data saved successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 발생 시 오류 메시지 반환
            return "Error occurred while saving JSON data.";
        }
    }


}
