package com.xuu54.httpfileserver.upload.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/upload")
public class UploadController {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    @PostMapping(value = "/local", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Dict local(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Dict.create().set("code", 400).set("message", "文件内容为空");
        }
        String fileName = file.getOriginalFilename();
        String rawFileName = StrUtil.subBefore(fileName, ".", true);
        String fileType = StrUtil.subAfter(fileName, ".", true);
        // upload/年/月/日/文件
        Date date = DateUtil.date();
        String saveFileDir = String.format("%s/%d/%d/%d", filePath,
                DateUtil.year(date), (DateUtil.month(date) + 1), DateUtil.dayOfMonth(date));
        FileUtil.mkdir(saveFileDir);
        String localFilePath = StrUtil.appendIfMissing(saveFileDir, "/") + rawFileName + "-" + DateUtil.current(false) + "." + fileType;
        try {
            file.transferTo(new File(localFilePath));
        } catch (IOException e) {
            log.error("【文件上传至本地】失败，绝对路径：{}", localFilePath);
            return Dict.create().set("code", 500).set("message", "文件上传失败");
        }

        log.info("【文件上传至本地】绝对路径：{}", localFilePath);
        return Dict.create().set("code", 200).set("message", "上传成功").set("data", Dict.create().set("fileName", fileName).set("filePath", localFilePath));
    }
}
