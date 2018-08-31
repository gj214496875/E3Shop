package com.e3mall.manager.controller;

import com.e3mall.utils.FastDFSClient;
import com.e3mall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片
 *
 * @author gj214
 */
@Controller
public class PictureController {
    @Value("${FASTDFS_SERVICE_ADDRESS}")
    private String FASTDFS_SERVICE_ADDRESS;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String fileUpLoad(MultipartFile uploadFile) {
        Map result = new HashMap(16);
        try {
            //获得文件名
            String uploadFileName = uploadFile.getOriginalFilename();
            String fileName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
            //创建fastDFS客户端
            FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");
            //执行文件上传
            String path = client.uploadFile(uploadFile.getBytes(), fileName);
            //拼接IP地址和返回的路径，组成完整的url
            String url = FASTDFS_SERVICE_ADDRESS + path;
            result.put("error", 0);
            result.put("url", url);
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            result.put("error", 1);
            result.put("message", "图片上传失败！");
            return JsonUtils.objectToJson(result);
        }
    }
}
