package com.jay.controller;

import com.jay.service.Book;
import com.jay.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/sale")
@Slf4j
public class SaleController {

    @Autowired
    private BookService bookService;

    /**
     * 进行图书销售
     */
    @RequestMapping(value = "/sale-book/{bookId}", method = RequestMethod.GET)
    public String saleBook(@PathVariable Integer bookId) {

        //用于测试zuul 重试，可通过该日志，查看请求重试次数
//        log.info("request two name is "+bookId);
//        try{
//            Thread.sleep(1000000);
//        }catch ( Exception e){
//            log.error(" hello two error",e);
//        }

        // 调用book服务查找
        Book book = bookService.find(bookId);
        // 控制台输入，模拟进行销售
        System.out.println("销售模块处理销售，要销售的图书id: " + book.getId() + ", 书名："
                + book.getName());
        // 销售成功
        return "SUCCESS";
    }

    @RequestMapping(value = "/testHeader", method = RequestMethod.GET)
    public String testHeader(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            System.out.println("#############" + headerName);
        }
        return "testHeader";
    }

    @RequestMapping(value = "/errorTest", method = RequestMethod.GET)
    public String errorTest() throws Exception {
        Thread.sleep(3000);
        return "errorTest";
    }

}
