package stormbroken.hackathon2020.service;

import stormbroken.hackathon2020.form.statuslog.StatusLogForm;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

public interface StatusLogService {
    boolean add(StatusLogForm statusLogForm);
}
