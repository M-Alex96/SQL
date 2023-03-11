package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static QueryRunner runner = new QueryRunner();

    private SQLHelper () {
    }

    private static Connection getConn () throws SQLException {
        return DriverManager.getConnection ("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    public static DataHelper.VerificationCode getVerificationCode () {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var code = runner.query (conn, codeSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(code);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var connection = getConn();
        runner.execute("DELETE FROM auth_codes");
        runner.execute("DELETE FROM card_transactions");
        runner.execute("DELETE FROM cards");
        runner.execute("DELETE FROM users");
    }
}
