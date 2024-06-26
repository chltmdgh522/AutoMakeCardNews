package amcn.amcn.file;

import amcn.amcn.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileStore fileStore;
    private final MemberRepository memberRepository;
    private final FileStore2 fileStore2;

    //마이페이지 프로필
    @ResponseBody
    @GetMapping("/my-page/image/{filename}")
    public Resource profileImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    //이미지 생성
    @ResponseBody
    @GetMapping("/ai/image/{filename}")
    public Resource AIImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    //단일 이미지 저장
    @ResponseBody
    @GetMapping("/ai/imageone/{filename}")
    public Resource AI2Image(@PathVariable String filename) throws MalformedURLException {
        log.info("2번쨰");
        return new UrlResource("file:" + fileStore2.getFullPath(filename));
    }

    //json 읽기
    @ResponseBody
    @GetMapping("/ai/json/{filename}")
    public Resource jsonImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore2.getFullPath2(filename));
    }

    //액자
    @ResponseBody
    @GetMapping("/reactor")
    public Resource reactorImage() throws MalformedURLException {
        return new UrlResource("file:" + fileStore2.getFullPath3("액자.png"));
    }

}
