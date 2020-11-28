package stormbroken.hackathon2020.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stormbroken.hackathon2020.constant.TaskStatus;
import stormbroken.hackathon2020.entity.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/26
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskVO {
    private Integer taskId;
    private Integer superTaskId;
    private Integer organizationId;
    private Integer createUserId;
    private String name;
    private String details;
    private LocalDateTime createTime;
    private LocalDateTime deadLine;
    private LocalDateTime endTime;
    private TaskStatus status;

    private String realName;
    private String content;
    private String url;

    private String departmentName;
    private Integer departmentLevel;

    private List<DoTask> doTasks;
    private List<AdministerTask> administerTasks;

    public TaskVO(Task task, User user, Department department, List<DoTask> doTasks, List<AdministerTask> administerTasks){
        this.taskId = task.getTaskId();
        this.superTaskId = task.getSuperTaskId();
        this.organizationId = task.getOrganizationId();
        this.createUserId = task.getCreateUserId();
        this.name = task.getName();
        this.details = task.getDetails();
        this.createTime = task.getCreateTime();
        this.deadLine = task.getDeadLine();
        this.endTime = task.getEndTime();
        this.status = task.getStatus();

        this.realName = user.getRealName();
        this.content = user.getContent();
        this.url = user.getUrl();

        this.departmentName = department.getName();
        this.departmentLevel = department.getLevel();

        this.doTasks = doTasks;
        this.administerTasks = administerTasks;
    }
}
