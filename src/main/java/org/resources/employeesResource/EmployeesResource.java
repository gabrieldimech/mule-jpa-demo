package org.resources.employeesResource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.dao.employees.EmployeesDao;
import org.entity.employees.Employees;

/**
 * Operation for retrieving Employee details.
 * 
 */

@Path("/emp")
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
public class EmployeesResource {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(EmployeesResource.class);

	@Autowired
	private EmployeesDao employeesDao;

	/**
	 * Getters/Setters employeesDao.
	 */
	public EmployeesDao getEmployeesDao() {
		return employeesDao;
	}

	/**
	 * Sets employeesDao.
	 */
	public void setEmployeesDao(EmployeesDao employeesDao) {
		this.employeesDao = employeesDao;
	}

	@GET
	@Path("/all")
	public List<Employees> findAll() {
		return employeesDao.findAll();
	}

	/**
	 * Get Employee info details by Id
	 * 
	 * @param empId
	 *            employee Identifier
	 */
	@GET
	@Path("{id}")
	public Response findByEmployeeId(@PathParam("id") String id) {
		LOGGER.info("EmployeesResource findByEmployeeId Id= " + id);

		Employees employees = employeesDao.findByEmployeeId(id);
		if (employees != null) {
			return Response.status(Status.OK).entity(employees)
					.type(MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.NOT_FOUND)
					.entity("No record match found for Id: " + id)
					.type(MediaType.APPLICATION_JSON).build();
		}
	}

	/**
	 * Create Employee
	 * 
	 * @param Employees
	 *            employee
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Employees createEmployees(Employees employees) {
		employeesDao.createEmployees(employees);
		return employees;
	}

	/**
	 * Update Employee
	 * 
	 * @param Employees
	 *            employee
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Employees updateEmployees(@PathParam("id") String id,
			Employees employees) {
		return employeesDao.updateEmployees(employees);
	}

	/**
	 * Delete Employee
	 * 
	 * @param id
	 *            Employee Identifier
	 */
	@Path("{id}")
	@DELETE
	public Response deleteEmployees(@PathParam("id") String id) {

		Employees result = employeesDao.findByEmployeeId(id);
		if (result != null) {
			if (employeesDao.deleteByEmployeeId(id)) {
				return Response.status(Status.OK).entity("Record deleted.")
						.type(MediaType.APPLICATION_JSON).build();
			}
		}
		return Response.status(Status.NOT_FOUND)
				.entity("DELETE operation failed!")
				.type(MediaType.APPLICATION_JSON).build();
	}
}