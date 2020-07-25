package com.sdqube.auth;

import com.sdqube.entities.AuthenticationPb;
import com.sdqube.entities.AuthenticationRpcGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/26/20 3:28 AM
 */
public class AuthenticationRpcImpl extends AuthenticationRpcGrpc.AuthenticationRpcImplBase {

    private final AuthenticationService authenticationService;

    public AuthenticationRpcImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void authorize(AuthenticationPb.AuthBaseRequest request, StreamObserver<AuthenticationPb.AuthBaseResponse> responseObserver) {
        super.authorize(request, responseObserver);
    }

    @Override
    public void login(AuthenticationPb.AuthBaseRequest request, StreamObserver<AuthenticationPb.AuthBaseResponse> responseObserver) {
        responseObserver.onNext(authenticationService.login(request.getLoginRequest(), request.getDebug()));
        responseObserver.onCompleted();
    }

    @Override
    public void logout(AuthenticationPb.AuthBaseRequest request, StreamObserver<AuthenticationPb.AuthBaseResponse> responseObserver) {
        super.logout(request, responseObserver);
    }
}
