package com.sdqube.auth;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationInterceptor implements ServerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(next.startCall(call, headers)) {
            @Override
            public void onMessage(ReqT message) {
                logger.info("gRPC Method: {}, Request message: {}", call.getMethodDescriptor().getFullMethodName(), message);
                super.onMessage(message);
            }
        };
    }

}
