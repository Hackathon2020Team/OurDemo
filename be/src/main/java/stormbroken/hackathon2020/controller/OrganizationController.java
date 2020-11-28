package stormbroken.hackathon2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.form.organization.*;
import stormbroken.hackathon2020.service.OrganizationService;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;

    @PostMapping("/add")
    SimpleResponse add(@RequestBody OrganizationForm organizationForm){
        System.out.println("POST:/organization/add:" + organizationForm.toString());
        return organizationService.add(organizationForm);
    }

    @PostMapping("/addEmployee")
    SimpleResponse addEmployee(@RequestBody EmployeeForm employeeForm){
        System.out.println("POST:/organization/addEmployee:" + employeeForm.toString());
        return organizationService.addEmployee(employeeForm);
    }

    @PostMapping("/remove")
    SimpleResponse remove(@RequestBody RemoveOrgForm removeOrgForm){
        System.out.println("POST:/organization/remove:"+ removeOrgForm.toString());
        return organizationService.remove(removeOrgForm);
    }

    @PostMapping("/delete")
    SimpleResponse delete(@RequestBody DeleteOrgForm deleteOrgForm){
        System.out.println("POST:/organization/delete:" + deleteOrgForm.toString());
        return organizationService.delete(deleteOrgForm);
    }

    @GetMapping("/higher")
    SimpleResponse higher(@RequestParam String token){
        System.out.println("POST:/organization/higher?token=" + token);
        return organizationService.higher(token);
    }

    @GetMapping("/equal")
    SimpleResponse equal(@RequestParam String token){
        System.out.println("POST:/organization/equal?token=" + token);
        return organizationService.equal(token);
    }

    @GetMapping("/allLower")
    SimpleResponse allLower(@RequestParam String token){
        System.out.println("POST:/organization/allLower?token=" + token);
        return organizationService.allLower(token);
    }

    @GetMapping("/lower")
    SimpleResponse lower(@RequestParam String token){
        System.out.println("POST:/organization/lower?token=" + token);
        return organizationService.lower(token);
    }
}
