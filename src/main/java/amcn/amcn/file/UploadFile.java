package amcn.amcn.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class  UploadFile {
    private String storeFileName; // 서버에서 내부관리하는 파일명
}