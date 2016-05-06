package ru.kroshchenko.ruven.portal.dao;

import ru.kroshchenko.ruven.entities.User;
import ru.kroshchenko.ruven.utils.ClassNameUtil;
import ru.kroshchenko.ruven.utils.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static User validate(String login, String password) {
        User user = null;
        String sql = "SELECT * FROM user WHERE login = ? AND password = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, login);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            byte byteData[] = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            ps.setString(2, sb.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs);
                }
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }
}