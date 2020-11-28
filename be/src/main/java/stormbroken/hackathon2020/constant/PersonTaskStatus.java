package stormbroken.hackathon2020.constant;

/**
 * @Author stormbroken
 * Create by 2020/11/26
 * @Version 1.0
 **/

public enum PersonTaskStatus {
    NOT_RECEIVE("任务未确认"),
    EXECUTING("任务执行"),
    REFUSED("任务拒绝"),
    CHECKING("任务检查中"),
    CANCELED("任务取消"),
    REFUSE_CHECK("任务被退回"),
    FINISHED("任务完成");

    private final String describe;

    private PersonTaskStatus(String describe) {
        this.describe = describe;
    }


    @Override
    public String toString() {
        return this.describe;
    }
}
