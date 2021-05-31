package com.example.besuandweb3j;

import java.net.ConnectException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.besu.Besu;
import org.web3j.protocol.websocket.WebSocketService;

@Configuration
public class BesuConfiguration {

  @Bean
  public Besu besu(@Value("${besu.url}") String url) {
    try {
      WebSocketService websocketService = new WebSocketService(url, false);
      websocketService.connect();
      return Besu.build(websocketService);
    } catch (ConnectException e) {
      throw new RuntimeException(e);
    }
  }

}
