package code.sample.spring.jdbc.daosupport.v2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import code.sample.spring.jdbc.dao.UserDao;
import code.sample.spring.jdbc.model.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDaoImpl extends NamedParameterJdbcDaoSupport implements UserDao {

    public void displayData() {
        String sql = "select * from STUDENTS_T";
        List<Student> students = getNamedParameterJdbcTemplate().query(sql, new StudentMapper());
        for (Student student : students)
            System.out.println("ID : " + student.getId() + "\tNAME : " + student.getName());
    }

    private static final class StudentMapper implements RowMapper<Student> {
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getInt(1)); // student.setId(rs.getInt("STUDENT_ID"));
            student.setName(rs.getString(2)); // student.setName(rs.getString("STUDENT_NAME"));
            return student;
        }
    }
}
