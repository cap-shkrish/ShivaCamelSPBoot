package SpringBootDemo.Beans;



public class Person  {
	
	private Integer age;
	private String first_name;
	private String last_name;
	private String phone;
	private String email;
	private Address address;
	private Integer person_id;
	
	public Integer getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}
	
	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	

	@Override
	public String toString() {
		return "{age:" + age + ", first_name:" + first_name + ", last_name:" + last_name + ", phone:" + phone
				+ ", email:" + email + ", address:" + address + ", person_id:" + person_id + ", company:" + company
				+ ", department:" + department + "}";
	}

	public Person(Integer age, String first_name, String last_name, String phone, String email) {
		
		this.age = age;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone = phone;
		this.email = email;
	}

	public Person() {
		
		// TODO Auto-generated constructor stub
	}


	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	private Company company;
	private Department department;


}