package com.tsingtec.follow.controller.rest;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.tsingtec.follow.config.qiniu.ConstantQiniu;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.vo.resp.file.FileRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @Author lj
 * @Date 2020/3/9 16:42
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private ConstantQiniu constantQiniu;

    @Value("${file-path}")
    private String docBase;

    /**
     * 文件上传到自己服务器
     *
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "/file")
    public DataResult uploadCover(@RequestParam("file") MultipartFile multipartFile) {
        FileRespVO fileRespVO = new FileRespVO();
        /**
         * 文件保存路径按照日期进行保存
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //当前文件加
        String savePath = sdf.format(new Date());

        File saveFile = new File(docBase + "/" + savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        String fileName = "";
        String fileRandomName = "";
        try {

            fileName = multipartFile.getOriginalFilename();

            String fileType = fileName.substring(fileName.lastIndexOf("."));

            fileRandomName = UUID.randomUUID().toString() + fileType;

            //使用绝对路径进行文件保存
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(docBase + "/" + savePath + "/" + fileRandomName));

            fileRespVO.setSrc("/" + savePath + "/" + fileRandomName);
            fileRespVO.setName(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Message:文件:{}，保存失败", fileName);
            return DataResult.fail("文件上传失败");
        }
        return DataResult.success(fileRespVO);
    }

    /**
     * base64 格式为:
     * @param json
     * @return
     */
    @PostMapping("/base64")
    public DataResult base64(@RequestBody String json) {
        FileRespVO fileRespVO = new FileRespVO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String savePath = sdf.format(new Date());

        File saveFile = new File(docBase + "/" + savePath);

        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }

        String[] strs = json.split(",");

        String fileRandomName = UUID.randomUUID().toString() + strs[2];

        File file = new File(docBase + "/" + saveFile, fileRandomName);
        byte[] fileBytes = Base64.getDecoder().decode(strs[1]);
        try {
            FileUtils.writeByteArrayToFile(file, fileBytes);
            fileRespVO.setSrc("/" + savePath + "/" + fileRandomName);
            fileRespVO.setName(fileRandomName);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Message:文件:{}，保存失败", fileRandomName);
            return DataResult.fail("文件上传失败");
        }
        return DataResult.success(fileRespVO);
    }


    /**
     * base64 格式为:
     *
     * @param json
     * @return
     */
    @PostMapping("/base64/qiniu")
    public DataResult qiniuBase64(@RequestBody String json) {
        return uploadBase64(json);
    }

    /**
     * 上传图片到七牛
     * @return
     * @throws IOException
     */
    @PostMapping("/file/qiniu")
    public DataResult uploadImgQiniu(@RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        FileInputStream inputStream = null;
        try {
            inputStream = (FileInputStream) file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadQNImg(inputStream, fileName);
    }

    /**
     * 将文件上传到七牛云
     */
    private DataResult uploadQNImg(FileInputStream file, String fileName) {
        FileRespVO fileRespVO = new FileRespVO();
        String fileType = fileName.substring(fileName.lastIndexOf("."));

        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        try {
            Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
            String upToken = auth.uploadToken(constantQiniu.getBucket());
            try {
                String key = UUID.randomUUID().toString() + fileType;

                Response response = uploadManager.put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

                String returnPath = constantQiniu.getPath() + "/" + putRet.key;

                fileRespVO.setSrc(returnPath);
                fileRespVO.setName(fileName);

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
                return DataResult.fail("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DataResult.fail("上传失败");
        }
        return DataResult.success(fileRespVO);
    }


    /**
     * 将文件上传到七牛云
     */
    private DataResult uploadBase64(String base64) {
        FileRespVO fileRespVO = new FileRespVO();
        String[] strs = base64.split(",");
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        try {
            Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
            String upToken = auth.uploadToken(constantQiniu.getBucket());
            try {
                String fileRandomName = UUID.randomUUID().toString() + strs[2];
                Response response = uploadManager.put(Base64.getDecoder().decode(strs[1]), fileRandomName, upToken);
                // 解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

                String returnPath = constantQiniu.getPath() + "/" + putRet.key;

                fileRespVO.setSrc(returnPath);
                fileRespVO.setName(fileRandomName);

            } catch (QiniuException ex) {
                Response r = ex.response;
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
                return DataResult.fail("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DataResult.fail("上传失败");
        }
        return DataResult.success(fileRespVO);
    }

}
