package amcn.amcn.news.web.newcreate;


import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.news.domain.News;
import amcn.amcn.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NewsCreateController {
    private final NewsRepository newsRepository;
    private final MemberRepository memberRepository;


    @GetMapping("/news/create")
    public String getNewsCreate(Model model,
                                @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                Member loginMember) {

        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());

        if (findMember.isPresent()) {
            Member member = findMember.get();

            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }
        News news = new News();

        model.addAttribute("news", news);

        return "news/newsCreate";

    }


    @PostMapping("/news/create")
    public String postNewsCreate(
            @ModelAttribute News news,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
            Member loginMember,
            RedirectAttributes redirectAttributes) {

        news.setMember(loginMember);

        String summary = summary_py(news.getOriginalContent());
        news.setSummaryContent(summary);

        Long id = newsRepository.save(news);

        redirectAttributes.addAttribute("id", id);

        return "redirect:/news/{id}";

    }


    private String summary_py(String content) {
        String[] command = {
                "C:/Users/chltm/Github/amcn/src/main/java/amcn/amcn/python/pythonai/venv/Scripts/python.exe",
                "C:/Users/chltm/Github/amcn/src/main/java/amcn/amcn/Python/pythonAI/summary/main/runmain.py",
                content
        };
        StringBuilder summary = new StringBuilder();
        StringBuilder errors = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            // Read the standard output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                summary.append(line);
            }

            // Read the standard error
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                errors.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.info("Python script exited with error code: " + exitCode);
                log.info("Error output: " + errors.toString());
                return "An error occurred while summarizing the content.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return summary.toString();
    }

}
