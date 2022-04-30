package com.example.knearestneighbors.Service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface MnistService {

    //    public  void main(String[] args) {
    //        getResult();
    //    }
    Map<String, Integer> getResult(byte[] fileImg,String inputK);

    void loadTrain();
}
