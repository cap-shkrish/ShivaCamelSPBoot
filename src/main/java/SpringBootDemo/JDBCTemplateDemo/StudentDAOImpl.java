package SpringBootDemo.JDBCTemplateDemo;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import SpringBootDemo.Beans.Student;

@Repository
public class StudentDAOImpl implements StudentDAO {
   
   @Autowired JdbcTemplate jdbcTemplate;
   
    public void create(String name, Integer age, String phone, String email) {
      String SQL = "insert into Student (name, age, phone, email) values (?, ?, ?, ?)";
      jdbcTemplate.update( SQL, name, age, phone, email);
      System.out.println("Created Record Name = " + name + " Age = " + age + " Phone = " + phone + " Email = " + email);
      return;
   }
    
   
   public Student getStudent(Integer id) {
	  
      String SQL = "select * from Student where id = ?";
      Student student = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new StudentMapper());
      
      //Use below if the Student properties match db table column names exactly
       //student =  (Student) jdbcTemplate.queryForObject(SQL, new Object[]{id}, new BeanPropertyRowMapper(Student.class));
      
      return student;
   }
      
   public List<Student> listStudents() {
      String SQL = "select * from Student";
      List <Student> students = jdbcTemplate.query(SQL, new StudentMapper());
      List <Student> students2 = jdbcTemplate.query(SQL, new BeanPropertyRowMapper(Student.class));
      System.out.println("List <Student> students2 BeanPropertyRowMapper: " + students2);      
      
      return students2;
   }
   public void delete(Integer id) {
      String SQL = "delete from Student where id = ?";
      jdbcTemplate.update(SQL, id);
      System.out.println("Deleted Record with ID = " + id );
      return;
   }
   public void update(Integer id, Integer age){
      String SQL = "update Student set age = ? where id = ?";
      jdbcTemplate.update(SQL, age, id);
      System.out.println("Updated Record with ID = " + id );
      return;
   }
   
   public void count() {
	   int result = jdbcTemplate.queryForObject(
			    "SELECT COUNT(*) FROM Student", Integer.class);
	   System.out.println("Count of Student Records  = " + result );
   }
   
   
   
   
}