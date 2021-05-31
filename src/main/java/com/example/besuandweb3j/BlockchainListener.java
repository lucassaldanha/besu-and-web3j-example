package com.example.besuandweb3j;

import io.reactivex.disposables.Disposable;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.besu.Besu;

@Component
public class BlockchainListener {

  private static final Logger logger = LoggerFactory.getLogger(BlockchainListener.class);

  private final Besu node;

  private final Set<Disposable> subscriptions = new HashSet<>();

  @Autowired
  public BlockchainListener(final Besu node) {
    this.node = node;
  }

  @PostConstruct
  void startSubscriptions() {
    Disposable blocksSubscription = node.blockFlowable(false).subscribe(block -> {
          logger.info("New block {} added to chain", block.getBlock().getHash());
        },
        err -> {
          logger.error("Error subscribing to blocks", err);
        });

    Disposable txSubscription = node.transactionFlowable().subscribe(tx -> {
      logger.info("New transaction {} from {} added to the chain", tx.getHash(), tx.getFrom());
    }, err -> {
      logger.error("Error subscribing to transactions", err);
    });

    subscriptions.add(blocksSubscription);
    subscriptions.add(txSubscription);
  }

  @PreDestroy
  void stopSubscriptions() {
    subscriptions.forEach(Disposable::dispose);
  }
}
