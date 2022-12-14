package databasequeries;

import models.Product;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.PostgresSqlUtils.SQL_QUERY_FAILED;
import static utils.PostgresSqlUtils.sendSelectQuery;

public class ProductQueries {
    private static final Logger LOG = Logger.getLogger(ProductQueries.class);
    private static final String SELECT_STR = "SELECT * FROM product WHERE id = %s";

    public static Product getProductById(int id) {
        String selectQuery = String.format(SELECT_STR, id);
        ResultSet resultSet = sendSelectQuery(selectQuery);
        try {
            resultSet.next();
            Product product = new Product(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getFloat(3),
                    resultSet.getBoolean(4));
            return product;
        } catch (SQLException e) {
            LOG.error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }
}