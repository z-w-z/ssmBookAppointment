package com.zwz.ssm.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.zwz.ssm.dto.AppointExecution;
import com.zwz.ssm.enums.AppointStateEnum;
import com.zwz.ssm.enums.Result;
import com.zwz.ssm.exception.NoNumberException;
import com.zwz.ssm.exception.RepeatAppointException;
import com.zwz.ssm.mapper.BookMapper;
import com.zwz.ssm.po.Appointment;
import com.zwz.ssm.po.Book;
import com.zwz.ssm.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * creates by ${user} on 2018/5/24
 * @author ZWZ
 */
@Controller
@RequestMapping("/books")
public class BookController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookService bookService;
    //获取图书列表
    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public String List(Model model){
        List<Book> list = bookService.getList();
        model.addAttribute("list",list);
        return "list";
    }
    //搜索是否有图书
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public void search(HttpServletRequest request, HttpServletResponse response)throws Exception{
        //接受页面的值
        String name = request.getParameter("name");
        name = name.trim();
        //向页面传值
        request.setAttribute("name",name);
        request.setAttribute("list",bookService.getSomeList(name));
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request,response);
    }
    //查看某图书的详细情况
    @RequestMapping(value = "/{bookId}/detail" ,method = RequestMethod.GET)
    public String detail(@PathVariable("bookId") Long bookId,Model model)throws Exception{
        if(bookId == null){
            return "redict:/book/list";
        }
        Book book = bookService.getById(bookId);
        if(book==null){
            return"redict:/book/list";
        }
        model.addAttribute("book",book);
        System.out.println(book);
        return "detail";
    }
    //执行预约的逻辑
    @RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.POST, produces = {
            "application/json; charset=utf-8" })
    @ResponseBody
    private Result<AppointExecution> execute(@PathVariable("bookId") Long bookId, @RequestParam("studentId") Long studentId){
        Result<AppointExecution> result;
        AppointExecution execution=null;

        try{//手动try catch,在调用appoint方法时可能报错
            execution=bookService.appoint(bookId, studentId);
            result=new Result<AppointExecution>(true,execution);
            return result;

        } catch(NoNumberException e1) {
            execution=new AppointExecution(bookId, AppointStateEnum.NO_NUMBER);
            result=new Result<AppointExecution>(true,execution);
            return result;
        }catch(RepeatAppointException e2){
            execution=new AppointExecution(bookId,AppointStateEnum.REPEAT_APPOINT);
            result=new Result<AppointExecution>(true,execution);
            return result;
        }catch (Exception e){
            execution=new AppointExecution(bookId,AppointStateEnum.INNER_ERROR);
            result=new Result<AppointExecution>(true,execution);
            return result;
        }
    }
    //预约图书列表
    @RequestMapping("/appoint")
    public String appointBooks(@RequestParam("studentId") Long studentId,Model model){
        List<Appointment> appointlist = new ArrayList<Appointment>();
        appointlist = bookService.getAppointByStu(studentId);
        model.addAttribute("appointlist" ,appointlist);
        return "appointList";
    }
}


