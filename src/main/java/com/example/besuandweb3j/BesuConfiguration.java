package com.example.besuandweb3j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.besu.Besu;
import org.web3j.protocol.http.HttpService;

@Configuration
public class BesuConfiguration {

  @Bean
  public Besu besu(@Value("${besu.url}") String url) {
    return Besu.build(new HttpService(url));
  }

}
