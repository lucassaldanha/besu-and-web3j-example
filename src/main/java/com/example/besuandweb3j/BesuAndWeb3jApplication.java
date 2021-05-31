package com.example.besuandweb3j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BesuAndWeb3jApplication {

  public static void main(String[] args) {
    SpringApplication.run(BesuAndWeb3jApplication.class, args);
  }
}
