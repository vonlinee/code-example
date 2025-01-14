package code.sample.spring.jdbc.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.sample.spring.jdbc.dao.UserDao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class UserDaoImpl1 implements UserDao {

    @Autowired
    private DataSource dataSource;

    public void displayData() {
        try {
            String sql = "select STUDENT_NAME from STUDENTS_T where STUDENT_ID=1";
            Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
