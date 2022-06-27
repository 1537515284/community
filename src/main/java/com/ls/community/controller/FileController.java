package com.ls.community.controller;

import com.ls.community.dto.FileDTO;
import com.ls.community.provider.UFileService;
import com.ls.community.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class FileController {

    @Autowired
    private UFileService uFileService;

    @PostMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(
            HttpServletRequest request) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("editormd-image-file");

        String originalFilename = multipartFile.getOriginalFilename();
        if(!FileUtils.hasSuffix(originalFilename)) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("不支持的格式");
            return fileDTO;
        }
        try {
            String url = uFileService.upload(multipartFile.getInputStream(),multipartFile.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(url);
            return fileDTO;
        }catch (Exception e) {
            log.error("upload error", e);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }
    }


}
