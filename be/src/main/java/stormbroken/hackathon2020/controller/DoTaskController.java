package stormbroken.hackathon2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.form.task.doTask.DoTaskCheckForm;
import stormbroken.hackathon2020.form.task.doTask.DoTaskAddForm;
import stormbroken.hackathon2020.form.task.doTask.DoTaskReceiveForm;
import stormbroken.hackathon2020.service.DoTaskService;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@RestController
@RequestMapping("/task/do")
public class DoTaskController {

    @Autowired
    DoTaskService doTaskService;

    @PostMapping("/add")
    public SimpleResponse add(@RequestBody DoTaskAddForm doTaskAddForm){
        System.out.println("POST:/task/do/add:" + doTaskAddForm.toString());
        // TODO 执行者不可以为管理者
        // TODO 必须为接受状态才有权限
        return doTaskService.add(doTaskAddForm);
    }

    @PostMapping("/receive")
    public SimpleResponse receive(@RequestBody DoTaskReceiveForm doTaskReceiveForm){
        System.out.println("POST:/task/do/receive:" + doTaskReceiveForm.toString());
        return doTaskService.receive(doTaskReceiveForm);
    }

    @PostMapping("/check")
    public SimpleResponse check(@RequestBody DoTaskCheckForm doTaskCheckForm){
        System.out.println("POST:/task/do/checkDo:" + doTaskCheckForm.toString());
        return doTaskService.check(doTaskCheckForm);
    }
}
