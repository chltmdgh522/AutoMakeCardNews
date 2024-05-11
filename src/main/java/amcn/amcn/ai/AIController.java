package amcn.amcn.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AIController {
    private final AIService aiService;

    @GetMapping("/ai-image")
    public String getImage(Model model){
        String apple = aiService.generatePicture("apple");
        model.addAttribute("url",apple);
        return "test";
    }
    @PostMapping("/ai-image")
    public ResponseEntity<?> generateImage(@RequestBody String prompt) {
        return new ResponseEntity<>(aiService.generatePicture(prompt), HttpStatus.OK);
    }
}
