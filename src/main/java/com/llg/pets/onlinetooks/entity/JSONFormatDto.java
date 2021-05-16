package com.llg.pets.onlinetooks.entity;

import lombok.Data;

import java.util.List;

@Data
public class JSONFormatDto {
    private String label;
    private List<JSONFormatDto> children;

    public JSONFormatDto(String label){
        this.label = label;
    }
}
