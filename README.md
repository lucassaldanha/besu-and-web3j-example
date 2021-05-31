# Besu and Web3j example

Simple example on how to use Web3j to interact with Besu.

1. Start a Besu running
   docker (https://besu.hyperledger.org/en/stable/HowTo/Get-Started/Installation-Options/Run-Docker-Image/)

```
mkdir /tmp/besu
docker run -p 8545:8545 -p 8546:8546 --mount type=bind,source=/tmp/besu,target=/var/lib/besu hyperledger/besu:latest --miner-enabled --miner-coinbase fe3b557e8fb62b89f4916b721be55ceb828dbd73 --rpc-http-enabled --rpc-ws-enabled --network=dev --data-path=/var/lib/besu
```

2. Run the application:

```
./gradlew bootRun
```

The application does the following:

1. Check the balance of Alice and Bob accounts
2. Create a transfer between Alice and Bob
3. Check the balance of Alice and Bob after the transfer