package school.hei.schoolapigroupone.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private String userName;
    private String pwd;
    private String url;

    public DatabaseConnection(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
        this.url = "jdbc:postgresql://localhost:5432/school";
    }

    public Statement connection () throws SQLException {
        Connection con = null;
        Statement stm = null;

        con = DriverManager.getConnection(url, userName, pwd);
        stm = con.createStatement();

        return stm;

    }
}
