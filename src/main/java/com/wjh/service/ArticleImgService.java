package com.wjh.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class ArticleImgService {
    public String[] uploadImg(List<MultipartFile> list) throws Exception {
        String imgUploadAbsolutePath = ResourceUtils.getURL("classpath:").getPath() + "static";
        File file = new File("");
        String imgUploadFileSystemPath = file.getCanonicalPath() + "\\src\\main\\resources\\static";
        System.out.println("imgUploadAbsolutePath: " + imgUploadAbsolutePath);
        System.out.println("imgUploadFileSystemPath: " + imgUploadFileSystemPath);
        String imgUploadRelativePath = "/uplaod/articleimg/";
        String imgUploadFileSystemRelativePath = "\\uplaod\\articleimg\\";
        String finalPath = imgUploadAbsolutePath + imgUploadRelativePath;  //绝对路径　＋　相对路径
        String fileSystemPath = imgUploadFileSystemPath+imgUploadFileSystemRelativePath;
        File finalPathFile = new File(finalPath);
        File fileSystemPathFile = new File(fileSystemPath);
        if (!finalPathFile.exists()) {
            finalPathFile.mkdirs();
        }
        if (!fileSystemPathFile.exists()) {
            fileSystemPathFile.mkdirs();
        }
        System.out.println("finalPath: " + finalPath);
        System.out.println("fileSystemPath: " + fileSystemPath);
        String[] urlData = new String[1];
        int index = 0;
        for (MultipartFile img : list) {
            String fileName = img.getOriginalFilename();
            if (fileName == "") {
                continue;
            }
            //为了保证文件名不一致，因此文件名称使用当前的时间戳和4位的随机数，还有原始文件名组成
            //觉得实际的企业开发不应当使用原始文件名，否则上传者使用一些不好的名字，对于下载者就不好了．
            //这里为了调试方便，可以加上．
            String finalFileName = (new Date().getTime()) + Math.round(Math.random() * 1000)  //文件名动态部分
                    + fileName; //文件名　原始文件名
            File newfile = new File(finalPath + finalFileName);
            File newSystemFile = new File(fileSystemPath + finalFileName);
            System.out.println("newfile path: " + newfile.getAbsolutePath());
            System.out.println("newSystemFile path: " + newSystemFile.getAbsolutePath());

            //保存文件到本地和classes
            FileUtils.copyInputStreamToFile(img.getInputStream(),newSystemFile);
            img.transferTo(newfile);

//            //持久化到数据库
//            ArticleImg articleImg = new ArticleImg();
//            articleImg.setImgName(finalFileName);
//            articleImg.setImgUrl(imgUploadRelativePath + finalFileName);
//            articleImg.setUploadTime(new Date());
//            insertSelective(articleImg);

            //这里的路径是项目路径＋文件路径＋文件名称
            //这么写不是规范的做法，项目路径应是放在前端处理，只需要发送相对路径和文件名称即可，项目路径由前端加上．
            urlData[index++] = imgUploadFileSystemRelativePath + finalFileName;
            System.out.println("urlData[" + (index - 1) + "]: " + urlData[index - 1]);
        }
        return urlData;
    }
}
