package SpringBootDemo.Beans;



public class Student  {
	
	private Integer age;
	private String name;
	private String phone;
	private String email;
	private Integer id;

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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Student() {super();}

	public Student(Integer age, String name, String phone, String email) {
		super();
		this.age = age;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("Id : ").append(id).append(",");
		sb.append("Name : ").append(name).append(",");
		sb.append("age : ").append(age).append(" - ");
		sb.append("Phone : ").append(phone).append(",");
		sb.append("Email : ").append(email);
		sb.append("}");

		return sb.toString();
	}
}