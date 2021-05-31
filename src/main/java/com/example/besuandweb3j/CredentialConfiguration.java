package com.example.besuandweb3j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;

@Configuration
public class CredentialConfiguration {

  @Bean
  public Credentials alice(@Value("${keys.alice}") String privateKey) {
    return Credentials.create(privateKey);
  }

  @Bean
  public Credentials bob(@Value("${keys.bob}") String privateKey) {
    return Credentials.create(privateKey);
  }

}
