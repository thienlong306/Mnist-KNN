package com.example.knearestneighbors.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinstModel {
    private String label;
    private String[] data;
    private double distance;
    public MinstModel(String label, String[] data) {
        this.label = label;
        this.data = data;
    }
}
