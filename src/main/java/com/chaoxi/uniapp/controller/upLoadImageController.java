package com.chaoxi.uniapp.controller;

import com.chaoxi.uniapp.R;
import com.chaoxi.uniapp.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("test")
public class upLoadImageController {
    @Autowired
    FileUploadUtil fileUploadUtil;

    @PostMapping("/admin/file/upload/element")
    R upLoadImage(@RequestParam("file")MultipartFile file) throws IOException {

        // 获取文件名
        String suffix = file.getOriginalFilename().substring(
                // 获取后缀名
                file.getOriginalFilename().lastIndexOf(".")
        );

        // 创建新的文件名称
        String newFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        String result = fileUploadUtil.uploadByBytes(
                file.getBytes(),
                suffix + newFileName
        );
        return R.success(result);
    }
}
