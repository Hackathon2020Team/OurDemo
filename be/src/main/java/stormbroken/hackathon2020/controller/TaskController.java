package stormbroken.hackathon2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stormbroken.hackathon2020.constant.SimpleResponse;
import stormbroken.hackathon2020.form.task.TaskCancelForm;
import stormbroken.hackathon2020.form.task.TaskFinishForm;
import stormbroken.hackathon2020.form.task.TaskForm;
import stormbroken.hackathon2020.service.TaskService;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/add")
    public SimpleResponse add(@RequestBody TaskForm taskForm){
        System.out.println("POST:/task/add:" + taskForm.toString());
        return taskService.add(taskForm);
    }

    @PostMapping("/cancel")
    public SimpleResponse cancel(@RequestBody TaskCancelForm taskCancelForm){
        System.out.println("POST:/task/cancel:" + taskCancelForm.toString());
        return taskService.cancel(taskCancelForm);
    }

    @PostMapping("/finish")
    public SimpleResponse finish(@RequestBody TaskFinishForm taskFinishForm){
        System.out.println("POST:/task/finish:" + taskFinishForm.toString());
        return taskService.finish(taskFinishForm);
    }


    @GetMapping("/my")
    public SimpleResponse my(@RequestParam String token,
                             @RequestParam(required = false) String status){
        System.out.println("GET:/task/my?token="+token + "&status="+status);
        return taskService.my(token, status);
    }

    @GetMapping("/myDo")
    public SimpleResponse myDo(@RequestParam String token,
                               @RequestParam(required = false) String status){
        System.out.println("GET:/task/myDo?token=" + token + "&status=" +status);
        return taskService.myDo(token, status);
    }

    @GetMapping("/subtask")
    public SimpleResponse subtask(@RequestParam String token,
                                  @RequestParam Integer taskId){
        System.out.println("GET:/task/subtask?token"+token + "&taskId=" + taskId);
        return taskService.subtask(token, taskId);
    }

}
