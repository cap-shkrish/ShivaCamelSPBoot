package SpringBootDemo.Beans;

public class AddressBean {
	private Address address;
	private Person person;
	
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	@Override
	public String toString() {
		return address + ", person=" + person + "]";
	}


	
}
	
	
