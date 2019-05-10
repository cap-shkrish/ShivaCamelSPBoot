package SpringBootDemo.Services;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


import SpringBootDemo.Beans.Address;
import SpringBootDemo.Beans.AddressBean;
import SpringBootDemo.Beans.Person;



@Repository
@Mapper
public interface PersonAddressMapper {
	
	public Person selectPerson(int id);
	public Address selectAddress(int id);
	public Address getPersonsForAddress(int id);
	public List<Person> getPersons(int id);
	public Address getAddressAndPersons(int id);
	public List<AddressBean> getAddressBean(int id);
	


}
