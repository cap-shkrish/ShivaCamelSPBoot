package SpringBootDemo.Beans;

public class Department {
	
	private Integer id;
	private Integer companyId;
	private String department_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	@Override
	public String toString() {
		return "{id:" + id + ", companyId:" + companyId + ", department_name:" + department_name + "}";
	}
	
	

}