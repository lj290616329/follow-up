package com.tsingtec.follow;

import com.alibaba.fastjson.JSON;
import com.tsingtec.follow.entity.Examine;
import com.tsingtec.follow.entity.mini.Review;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.MaUserService;
import com.tsingtec.follow.service.mini.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FollowApplicationTests {

    @Autowired
    private MaUserService maUserService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private DoctorService doctorService;

    @Test
    void contextLoads() {
        Examine examine = new Examine();

        Review review = reviewService.findById(2);
        String json = "{\"cbc\":[\"http://gridpic.tsing-tec.com/e0331c1c-49e4-4efd-b98c-701371a4978e.png\",\"http://gridpic.tsing-tec.com/f6debe8d-9519-4e22-bdef-174f7d6b1b19.png\"],\"biochemistry\":[\"http://gridpic.tsing-tec.com/7ad006fe-66ae-4386-95bb-4007fe8d9314.png\",\"http://gridpic.tsing-tec.com/f3085581-04f4-48a4-9f84-cc72e78aa873.png\",\"http://gridpic.tsing-tec.com/cd2d30b9-95d7-4cb3-9894-b96261036548.png\"],\"dic\":[\"http://gridpic.tsing-tec.com/90635da4-60bb-4da1-873d-d8e392e500cb.png\"],\"swelling\":[\"http://gridpic.tsing-tec.com/fdad0baa-af72-4cf2-a430-2f3abf7b3fb8.png\"],\"bMode\":[\"http://gridpic.tsing-tec.com/a160ef3c-fc15-4c56-be6e-ca8839c5e36a.png\"],\"ct\":[\"http://gridpic.tsing-tec.com/5720d520-6869-41d3-81ed-e5e3d8d572f8.png\",\"http://gridpic.tsing-tec.com/065a3cf9-9037-4caa-a6ed-d67f916831c6.png\"],\"mri\":[\"http://gridpic.tsing-tec.com/80cae499-33ab-416a-9e6f-3140af7d53b8.png\"]}";
        examine = JSON.parseObject(json,Examine.class);
        review.setExamine(examine);
        reviewService.save(review);





        /*MaUser maUser = new MaUser();
        maUser.setOpenId("oMRsD5RgdyStDUDQMrC8KJ2uqYX0");
        maUser.setUnionId("ofkfww9S7ahbdtIaQyOpQMSrzHXc");
        maUserService.save(maUser);

        Information information = new Information();
        information.setName("李先生");
        information.setPhone("15073132755");
        informationService.save(information);
        maUser.setInformation(information);
        maUserService.save(maUser);*/
    }

}
