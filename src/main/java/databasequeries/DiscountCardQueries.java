package databasequeries;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.PostgresSqlUtils.SQL_QUERY_FAILED;
import static utils.PostgresSqlUtils.sendSelectQuery;

public class DiscountCardQueries {
    private static final Logger LOG = Logger.getLogger(DiscountCardQueries.class);
    private static final String SELECT_STR = "SELECT percent FROM discount_card WHERE number = '%s'";

    public static byte getPercentByNumber(String number) {
        String selectQuery = String.format(SELECT_STR, number);
        ResultSet resultSet = sendSelectQuery(selectQuery);
        try {
            resultSet.next();
            return resultSet.getByte(1);
        } catch (SQLException e) {
            LOG.error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }
}