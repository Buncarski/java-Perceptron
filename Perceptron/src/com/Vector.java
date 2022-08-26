package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Vector {
    private List<Double> positions = new ArrayList<>();
    private String vClass;

    public Vector(String s) {
        String[] tmp = s.split(",");
        for(int i=0; i < tmp.length; i++){
            if(i < tmp.length-1){
                this.positions.add(Double.parseDouble(tmp[i]));
            } else {
                if(tmp[i].equals("null")){
                    this.vClass = null;
                } else {
                    this.vClass = tmp[i];
                }
            }
        }
    }

    public List<Double> getPositions() {
        return positions;
    }

    public String getvClass() {
        return vClass;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "positions=" + positions +
                ", vClass='" + vClass + '\'' +
                '}';
    }

    public void updateVector(Vector x, Double learningRate, int delta, int y){
        for(int i = 0; i < this.positions.size(); i++){
            Double value = this.positions.get(i) + learningRate*(delta-y)*x.getPositions().get(i);
            this.positions.set(i, value);
            System.out.print(this.positions.get(i) + " ");
        }
        System.out.println();
    }
}
