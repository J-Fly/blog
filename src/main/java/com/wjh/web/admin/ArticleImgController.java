package com.wjh.web.admin;

import com.wjh.service.ArticleImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/articleImg")
public class ArticleImgController {

    @Autowired
    private ArticleImgService articleImgService;

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map<String,Object> uploadImg(@RequestParam("img") List<MultipartFile> list){
        Map<String,Object> map = new HashMap<String, Object>();
        int errno = 1;//错误代码
        String[] data = new String[0];//存放数据
        try {
            data = articleImgService.uploadImg(list);
            errno = 0;
        }catch (Exception e){
            errno = 1;
            e.printStackTrace();
        }
        map.put("errno",errno);
        map.put("data",data);
        return map;
    }
}
