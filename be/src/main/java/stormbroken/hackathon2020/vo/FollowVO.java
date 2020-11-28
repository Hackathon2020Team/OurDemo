package stormbroken.hackathon2020.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author stormbroken
 * Create by 2020/11/25
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowVO {
    private UserPartVO userPartVO;
    private List<FollowVO> followVOS;
}
