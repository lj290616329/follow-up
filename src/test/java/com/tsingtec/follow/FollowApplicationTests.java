package com.tsingtec.follow;

import com.tsingtec.follow.entity.mini.Disease;
import com.tsingtec.follow.service.mini.*;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.vo.req.disease.DiseaseEditReqVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class FollowApplicationTests {

    @Autowired
    private MaUserService maUserService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private ReviewPlanService reviewPlanService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private DiseaseService diseaseService;


    @Test
    void contextLoads() {
        DiseaseEditReqVO diseaseEditReqVO = new DiseaseEditReqVO(null,"其他",0,true);
        Disease disease = BeanMapper.map(diseaseEditReqVO,Disease.class);
        diseaseService.save(disease);
    }

}
