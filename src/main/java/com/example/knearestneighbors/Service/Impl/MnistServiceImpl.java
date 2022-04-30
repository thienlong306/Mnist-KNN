package com.example.knearestneighbors.Service.Impl;

import com.example.knearestneighbors.Model.MinstModel;
import com.example.knearestneighbors.Service.MnistService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MnistServiceImpl implements MnistService {
    public  ArrayList<MinstModel> listTrain=new ArrayList<MinstModel>();
    public  MinstModel test=new MinstModel();

    @Override
    public Map<String, Integer> getResult(byte[] fileImg,String inputK){
        //        loadTrain();
        //        listTrain.clear();
        getImg(fileImg);
        return classify(listTrain,test,inputK);
    }

    @Override
    public void loadTrain() {
        BufferedReader br = null;
        try {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
//            System.out.println("Current absolute path is: " + s);
            br = new BufferedReader(new FileReader("mnist_train.csv"));
            String line = "";
            String[] tmp;
            while ((line = br.readLine()) != null) {
                tmp = line.split(",");
                String[] data = Arrays.copyOfRange(tmp, 1, tmp.length);
                MinstModel dp = new MinstModel(tmp[0], data);
                listTrain.add(dp);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(listTrain.size());
    }
    public void getImg(byte[] fileImg){
        BufferedImage imageIn =null;
        BufferedImage imageOut =null;
        try {
            imageIn = ImageIO.read(new ByteArrayInputStream(fileImg));
            resize(imageIn,28,28);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void resize(BufferedImage imageIn, int scaledWidth, int scaledHeight) {
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, imageIn.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(imageIn, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        convertTo2DUsingGetRPG(outputImage);
    }
    public  void convertTo2DUsingGetRPG(BufferedImage image) {
        int width=image.getWidth();
        int height=image.getHeight();
        String[] data = new String[28*28];
        int count=0;
//        System.out.println(width+"-" +height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color color = new Color(image.getRGB(col, row));
                data[count]=String.valueOf(color.getRed());
                count++;
            }
        }
        test.setData(data);
        for (int i=0;i<28*28;i++){
            System.out.print(data[i]);
            if (data[i]=="255"){
                System.out.print(" ");
            }else
                System.out.print("   ");
            if (i%28==0)
                System.out.println();
        }
    }
    public Map<String, Integer> classify(ArrayList<MinstModel> listTrain, MinstModel test,String inputK){
        for(MinstModel ele:listTrain){
            ele.setDistance(distance(ele.getData(),test.getData()));
        }
        Collections.sort(listTrain, new Comparator<MinstModel>() {
            @Override
            public int compare(MinstModel o1, MinstModel o2) {
                if (o1.getDistance()>o2.getDistance())
                    return 1;
                if (o1.getDistance()<o2.getDistance())
                    return -1;
                return 0;
            }
        });
        Map<String,Integer> map=new HashMap<>();
        for (int i=0;i<Integer.parseInt(inputK);i++){
//            System.out.println(listTrain.get(i).getLabel()+"-"+listTrain.get(i).getDistance());
            if (map.get(listTrain.get(i).getLabel())==null){
                map.put(listTrain.get(i).getLabel(), 1);
            }else
                map.put(listTrain.get(i).getLabel(), map.get(listTrain.get(i).getLabel())+1);
        }
        System.out.println();
        for(int i=Integer.parseInt(inputK);i>=0;i--){
            System.out.println(listTrain.get(i).getLabel()+"-"+listTrain.get(i).getDistance());
        }
        Comparator<Map.Entry<String, Integer>> comp = Map.Entry
                .<String, Integer>comparingByValue().reversed()
                .thenComparing(Map.Entry.comparingByKey());
        Map<String, Integer> lhmap =
                map.entrySet().stream().sorted(comp)
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> a,      // merge function
                                LinkedHashMap::new));
        return lhmap;
    }

    public double distance(String[] d1, String[] d2){
        double result = 0;
        for (int i=0;i<d1.length;i++){
            if (!d1[i].equals(d2[i]))
                result+=Math.pow(Double.parseDouble(d1[i])-Double.parseDouble(d2[i]),2);
        }
        return Math.sqrt(result);
    }
}
