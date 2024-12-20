package amcn.amcn.cardnews.web;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.domain.cardnews.ImageResponse;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.cardnews.service.CardNewsService;
import amcn.amcn.file.FileService;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.repository.MemberRepository;
import amcn.amcn.member.web.session.SessionConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CardNewsMakeController {
    private final CardNewsService cardNewsService;
    private final MemberRepository memberRepository;
    private final CardNewsRepository cardNewsRepository;
    private final FileService fileService;
    Executor executor = Executors.newFixedThreadPool(10); // 필요에 따라 스레드 수 조정
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
            if (member.getRoleType().name().equals("USER")) {
                return "redirect:/";
            }
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

            Path destinationPath = Paths.get(fileDir, fileName);
            // 파일로 저장
            File outputfile = new File(fileName);
            ImageIO.write(img, "png", destinationPath.toFile());


            if (loginMember.isAiImg()) {
                cardNews.setEdit("X");
                loginMember.setAiImg(false);
            } else {
                cardNews.setEdit("O");
            }

            cardNews.setJsonUrl(jsonname);
            cardNews.setImageUrl(fileName);
            cardNews.setMember(loginMember);
            cardNews.setTrash("X");

            cardNews.setOriginalUrl(loginMember.getOriginalUrl());
            Long cardId = cardNewsRepository.save(cardNews);
            redirectAttributes.addAttribute("id", cardId);

            loginMember.setOriginalUrl("");
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
    public ResponseEntity<ImageResponse> generateImage(@RequestParam String prompt, Model model,
                                                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        try {
            // 비동기 호출 (이미 서비스에서 CompletableFuture를 반환하므로 supplyAsync는 필요 없음)
            CompletableFuture<List<String>> textFuture = cardNewsService.generateText(prompt);
            CompletableFuture<String> imageUrlFuture = cardNewsService.generatePicture(prompt);


            // 두 작업 완료 대기
            CompletableFuture.allOf(textFuture, imageUrlFuture).join();

            // 결과 가져오기
            List<String> text = textFuture.get();
            for (String s : text) {
                log.info(s);
                log.info("\n");
            }

            String url = imageUrlFuture.get();

            // 사진 저장
            String path = fileService.saveImageFromUrl(url);

            String replace = path.replace('\\', '/');
            String substringPath = replace.substring(59);
            log.info(substringPath);
            loginMember.setOriginalUrl(substringPath);
            loginMember.setAiImg(true);
            memberRepository.updateUrl(loginMember);

            ImageResponse response = new ImageResponse(substringPath, text.get(0), text);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.info(e.toString());
            ImageResponse response = new ImageResponse(null, "특정 인물은 보안 때문에 AI가 생성을 못합니다.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    @PostMapping("/image-create")
//    @ResponseBody
//    public ResponseEntity<ImageResponse> generateImage(@RequestParam String prompt, Model model,
//                                                @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
//                                Member loginMember)
//            throws IOException, InterruptedException {
//        try {
//
//            //문구
//            List<String> text = cardNewsService.generateText(prompt);
//
//            for (String s : text) {
//                log.info(s);
//                log.info("\n");
//            }
//
//            //사진
//            String url = cardNewsService.generatePicture(prompt);
//            String path = fileService.saveImageFromUrl(url);
//
//            log.info(path);
//            String replace = path.replace('\\', '/');
//            String substring_path = replace.substring(59);
//            log.info(substring_path);
//
//            loginMember.setOriginalUrl(substring_path);
//            loginMember.setAiImg(true);
//            memberRepository.updateUrl(loginMember);
//
//
//
//            ImageResponse response = new ImageResponse(substring_path, text.get(0),text);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            log.info(e.toString());
//            ImageResponse response = new ImageResponse(null, "특정 인물은 보안 때문에 AI가 생성을 못합니다.",null);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }

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


    @ResponseBody
    @PostMapping("/tts")
    public void generateTTS(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String text = request.get("text");
        String outputFileName = "/home/ubuntu/AutoMakeCardNews/output.mp3";  // 생성할 MP3 파일명
        String pythonScriptPath = "/home/ubuntu/AutoMakeCardNews/src/main/java/amcn/amcn/Python/pythonAI/tts/tts.py"; // Python 스크립트 경로

        try {
            // Python 스크립트를 호출하여 TTS 파일 생성
            log.info("tts1");

            String[] command = {"/home/ubuntu/AutoMakeCardNews/src/main/java/amcn/amcn/Python/pythonAI/venv/bin/python",
                    "/home/ubuntu/AutoMakeCardNews/src/main/java/amcn/amcn/Python/pythonAI/tts/tts.py",
                    text, outputFileName};
            log.info("tts2");
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            process.waitFor();

            // 생성된 파일을 클라이언트에게 전송
            File mp3File = new File(outputFileName);
            if (mp3File.exists()) {
                response.setContentType("audio/mpeg");
                response.setHeader("Content-Disposition", "attachment; filename=tts.mp3");

                Files.copy(mp3File.toPath(), response.getOutputStream());
                response.getOutputStream().flush();
                log.info("tts3");

                // 생성된 파일 삭제
                if (mp3File.delete()) {
                    log.info("File {} deleted successfully", outputFileName);
                } else {
                    log.warn("Failed to delete file {}", outputFileName);
                }
            } else {
                log.error("File {} does not exist", outputFileName);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error during TTS processing", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static final String IMAGE_PATH = "/home/ubuntu/AutoMakeCardNews/video_image.png";
    private static final String AUDIO_PATH = "C:/Users/chltm/Github/amcn";
    private static final String VIDEO_OUTPUT_PATH = "/home/ubuntu/AutoMakeCardNews/video.mp4";
    private static File ttsFile = null;

    @ResponseBody
    @PostMapping("/video")
    public ResponseEntity<byte[]> createVideo(@RequestParam("imageData") String imageData,
                                              @RequestParam("audioFile") MultipartFile audioFile) {
        try {
            // 1. 이미지 저장 (Base64로 인코딩된 데이터를 디코딩해서 저장)
            byte[] imageBytes = Base64.getDecoder().decode(imageData.split(",")[1]);
            File imageFile = new File(IMAGE_PATH); // 이미지 저장 경로
            java.nio.file.Files.write(imageFile.toPath(), imageBytes);


            // 2. 파이썬 스크립트 실행 (비디오 생성)
            ProcessBuilder pb = new ProcessBuilder(
                    "/home/ubuntu/AutoMakeCardNews/src/main/java/amcn/amcn/Python/pythonAI/venv/bin/python",
                    "/home/ubuntu/AutoMakeCardNews/src/main/java/amcn/amcn/Python/pythonAI/tts/video.py");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // 파이썬 스크립트 실행 중 출력 로그 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.info("파이썬 스크립트 실행 실패: " + exitCode);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            log.info("비디오 생성 완료");

            // 3. 생성된 비디오 파일 클라이언트로 전송
            File videoFile = new File(VIDEO_OUTPUT_PATH); // 비디오 파일 경로
            if (!videoFile.exists()) {
                System.err.println("비디오 파일을 찾을 수 없습니다: " + videoFile.getPath());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            byte[] videoContent = java.nio.file.Files.readAllBytes(videoFile.toPath());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=video.mp4");

            // 생성된 파일 삭제
            if (videoFile.delete()) {
                log.info("File {} deleted successfully", VIDEO_OUTPUT_PATH);
                ttsFile.delete();

            } else {
                log.warn("Failed to delete file {}", VIDEO_OUTPUT_PATH);
            }

            return new ResponseEntity<>(videoContent, headers, HttpStatus.OK);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/tts2")
    public ResponseEntity<String> generateTTS(@RequestBody Map<String, String> request) {
        String text = request.get("text");

        try {
            // 파이썬 스크립트 실행 (TTS 생성)
            ProcessBuilder pb = new ProcessBuilder("/home/ubuntu/AutoMakeCardNews/src/main/java/amcn/amcn/Python/pythonAI/venv/bin/python",
                    "/home/ubuntu/AutoMakeCardNews/src/main/java/amcn/amcn/Python/pythonAI/tts/video_tts.py", text);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();

            ttsFile = new File("/home/ubuntu/AutoMakeCardNews/video_tts.mp3");
            if (ttsFile.exists()) {
                return new ResponseEntity<>("TTS 파일 생성 완료", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("TTS 파일 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>("오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}