package io.spring.boot.service;

import java.util.ArrayList;
import java.util.List;

import io.spring.boot.common.db.TargetDataSource;
import io.spring.boot.common.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Student Service
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年1月12日
 */
@Service("studentService")
@Transactional
public class StudentService implements IStudentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // @Autowired
    // private StudentMapper studentMapper;

    /* (non-Javadoc)
     * @see org.springboot.sample.service.IStudentService#likeName(java.lang.String)
     */
    @Override
    @TargetDataSource(name = "ds2")
    public List<Student> likeName(String name) {
        // return studentMapper.likeName(name);
        return new ArrayList<>();
    }

    /* (non-Javadoc)
     * @see org.springboot.sample.service.IStudentService#testSave()
     */
    @Override
    public int testSave() {
        Student stu = new Student();
        stu.setAge(33);
        stu.setName("测试新增");
        stu.setSumScore("66");
        stu.setAvgScore("22");
        // return studentMapper.insert(stu);
        return 1;
    }

    /* (non-Javadoc)
     * @see org.springboot.sample.service.IStudentService#likeNameByDefaultDataSource(java.lang.String)
     */
    @Override
    @Transactional
    public List<Student> likeNameByDefaultDataSource(String name) {
        Student stu = new Student();
        stu.setAge(20);
        stu.setSumScore("289.5");
        stu.setAvgScore("88.5");
        stu.setName("莉莉");
        // studentMapper.add(stu);
        if (name.equals("ERROR"))
            throw new RuntimeException("测试事务异常回滚");
        // return studentMapper.likeName(name);
        return new ArrayList<>();
    }

    /* (non-Javadoc)
     * @see org.springboot.sample.service.IStudentService#getList()
     */
    @Override
    public List<Student> getList() {
        String sql = "SELECT ID,NAME,SCORE_SUM,SCORE_AVG, AGE FROM STUDENT";

        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            student.setName("1");
            list.add(student);
        }
        return list;
        // return jdbcTemplate.query(sql, (rs, rowNum) -> {
        //     Student stu = new Student();
        //     stu.setId(rs.getInt("ID"));
        //     stu.setAge(rs.getInt("AGE"));
        //     stu.setName(rs.getString("NAME"));
        //     stu.setSumScore(rs.getString("SCORE_SUM"));
        //     stu.setAvgScore(rs.getString("SCORE_AVG"));
        //     return stu;
        // });
    }

    /* (non-Javadoc)
     * @see org.springboot.sample.service.IStudentService#getListByDs1()
     */
    @Override
    @Transactional
    @TargetDataSource(name = "ds1")
    public List<Student> getListByDs1() {
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            student.setName("1");
            list.add(student);
        }
        return list;

        // String sql = "SELECT ID,NAME,SCORE_SUM,SCORE_AVG, AGE   FROM STUDENT";
        // return jdbcTemplate.query(sql, new RowMapper<Student>() {
        //     @Override
        //     public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        //         Student stu = new Student();
        //         stu.setId(rs.getInt("ID"));
        //         stu.setAge(rs.getInt("AGE"));
        //         stu.setName(rs.getString("NAME"));
        //         stu.setSumScore(rs.getString("SCORE_SUM"));
        //         stu.setAvgScore(rs.getString("SCORE_AVG"));
        //         return stu;
        //     }
        //
        // });
    }

    /* (non-Javadoc)
     * @see org.springboot.sample.service.IStudentService#getListByDs2()
     */
    @Override
    @TargetDataSource(name = "ds2")
    public List<Student> getListByDs2() {
        // String sql = "SELECT ID,NAME,SCORE_SUM,SCORE_AVG, AGE   FROM STUDENT";
        // return jdbcTemplate.query(sql, (rs, rowNum) -> {
        //     Student stu = new Student();
        //     stu.setId(rs.getInt("ID"));
        //     stu.setAge(rs.getInt("AGE"));
        //     stu.setName(rs.getString("NAME"));
        //     stu.setSumScore(rs.getString("SCORE_SUM"));
        //     stu.setAvgScore(rs.getString("SCORE_AVG"));
        //     return stu;
        // });
        return new ArrayList<>();
    }
}
