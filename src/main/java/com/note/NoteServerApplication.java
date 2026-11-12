package com.note;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.note.mapper")
@SpringBootApplication
public class NoteServerApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(NoteServerApplication.class, args);
    }
}
