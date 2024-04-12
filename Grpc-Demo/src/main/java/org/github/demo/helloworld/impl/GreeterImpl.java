package org.github.demo.helloworld.impl;

import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

  @Override
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
    System.err.println("receive a request: "+ request.toString());
    HelloReply reply = HelloReply.newBuilder().setMessage("hello," + request.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
