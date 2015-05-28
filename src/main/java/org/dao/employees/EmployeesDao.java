package org.dao.employees;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.entity.employees.Employees;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class EmployeesDao {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(EmployeesDao.class);

	private EntityManager entityManager;

	// Getter & setter for EntityManager
	/**
	 * Get entityManager.
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Set entityManager.
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	public List<Employees> findAll() {
		return (List<Employees>) getEntityManager().createNamedQuery(
				"Employees.findAll").getResultList();
	}

	public Employees findByEmployeeId(String id) {
		try {
			return (Employees) getEntityManager()
					.createNamedQuery("Employees.findByEmployeeId")
					.setParameter("employeeId", id).getSingleResult();
		} catch (NoResultException ex) {
			LOGGER.error("findByEmployeeId: No matching record found! "
					+ ex.getMessage());
		} catch (Exception ex) {
			LOGGER.error("findByEmployeeId: " + ex.getMessage());
		}
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean createEmployees(Employees Employees) {
		try {
			if (Employees.getEmployeeId() == null) {
				LOGGER.error("createEmployees:: Record not saved - PrimaryKey found null or missing!");
			} else {
				getEntityManager().persist(Employees);
				LOGGER.trace("createEmployees:: Record saved.");
			}
		} catch (Exception ex) {
			LOGGER.error("save operation failed with error: " + ex.getMessage());
		}
		return true;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Employees updateEmployees(Employees employee) {

		Employees currentRecord = null;
		try {
			if (employee.getEmployeeId() != null) {
				currentRecord = (Employees) getEntityManager()
						.createNamedQuery("Employees.findByEmployeeId")
						.setParameter("employeeId", employee.getEmployeeId())
						.getSingleResult();
				if (currentRecord != null) {
					LOGGER.trace("EmployeesDao: updateEmployees:: Record updated.");
					return getEntityManager().merge(employee);
				} else {
					LOGGER.warn("EmployeesDao: updateEmployees:: Record not updated - no matching record found!");
				}
			}
		} catch (NoResultException ex) {
			LOGGER.warn("update called, but no match found!");
		} catch (Exception ex) {
			LOGGER.error("update operation failed with error: "
					+ ex.getMessage());
		}
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean deleteByEmployeeId(String id) {

		try {
			Employees employees = (Employees) getEntityManager()
					.createNamedQuery("Employees.findByEmployeeId")
					.setParameter("employeeId", id).getSingleResult();
			if (employees != null) {
				LOGGER.info("delete called for Orders using EmployeeId=" + id);
				getEntityManager().remove(employees);
				LOGGER.trace("EmployeesDao: deleteByEmployeeId:: Record deleted.");
				return true;
			} else {
				LOGGER.warn("EmployeesDao: deleteOrders:: Record not deleted - no matching record found!");
				return false;
			}
		} catch (NoResultException ex) {
			LOGGER.warn("delete called, but no match found for EmployeeId="
					+ id);
		} catch (Exception ex) {
			LOGGER.error("delete operation failed with error: "
					+ ex.getMessage());
		}
		return false;
	}
}