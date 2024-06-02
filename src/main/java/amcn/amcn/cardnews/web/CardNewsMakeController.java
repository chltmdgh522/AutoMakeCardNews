package amcn.amcn.cardnews.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.cardnews.service.CardNewsService;
import amcn.amcn.file.FileService;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CardNewsMakeController {
    private final CardNewsService cardNewsService;
    private final MemberRepository memberRepository;
    private final CardNewsRepository cardNewsRepository;
    private final FileService fileService;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/ai-image")
    public String getImage(Model model,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        CardNews cardNews=new CardNews();
        model.addAttribute("cardNews", cardNews);
        return "cardNews/cardnewsmake";
    }


    @PostMapping("/ai-image")
    public String saveImage(@RequestParam("imageData") String imageData,
                            @Validated
                            @ModelAttribute CardNews cardNews,
                            BindingResult bindingResult,
                            Model model,
                            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                            RedirectAttributes redirectAttributes) {

        if(cardNews.getTitle().isEmpty()){
            bindingResult.reject("fail","제목을 입력해주세요");
            model.addAttribute("member",loginMember);
            return "cardNews/cardnewsmake";
        }
        if(cardNews.getContent().isEmpty()){
            bindingResult.reject("fail","내용을 입력해주세요");
            model.addAttribute("member",loginMember);
            return "cardNews/cardnewsmake";
        }
        if(cardNews.getCategory()==null){
            bindingResult.reject("fail","카테고리를 선택해주세요");
            model.addAttribute("member",loginMember);
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
            String fileName = UUID.randomUUID().toString()+".png";
            Path destinationPath = Paths.get(fileDir, fileName);
            // 파일로 저장
            File outputfile = new File(fileName);
            ImageIO.write(img, "png", destinationPath.toFile());

            cardNews.setImage_url(fileName);
            cardNews.setMember(loginMember);
            Long cardId = cardNewsRepository.save(cardNews);
            redirectAttributes.addAttribute("id",cardId);
            return "redirect:/cardnews/{id}";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }





    @PostMapping("/image-create")
    @ResponseBody
    public String generateImage(@RequestParam String prompt, Model model) throws IOException, InterruptedException {
         String url= cardNewsService.
                generatePictureV2(prompt);

        String path = fileService.saveImageFromUrl(url);
        log.info(path);
        String replace = path.replace('\\', '/');
        String substring_path = replace.substring(57);
        log.info(substring_path);
        return substring_path;
    }



}


