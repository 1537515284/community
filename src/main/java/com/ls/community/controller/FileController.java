package com.ls.community.controller;

import com.ls.community.dto.FileDTO;
import com.ls.community.exception.CustomizeErrorCode;
import com.ls.community.exception.CustomizeException;
import com.ls.community.provider.UFileService;
import com.ls.community.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Controller
public class FileController {

    @Autowired
    private UFileService uFileService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 上传到本地目录
     * @param request
     * @return
     */
    @PostMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(
            HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("editormd-image-file");

        try {
            if (multipartFile == null)
                throw new CustomizeException(CustomizeErrorCode.REQUEST_PARAMS_NOT_VALID);
            String originalFilename = multipartFile.getOriginalFilename();
            if (!FileUtils.hasSuffix(originalFilename))
                throw new CustomizeException(CustomizeErrorCode.UNSUPPORTED_IMAGE_FORMAT);
            String format = sdf.format(new Date());
            File folder = new File(uploadFolder + "/"+format);
            if (!folder.isDirectory() && !folder.mkdirs())
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);

            String oldName = multipartFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString() + Objects.requireNonNull(oldName).substring(oldName.lastIndexOf("."));
            multipartFile.transferTo(new File(folder, newName));
            log.info("{}---save success", newName);

            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/files/" + format + "/" + newName;
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(url);
            return fileDTO;
        } catch (Exception e) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage(e.getMessage());
            return fileDTO;
        }
    }
    /**
     * 上传到七牛云oss
     */
//    @PostMapping("/file/upload")
//    @ResponseBody
//    public FileDTO upload(
//            HttpServletRequest request) {
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            MultipartFile multipartFile = multipartRequest.getFile("editormd-image-file");
//
//        String originalFilename = multipartFile.getOriginalFilename();
//        if(!FileUtils.hasSuffix(originalFilename)) {
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setSuccess(0);
//            fileDTO.setMessage("不支持的格式");
//            return fileDTO;
//        }
//        try {
//            String url = uFileService.upload(multipartFile.getInputStream(),multipartFile.getOriginalFilename());
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setSuccess(1);
//            fileDTO.setUrl(url);
//            return fileDTO;
//        }catch (Exception e) {
//            log.error("upload error", e);
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setSuccess(0);
//            fileDTO.setMessage("上传失败");
//            return fileDTO;
//        }
//    }
}
