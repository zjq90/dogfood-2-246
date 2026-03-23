package com.it.controller;

import com.it.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.url}")
    private String uploadUrl;

    @PostMapping("/image")
    public Result<String> uploadImage(MultipartFile file, HttpSession session) {
        log.info("upload image");
        if (session.getAttribute("userInfo") == null) {
            return Result.error("Not logged in");
        }
        if (file == null || file.isEmpty()) {
            return Result.error("File is empty");
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + suffix;
        File dest = new File(uploadPath + newFileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            String url = uploadUrl + newFileName;
            log.info("upload success, url: {}", url);
            return Result.success("Upload success", url);
        } catch (IOException e) {
            log.error("upload failed", e);
            return Result.error("Upload failed");
        }
    }

    @PostMapping("/avatar")
    public Result<String> uploadAvatar(MultipartFile file, HttpSession session) {
        log.info("upload avatar");
        if (session.getAttribute("userInfo") == null) {
            return Result.error("Not logged in");
        }
        if (file == null || file.isEmpty()) {
            return Result.error("File is empty");
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = "avatar_" + UUID.randomUUID().toString() + suffix;
        File dest = new File(uploadPath + "avatar/" + newFileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            String url = uploadUrl + "avatar/" + newFileName;
            log.info("upload avatar success, url: {}", url);
            return Result.success("Upload success", url);
        } catch (IOException e) {
            log.error("upload avatar failed", e);
            return Result.error("Upload failed");
        }
    }
}
