package com.infy.tele.web.rest;
import com.infy.tele.domain.Employee;
import com.infy.tele.service.EmployeeService;
import com.infy.tele.web.rest.errors.BadRequestAlertException;
import com.infy.tele.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Employee.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*") public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "platformsevenEmployee";

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * POST  /employees : Create a new employee.
     *
     * @param employee the employee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employee, or with status 400 (Bad Request) if the employee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employee);
        if (employee.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Employee result = employeeService.save(employee);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employees : Updates an existing employee.
     *
     * @param employee the employee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employee,
     * or with status 400 (Bad Request) if the employee is not valid,
     * or with status 500 (Internal Server Error) if the employee couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employee);
        if (employee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Employee result = employeeService.save(employee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employees : get all the employees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employees in body
     */
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        log.debug("REST request to get all Employees");
        return employeeService.findAll();
    }

    /**
     * GET  /employees/:id : get the "id" employee.
     *
     * @param id the id of the employee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employee, or with status 404 (Not Found)
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        Optional<Employee> employee = employeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employee);
    }

    /**
     * DELETE  /employees/:id : delete the "id" employee.
     *
     * @param id the id of the employee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
