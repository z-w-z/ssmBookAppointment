package com.zwz.ssm.mapper;

import com.zwz.ssm.po.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface StudentMapper {
    // 相互数据库中验证输入的密码是否正确
    Student quaryStudent(@Param("student") long studentId,@Param("password") long password);

}
