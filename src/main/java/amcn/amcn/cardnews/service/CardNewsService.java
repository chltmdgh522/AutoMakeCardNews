package amcn.amcn.cardnews.service;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
@Slf4j
public class CardNewsService {

    @Autowired
    private OpenAiService openAiService;

    @Value("${openai.key}")
    private String openaiApiKey;

    @Async
    public CompletableFuture<String> generatePicture(String prompt) throws IOException, InterruptedException {
        String url = "https://api.openai.com/v1/images/generations";

        // 사용자 입력을 정리
        String cleanedPrompt = prompt
                .replaceAll("카드뉴스", "") // 불필요한 단어 제거
                .replaceAll("생성해줘", "") // 불필요한 단어 제거
                .replaceAll("만들어줘", "") // 불필요한 단어 제거
                .replaceAll("관한", "") // "에 관한" 제거
                .replaceAll("관련", "")
                .replaceAll("에","")// "에 대한" 제거
                .trim(); // 앞뒤 공백 제거

        // 동적 핵심 요소 생성
        String dynamicKeyElements = String.format(
                "An illustration that visually represents '%s'. The image should include a few expressive people reflecting emotions related to '%s'. Add symbolic elements that clearly convey the concept of '%s'. Use a simple and clear style with a soft color palette.",
                cleanedPrompt, cleanedPrompt, cleanedPrompt
        );

        // 최종 프롬프트 생성
        String prompt_2 = String.format(
                "Topic Overview: " +
                        "- Goal: Create a visually clear and expressive illustration about '%s', focusing on making the topic understandable at a glance. " +
                        "Role: " +
                        "- You are an expert illustrator specializing in creating simple and expressive visuals that effectively communicate ideas. " +
                        "Visual Elements: " +
                        "- Include people with expressive faces to reflect the emotions of '%s'. " +
                        "- Add symbolic elements closely related to '%s' to make the concept immediately recognizable. " +
                        "Design Guidelines: " +
                        "- Use a clean, minimal style with soft, neutral colors. " +
                        "- Avoid clutter and ensure the main idea stands out clearly. " +
                        "- Exclude any text in the illustration, focusing solely on visual representation.",
                cleanedPrompt, cleanedPrompt, cleanedPrompt
        );

        System.out.println(prompt_2); // 생성된 프롬프트 확인용

        // JSON 문자열 생성
        String requestBody = String.format(
                "{\"model\":\"dall-e-3\",\"prompt\":\"%s\",\"n\":1,\"size\":\"1024x1024\"}",
                prompt_2);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openaiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 응답 본문에서 URL 추출
        String responseBody = response.body();

        int startIndex = responseBody.indexOf("https://");
        if (startIndex == -1) {
            throw new RuntimeException("URL이 응답 본문에 포함되어 있지 않습니다.");
        }
        int endIndex = responseBody.indexOf("\"", startIndex);
        if (endIndex == -1) {
            throw new RuntimeException("URL의 끝을 찾을 수 없습니다.");
        }
        String imageUrl = responseBody.substring(startIndex, endIndex);

        log.info(response.body());
        log.info("===============");
        log.info(imageUrl);

        return CompletableFuture.completedFuture(imageUrl);
    }

    @Async
    public CompletableFuture<List<String>> generateText(String prompt) throws IOException, InterruptedException {
        String url = "https://api.openai.com/v1/images/generations";
        // 제거할 단어 목록
        String[] removeWords = {"카드뉴스", "이미지", "관한", "대한", "이미저", "카드뉴소"};

        String userInput = removeWordsFromString(prompt, removeWords) +
                "이게 사용자 답변인데, 답변 내용 중 '카드뉴스'나 '이미지'와 관련된 단어가 있으면 무시해 주세요."+
                "남은 문장에 관한 주요 헤드라인 문구 10개를 생성해 주세요."+
                "헤드라인은 간결하고 매력적으로 작성하며, 뉴스 스타일로 작성해주세요.";
        // OpenAI API 호출
        String answer = "";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + openaiApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new JSONObject()
                            .put("model", "gpt-4o")
                            .put("messages", new JSONArray()
                                    .put(new JSONObject().put("role", "system").put("content", "너는 뉴스 헤드라인 문구를 생성하는 전문가야. 간결하고 정보력있는 헤드라인을 만들어줘."))
                                    .put(new JSONObject().put("role", "user").put("content", userInput)))
                            .put("max_tokens", 1000)
                            .toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            answer = jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");


        } catch (Exception e) {
            e.printStackTrace();
        }

        return  CompletableFuture.completedFuture(answer_list(answer));

    }


    List<String> answer_list(String answer) {
        // 결과를 담을 리스트 생성
        List<String> list = new ArrayList<>();
        // "1." 이후의 부분을 추출
        int i = answer.indexOf("1.");
        if (i != -1) {
            String substring_answer = answer.substring(i);

            // 문자열을 줄 단위로 나누기
            String[] lines = substring_answer.split("\n");

            // 각 줄에서 문장 번호를 제거하고 리스트에 추가
            for (String line : lines) {
                if (line.trim().isEmpty()) continue; // 빈 줄 처리
                // 문장 번호를 기준으로 나누기
                int dotIndex = line.indexOf(". ");
                if (dotIndex != -1) {
                    String sentence = line.substring(dotIndex + 2).trim(); // 문장 번호 뒤의 텍스트만 추가
                    list.add(sentence);
                }
            }
        }

        return list;

    }

    // 문자열에서 특정 단어를 제거하는 함수
    String removeWordsFromString(String inputString, String[] wordsToRemove) {
        for (String word : wordsToRemove) {
            inputString = inputString.replace(word, "");
        }
        return inputString.trim();
    }


    public String generatePictureV1(String prompt) {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt(prompt)
                .size("512x512")
                .user("dall-e-3")
                .n(1)
                .build();

        String url = openAiService.createImage(createImageRequest).getData().get(0).getUrl();
        return url;
    }
}