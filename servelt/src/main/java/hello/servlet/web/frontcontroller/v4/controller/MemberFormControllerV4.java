package hello.servlet.web.frontcontroller.v4.controller;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {
    
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
//        System.out.println("[5 - MemberFormControllerV4]MemberFormControllerV4.process will return [new-form]");
        return "new-form";
    }
}

