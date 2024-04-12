package org.github.demo.helloworld;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.github.demo.helloworld.impl.GreeterImpl;

public class HelloWorldServer {

  private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());
  private static final int port = 50051;
  private Server server;

  private void start() throws IOException {
    server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
        .addService(new GreeterImpl())
        .build()
        .start();
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime().addShutdownHook(
        new Thread(() -> {
          System.out.println("shutting down gRpc server since JVM is shutting down");
          try {
            HelloWorldServer.this.stop();
          } catch (InterruptedException e) {
            e.printStackTrace(System.err);
          }
          System.err.println("shutting down gRpc server since JVM is shutting down");
        })
    );
  }

  public void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  private void blockUtilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    final HelloWorldServer server = new HelloWorldServer();
    server.start();
    server.blockUtilShutdown();
  }

}

