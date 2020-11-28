package stormbroken.hackathon2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.form.department.DeleteDepartmentForm;
import stormbroken.hackathon2020.form.department.DepartmentForm;
import stormbroken.hackathon2020.form.department.RenameDepartmentForm;
import stormbroken.hackathon2020.service.DepartmentService;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @PostMapping("/add")
    SimpleResponse add(@RequestBody DepartmentForm departmentForm){
        System.out.println("POST:/department/add:" + departmentForm.toString());
        return departmentService.add(departmentForm);
    }

    @GetMapping("/getAll")
    SimpleResponse getAll(@RequestParam String token, @RequestParam Integer organizationId){
        System.out.println("POST:/department/getAll?token=" + token + "&organizationId=" + organizationId);
        return departmentService.getAll(token, organizationId);
    }

    @PostMapping("/rename")
    SimpleResponse rename(@RequestBody RenameDepartmentForm renameDepartmentForm){
        System.out.println("POST:/department/rename:" + renameDepartmentForm.toString());
        return departmentService.rename(renameDepartmentForm);
    }

    @PostMapping("/delete")
    SimpleResponse delete(@RequestBody DeleteDepartmentForm deleteDepartmentForm){
        System.out.println("POST:/department/delete:" + deleteDepartmentForm.toString());
        return departmentService.delete(deleteDepartmentForm);
    }
}
