package stormbroken.hackathon2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.form.task.administerTask.AdministerAddForm;
import stormbroken.hackathon2020.form.task.administerTask.DoTaskCheckForm;
import stormbroken.hackathon2020.form.task.administerTask.AdministerTaskReceiveForm;
import stormbroken.hackathon2020.service.AdministerTaskService;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@RestController
@RequestMapping("/task/administer")
public class AdministerTaskController {

    @Autowired
    AdministerTaskService administerTaskService;

    @PostMapping("/add")
    public SimpleResponse add(@RequestBody AdministerAddForm administerAddForm){
        System.out.println("POST:/task/administer/add:" + administerAddForm.toString());
        return administerTaskService.add(administerAddForm);
    }

    @PostMapping("/receive")
    public SimpleResponse receive(@RequestBody AdministerTaskReceiveForm administerTaskReceiveForm){
        System.out.println("POST:/task/administer/receive:" + administerTaskReceiveForm.toString());
        return administerTaskService.receive(administerTaskReceiveForm);
    }

    @PostMapping("/checkDo")
    public SimpleResponse checkDo(@RequestBody DoTaskCheckForm doTaskCheckForm){
        System.out.println("POST:/task/administer/checkDo:" + doTaskCheckForm.toString());
        return administerTaskService.checkDo(doTaskCheckForm);
    }

}
