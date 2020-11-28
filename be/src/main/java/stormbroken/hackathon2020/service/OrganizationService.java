package stormbroken.hackathon2020.service;

import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.entity.Organization;
import stormbroken.hackathon2020.form.organization.*;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

public interface OrganizationService {
    SimpleResponse add(OrganizationForm organizationForm);

    SimpleResponse addEmployee(EmployeeForm employeeForm);

    Integer findByAdministerId(Integer administerId);

    Organization findById(Integer organizationId);

    SimpleResponse higher(String token);

    SimpleResponse equal(String token);

    SimpleResponse allLower(String token);

    SimpleResponse lower(String token);

    SimpleResponse sameLevel(String token);

    SimpleResponse remove(RemoveOrgForm removeOrgForm);

    SimpleResponse delete(DeleteOrgForm deleteOrgForm);
}
