package com.example.demo.async.completablefuture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Data;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author: lijiawei04
 * @date: 2020/11/24 5:13 下午
 */
public class App {

    public static void main(String[] args) {
        //        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        //
        //        MessageService service = context.getBean(MessageService.class);
        //        System.out.println(service.getMessage());

        List<DTO> list = new ArrayList<>();
        DTO a = new DTO();
        a.setIdx(2);
        a.setHhh(12);
        list.add(a);

        DTO b = new DTO();
        b.setIdx(2);
        b.setHhh(11);
        list.add(b);
        DTO c = new DTO();
        c.setIdx(1);
        c.setHhh(11);
        list.add(c);

//        System.out.println(list.stream().sorted(comparing(DTO::getIdx).reversed().thenComparing(DTO::getHhh).reversed()).collect(toList()));
        System.out.println(list.stream().skip(1).limit(10).collect(toList()));
    }

    @Data
    public static class DTO {
        Integer idx;
        Integer hhh;
    }

}