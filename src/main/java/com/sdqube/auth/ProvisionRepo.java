package com.sdqube.auth;

import com.sdqube.entities.AuthenticationPb;
import com.sdqube.service.Config;
import com.sdqube.service.sql.JDBCException;
import com.sdqube.service.sql.SqlDataSource;
import com.sdqube.service.sql.SqlRepo;
import com.sdqube.service.utils.SDQubeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by >Sagar Duwal<
 * Email: sagar@oyster.io
 * Date: 2020-04-22 14:12
 */
public final class ProvisionRepo extends SqlRepo {
    private static final Logger logger = LoggerFactory.getLogger(ProvisionRepo.class);
    private static final String TX_DB_NAME = Config.getInstance().getString("txn_analytics.db.name", "txnanalytics");
    private static final String TX_TABLE_NAME = TX_DB_NAME + ".txn_expense";
    private static final String TX_COLS = "expense_id, expense_file_id, txn_id, user_id, business_id, expense_type, currency, file_name, file_type, href_url, directory_name, deleted, deleted_at, created_at, updated_at";

    private static final String TX_COLS_AS = "t.expense_id, t.expense_file_id, t.txn_id, t.user_id, t.business_id, t.expense_type, t.currency, t.file_name, t.file_type, t.href_url, t.directory_name, t.deleted, t.deleted_at, t.created_at, t.updated_at";

    private static final int LIMIT_DEFAULT = 30;
    private static final String DEBUG_MSG = "Running Query: {}";

    public ProvisionRepo(SqlDataSource dataSource) {
        super(dataSource);
    }

    AuthenticationPb.User authenticate(String username, String password) {
        String sqlQuery = "SELECT " + TX_COLS + "  FROM " + TX_TABLE_NAME + " WHERE expense_id= ?";

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = dataSource.prepareStatement(sqlQuery, connection);
            statement.setString(1, username);
            statement.setString(1, SDQubeUtils.passwordHash(password));
            logger.debug(statement.toString());
            return getResult(statement.executeQuery(), this::itrAuthentication);
        } catch (JDBCException | SQLException e) {
            logger.error(String.format("Error while getting txn. Query : %s", sqlQuery), e);
        } finally {
            close(statement);
            close(connection);
        }
        return null;
    }

    /*
     * dependency methods
     * */
    private AuthenticationPb.User itrAuthentication(ResultSet resultSet) {
        try {
            AuthenticationPb.User.Builder builder = AuthenticationPb.User.newBuilder();

            String user_id = resultSet.getString("user_id");
            String username = resultSet.getString("username");
            String email = resultSet.getString("email");
            long createdAt = resultSet.getLong("created_at");
            long updatedAt = resultSet.getLong("updated_at");

            builder.setUsername(username);
            builder.setEmail(email);
            builder.setCreatedAt(createdAt);
            builder.setUpdatedAt(updatedAt);


            return builder.build();
        } catch (SQLException e) {
            logger.error("Error itr txn expense details.", e);
        }
        return null;
    }
}
