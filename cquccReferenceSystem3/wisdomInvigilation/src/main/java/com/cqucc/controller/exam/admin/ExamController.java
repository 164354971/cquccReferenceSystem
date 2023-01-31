package com.cqucc.controller.exam.admin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqucc.pojo.data.QueryData;
import com.cqucc.pojo.exam.*;
import com.cqucc.pojo.exam.admin.*;
import com.cqucc.pojo.data.QueryInfo;
import com.cqucc.pojo.exam.teacher.ExamTeacher;
import com.cqucc.pojo.historyExam.HistoryExam;
import com.cqucc.pojo.user.Teacher;
import com.cqucc.service.exam.*;
import com.cqucc.service.exam.admin.*;
import com.cqucc.service.exam.teacher.ExamTeacherService;
import com.cqucc.service.historyExam.HistoryExamService;
import com.cqucc.service.user.TeacherService;
import com.cqucc.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Resource
    private TermService termService;
    @Resource
    private CollegeService collegeService;
    @Resource
    private ProfessionService professionService;
    @Resource
    private CourseService courseService;
    @Resource
    private ExamService examService;
    @Resource
    private HistoryExamService historyExamService;
    @Resource
    private AttendClassService attendClassService;
    @Resource
    private RoomService roomService;
    @Resource
    private ClassSumService classSumService;
    @Resource
    private RebuildService rebuildService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private ExamTeacherService examTeacherService;
    @Resource
    private MajorTeacherService majorTeacherService;

    @RequestMapping("/termList")
    public R getTermList(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(date));
        QueryWrapper<Term> qw = new QueryWrapper<>();
        qw.ge("start_time", dateFormat.format(date));
        List<Term> termList = termService.list(qw);
        if(termList.size() == 0){
            return new R(true, "", "未获取到学期信息");
        }else{
            return new R(true, termList);
        }
    }

    @RequestMapping(value = "/collegeList", method = RequestMethod.POST)
    @PostMapping
    public R getCollegeList(@RequestBody Term term){
        System.out.println(term.toString());
        QueryWrapper<College> qw = new QueryWrapper<>();
        qw.lt("found_time", term.getStartTime());
        List<College> collegeList = collegeService.list(qw);
        if(collegeList.size() == 0){
            return new R(true, "", "未获取到学院信息");
        }else{
            return new R(true, collegeList);
        }
    }

    @RequestMapping( "/professionList")
    @PostMapping
    public R getProfessionList(@RequestBody Map<String, Object> datas){
        System.out.println(datas.toString());
        College college = JSON.parseObject(JSON.toJSONString(datas.get("college")), College.class);
        Term term = JSON.parseObject(JSON.toJSONString(datas.get("term")), Term.class);
        System.out.println(college.toString());
        System.out.println(term.toString());
        //找
        QueryWrapper<Profession> qw = new QueryWrapper<>();
        //首先学院要相同
        qw.eq("college_id", college.getId());
        List<Profession> professionList = professionService.list(qw);
        if(professionList.size() == 0){
            return new R(true, "", "未获取到专业信息");
        }else{
            //其次学期创建时间-该专业年级创建时间年份大于年份制
            professionList.removeIf(p -> Integer.parseInt(term.getStartTime().substring(0, 4)) - Integer.parseInt(p.getFoundTime().substring(0, 4)) > p.getYear());
            if(professionList.size() == 0){
                return new R(true, "", "未获取到专业信息");
            }
            return new R(true, professionList);
        }
    }

    @RequestMapping( "/courseList")
    @PostMapping
    public R getCourseList(@RequestBody Map<String, Object> datas){
        System.out.println(datas.toString());
        College college = JSON.parseObject(JSON.toJSONString(datas.get("college")), College.class);
        Term term = JSON.parseObject(JSON.toJSONString(datas.get("term")), Term.class);
        Profession profession = JSON.parseObject(JSON.toJSONString(datas.get("profession")), Profession.class);
        System.out.println(college.toString());
        System.out.println(term.toString());
        System.out.println(profession.toString());
        //找
        QueryWrapper<Course> qw = new QueryWrapper<>();
        //匹配学期、学院、专业、年级，得到课程
        //先学院、专业、年级
        qw.eq("college", college.getCollege())
                .eq("profession", profession.getProfession())
                .eq("grade", profession.getGrade());

        List<Course> courseList = courseService.list(qw);
        if(courseList.size() == 0){
            return new R(true, "", "未获取到课程信息");
        }else{
            //然后查找学期,所选学期需要在课程开始学期与结束学期之间
            for (int i = 0; i < courseList.size(); i++) {
                if(Integer.parseInt(term.getTerm().substring(5,9)) <= Integer.parseInt(courseList.get(i).getTermEnd().substring(5,9))
                    && Integer.parseInt(term.getTerm().substring(0,4)) >= Integer.parseInt(courseList.get(i).getTermStart().substring(0,4))){
                    if(Integer.parseInt(term.getTerm().substring(5,9)) == Integer.parseInt(courseList.get(i).getTermEnd().substring(5,9))){
                        if(Integer.parseInt(term.getTerm().substring(0,4)) == Integer.parseInt(courseList.get(i).getTermStart().substring(0,4))
                                && Integer.parseInt(term.getTerm().substring(10,11)) <= Integer.parseInt(courseList.get(i).getTermEnd().substring(10,11))
                                && Integer.parseInt(term.getTerm().substring(10,11)) >= Integer.parseInt(courseList.get(i).getTermStart().substring(10,11))){
                            continue;
                        }
                        else{
                            courseList.remove(i--);
                        }
                    }else{
                        continue;
                    }
                }else{
                    courseList.remove(i--);
                }
            }
            if(courseList.size() == 0){
                return new R(true, "", "未获取到课程信息");
            }
            return new R(true, courseList);
        }
    }

    @RequestMapping("/queryInfo")
    public R QueryInfo(@RequestBody QueryInfo queryInfo) throws ParseException {
        //1.分析online值，看是否需要查询可用教室信息
        System.out.println(queryInfo.toString());
        //2.从 排课表 和 考试信息表 得到当前可用的教室列表
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date queryStartTime = sdf1.parse(queryInfo.getStartTime());
        Date queryEndTime = sdf1.parse(queryInfo.getEndTime());
        if(queryInfo.getOnline()){//线上考试
            List<Exam> examList = examService.list();
            //4.接下来获取考试的学生班级（注意此时间段内学生有无其他考试）

            //4.1 查exam表中是否安排了本学期该专业年级这项考试
            QueryWrapper<Exam> eqw = new QueryWrapper<>();
            eqw.eq("term", queryInfo.getTerm()).eq("college", queryInfo.getCollege());
            eqw.eq("profession", queryInfo.getProfession()).eq("grade", queryInfo.getGrade());
            eqw.eq("course", queryInfo.getCourse());
            List<Exam> examList1 = examService.list(eqw);
            if(examList1.size() > 0) return new R(true, "", "当前设置的学期所安排的考试课程 " + queryInfo.getCourse() + " 已存在！");

            //4.2 查history_exam表中是否安排过本学期该专业年级这项考试
            QueryWrapper<HistoryExam> heqw = new QueryWrapper<>();
            heqw.eq("term", queryInfo.getTerm()).eq("college", queryInfo.getCollege());
            heqw.eq("profession", queryInfo.getProfession()).eq("grade", queryInfo.getGrade());
            heqw.eq("course", queryInfo.getCourse());
            List<HistoryExam> historyExamList1 = historyExamService.list(heqw);
            if(historyExamList1.size() > 0) return new R(true, "", "当前设置的学期所安排的考试课程 " + queryInfo.getCourse() + " 已存在！该考试课程已结束");

            //4.3 查排考时段是否与待考学生时间冲突
            for (Exam e : examList){
                Date startTime = sdf1.parse(e.getStartTime());
                Date endTime = sdf1.parse(e.getEndTime());
                //如果当前时段发生重叠，且正好是待考的学院年级专业
                if(!(queryStartTime.after(endTime) || queryEndTime.before(startTime))
                        && Objects.equals(queryInfo.getTerm(), e.getTerm())
                        && Objects.equals(queryInfo.getCollege(), e.getCollege())
                        && Objects.equals(queryInfo.getProfession(), e.getProfession())
                        && Objects.equals(queryInfo.getGrade(), e.getGrade())) {
                    return new R(true, "",  e.getCollege() + e.getGrade() + "级" + e.getProfession() + "专业学生的考试课程: " + e.getCourse() +  " 的考试时间冲突，请调整考试时间段至另一时段");
                }
            }

            //4.4 都符合条件了就把该学院年级专业的各个班级都调出来
            QueryWrapper<ClassSum> cqw = new QueryWrapper<>();
            cqw.eq("college", queryInfo.getCollege()).eq("profession", queryInfo.getProfession()).eq("grade", queryInfo.getGrade());
            List<ClassSum> classSumList = classSumService.list(cqw);

            //5.重修生（deal为false表示未排考）
            List<Rebuild> rebuildList = new ArrayList<>();
            if(queryInfo.getRebuild()){
                QueryWrapper<Rebuild> rqw = new QueryWrapper<>();
                rqw.eq("college", queryInfo.getCollege()).eq("profession", queryInfo.getProfession()).eq("deal", 0);
                rebuildList = rebuildService.list(rqw);
            }

            //6.监考老师
            //6.1 要求结课时间早于排考时间
            QueryWrapper<Teacher> tqw = new QueryWrapper<>();
            tqw.lt("close_time", queryInfo.getStartTime());

            //6.2 查询exam_teacher表，看看各个老师该时段是否有监考任务，有就排除那个监考老师
            List<ExamTeacher> examTeacherList = examTeacherService.list();
            for(ExamTeacher et : examTeacherList){
                //查询到监考老师对应的考试信息
                Exam e = examService.getById(et.getExamId());
                Date startTime = sdf1.parse(e.getStartTime());
                Date endTime = sdf1.parse(e.getEndTime());
                if(!(queryStartTime.after(endTime) || queryEndTime.before(startTime))) {
                    examTeacherList.remove(et);
                }
            }
            //6.3 最后对监考教师的本学期监考次数排序，次数少的优先排
            tqw.orderByAsc("exam_count");
            List<Teacher> teacherList = teacherService.list(tqw);
            if(teacherList.size() == 0) return new R(true, "", "当前时间段 " + queryInfo.getStartTime() + " 至 " + queryInfo.getEndTime() + " " + queryInfo.getCollege() + " 无空闲监考教师！请重新设置新的时间段");

            //7.主考教师
            QueryWrapper<MajorTeacher> mqw = new QueryWrapper<>();
            mqw.eq("course", queryInfo.getCourse());
            MajorTeacher majorTeacher = majorTeacherService.getOne(mqw);

            return new R(true, new QueryData(null, classSumList, rebuildList, teacherList, majorTeacher), "数据已更新");
        }
        else{//不是线上，需要查询可用教室
            List<Exam> examList = examService.list();
            List<AttendClass> attendClasses = attendClassService.list();
            List<Room> roomList = roomService.list();

            for (Exam e : examList){
                Date startTime = sdf1.parse(e.getStartTime());
                Date endTime = sdf1.parse(e.getEndTime());
                if(queryStartTime.after(endTime) || queryEndTime.before(startTime)) {
                    continue;
                }else{
                    for (int i = 0; i < roomList.size(); i++) {
                        if (Objects.equals(roomList.get(i).getLoc(), e.getLoc()) && Objects.equals(roomList.get(i).getRoom(), e.getRoom())){
                            roomList.remove(i);
                            break;
                        }
                    }
                }
            }
            for (AttendClass e : attendClasses){
                Date startTime = sdf1.parse(e.getStartTime());
                Date endTime = sdf1.parse(e.getEndTime());
                if(queryStartTime.after(endTime) || queryEndTime.before(startTime)) {
                    continue;
                }else{
                    for (int i = 0; i < roomList.size(); i++) {
                        if (Objects.equals(roomList.get(i).getLoc(), e.getLoc()) && Objects.equals(roomList.get(i).getRoom(), e.getRoom())){
                            roomList.remove(i);
                            break;
                        }
                    }
                }
            }
            //3.得到可用考场列表
            if(roomList.size() == 0)
                //没有空闲考场还考个屁
                return new R(true, "", "未获取到当前设置时段 " + queryInfo.getStartTime() + " 至 " + queryInfo.getEndTime() + " 的可用考场，请检查后重试");


            //4.接下来获取考试的学生班级（注意此时间段内学生有无其他考试）

            //4.1 查exam表中是否安排了本学期该专业年级这项考试
            QueryWrapper<Exam> eqw = new QueryWrapper<>();
            eqw.eq("term", queryInfo.getTerm()).eq("college", queryInfo.getCollege());
            eqw.eq("profession", queryInfo.getProfession()).eq("grade", queryInfo.getGrade());
            eqw.eq("course", queryInfo.getCourse());
            List<Exam> examList1 = examService.list(eqw);
            if(examList1.size() > 0) return new R(true, "", "当前设置的学期所安排的考试课程 " + queryInfo.getCourse() + " 已存在！");

            //4.2 查history_exam表中是否安排过本学期该专业年级这项考试
            QueryWrapper<HistoryExam> heqw = new QueryWrapper<>();
            heqw.eq("term", queryInfo.getTerm()).eq("college", queryInfo.getCollege());
            heqw.eq("profession", queryInfo.getProfession()).eq("grade", queryInfo.getGrade());
            heqw.eq("course", queryInfo.getCourse());
            List<HistoryExam> historyExamList1 = historyExamService.list(heqw);
            if(historyExamList1.size() > 0) return new R(true, "", "当前设置的学期所安排的考试课程 " + queryInfo.getCourse() + " 已存在！该考试课程已结束");

            //4.3 查排考时段是否与待考学生时间冲突
            for (Exam e : examList){
                Date startTime = sdf1.parse(e.getStartTime());
                Date endTime = sdf1.parse(e.getEndTime());
                //如果当前时段发生重叠，且正好是待考的学院年级专业
                if(!(queryStartTime.after(endTime) || queryEndTime.before(startTime))
                        && Objects.equals(queryInfo.getTerm(), e.getTerm())
                        && Objects.equals(queryInfo.getCollege(), e.getCollege())
                        && Objects.equals(queryInfo.getProfession(), e.getProfession())
                        && Objects.equals(queryInfo.getGrade(), e.getGrade())) {
                    return new R(true, "",  e.getCollege() + e.getGrade() + "级" + e.getProfession() + "专业学生的考试课程: " + e.getCourse() +  " 的考试时间冲突，请调整考试时间段至另一时段");
                }
            }

            //4.4 都符合条件了就把该学院年级专业的各个班级都调出来
            QueryWrapper<ClassSum> cqw = new QueryWrapper<>();
            cqw.eq("college", queryInfo.getCollege()).eq("profession", queryInfo.getProfession()).eq("grade", queryInfo.getGrade());
            List<ClassSum> classSumList = classSumService.list(cqw);

            //5.重修生（deal为false表示未排考）
            List<Rebuild> rebuildList = new ArrayList<>();
            if(queryInfo.getRebuild()){
                QueryWrapper<Rebuild> rqw = new QueryWrapper<>();
                rqw.eq("college", queryInfo.getCollege()).eq("profession", queryInfo.getProfession()).eq("deal", 0);
                rebuildList = rebuildService.list(rqw);
            }

            //6.监考老师
            //6.1 要求结课时间早于排考时间
            QueryWrapper<Teacher> tqw = new QueryWrapper<>();
            tqw.lt("close_time", queryInfo.getStartTime());

            //6.2 查询exam_teacher表，看看各个老师该时段是否有监考任务，有就排除那个监考老师
            List<ExamTeacher> examTeacherList = examTeacherService.list();
            for(ExamTeacher et : examTeacherList){
                //查询到监考老师对应的考试信息
                Exam e = examService.getById(et.getExamId());
                Date startTime = sdf1.parse(e.getStartTime());
                Date endTime = sdf1.parse(e.getEndTime());
                if(!(queryStartTime.after(endTime) || queryEndTime.before(startTime))) {
                    examTeacherList.remove(et);
                }
            }
            //6.3 最后对监考教师的本学期监考次数排序，次数少的优先排
            tqw.orderByAsc("exam_count");
            List<Teacher> teacherList = teacherService.list(tqw);
            if(teacherList.size() == 0) return new R(true, "", "当前时间段 " + queryInfo.getStartTime() + " 至 " + queryInfo.getEndTime() + " " + queryInfo.getCollege() + " 无空闲监考教师！请重新设置新的时间段");

            //7.主考教师
            QueryWrapper<MajorTeacher> mqw = new QueryWrapper<>();
            mqw.eq("course", queryInfo.getCourse());
            MajorTeacher majorTeacher = majorTeacherService.getOne(mqw);

            return new R(true, new QueryData(roomList, classSumList, rebuildList, teacherList, majorTeacher), "数据已更新");

           /* long time = s1.getTime();
            long d2Time = s2.getTime();
            System.out.println("time = " + time);
            System.out.println("d2Time = " + d2Time);
            System.out.println(time==d2Time);*/
        }
    }
}
