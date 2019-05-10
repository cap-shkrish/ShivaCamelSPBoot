package SpringBootDemo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringBootDemo.Beans.Address;
import SpringBootDemo.Beans.AddressBean;
import SpringBootDemo.Beans.Person;

@Service
public class MybatisDemoService {

	@Autowired PersonAddressMapper personAddressMapper;
	@Autowired ResultsSetMapper resultsSetMapper;

	public  List<AddressBean> getAddressBeans( int id)
	{
		List<AddressBean> addressBeans = personAddressMapper.getAddressBean(id);
		System.out.println("\npersonAddressMapper.getAddressBean(2)\n");
		System.out.println("Array Length = " + addressBeans.size() + " \n" + addressBeans);
		for (AddressBean ab : addressBeans) {
			System.out.println("\n");
			System.out.println(ab.toString());
		}
		
		return addressBeans;

	}
	
	public  Address selectAddress( int id)
	{
		Address address = personAddressMapper.getPersonsForAddress(id);
		System.out.println("\npersonAddressMapper.getPersonsForAddress(2)\n");
		System.out.println(address);

		address = personAddressMapper.getAddressAndPersons(id);
		System.out.println("\npersonAddressMapper.getAddressAndPersons(2)\n");
		System.out.println(address);

		resultsSetMapper.selectAddress(id);
		System.out.println(address);

		address = resultsSetMapper.getAddress(id);
		System.out.println(address);
		
		return address;

	}

	public  List<Person> selectPerson( int id)
	{
		Person person = personAddressMapper.selectPerson(id);
		System.out.println("\npersonAddressMapper.selectPerson(3)\n");
		System.out.println(person);

		List<Person> persons = personAddressMapper.getPersons(id);			
		System.out.println(persons);
		
		persons = resultsSetMapper.callStoredProdForPerson(id);
		System.out.println(persons);

		persons = resultsSetMapper.getPersons(id);
		System.out.println(persons);

		persons = resultsSetMapper.getPersonByName("Franz");
		System.out.println(persons);
		
		return persons;

	}
	
	public  void CRUDPerson()
	{
		Person p = new Person(33, "Roberto", "Falcao", "999-557-9988", "roberto@falcao.com");
		p.setPerson_id(5);
		//resultsSetMapper.InsertPerson(p);
		resultsSetMapper.updatePerson(p);
		resultsSetMapper.deletePerson(9);
	
	}

	
	public  void selectPersonSql( String name)
	{
		Person p = resultsSetMapper.selectPersonSql1(name);
		System.out.println("\n Result of the complicated query : \n");
		System.out.println(p);
	
	}

}
