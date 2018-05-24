package com.zwz.ssm.service;

import com.zwz.ssm.dto.AppointExecution;
import com.zwz.ssm.po.Appointment;
import com.zwz.ssm.po.Book;
import com.zwz.ssm.po.Student;

import java.util.List;

/**
 * creates by ${user} on 2018/5/24
 * @author ZWZ
 */
public interface BookService {
//   查询一本图书
    Book getById(long bookId);
//    查询所有图书
    List<Book> getList();
//   登录数据库时查询数据库是否有该学生记录
    Student validateStu(Long student,Long password);
//    按照图书名称查询
//    按条件搜索图书
    List<Book> getSomeList(String name);
//    查看某学生的预约的搜优图书
    List<Appointment> getAppointByStu(Long studentId);
    //返回预约成功的实体类
    AppointExecution appoin(long bookId , long studentId);
}
