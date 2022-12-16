package dao;

import models.SimpleItem;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.PostgresSqlUtils.SQL_QUERY_FAILED;
import static utils.PostgresSqlUtils.sendSelectQuery;

public class ProductDao {
    private static final Logger LOG = Logger.getLogger(ProductDao.class);
    private static final String SELECT_STR = "SELECT * FROM product WHERE id = %s";
    private static final String INVALID_ID = "Invalid item with id = ";

    public static SimpleItem getProductById(int id) {
        String selectQuery = String.format(SELECT_STR, id);
        ResultSet resultSet = sendSelectQuery(selectQuery);
        try {
            resultSet.next();
            return new SimpleItem(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3));
        } catch (SQLException e) {
            LOG.error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }

    public static boolean isProductWithId(int id) {
        String selectQuery = String.format(SELECT_STR, id);
        ResultSet resultSet = sendSelectQuery(selectQuery);
        try {
            if (resultSet.next()) {
                return true;
            } else {
                LOG.error(INVALID_ID + id);
                return false;
            }
        } catch (SQLException e) {
            LOG.error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }
}