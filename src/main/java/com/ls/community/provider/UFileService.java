package com.ls.community.provider;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.ls.community.dto.FileDTO;
import com.ls.community.utils.FileUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@PropertySource(value = "classpath:application.yml")
@ConfigurationProperties(prefix = "qiniuyun")
public class UFileService {

    // 设置需要操作的账号的AK和SK
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    // 要上传的空间
    @Value("${bucket}")
    private String bucket;
    //外链地址
    @Value("${domain}")
    private String domain;



    public String  upload(InputStream inputStream,String fileName) {

        // 判断是否是合法的文件后缀
        if (!FileUtils.hasSuffix(fileName)){
            return null;
        }


        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);

        try {
            String token = auth.uploadToken(bucket);
            //生成文件名
            String newFileName = UUID.randomUUID().toString().replaceAll("-","")+fileName.substring(fileName.lastIndexOf("."));
            //实现文件上传
            Response res = uploadManager.put(inputStream, newFileName, token, null, null);
            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址
                return domain +JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                log.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            e.printStackTrace();
            return null;
        }
    }
}
