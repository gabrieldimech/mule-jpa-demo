package org.entity.employees;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 *
 * @author
 */
@Entity
@Table(name = "EMPLOYEES")
@JsonPropertyOrder({ "employeeId", "firstName", "lastName", "email",
		"phoneNumber", "hireDate", "departmentName" })
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e"),
		@NamedQuery(name = "Employees.findByEmployeeId", query = "SELECT e FROM Employees e WHERE e.employeeId = :employeeId") })
public class Employees implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Basic(optional = false)
	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "HIRE_DATE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hireDate;

	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;

	public Employees() {
	}

	public Employees(String employeeId) {
		this.employeeId = employeeId;
	}

	public Employees(String employeeId, String lastName) {
		this.employeeId = employeeId;
		this.lastName = lastName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (employeeId != null ? employeeId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Employees)) {
			return false;
		}
		Employees other = (Employees) object;
		if ((this.employeeId == null && other.employeeId != null)
				|| (this.employeeId != null && !this.employeeId
						.equals(other.employeeId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.appnovation.rest.entity.Employees[ employeeId="
				+ employeeId + ", firstName: " + firstName + ", lastName: "
				+ lastName + ", email: " + email + ", phoneNumber: "
				+ phoneNumber + ", hireDate: " + hireDate + ", departmentName:"
				+ departmentName + "]";
	}
}
