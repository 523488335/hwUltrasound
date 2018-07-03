package com.hw;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hw.command.Conection;
import com.hw.command.Manager;

@SpringBootApplication
public class HwUltrasoundApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HwUltrasoundApplication.class, args);
	}

}
