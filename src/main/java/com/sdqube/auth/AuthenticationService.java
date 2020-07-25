package com.sdqube.auth;

import com.sdqube.entities.AuthenticationPb;
import com.sdqube.entities.CommonsPb;
import com.sdqube.service.grpc.GrpcServiceAbstract;
import com.sdqube.service.logger.SDQubeLogger;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/26/20 2:54 AM
 */
public abstract class AuthenticationService extends GrpcServiceAbstract<AuthenticationPb.AuthBaseResponse> {
    private static final SDQubeLogger logger = SDQubeLogger.getLogger(AuthenticationService.class);

    public AuthenticationService() {
    }

    abstract AuthenticationPb.AuthBaseResponse authorize(AuthenticationPb.GAuthorization authorization,
                                                         String permission, CommonsPb.Debug debug);


    abstract AuthenticationPb.AuthBaseResponse login(AuthenticationPb.LoginRequest loginRequest, CommonsPb.Debug debug);


    @Override
    public AuthenticationPb.AuthBaseResponse error(CommonsPb.ErrorCode errorCode, String s) {
        return null;
    }
}
