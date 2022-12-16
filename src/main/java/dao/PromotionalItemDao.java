package dao;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.PostgresSqlUtils.SQL_QUERY_FAILED;
import static utils.PostgresSqlUtils.sendSelectQuery;

public class PromotionalItemDao {
    private static final Logger LOG = Logger.getLogger(PromotionalItemDao.class);
    private static final String SELECT_STR = "SELECT * FROM promotional_item";

    public static List<Long> getListPromotionalItem() {
        ResultSet resultSet = sendSelectQuery(SELECT_STR);
        List<Long> listPromotionalItems = new ArrayList<>();
        try {
            while (resultSet.next()) {
                listPromotionalItems.add(resultSet.getLong(1));
            }
            return listPromotionalItems;
        } catch (SQLException e) {
            LOG.error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }
}