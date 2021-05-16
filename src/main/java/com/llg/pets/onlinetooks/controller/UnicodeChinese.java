package com.llg.pets.onlinetooks.controller;

import com.llg.pets.utils.ResponseResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class UnicodeChinese {

    @GetMapping("/chinese2Unicode")
    public ResponseResult<String> chinese2Unicode(@RequestParam String inp){
        char[] utfBytes = inp.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return ResponseResult.buildSuccess(unicodeBytes);
    }

    @GetMapping("/unicode2Chinese")
    public ResponseResult<String> unicodeToChinese(@RequestParam String inp){
        int start = 0;
        final StringBuilder sb = new StringBuilder();
        while (start > -1) {
            int end = inp.indexOf("\\u", start + 2);
            String charStr;
            if (end == -1) {
                charStr = inp.substring(start + 2);
            } else {
                charStr = inp.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            sb.append(letter);
            start = end;
        }
        return ResponseResult.buildSuccess(sb.toString());
    }
}
