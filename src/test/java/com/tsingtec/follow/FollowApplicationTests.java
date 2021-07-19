package com.tsingtec.follow;

import com.google.common.collect.Lists;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.MaUserService;
import com.tsingtec.follow.service.mini.ReviewService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

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

        String[] paths ={
                "https://i.ydds.cc/forum/201911/27/023710lqyqk2yzmv77nq7k.jpg","https://i.ydds.cc/forum/201911/27/023607w9s98c7soo777r87.jpg","https://i.ydds.cc/forum/201911/27/023609a1722zq1hiqh1eos.jpg","https://i.ydds.cc/forum/201911/27/023610nbmexm41hlcc1ep1.jpg","https://i.ydds.cc/forum/201911/27/023610p2b17i7m7ao6646i.jpg","https://i.ydds.cc/forum/201911/27/023611jata6apvt7apq23v.jpg","https://i.ydds.cc/forum/201911/27/023613f799xk28l9wp9s9x.jpg","https://i.ydds.cc/forum/201911/27/023614q4fdxbqqzbadfzq5.jpg","https://i.ydds.cc/forum/201911/27/023617waf6y0sywswn100l.jpg","https://i.ydds.cc/forum/201911/27/023621fqfov35u3e3orb3v.jpg","https://i.ydds.cc/forum/201911/27/023624ycqoccrxrc8f8zxq.jpg","https://i.ydds.cc/forum/201911/27/023627paipaxag4fszti2f.jpg","https://i.ydds.cc/forum/201911/27/023629kgjee55sgel65eze.jpg","https://i.ydds.cc/forum/201911/27/023637eb4ilulbuibhtizi.jpg","https://i.ydds.cc/forum/201911/27/023642iv3xb5b433tqbxzz.jpg","https://i.ydds.cc/forum/201911/27/023647oehl1d6vkzgxuzd1.jpg","https://i.ydds.cc/forum/201911/27/023650gat1ixiq7i2to2gt.jpg","https://i.ydds.cc/forum/201911/27/023654riemnxg9tb7ey7dy.jpg","https://i.ydds.cc/forum/201911/27/023703qhrgewhhvcl43olg.jpg","https://i.ydds.cc/forum/201911/27/023706ziyx8274sz7s6ywf.jpg","https://i.ydds.cc/forum/201911/27/023708kr8g8tz0rflsgsqy.jpg","https://i.ydds.cc/forum/201911/27/023713cykzgllyoryk8q9d.jpg","https://i.ydds.cc/forum/201911/27/023716jk9guxwcxixzuolk.jpg","https://i.ydds.cc/forum/201911/27/023719i9qz23fe2wbt9wv9.jpg","https://i.ydds.cc/forum/201911/27/023723zutqk04knzu0j70e.jpg","https://i.ydds.cc/forum/201911/27/023726dmr6k5e54s9ggwkg.jpg","https://i.ydds.cc/forum/201911/27/023728mknnkji96u9d4i9i.jpg","https://i.ydds.cc/forum/201911/27/023731mhp575snrd5nh7gh.jpg","https://i.ydds.cc/forum/201911/27/023736nee7jtsspztypnm3.jpg","https://i.ydds.cc/forum/201911/27/023739ub0ybbrhh2sdw2j0.jpg","https://i.ydds.cc/forum/201911/27/023742t3va3w29vzl62giy.jpg","https://i.ydds.cc/forum/201911/27/023746bh8vtskrh2tsss8s.jpg","https://i.ydds.cc/forum/201911/27/023749daihsif02jhrf6ij.jpg","https://i.ydds.cc/forum/201911/27/023752w6djz30k3v030fff.jpg","https://i.ydds.cc/forum/201911/27/023754w099j6m6n90s69vo.jpg","https://i.ydds.cc/forum/201911/27/023757x116f2rqfo1okjkq.jpg","https://i.ydds.cc/forum/201911/27/023800my0nrmnl0rsmn396.jpg","https://i.ydds.cc/forum/201912/10/033314s65665569nxgrgh5.jpg","https://i.ydds.cc/forum/201912/10/033138ssbupsusjzpv3v66.jpg","https://i.ydds.cc/forum/201912/10/033141d7u2yeucyxu8ex3t.jpg","https://i.ydds.cc/forum/201912/10/033145eun79w0fhacd2lm2.jpg","https://i.ydds.cc/forum/201912/10/033147k16qi6qo7ioevgg8.jpg","https://i.ydds.cc/forum/201912/10/033151r11lmk3415v4k3mv.jpg","https://i.ydds.cc/forum/201912/10/033154nbyaz7zyzy5y6bh5.jpg","https://i.ydds.cc/forum/201912/10/033156ajwjv9vjc6ejj9v9.jpg","https://i.ydds.cc/forum/201912/10/033200dbsuzn0n9rzhc8ov.jpg","https://i.ydds.cc/forum/201912/10/033203fx6j8aon0kelnrbh.jpg","https://i.ydds.cc/forum/201912/10/033205ftl4slsnocqal0al.jpg","https://i.ydds.cc/forum/201912/10/033209yhyt1199t68zt1i5.jpg","https://i.ydds.cc/forum/201912/10/033211t1awtya11b8l5bnz.jpg","https://i.ydds.cc/forum/201912/10/033216h441e55eto4nsosv.jpg","https://i.ydds.cc/forum/201912/10/033219y6nbyjy0dmw4o6cy.jpg","https://i.ydds.cc/forum/201912/10/033221tkc5q5jf48rr5u4m.jpg","https://i.ydds.cc/forum/201912/10/033223yervsq3ekr5ce7ec.jpg","https://i.ydds.cc/forum/201912/10/033225b6jzje06fm7jj76j.jpg","https://i.ydds.cc/forum/201912/10/033229fxy1nxxxfnflf4ry.jpg","https://i.ydds.cc/forum/201912/10/033231eqlqaqfqxvqqxq2s.jpg","https://i.ydds.cc/forum/201912/10/033233jqg2d3lo2tzg8tzd.jpg","https://i.ydds.cc/forum/201912/10/033238eqf9fq32gy2pq2ww.jpg","https://i.ydds.cc/forum/201912/10/033240n9vsrg55b1sb7qqe.jpg","https://i.ydds.cc/forum/201912/10/033243czlvwfqizpilqqw5.jpg","https://i.ydds.cc/forum/201912/10/033246c0mls1xmmgx11x5l.jpg","https://i.ydds.cc/forum/201912/10/033248xuaamscaimrparfc.jpg","https://i.ydds.cc/forum/201912/10/033252nbxc1bne1enmbu1s.jpg","https://i.ydds.cc/forum/201912/10/033255kxe11h6xee1x8r88.jpg","https://i.ydds.cc/forum/201912/10/033257xel770u04lwlzn6u.jpg","https://i.ydds.cc/forum/201912/10/033301x648vykgpunb5iqq.jpg","https://i.ydds.cc/forum/201912/10/033304mc3ennc4nj02n0ak.jpg","https://i.ydds.cc/forum/201912/10/033307gqq1x8diriy8odyp.jpg","https://i.ydds.cc/forum/201912/10/033310xnaqi8qx2vihp41x.jpg","https://i.ydds.cc/forum/201912/10/033312b7pdhc47xk50xax5.jpg","https://i.ydds.cc/forum/201912/10/033317u18172bf2x9fz8n1.jpg","https://i.ydds.cc/forum/201912/10/033319i33fujpel3jpkyol.jpg","https://i.ydds.cc/forum/201912/10/033322r17p3k3023mk3213.jpg","https://i.ydds.cc/forum/201912/10/033324rkc36p56j67v0v6s.jpg","https://i.ydds.cc/forum/201912/10/033328z2nbc6dxsi40diks.jpg","https://i.ydds.cc/forum/201912/10/033330wjz4eb46z4hjhta8.jpg","https://i.ydds.cc/forum/201912/10/033334wjxwjwksfpzuub9f.jpg","https://i.ydds.cc/forum/201912/10/033337ar5c9giwn5m8gj8c.jpg"
        };
        for(String path:paths){
            try {
                InputStream inputStream = null;
                try{
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(path).openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
                    httpURLConnection.setRequestProperty("Accept-Encoding", "gzip");
                    httpURLConnection.setRequestProperty("Referer","no-referrer");
                    httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    httpURLConnection.setConnectTimeout(15000);
                    httpURLConnection.setReadTimeout(20000);
                    inputStream = httpURLConnection.getInputStream();
                }catch (IOException e){
                    e.printStackTrace();
                }
                String name = UUID.randomUUID().toString();
                String imageName =  "F:\\Users\\lj\\Pictures\\美图\\美尤网作品NO.13玉女无圣光套图\\"+name+".jpg";
                FileUtils.copyToFile(inputStream,new File(imageName));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        String json = "[[{\"title\":\"母乳喂养体位指导\",\"des\":\"母亲的体位母亲舒适而自然的哺乳姿势是保证婴儿正确含接的前提，无论何种姿势，都要保证母亲放松舒适。\"},{\"title\":\"孕、产妇康复操指导\",\"des\":\"产妇适当运动和做产后健身操，可促进机体复原，逐步恢复适宜体重，恢复盆底和腹部肌肉张力，减少尿失禁、盆腔脏器脱垂、下肢深静脉血栓等产后并发症。产妇适当运动还有利于预防远期糖尿病、心血管疾病、乳腺癌等慢性非传染性疾病的发生。\"},{\"title\":\"手挤奶方法、吸奶器的使用方法\",\"des\":\"帮助母亲建立信心，尽量减少疼痛和焦虑。帮助母亲对孩子有美好的想法和感情。给母亲以实际的帮助，帮助及建议她：单独一人或有一位支持她的好友陪伴她，安静地坐好。有些母亲在与给孩子挤奶的母亲相伴时，就容易挤奶成功。抱着孩子，尽可能进行皮肤与皮肤的接触，挤奶时可看着孩子。\"},{\"title\":\"乳房冷敷方法\",\"des\":\"生理性胀奶是母体为正常泌乳所要做的必然生理准备，是乳腺腺泡开始分泌成熟孔的阶段。这时，受泌乳素的影响，乳房充盈肿胀变大变硬，出现水肿、疼痛，甚至全维有短行的发热症状，一般体温不会超过39℃，产后3-4天出现，持续时间不超过48小时。\"},{\"title\":\"乳头凹陷喂养指导及矫正方法\",\"des\":\"乳头凹陷指乳头不能突出于乳晕表面而是向内凹陷。内陷程度因人而异，有的表现为无乳头颈，有的乳头全部埋在乳晕下。乳头埋在乳晕下多为先天发育不良导致，容易并发乳头乳晕炎症或乳腺炎\"},{\"title\":\"产后抑郁照护\",\"des\":\"分娩后，由于机体的疲惫可导致内分泌不稳定。此外，心理准备不充分，缺乏妊娠分娩和新生儿护理知识，过分担心难产和新生儿意外，对新生儿护理束手无策，增加了心理压力，易引起极度紧张，再加上严重睡眠不足，会影响产妇的情绪，严重的会出现产后抑郁症状。\"},{\"title\":\"腹直肌分离康复指导\",\"des\":\"在怀孕和分娩过程中，随着腹部的增大，大多数女性腹部肌肉中间都会出现一个空隙。这个空隙通常在产后4-8周后会逐渐关闭。对于一部分女性，在怀孕期间腹部肌肉有可能会被过度拉伸，导致产后无法自动修复，这种情况称作“腹直肌分离”。\"},{\"title\":\"新生儿盆浴及新生儿抚触\",\"des\":\"新生儿沐浴是通过温水洗浴，达到清洁皮肤和促进血液循环的目的，盆浴方法相对安全、实用。\"},{\"title\":\"冲调奶粉及人工喂奶\",\"des\":\"z定喂养计划根据不同目的新生儿的需求，制定人工喂养计划。\"},{\"title\":\"为新生儿穿脱衣服、换尿布及包裹\",\"des\":\"新生儿的衣物应柔软、宽松、舒适。尽量是纯棉质衣物。若用放置时间长的衣服应在新生儿出生前将衣物除菌清洗、晾晒。如给新生儿穿有带子的衣服，带子不可扎得过高、过紧，以防损伤腋下皮肤。\"},{\"title\":\"预防新生儿误吸及应急处理\",\"des\":\"新生儿生理结构特殊易呛奶，又由于神经系统发育不完善，吞咽、咳嗽等反射较弱，不能把呛入气道的奶汁咳出，导致气道机械性阻塞发生窒息缺氧，严重时导致新生儿猝死。争分夺秒的抢救是复苏的关键，每一项急救措施都要体现出急救护理的时效性，为进一步的脑复苏争取宝贵的时间。\"},{\"title\":\"婴幼儿智护训练\",\"des\":\"zhaiyao\"}],[{\"title\":\"用轮椅运送行动不便的老人\",\"des\":\"不能自主活动者，应根据其身体情况及躯体活动受限程度，选用合适的运送工具协助其活动。通常有轮椅运送法、平车运送法等，在运送过程中，照护师运用正确的人体力学原理，利于减轻操作疲劳，提高工作效率。\"},{\"title\":\"预防老年人跌倒与碰伤\",\"des\":\"老年人由于衰老，各组织功能退化，神经反射减弱，反应不灵敏，视力减退，记忆力下降，再加上老年人多病共存，易发生意外伤害。老年人常见的意外伤害有跌倒、误吸、烧烫伤、褥疮、猝死、走失等。\"},{\"title\":\"预防失智老年人走失\",\"des\":\"失智老年人因认知功能、定向力、导航寻路能力下降，经常漫无目的地徘徊，极易发生走失或迷路，最严重的是夜间游走，一旦走到不安全的环境，会造成不必要的伤害。\"},{\"title\":\"预防老年人误吸及处理\",\"des\":\"误吸是指进食或者非进食时在吞咽过程中有数量不一的液体或固体食物（甚至还可包括分泌物或血液等）进入声门以下的气道，而不是像通常一样全部食团随着吞咽动作顺利地进入食管。\"},{\"title\":\"协助不能自理老年人进食及更换衣服\",\"des\":\"协助进食的方法分为餐前准备、协助进餐，餐后的照护以及饮水照护四部分\"},{\"title\":\"为睡眠障碍的老年人布置睡眠环境、协助入睡\",\"des\":\"年人的身心健康和安全与其居住环境有密切关系。调整并改善环境中的不利因素，为老年人营造一个安全、舒适、整洁和便利的后住环境，对于促进老年人健康、提高老年人生活质量具有重要意义。\"},{\"title\":\"为老年人扣背排痰及雾化吸入\",\"des\":\"喷雾剂吸入法是通过口、鼻吸入喷雾剂气溶胶，达到解除支气管痉挛、减少黏膜水肿和液化支气管分泌物、促进炎症的控制和通气功能改善的方法，适用于急性支气管疾病的对症治疗。\"},{\"title\":\"为老年人滴耳药及协助口服药物\",\"des\":\"用药的目的不同，给药的途径也不同。常用的给药途径有：口服、舌下含服、吸入、皮肤黏膜用药、注射（皮内、皮下、肌内、静脉）以及插入法（直肠、阴道给药）等。\"},{\"title\":\"冷湿敷及热湿敷的应用\",\"des\":\"中药湿敷法古称“湖法”，是利用多种药物煎汤后将无菌纱布用药液浸透，敷于患处，通过药液的渗透及冷、热原理，达到通经活络、疏通腠理、清热解毒、消肿散结目的的一种外治方法。\"},{\"title\":\"老年人便秘处理\",\"des\":\"60岁以上老年人便秘的发生率在15%-30%，而且随着年龄的增长发生率逐年升高。便秘病程长，可长达20-30年。便秘导致排便用力过度，易诱发老年人心脑血管意外，危害极大。\"},{\"title\":\".老年人助行器使用\",\"des\":\"行动辅助用具种类繁多，老年人常用的种类包括轮椅、拐杖、助步器。\"}],[{\"title\":\"面肌、舌肌康复训练\",\"des\":\"zhaiyao\"},{\"title\":\"清洁更换造口袋\",\"des\":\"造口袋更换的目的是保持造口周围皮肤的清洁以及帮助患者掌握正确护理造口的方法\"},{\"title\":\"压疮预防\",\"des\":\"对压疮发生的高危人群进行压疮预防措施的干预，能有效预防该人群的压疮发生。主要的措施一方面要减轻局部的压力、剪切力和摩擦力，另一方面还需保持皮肤干燥、进行营养支持，加强对被照护者及家属的健康教育。\"},{\"title\":\"鼻饲喂养\",\"des\":\"鼻饲法是指将胃管经鼻腔插入胃内，从胃管内灌注流质食物、水分和药物，达到营养支持、治疗目的的一种方法。\"},{\"title\":\"口腔护理\",\"des\":\"对高热、禁食、鼻饲、手术后、昏迷、危重、口腔疾患及生活不能自理的被照护者进行口腔护理。一般每日2次，有特殊问题（如呕血、咯血、痰液堆积咽颊部等）时，应随时进行口腔照护。\"},{\"title\":\"体温脉搏呼吸血压测量\",\"des\":\"正确的测量方法能更准去的掌握患者的病情状况。\"},{\"title\":\"家用血糖仪监测手指血糖\",\"des\":\"家用血糖仪监测的血糖值，反映的是血糖波动趋势，合理的监测血糖能够帮助糖友了解饮食、运动和药物的规律，更好的进行血糖管理。\"},{\"title\":\"单人心肺复苏术\",\"des\":\"搏骤停一旦发生，如得不到即刻及时地抢救复苏，4～6分钟后会造成患者脑和其他人体重要器官组织的不可逆的损害，因此心搏骤停后的心肺复苏必须在现场立即进行。\"},{\"title\":\"穿脱隔离衣\",\"des\":\"隔离衣是用于保护照护师避免受到血液、体液和其他感染性物质污染，或用于保护被照护者的一种医疗防护用品。常用的隔离衣包括一次性隔离衣和布制隔离衣2种。\"},{\"title\":\"良肢位摆放及主动运动和被动运动\",\"des\":\"。脑卒中患者可出现认知、吞咽、言语、感觉、运动、心理、情感等方面的障碍，最常见的是运动功能障碍一—偏瘫，严重影响了患者的日常生活和身心健康。\"},{\"title\":\"呼吸功能锻炼及家庭氧气吸入\",\"des\":\"呼吸功能锻炼是肺部感染预防和护理中的重要措施。通过指导患者学会呼吸控制并运用有效呼吸模式，使吸气时胸腔扩大，呼气时胸腔缩小，将浅而快的呼吸改变为深而慢的有效呼吸。通过锻炼，能够有效促进膈肌、胸腔运动，提高通气量，改善呼吸功能。\"}],[{\"title\":\"呼吸系统：肺心病、哮喘\",\"des\":\"支气管哮喘简称哮喘，是由多种细胞（如嗜酸性粒细胞、肥大细胞、T淋巴细胞、中性粒细胞、平滑肌细胞、气道上皮细胞等）和细胞组分参与的管道慢性炎症性疾痛。\"},{\"title\":\"消化系统：消化性溃疡、肝硬化\",\"des\":\"肝硬化是一种由不同病因引起的慢性进行性、弥漫性肝病。其特点为广泛的肝细地变性坏死、纤维组织增生、正常肝小叶结构被破坏和假小叶形成，致使肝内循环失常、肝细胞营养障碍，导致肝脏逐渐变形、变硬而成为肝硬化。\"},{\"title\":\"心血管系统：高血压、冠心病\",\"des\":\"原发性高血压是最常见的心血管疾病，指病因未明、以体循环动脉血压升高为主要表现的临床综合征，通常简称为高血压。高血压是多种心、脑血管疾病的重要病因和危险因素。\"},{\"title\":\"神经系统：脑卒中、帕金森病\",\"des\":\"脑血管病又称脑卒中或中风，是危害人类生命与健康的常见病与多发病，具有“四高”的特点，即发病率高、致残率高、病死率高、复发率高，是中老年人致死和致残的主要疾病。\"},{\"title\":\"内分泌系统：糖尿病、甲状腺功能亢进\",\"des\":\"糖尿病是一组以高血糖为特征的代谢综合征，是由于体内胰岛素分泌缺陷或其生物学作用障碍而引起的糖类、蛋白质、脂肪和水电解质代谢失常，常并发全身微血管、大血管病变，并可导致心、脑、肾、神经、眼睛及足等器官的慢性功能损害。\"},{\"title\":\"泌尿系统：慢性肾炎、肾功能不全\",\"des\":\"慢性肾功能衰竭简称慢性肾衰，是由多种慢性疾病引起肾脏损害和进行性恶化的结果，使机体在排泄代谢废物和调节水、电解质、酸碱平衡等方面出现失常的临床综合征。慢性肾衰的终末期，人们往往又称为尿毒症。\"},{\"title\":\"骨骼系统：腰椎间盘突出、骨折\",\"des\":\"骨的完整性或连续性发生部分或完全中断即为骨折。\"},{\"title\":\"眼科：青光眼、白内障\",\"des\":\"白内障指晶状体浑浊。任何影响眼内环境的因素，如衰老、物理损伤、化学损伤、手术、肿瘤、炎症、药物（包括中毒）以及某些全身性代谢性或免疫性疾病，都可导致晶状体浑浊。此外，晶状体或眼球的发育异常以及某些先天性综合征，都可以导致晶状体的形成异常而致白内障。\"}]]";
//
//        JSONArray array = JSONArray.parseArray(json);
//
//        File file = new File("F:\\Users\\lj\\Desktop\\111");
//        File[] files = file.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            if (files[i].isFile()) {
//                String fileName = files[i].getName();
//                String filen = fileName.substring(0,fileName.lastIndexOf("."));
//                String[] name = filen.split("-");
//                Integer index = Integer.valueOf(name[0])-1;
//                Integer k = Integer.valueOf(name[1])-1;
//                JSONArray arr = array.getJSONArray(index);
//                JSONObject object = arr.getJSONObject(k);
//                if(name.length==2){
//                    object.put("url",fileName);
//                }
//                if(name.length==3){
//                    object.put("thumb",fileName);
//                }
//            }
//        }
//
//        System.out.println(array.toJSONString());





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
    private static void getFile(String path) {
        // get file list where the path has
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        List<String> fileNames = Lists.newArrayList();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                fileNames.add(array[i].getName());
            }
        }
    }

}
