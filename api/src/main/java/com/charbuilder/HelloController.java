package com.charbuilder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/randomCharacter")
    public String randomCharacter() {
        Character testCharacter = new Character();
        System.out.println(testCharacter);
        return testCharacter.toString();
    }
}