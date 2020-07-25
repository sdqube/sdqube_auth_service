package com.sdqube.auth;

import com.sdqube.entities.AuthenticationRpcGrpc;
import com.sdqube.service.Bootstrap;
import com.sdqube.service.Config;
import com.sdqube.service.Injector;
import com.sdqube.service.SDQubeMain;
import com.sdqube.service.grpc.GrpcMain;
import com.sdqube.service.grpc.GrpcServiceCall;
import com.sdqube.service.logger.SDQubeLogger;
import com.sdqube.service.sql.SqlDataSource;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/22/20 12:16 AM
 */
public class AuthenticationMain extends GrpcMain {
    private static final SDQubeLogger logger = SDQubeLogger.getLogger(SDQubeMain.class);
    private GrpcServiceCall<AuthenticationRpcGrpc.AuthenticationRpcBlockingStub> authServiceCall;
    private final int port;
    private ProvisionRepo provRepo;

    public AuthenticationMain(int port) {
        this.port = port;
    }

    @Override
    protected void cleanUp() {
        try {
            authServiceCall.stop();
        } catch (InterruptedException e) {
            logger.error("Error while stopping service.", e);
        }
    }

    @Override
    public void configure() {
        authServiceCall = Injector.instance(Config.getInstance()
                .getString("service.call.authService",
                        "com.sdqube.service.servicecall.AuthenticationGrpcServiceCall"));

        SqlDataSource provSqlDataSource = new SqlDataSource(ProvisionDbConnection.getInstance());
        provRepo = new ProvisionRepo(provSqlDataSource);
        AuthenticationService authenticationService = new AuthenticationServiceImpl(provRepo);

        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port)
                .addService(ServerInterceptors.intercept(new AuthenticationRpcImpl(authenticationService), new AuthenticationInterceptor()));
        this.buildServer("authService", serverBuilder);
    }

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run(new AuthenticationMain(9091));
    }
}
