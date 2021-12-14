package com.tsingtec.follow.vo.req.disease;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author lj
 * @Date 2021/12/10 16:32
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class DiseaseEditReqVO {
    private Integer id;
    private String title;
    private Integer pid=0;
    private Boolean optional = false;
}
