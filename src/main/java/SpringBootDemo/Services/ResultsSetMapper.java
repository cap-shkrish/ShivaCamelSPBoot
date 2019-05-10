package SpringBootDemo.Services;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import SpringBootDemo.Beans.Address;
import SpringBootDemo.Beans.Person;



@Repository
@Mapper
public interface ResultsSetMapper {

	
	@Select(value= "{call getAddressPerson(#{id,jdbcType=INTEGER,mode=IN})}")
	@Options(statementType = StatementType.CALLABLE)
	@ResultMap("userResult")		  
	public List<Person> callStoredProdForPerson(int id);
	
	public Address selectAddress(int id); // method id defined in XML mapper file
	
	//Call to load an association/collection property in a bean
	@Results(value ={
		     @Result(property = "id", column = "Id"),
			 @Result(property = "personList", column = "id",
			    	 many=@Many(select = "getPerson"))})	
	@Select("select * from Address where id=#{addressId}")
	public Address getAddress(Integer addressId);	
	@Select("select * from Personal where addressId=#{addressId}")
	public Person getPerson(Integer addressId);
	
	//id userResult can be reused elsewhere  
	@Results(id = "userResult", value ={
		     @Result(property="person_id", column = "person_id" ),
		     @Result(property="address.id", column = "id"),
		     @Result(property="address.address1", column = "address1"),
		     @Result(property="address.address2", column = "address2"),
		     @Result(property="address.city", column = "city"),
		     @Result(property="address.state", column = "state"),
		     @Result(property="address.country", column = "country"),
		     @Result(property="address.postalcode", column = "postalcode"),
		     })		  
	@Select("select * from Address A left outer join Personal P on A.id = P.addressId where A.id = #{id}")
			public List<Person> getPersons(Integer id);
	
	//Call a sql builder utility class method for dynamic queries
	@SelectProvider(type=MybatisUtility.class, method="getPersonByName")
	public List<Person> getPersonByName(String name);	
	
	//Call a sql builder utility class method to insert an object
	@Options(useGeneratedKeys=true, keyProperty="person_id") 
	@SelectKey(statement = "SELECT LAST_INSERT_ID() as person_id", keyProperty = "person_id", before = false, resultType = Integer.class)		
	@SelectProvider(type=MybatisUtility.class, method="insertPersonSql")
	public void InsertPerson(Person person);	
	
	//Direct inline SQL to insert an object
	String INSERT_PERSON ="INSERT INTO Person (person_id,first_name,last_name, AGE, phone, email, addressId) "
			+ "VALUES(#{person_id},#{first_name},#{last_name},#{age},#{phone},#{email},'5')"; 	
	@Insert(INSERT_PERSON)
	public void InsertPerson2(Person person);
	
	String UPDATE_PERSON = "UPDATE Person SET FIRST_NAME = #{first_name},  AGE = #{age}, PHONE = #{phone}, EMAIL = #{email} WHERE person_id = #{person_id}";
	@Update(UPDATE_PERSON)
	void updatePerson(Person person);
	
	@Delete("DELETE FROM Person WHERE person_id =#{id}")
	void deletePerson(int id);
	
	//Call a sql builder utility class method for dynamic queries
	@Results(id = "personResult", value ={
		     @Result(property="person_id", column = "person_id" ),
		     @Result(property="address.address1", column = "address1"),
		     @Result(property="address.state", column = "state"),
		     @Result(property="department.department_name", column = "department_name"),
		     @Result(property="company.company_name", column = "company_name"),
		     })		  
	@SelectProvider(type=MybatisUtility.class, method="selectPersonSql")
	public Person selectPersonSql1(String name);	
	
	String sql = "SELECT P.PERSON_ID, A.address1, A.state, P.FIRST_NAME, D.DEPARTMENT_NAME, C.COMPANY_NAME" + 
			"		    FROM(Person P, Address A)" + 
			"            INNER JOIN Department D on D.ID = P.DEPARTMENTID" + 
			"            INNER JOIN Company C on D.COMPANYID = C.ID" + 
			" 		    WHERE(" + 
			"            P.ADDRESSID = A.ID and " + 
			"            P.FIRST_NAME like #{name})";
	@Select(sql)
	@ResultMap("personResult")
	public Person selectPersonSql(String name);	
}
