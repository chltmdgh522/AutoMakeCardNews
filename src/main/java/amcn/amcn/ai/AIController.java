package amcn.amcn.ai;

import amcn.amcn.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AIController {
    private final AIService aiService;
    private final MemberRepository memberRepository;

    @GetMapping("/ai-image")
    public String getImage(Model model){
        return "cardNews/cardnewsmake";
    }
    @PostMapping("/image-create")
    @ResponseBody
    public String generateImage(@RequestParam String prompt, Model model) throws IOException, InterruptedException {
         String url= aiService.
                generatePictureV2(prompt);
        log.info(url);
         return url;
    }
}
