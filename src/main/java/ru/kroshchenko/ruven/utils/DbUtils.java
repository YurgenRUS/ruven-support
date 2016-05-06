package ru.kroshchenko.ruven.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Kroshchenko
 *         2016.04.20
 */
public class DbUtils {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static HashMap<String, Object> getOneRow(ResultSet resultSet, List<String> columnNames) throws SQLException {
        if (resultSet.next()) {
            HashMap<String, Object> row = new HashMap<>(columnNames.size());
            for (String columnName : columnNames) {
                row.put(columnName, resultSet.getObject(columnName));
            }
            return row;
        }
        return null;
    }

    public static List<HashMap<String, Object>> getList(ResultSet resultSet, List<String> columnNames) throws SQLException {
        List<HashMap<String, Object>> result = new ArrayList<>();
        while (resultSet.next()) {
            HashMap<String, Object> row = new HashMap<>(columnNames.size());
            for (String columnName : columnNames) {
                row.put(columnName, resultSet.getObject(columnName));
            }
            result.add(row);
        }
        return result;
    }
}
