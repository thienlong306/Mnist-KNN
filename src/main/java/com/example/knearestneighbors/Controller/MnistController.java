package com.example.knearestneighbors.Controller;

import com.example.knearestneighbors.Service.MnistService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

@Controller
@RequestMapping("")
public class MnistController{
    @Autowired
    MnistService mnistService;
    @GetMapping("")
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @RequestMapping(value = "postImg",method = RequestMethod.POST)
    @ResponseBody public Map<String,String> postImg(@RequestParam(value="imageBase64",defaultValue = "") String imageBase64,@RequestParam(value="inputK",defaultValue = "") String inputK){
        Map<String,String> res = new HashMap<String, String>();
    //    System.out.println(inputK);
        String base64Image=imageBase64.split(",")[1];
        byte[] bytes= DatatypeConverter.parseBase64Binary(base64Image);
        res.put("msg",mnistService.getResult(bytes,inputK).toString().replace("{","").replace("}",""));
        res.put("test",Arrays.toString(mnistService.getTest().getData()));
        return res;
    }

}
