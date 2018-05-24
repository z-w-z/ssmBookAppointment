package com.zwz.ssm.service.Impl;

import com.zwz.ssm.dto.AppointExecution;
import com.zwz.ssm.enums.AppointStateEnum;
import com.zwz.ssm.exception.AppointException;
import com.zwz.ssm.exception.NoNumberException;
import com.zwz.ssm.exception.RepeatAppointException;
import com.zwz.ssm.mapper.AppointmentMapper;
import com.zwz.ssm.mapper.BookMapper;
import com.zwz.ssm.mapper.StudentMapper;
import com.zwz.ssm.po.Appointment;
import com.zwz.ssm.po.Book;
import com.zwz.ssm.po.Student;
import com.zwz.ssm.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * creates by ${user} on 2018/5/24
 */
@Service
public class BookServiceImpl  implements BookService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private StudentMapper studentMapper;



    public Book getById(long bookId) {
        return bookMapper.queryById(bookId);
    }

    public List<Book> getList() {
        return bookMapper.queryAll(0,1000);
    }

    public Student validateStu(Long studentId, Long password) {
        return studentMapper.quaryStudent(studentId,password );
    }

    public List<Book> getSomeList(String name) {
        return bookMapper.querySome(name);
    }

    public List<Appointment> getAppointByStu(Long studentId) {
        return appointmentMapper.queryAndReturn(studentId);
    }

    @Transactional
    public AppointExecution appoin(long bookId, long studentId) {
        try {
            int update = bookMapper.reduceNumber(bookId);//减库存
            if(update <= 0){//已经没有库存
                throw new NoNumberException("no number");
            }else {//执行操作
                int insert = appointmentMapper.insertAppointment(bookId,studentId);
                if (insert <= 0){
                    throw new RepeatAppointException("重复预约了");
                }else {
                    return new AppointExecution(bookId, AppointStateEnum.SUCCESS);
                }

            }
        }catch (NoNumberException e1){
            throw e1;
        }catch (RepeatAppointException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常转换为运行期异常
            throw new AppointException("appoint inner error:"+e.getMessage());
        }

        }


    }

