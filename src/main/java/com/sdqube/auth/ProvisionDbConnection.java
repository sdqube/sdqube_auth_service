package com.sdqube.auth;

import com.sdqube.service.Config;
import com.sdqube.service.logger.SDQubeLogger;
import com.sdqube.service.sql.AbstractConnectionFactory;
import com.sdqube.service.sql.DbInfo;
import com.sdqube.service.sql.JDBCException;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/26/20 3:10 AM
 */
public class ProvisionDbConnection extends AbstractConnectionFactory {
    private static final SDQubeLogger logger = SDQubeLogger.getLogger(ProvisionDbConnection.class);
    private static final ProvisionDbConnection INSTANCE = new ProvisionDbConnection();

    private ProvisionDbConnection() {
        try {
            this.start();
        } catch (JDBCException e) {
            logger.error("Error while initializing db connection.", e);
        }
    }

    static ProvisionDbConnection getInstance() {
        return INSTANCE;
    }

    @Override
    public DbInfo config() {
        DbInfo dbInfo = new DbInfo();
        dbInfo.setValidationQuery(Config.getInstance().getString("prov.db.validation.query", "SELECT 1"));
        dbInfo.setPassword(Config.getInstance().getString("prov.db.password", "root"));
        dbInfo.setUsername(Config.getInstance().getString("prov.db.user", "root"));
        dbInfo.setDriver(Config.getInstance().getString("prov.db.driver", "com.mysql.cj.jdbc.Driver"));
        dbInfo.setDatabaseName(Config.getInstance().getString("prov.db.name", "oyster_prov"));
        dbInfo.setUrl(Config.getInstance().getString("prov.db.url"
                , String.format("jdbc:mysql://%s:%d/%s?useSSL=false",
                        Config.getInstance().getString("prov.db.host", "localhost"),
                        Config.getInstance().getInt("prov.db.port", 3306),
                        dbInfo.getDatabaseName())));
        logger.debug("Prov DB URL : {}", dbInfo.getUrl());
        return dbInfo;
    }
}
