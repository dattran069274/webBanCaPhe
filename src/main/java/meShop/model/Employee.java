package meShop.model;

public class Employee {

	private Integer id;
	private String name;
	private String Designation;
	private String company;
	 // generate getter setter and toString()
	public Integer getId() {
		return id;
	}
	
	public Employee() {
	}
	public Employee(Integer id, String name, String designation, String company) {
		super();
		this.id = id;
		this.name = name;
		Designation = designation;
		this.company = company;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDesignation() {
		return Designation;
	}


	public void setDesignation(String designation) {
		Designation = designation;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", Designation=" + Designation + ", company=" + company
				+ "]";
	}
	
	}
