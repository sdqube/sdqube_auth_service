package com.sdqube.auth;

import com.sdqube.entities.AuthenticationPb;
import com.sdqube.entities.CommonsPb;
import com.sdqube.service.logger.SDQubeLogger;
import com.sdqube.service.utils.SDQubeDate;
import com.sdqube.service.utils.SDQubeUtils;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/26/20 3:01 AM
 */
public final class AuthenticationServiceImpl extends AuthenticationService {
    private static final SDQubeLogger logger = SDQubeLogger.getLogger(AuthenticationServiceImpl.class);
    private final ProvisionRepo provRepo;

    public AuthenticationServiceImpl(ProvisionRepo provRepo) {
        this.provRepo = provRepo;
    }

    @Override
    AuthenticationPb.AuthBaseResponse authorize(AuthenticationPb.GAuthorization authorization, String permission, CommonsPb.Debug debug) {
        // validate auth token with jwt
        return AuthenticationPb.AuthBaseResponse.newBuilder()
                .setSuccess(true).build();
    }

    @Override
    AuthenticationPb.AuthBaseResponse login(AuthenticationPb.LoginRequest loginRequest, CommonsPb.Debug debug) {
        // todo add login logic - redis, mongo
        // token generate with jwt

        return AuthenticationPb.AuthBaseResponse.newBuilder()
                .setLoginResponse(AuthenticationPb.LoginResponse.newBuilder()
                        .setAuthorization(AuthenticationPb.GAuthorization.newBuilder()
                                .setToken(SDQubeUtils.uuid())
                                .build())
                        .setDebug(debug)
                        .setUser(AuthenticationPb.User.newBuilder()
                                .setEmail("sagarduwal0102@hotmail.com")
                                .setUsername("sagarduwal")
                                .setCreatedAt(SDQubeDate.timestamp())
                                .setUpdatedAt(SDQubeDate.timestamp())
                                .build())
                        .build())
                .build();

    }
}
