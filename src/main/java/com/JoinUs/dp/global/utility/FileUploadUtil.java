package com.JoinUs.dp.global.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUploadUtil {

    // 공통 저장 로직
    private String saveFile(MultipartFile file, String subDir) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String baseDir = System.getProperty("user.dir") + "/uploads/" + subDir + "/";
        File dir = new File(baseDir);
        if (!dir.exists()) dir.mkdirs();

        String original = file.getOriginalFilename() != null ? file.getOriginalFilename() : "file";
        String safeName = UUID.randomUUID() + "_" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path fullPath = Paths.get(baseDir, safeName);

        // 파일 저장
        Files.write(fullPath, file.getBytes());

        // WebConfig가 /uploads/** 를 file:<프로젝트>/uploads 로 매핑하므로 이 경로로 접근 가능
        return "/uploads/" + subDir + "/" + safeName;
    }

    // 이미지 저장
    public String saveImage(MultipartFile file) throws IOException {
        return saveFile(file, "images");
    }

    // ✅ PDF 저장
    public String savePdf(MultipartFile file) throws IOException {
        // (선택) MIME 타입 검증
        // if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
        //     throw new IllegalArgumentException("PDF 파일만 업로드 가능합니다.");
        // }
        return saveFile(file, "pdfs");
    }
}
