package com.example.knearestneighbors.Controller;

import com.example.knearestneighbors.Service.MnistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Controller
public class BaseController {
    @Autowired
    MnistService mnistService;
    @PostConstruct
    public void Init() {
        mnistService.loadTrain();
    }
}
