package amcn.amcn.news.web.newspage;

import amcn.amcn.like.domain.like.Likes;
import amcn.amcn.like.repository.LikeRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.news.domain.News;
import amcn.amcn.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.python.modules._locale._locale;
import org.python.util.PythonInterpreter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class NewsPageController {

    private final NewsRepository newsRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private Process ttsProcess; // TTS 스크립트를 실행할 프로세스


    @GetMapping("/news/{id}")
    public String getNewsPage(@PathVariable Long id, Model model,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {

        Likes likes = new Likes();
        Optional<Member> findMember = memberRepository.findMemberId(loginMember.getMemberId());
        if(findMember.isPresent()) {
            Member member = findMember.get();
            if (member.getRoleType().name().equals("USER")) {
                return "redirect:/";
            }
            model.addAttribute("type", member.getRoleType().name());
            model.addAttribute("member", member);
        } else {
            return null;
        }

        Optional<News> byNews = newsRepository.findById(id);
        if (byNews.isPresent()) {
            News news = byNews.get();
            likes.setNews(news);
            likes.setMember(loginMember);
            model.addAttribute("news", news);
        } else {
            return null;
        }


        // 좋아요 확인
        String correct = likeRepository.findByNewsLike(likes);
        model.addAttribute("newslike", correct);

        // 좋아요  갯수
        int newsLike = likeRepository.findByBookmarkNewsLike(likes).size();
        model.addAttribute("newsLike", newsLike);

        return "news/newspage";
    }


    @PostMapping("/run-tts")
    @ResponseBody
    public void postTTSScript(@RequestParam("newsId") String content) {
        try {
            // "C:/Users/chltm/PycharmProjects/amcn_AI/tts/tts.py"
            // 파이썬 스크립트를 실행하는 명령어
            String[] command = {"python", "C:/Users/chltm/Github/amcn/src/main/java/amcn/amcn/Python/pythonAI/tts/tts.py", content};

            // 프로세스 빌더를 사용하여 명령어 실행
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            ttsProcess = processBuilder.start();

            // 실행 결과를 읽어오기 위해 BufferedReader 사용
            BufferedReader reader = new BufferedReader(new InputStreamReader(ttsProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            // 에러 출력 읽기
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(ttsProcess.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                log.info(line);
            }

            // 프로세스 종료 상태 확인
            int exitCode = ttsProcess.waitFor();
            log.info(String.valueOf(exitCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // POST 요청을 받아 파이썬 스크립트 실행 취소
    @PostMapping("/cancel-tts")
    @ResponseBody
    public void cancelTTSScript() {
        if (ttsProcess != null) {
            ttsProcess.destroy(); // 파이썬 스크립트 실행 프로세스를 종료
            log.info("TTS script execution canceled.");
        } else {

            log.info("No TTS script is currently running.");
        }
    }

    // POST 요청을 받아 파이썬 스크립트 실행 취소
    @PostMapping("/cancel-tts2")
    @ResponseBody
    public void cancelTTSScriptOther() {
        log.info("하이");
        if (ttsProcess != null) {
            ttsProcess.destroy(); // 파이썬 스크립트 실행 프로세스를 종료
            log.info("TTS script execution canceled.");
        } else {
            log.info("No TTS script is currently running.");
        }
    }


    //@PostMapping("/run-tts")
    @ResponseBody
    public void postTTSScriptV2(@RequestParam("newsId") String content) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://127.0.0.1:8000/tts";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject request = new JSONObject();
            request.put("content", content);

            HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            System.out.println("Response: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

