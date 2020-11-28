package stormbroken.hackathon2020.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stormbroken.hackathon2020.dao.StatusLogMapper;
import stormbroken.hackathon2020.entity.StatusLog;
import stormbroken.hackathon2020.form.statuslog.StatusLogForm;
import stormbroken.hackathon2020.service.StatusLogService;

/**
 * @Author stormbroken
 * Create by 2020/11/24
 * @Version 1.0
 **/

@Service
public class StatusLogServiceImpl implements StatusLogService {
    private static String NOT_LOGIN = "用户没有登录";
    private static String DATA_BASE = "Database Exception at StatusLog:";

    @Autowired
    StatusLogMapper statusLogMapper;

    @Override
    public boolean add(StatusLogForm statusLogForm) {
        StatusLog statusLog = new StatusLog(statusLogForm);
        try{
            statusLogMapper.insert(statusLog);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
