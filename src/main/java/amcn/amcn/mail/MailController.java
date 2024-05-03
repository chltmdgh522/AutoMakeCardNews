package amcn.amcn.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;
    @PostMapping("/email-auth")
    @ResponseBody
    public String getEmail(@RequestParam("email") String email) throws Exception {
        return mailService.sendSimpleMessage(email);
    }
}
