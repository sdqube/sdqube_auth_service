package com.sdqube.auth;

import com.sdqube.entities.AuthenticationPb;
import com.sdqube.entities.CommonsPb;
import com.sdqube.service.sql.SqlDataSource;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/26/20 3:50 AM
 */
public class AuthenticationServiceTest {
    private ProvisionRepo provRepo;
//    private AuthenticationRpcGrpc.AuthenticationRpcBlockingStub authServiceCall;

    AuthenticationService authenticationService;

    @Before
    public void setup() {
        SqlDataSource provSqlDataSource = new SqlDataSource(ProvisionDbConnection.getInstance());
        provRepo = new ProvisionRepo(provSqlDataSource);
        authenticationService = new AuthenticationServiceImpl(provRepo);

    }

    @Test
    public void testing() {

        AuthenticationPb.AuthBaseResponse authResponse = authenticationService.login(AuthenticationPb.LoginRequest.newBuilder()
                .setUsernameEmail("sagar")
                .build(), CommonsPb.Debug.newBuilder().build());
        System.out.println("authResponse = " + authResponse);
    }
}
