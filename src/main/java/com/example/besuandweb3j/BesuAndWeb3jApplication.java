package com.example.besuandweb3j;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BesuAndWeb3jApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(BesuAndWeb3jApplication.class, args);
  }

  @Autowired
  private TransactionSender transactionSender;

  @Override
  public void run(final String... args) throws Exception {
    // Check initial balance
    transactionSender.checkBalance();

    // Send transfer transaction and, after it is complete, check the balance
    transactionSender.transferEth(BigDecimal.TEN).thenRun(() -> transactionSender.checkBalance())
        .get();
  }
}
