package amcn.amcn.cardnews.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.cardnews.service.CardNewsService;
import amcn.amcn.file.FileService;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
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
    private static String jsonname;
    @Value("${file.dir}")
    private String fileDir;

    @Value("${json.dir}")
    private String jsonDir;

    @Value("${ai.dir}")
    private String aiDir;


    @GetMapping("/ai-image")
    public String getImage(Model model,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if (findMember.isPresent()) {
            Member member = findMember.get();
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        CardNews cardNews = new CardNews();
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

            cardNews.setJsonUrl(jsonname);
            cardNews.setImageUrl(fileName);
            cardNews.setMember(loginMember);
            cardNews.setTrash("X");
            cardNews.setEdit("X");
            cardNews.setOriginalUrl(loginMember.getOriginalUrl());
            Long cardId = cardNewsRepository.save(cardNews);
            redirectAttributes.addAttribute("id", cardId);
            return "redirect:/cardnews/{id}";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @PostMapping("/ai-Json")
    @ResponseBody
    public String saveJsonData(@RequestBody Map<String, Object> jsonData,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                               Member loginMember) {
        try {
            log.info("JSON 데이터가 도착했습니다.");

            if (jsonData.containsKey("backgroundImage")) {
                jsonData.put("backgroundImage", loginMember.getOriginalUrl());
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


    @PostMapping("/image-create")
    @ResponseBody
    public String generateImage(@RequestParam String prompt, Model model,
                                @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                Member loginMember)
            throws IOException, InterruptedException {
        String url = cardNewsService.
                generatePictureV2(prompt);

        String path = fileService.saveImageFromUrl(url);

        log.info(path);
        String replace = path.replace('\\', '/');
        String substring_path = replace.substring(56);
        log.info(substring_path);
        loginMember.setOriginalUrl(substring_path);
        memberRepository.updateUrl(loginMember);
        return substring_path;
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    public String uploadImage(@RequestParam("image") MultipartFile image,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                              Member loginMember) {
        log.info("단일 파일");

        if (image.isEmpty()) {
            return "파일선택좀";
        }

        try {
            // Get the filename
            String uuid = UUID.randomUUID().toString();
            String filename = StringUtils.cleanPath(uuid);
            loginMember.setOriginalUrl(filename);
            memberRepository.updateUrl(loginMember);

            // Get the path to save the file
            String uploadDir = new File(aiDir).getAbsolutePath();
            // Resolve the path for the file
            Path path = Paths.get(uploadDir + File.separator + filename);
            // Copy the file to the upload directory
            Files.copy(image.getInputStream(), path);

            return "업로드 성공: " + filename;
        } catch (IOException e) {
            e.printStackTrace();
            return "업로드 실패";
        }
    }
}

