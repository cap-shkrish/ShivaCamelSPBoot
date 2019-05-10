package SpringBootDemo.Beans;

import java.util.List;

public class Address {
	
	private Integer id;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private Integer postalcode;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private List<Person> personList;


	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	@Override
	public String toString() {
		return "{id:" + id + ", address1:" + address1 + ", address2:" + address2 + ", city:" + city + ", state:"
				+ state + ", country:" + country + ", postalcode:" + postalcode + ", personList:" + personList + "}";
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(Integer postalcode) {
		this.postalcode = postalcode;
	}

	
	

}
