package com.llg.pets.onlinetooks.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.llg.pets.onlinetooks.entity.JSONFormatDto;
import com.llg.pets.utils.ResponseResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class JSONFormatController {

    @GetMapping("/jsonFormat")
    public ResponseResult<List<JSONFormatDto>> chinese2Unicode(@RequestParam String inp){
        List<JSONFormatDto> resultList = new ArrayList<>();
        JSONFormatDto result;
        if(inp.startsWith("{")){
            result = new JSONFormatDto("{...}");
            JSONObject jsonObject = JSONObject.parseObject(inp, Feature.OrderedField);
            result.setChildren(getChildren(jsonObject));
        }else{
            result = new JSONFormatDto("[...]");
            JSONArray jsonArray = JSONArray.parseArray(inp);
            result.setChildren(getChildren(jsonArray));
        }
        resultList.add(result);
        return ResponseResult.buildSuccess(resultList);

    }

    private static List<JSONFormatDto> getChildren(JSONObject jsonObject){
        List<JSONFormatDto> resultList = new ArrayList<>();
        for (Map.Entry entry : jsonObject.entrySet()) {
            JSONFormatDto jf = new JSONFormatDto((String)entry.getKey());
            resultList.add(jf);
            if(entry.getValue() instanceof JSONObject){
                jf.setLabel(jf.getLabel() + ": " + "{...}");
                jf.setChildren(getChildren((JSONObject)entry.getValue()));
            }else if (entry.getValue() instanceof JSONArray) {
                jf.setLabel(jf.getLabel() + ": " + "[...]");
                jf.setChildren(getChildren((JSONArray) entry.getValue()));
            } else if(entry.getValue() instanceof String){
                jf.setLabel(jf.getLabel() + ": \"" + entry.getValue() + "\"");
            } else{
                jf.setLabel(jf.getLabel() + ": " + entry.getValue() + "");
            }
            jf.setLabel(jf.getLabel() + ",");
        }
        // 删除最后的逗号
        if(resultList.size() > 0){
            JSONFormatDto lastItem = resultList.get(resultList.size() - 1);
            String label = lastItem.getLabel();
            if(label != null && label.length() > 0){
                lastItem.setLabel(label.substring(0 ,label.length() - 1));
            }
        }
        return resultList;
    }

    private static List<JSONFormatDto> getChildren(JSONArray jsonArray){
        List<JSONFormatDto> resultList = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONFormatDto jf = new JSONFormatDto("{...}");
            jf.setChildren(getChildren(jsonArray.getJSONObject(i)));
            resultList.add(jf);
            if(i < jsonArray.size() - 1){
                jf.setLabel(jf.getLabel() + ",");
            }
        }
        return resultList;
    }

}
