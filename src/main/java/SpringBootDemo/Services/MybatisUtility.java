package SpringBootDemo.Services;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.boot.env.RandomValuePropertySource;

import SpringBootDemo.Beans.Person;

public class MybatisUtility {

	public String getPersonByName(String name) {
		return new SQL() {
			{
				SELECT("*");
				FROM("Personal");
				if (name != null && !name.isEmpty()) {
					WHERE("first_name like #{name} || '%'");
				}
				ORDER_BY("first_name");
			}
		}.toString();
	}

	public String selectPersonSql(String name) {
		return new SQL() {
			{
				SELECT("P.PERSON_ID", "A.address1", "A.state", "P.FIRST_NAME", "D.DEPARTMENT_NAME", "C.COMPANY_NAME");
				FROM("Person P", "Address A", "Department D", "Company C");
				WHERE(	"D.ID = P.DEPARTMENTID","D.COMPANYID = C.ID",
						"P.ADDRESSID = A.ID", "P.FIRST_NAME like #{name}");
				ORDER_BY("P.PERSON_ID", "P.FIRST_NAME");
			}
		}.toString();

	}

	public String insertPersonSql(Person person) {
		return new SQL() {
			{
				INSERT_INTO("Person");
				// VALUES("person_id", "#{person_id}");
				VALUES("first_Name, LAST_NAME, age, phone, email",
						"#{first_name}, #{last_name}, #{age}, #{phone}, #{email}");
				VALUES("addressId", "5");
			}
		}.toString();
	}

	public String deletePersonSql(Integer id) {
		return new SQL() {
			{
				DELETE_FROM("Person");
				WHERE("ID = #{id}");
			}
		}.toString();
	}

	public String updatePersonSql(Person person) {
		return new SQL() {
			{
				UPDATE("Person");
				SET("FIRST_NAME = #{first_Name}");
				SET("LAST_NAME = #{last_Name}");
				WHERE("ID = #{person_id}");
			}
		}.toString();
	}
}