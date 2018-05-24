package com.zwz.ssm.controller;

import com.zwz.ssm.po.Student;
import com.zwz.ssm.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * creates by ${user} on 2018/5/24
 * @author ZWZ
 */

@Controller
@RequestMapping("/login")
public class LoginController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookService bookService;
    //验证输入的用户名、密码是否正确
    @RequestMapping(value="/verify", method = RequestMethod.POST, produces = {
            "application/json; charset=utf-8" })
    @ResponseBody
    private Map validate(Long studentId,Long password){   //(HttpServletRequest req,HttpServletResponse resp){
        Map resultMap=new HashMap();
        Student student =null;
        System.out.println("验证函数");
        student =bookService.validateStu(studentId,password);

        System.out.println("输入的学号、密码："+studentId+":"+password);
        System.out.println("查询到的："+student.getStudentId()+":"+student.getPassword());

        if(student!=null){
            System.out.println("SUCCESS");
            resultMap.put("result", "SUCCESS");
            return resultMap;
        }else{
            resultMap.put("result", "FAILED");
            return resultMap;
        }

    }
}
