package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Perceptron {
    Double bias = (double) 0;
    Double learningRate = Double.parseDouble("0.01");
    Double net;

    String trainSetName = "src/data/Data.csv";
    String testSetName = "src/data/Test.csv";
    List<Vector> vectorList = new ArrayList<>();
    List<Vector> testVectorList = new ArrayList<>();

    List<Integer> yValues = new ArrayList<>();

    public Perceptron() throws IOException, InterruptedException {


        //Read train data set into vector list
        BufferedReader br = new BufferedReader(new FileReader(trainSetName));
        String line;
        while ((line = br.readLine()) != null) {
            vectorList.add(new Vector(line));
        }
        br.close();

        //Start training
        Vector x = vectorList.get(0);
        Vector w = generateWeightVector(x.getPositions().size());

        net = calculateNet(x, w, bias);
        w.updateVector(x,learningRate,delta(x),function(net));
        bias = updateBias(delta(x),function(net));
        yValues.add(function(net));

        for(int i = 1; i < vectorList.size(); i++){
            x = vectorList.get(i);
            net = calculateNet(x, w, bias);
            w.updateVector(x,learningRate,delta(x),function(net));
            bias = updateBias(delta(x),function(net));

            yValues.add(function(net));
        }
        /*int current = 0;
        while(vectorList.size() < 500){
            int last = vectorList.size();
            for(int i = current; i < last; i++){
                x = vectorList.get(i);
                net = calculateNet(x, w, bias);
                w.updateVector(x,learningRate,delta(x),function(net));
                bias = updateBias(delta(x),function(net));
                vectorList.add(x);
                yValues.add(function(net));
            }
            current = last;
            System.out.println("weights: " + w);
            System.out.println("bias: " + bias);
            System.out.println(iterationError(vectorList, yValues));
            System.out.println(vectorList.size() + " " + yValues.size());
        }*/
        System.out.println("post-training");
        System.out.println("weights: " + w);
        System.out.println("bias: " + bias);

        System.out.println("Iteration Error: " + iterationError(vectorList, yValues));

        //Read test vectors into list
        BufferedReader brTest = new BufferedReader(new FileReader(testSetName));
        String lineTest;
        while ((lineTest = brTest.readLine()) != null) {
            testVectorList.add(new Vector(lineTest));
        }
        br.close();

        //prediction time
        for(int i = 0; i < testVectorList.size(); i++){
            net = calculateNet(testVectorList.get(i), w, bias);
            if(function(net) == 1){
                System.out.println("Predicition: Iris-versicolor --- " + "Real:" + testVectorList.get(i).getvClass());
            }
            if(function(net) == 0){
                System.out.println("Predicition: Iris-virginica --- " + "Real:" + testVectorList.get(i).getvClass());
            }
        }
    }

    public Double calculateNet(Vector x, Vector w, Double bias){
        Double net = (double) 0;
        for(int i = 0; i < x.getPositions().size(); i++){
            net += x.getPositions().get(i) * w.getPositions().get(i);
            //System.out.println("X:" + x.getPositions().get(i));
            //System.out.println(w.getPositions().get(i));
        }
        net -= bias;
        //System.out.println(net);
        //System.out.println();
        return net;
    }

    public Vector generateWeightVector(int dimensions){
        String s = "";
        Double random;
        for(int i = 0; i < dimensions; i++){
            random = Math.random()*2-1;
            s += random + ",";
        }
        s+="null";
        Vector v = new Vector(s);
        return v;
    }

    public int function(Double net){
        if(net >= 0){
            return 1;
        } else {
            return 0;
        }
    }

    public int delta(Vector v){
        if(v.getvClass().equals("Iris-versicolor")){
            return 1;
        }
        else {
            return 0;
        }
    }

    public Double updateBias(int d, int y){
        return bias - learningRate*(d - y);
    }

    public Double iterationError(List<Vector> list, List<Integer> yValues){
        Double sigma = (double) 0;
        for(int i = 0; i < list.size(); i++){
            sigma += Math.pow((delta(list.get(i))-yValues.get(i)),2);

        }
        return sigma/list.size();
    }
}
