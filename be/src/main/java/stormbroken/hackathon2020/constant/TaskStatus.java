package stormbroken.hackathon2020.constant;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

public enum TaskStatus {
    RELEASE("任务发布"),
    EXECUTING("任务执行"),
    CANCELED("任务取消"),
    FINISHED("任务完成");

    private final String describe;

    private TaskStatus(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return this.describe;
    }
}
