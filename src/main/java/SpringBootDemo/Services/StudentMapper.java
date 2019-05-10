package SpringBootDemo.Services;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import SpringBootDemo.Beans.Student;

import org.apache.ibatis.annotations.*;



@Repository
@Mapper
public interface StudentMapper {
	
	public int getRowCount();
	//@Select("SELECT * FROM Student")
	public List<Student> getAll();
	public int insert(Student student);
	public void update(Student student);
	public Student getById(int id);
	public void deleteById(int id);
	public List<Student> getRecByName(Student student);
	public List<Student> getRecByName_Id(Student student);
	public List<Student> getRecByName_Id_age(Student student);
	public List<Student> getRecByName_age(Student student);
	public List<Student> getName_Id_phone(Student student);
	
	public List<Student> callByAge( int age);
	public Student callById( int id);
	public List<Student> callByNameAge( Student student);

	@Select("SELECT * FROM Student WHERE age = #{age}")
	public List<Student> getByAge(@Param("age") int age);

		
	final String findById = "SELECT * FROM Student WHERE ID = #{id}";
	
	@Select(findById)
	@Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "name", column = "NAME"),
	      @Result(property = "age", column = "AGE"),       
	      @Result(property = "phone", column = "PHONE"),
	      @Result(property = "email", column = "EMAIL")})
	   
	Student findById(int id);	

}
