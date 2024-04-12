package org.github.demo.helloworld;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class HelloWorldClient {

  private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());
  private final GreeterGrpc.GreeterBlockingStub blockingStub;


  private HelloWorldClient(Channel channel) {
    this.blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  public void greet(String name) {
    logger.info("will try to greet " + name + "...");
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
    HelloReply helloReply = blockingStub.sayHello(request);
    System.out.println("=== response ===");
    System.out.println(helloReply.toString());
    logger.info("greet finished" + name + "...");
  }

  public static void main(String[] args) throws InterruptedException {
    String host = "localhost";
    int port = 50051;

    ManagedChannel managedChannel =
        Grpc.newChannelBuilderForAddress(host, port, InsecureChannelCredentials.create()).build();
    try {
      HelloWorldClient helloWorldClient = new HelloWorldClient(managedChannel);
      logger.info("hello, I am LagLang");
      helloWorldClient.greet("LagLang");
    } finally {
      managedChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}
