package org.github.demo.helloworld.impl;

import com.example.user.User;
import com.example.user.UserList;
import com.example.user.UserListRequest;
import com.example.user.UserRequest;
import com.example.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

  private Map<String, User> mockUserDao = new HashMap<>();


  @Override
  public void getUser(UserRequest request, StreamObserver<User> responseObserver) {
    if (mockUserDao.containsKey(request.getEmail())) {
      responseObserver.onNext(mockUserDao.get(request.getEmail()));
      responseObserver.onCompleted();
    } else {
      responseObserver.onError(new RuntimeException("not existed user: " + request.getEmail()));
    }
  }

  @Override
  public void getBatchUsers(UserListRequest request, StreamObserver<User> responseObserver) {
    for (String email : request.getEmailsList()) {
      if (mockUserDao.containsKey(email)) {
        responseObserver.onNext(mockUserDao.get(email));
      }
    }
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<UserRequest> getBatchUsersFromIds(StreamObserver<UserList> responseObserver) {
    new StreamObserver<UserRequest>() {

      private final List<User> users = new ArrayList<>();

      @Override
      public void onNext(UserRequest userRequest) {
        if (mockUserDao.containsKey(userRequest.getEmail())) {
          users.add(mockUserDao.get(userRequest.getEmail()));
        }
      }

      @Override
      public void onError(Throwable t) {
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        UserList userList = UserList.newBuilder().addAllUsers(users).build();
        responseObserver.onNext(userList);
        responseObserver.onCompleted();
      }
    };

    return super.getBatchUsersFromIds(responseObserver);
  }

  @Override
  public StreamObserver<UserRequest> getBatchUsersViaIds(StreamObserver<User> responseObserver) {
    return new StreamObserver<UserRequest>() {

      @Override
      public void onNext(UserRequest userRequest) {
        if (mockUserDao.containsKey(userRequest.getEmail())) {
          responseObserver.onNext(mockUserDao.get(userRequest.getEmail()));
        }
      }

      @Override
      public void onError(Throwable t) {
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }
}
