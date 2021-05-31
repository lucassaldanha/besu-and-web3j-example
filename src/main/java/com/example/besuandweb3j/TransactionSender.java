package com.example.besuandweb3j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.besu.Besu;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

@Component
public class TransactionSender {

  private static final Logger logger = LoggerFactory.getLogger(BesuAndWeb3jApplication.class);

  private final Besu node;
  private final Credentials aliceCredentials;
  private final Credentials bobCredentials;

  @Autowired
  public TransactionSender(final Besu node,
      @Qualifier("alice") final Credentials aliceCredentials,
      @Qualifier("bob") final Credentials bobCredentials) {
    this.node = node;
    this.aliceCredentials = aliceCredentials;
    this.bobCredentials = bobCredentials;
  }

  /**
   * Check the balance of Alice and Bob
   */
  public void checkBalance() {
    CompletableFuture
        .allOf(checkBalanceInternal(aliceCredentials), checkBalanceInternal(bobCredentials));
  }

  private CompletableFuture<?> checkBalanceInternal(Credentials credentials) {
    return node.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync()
        .handleAsync((balance, err) -> {
          if (balance != null && err == null) {
            logger.info("Account {} has balance of {}", credentials.getAddress(),
                convertToEth(balance.getBalance()));
            return CompletableFuture.completedFuture(null);
          } else {
            logger.warn("Error checking balance of account {}", credentials.getAddress(), err);
            return CompletableFuture.failedFuture(err);
          }
        });
  }

  private BigInteger convertToEth(BigInteger wei) {
    return wei.divide(Unit.ETHER.getWeiFactor().toBigInteger());
  }

  /**
   * Transfer ether from Alice to Bob
   * @param amount the amount of ether to transfer
   */
  public CompletableFuture<?> transferEth(BigDecimal amount) {
    return transferEthInternal(amount, aliceCredentials, bobCredentials.getAddress());
  }

  private CompletableFuture<?> transferEthInternal(BigDecimal amount, Credentials from, String to) {
    try {
      return Transfer.sendFunds(node, from, to, amount,
          Unit.ETHER).sendAsync().handleAsync((rcpt, err) -> {
        if (rcpt != null && err == null) {
          logger
              .info("Successfully transferred {} eth from {} to {}", amount, from.getAddress(), to);
          return CompletableFuture.completedFuture(null);
        } else {
          logger.warn("Error transferring funds from {} to {}", from.getAddress(), to, err);
          return CompletableFuture.failedFuture(err);
        }
      });
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
