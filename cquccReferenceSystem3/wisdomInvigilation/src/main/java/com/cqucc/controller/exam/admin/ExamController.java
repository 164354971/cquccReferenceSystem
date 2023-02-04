package com.cqucc.controller.exam.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqucc.pojo.data.IntelligenceArrangeExam;
import com.cqucc.pojo.data.QueryData;
import com.cqucc.pojo.data.QueryExam;
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
import com.cqucc.utils.ObjectToList;
import com.cqucc.utils.R;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Reader;
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
    public R queryInfo(@RequestBody QueryInfo queryInfo) throws ParseException {
        //1.分析online值，看是否需要查询可用教室信息
        System.out.println(queryInfo.toString());
        //2.从 排课表 和 考试信息表 得到当前可用的教室列表
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date queryStartTime = sdf1.parse(queryInfo.getStartTime());
        Date queryEndTime = sdf1.parse(queryInfo.getEndTime());

        //3.需要查询可用教室
        List<Exam> examList = examService.list();
        List<AttendClass> attendClasses = attendClassService.list();
        List<Room> roomList = roomService.list();
        //如果是线下考试，就要搜索可用教室
        if(!queryInfo.getOnline()){
            //3.1 查当前时段各个教室有没有在考试的
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
            //3.2 查当前时段各个教室有没有在上课的
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
            //3.3 得到可用考场列表
            if(roomList.size() == 0)
                //线下没有空闲考场还考个屁
                return new R(true, "", "未获取到当前设置时段 " + queryInfo.getStartTime() + " 至 " + queryInfo.getEndTime() + " 的可用考场，请检查后重试");
        }

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
        QueryWrapper<Rebuild> rqw = new QueryWrapper<>();
        rqw.eq("college", queryInfo.getCollege()).eq("profession", queryInfo.getProfession()).eq("course", queryInfo.getCourse()).eq("deal", 0);
        rqw.lt("grade", queryInfo.getGrade());
        rebuildList = rebuildService.list(rqw);

        //6.监考老师
        //6.1 要求结课时间早于排考时间
        QueryWrapper<Teacher> tqw = new QueryWrapper<>();
        tqw.lt("close_time", queryInfo.getStartTime()).or().isNull("close_time");
        tqw.orderByAsc("exam_count");
        List<Teacher> teacherList = teacherService.list(tqw);

        //6.2 查询exam_teacher表，看看各个老师该时段是否有监考任务，有就排除那个监考老师
        List<ExamTeacher> examTeacherList = examTeacherService.list();
        for(ExamTeacher et : examTeacherList){
            //查询到监考老师对应的考试信息
            Exam e = examService.getById(et.getExamId());
            Date startTime = sdf1.parse(e.getStartTime());
            Date endTime = sdf1.parse(e.getEndTime());
            if(!(queryStartTime.after(endTime) || queryEndTime.before(startTime))) {
                for(Teacher t : teacherList){
                    if(Objects.equals(t.getUsername(), et.getTeacher())){
                        teacherList.remove(t);
                        break;
                    }
                }
            }
        }
        //6.3 最后对监考教师的本学期监考次数排序，次数少的优先排
        if(teacherList.size() == 0) return new R(true, "", "当前时间段 " + queryInfo.getStartTime() + " 至 " + queryInfo.getEndTime() + " " + queryInfo.getCollege() + " 无空闲监考教师！请重新设置新的时间段");

        //7.主考教师
        QueryWrapper<MajorTeacher> mqw = new QueryWrapper<>();
        mqw.eq("course", queryInfo.getCourse());
        MajorTeacher majorTeacher = majorTeacherService.getOne(mqw);

        return new R(true, new QueryData(roomList, classSumList, rebuildList, teacherList, majorTeacher), "数据已更新");
    }

    @RequestMapping("/intelligenceArrangeExam")
    public R intelligenceArrangeExam(@RequestBody QueryData queryData){

        List<Room> roomList = queryData.getRoomList();
        List<ClassSum> classSumList = queryData.getClassSumList();
        List<Rebuild> rebuildList = queryData.getRebuildList();
        List<Teacher> teacherList = queryData.getTeacherList();
        MajorTeacher majorTeacher = queryData.getMajorTeacher();
        System.out.println("roomList: " + roomList.toString());
        System.out.println("classSumList: " + classSumList.toString());
        System.out.println("rebuildList: " + rebuildList.toString());
        System.out.println("teacherList: " + teacherList.toString());
        if(majorTeacher != null)
            System.out.println("majorTeacher: " + majorTeacher.toString());
        /*
        1.如果是线下考试：

        //1.考场尽量排在一起,尽可能在一所教学楼考试
        //2.考场空间利用率最大化
        //3.优先对教师监考次数较少的排考

        * */
        List<String> locs = new ArrayList<>();
        List<Integer> rooms = new ArrayList<>();
        List<Integer> personNums = new ArrayList<>();
        List<List<Integer>> classSums = new ArrayList<>();
        List<List<String>> rebuilds = new ArrayList<>();
        List<List<String>> teachers = new ArrayList<>();
        if(roomList.size() != 0){
            //Collections.sort(classSums);
            int i = 0, j = 0, k = 0;
            for(Room room : roomList){//对每间教室尽可能坐满
                int num2 = room.getNum2();
                int sum = 0;

                List<Integer> cs = new ArrayList<>();
                for(; i < classSumList.size(); i++){
                    if(sum + classSumList.get(i).getSum() > num2){
                        break;
                    }
                    sum += classSumList.get(i).getSum();
                    cs.add(classSumList.get(i).getBanji());
                }

                List<String> rs = new ArrayList<>();
                for(; j < rebuildList.size(); j++){
                    if(sum + 1 > num2){
                        break;
                    }
                    sum += 1;
                    rs.add(rebuildList.get(j).getUsername());
                }

                //如果这间教室有人，则考场生效
                if(sum != 0){
                    locs.add(room.getLoc());
                    rooms.add(room.getRoom());
                    personNums.add(sum);
                    classSums.add(cs);
                    rebuilds.add(rs);

                    List<String> ts = new ArrayList<>();
                    if(teacherList.size() > k)
                        ts.add(teacherList.get(k++).getUsername());
                    if(teacherList.size() > k)
                        if(cs.size() >= 2 || sum >= 60){//如果当前考场所选班级大于等于2个 或 考场总人数超过60人
                            //则该考场增加一个监考教师
                            ts.add(teacherList.get(k++).getUsername());
                        }
                    teachers.add(ts);
                }
            }
            //如果教室用完了还没有排完人数
            if(i < classSumList.size() || j < rebuildList.size()){
                return new R(true, new R(true, new IntelligenceArrangeExam(locs, rooms, personNums, classSums, rebuilds, teachers)), "考场建议排考座位不足！请替换新的考试时段，或根据实际情况做出调整");
            }
        }else{
            int i = 0, j = 0, k = 0;
            int classAve = classSumList.size() / queryData.getRoomSum();
            int classRes = classSumList.size() % queryData.getRoomSum();
            for(int l = 0; l < queryData.getRoomSum(); l++){
                if(classAve >= 1){//每个考场至少能分到一个班的情况
                    int sum = 0;
                    List<Integer> cs = new ArrayList<>();
                    //将剩余班级全部放进第一个线上考场
                    for(int h = 0; h < classAve + classRes; h++, i++){
                        sum += classSumList.get(i).getSum();
                        cs.add(classSumList.get(i).getBanji());
                    }
                    classRes = 0;
                    List<String> rs = new ArrayList<>();
                    //将重修生全部放进第一个线上考场
                    for(; j < rebuildList.size(); j++){
                        sum += 1;
                        rs.add(rebuildList.get(j).getUsername());
                    }
                    if(sum != 0){
                        personNums.add(sum);
                        classSums.add(cs);
                        rebuilds.add(rs);

                        List<String> ts = new ArrayList<>();
                        if(teacherList.size() > k)
                            ts.add(teacherList.get(k++).getUsername());
                        if(teacherList.size() > k)
                            if(cs.size() >= 2 || sum >= 60){//如果当前考场所选班级大于等于2个 或 考场总人数超过60人
                                //则该考场增加一个监考教师
                                ts.add(teacherList.get(k++).getUsername());
                            }
                        teachers.add(ts);
                    }
                }
                else{//平均每个考场分不到一个班级
                    int sum = 0;
                    List<Integer> cs = new ArrayList<>();
                    if(classSumList.size() > i){
                        sum += classSumList.get(i).getSum();
                        cs.add(classSumList.get(i).getBanji());
                        i++;
                    }
                    List<String> rs = new ArrayList<>();
                    //将重修生全部放进第一个线上考场
                    for(; j < rebuildList.size(); j++){
                        sum += 1;
                        rs.add(rebuildList.get(j).getUsername());
                    }

                    personNums.add(sum);
                    classSums.add(cs);
                    rebuilds.add(rs);
                    List<String> ts = new ArrayList<>();
                    if(sum != 0){
                        if(teacherList.size() > k)
                            ts.add(teacherList.get(k++).getUsername());
                        if(teacherList.size() > k)
                            if(cs.size() >= 2 || sum >= 60){//如果当前考场所选班级大于等于2个 或 考场总人数超过60人
                                //则该考场增加一个监考教师
                                ts.add(teacherList.get(k++).getUsername());
                            }
                        teachers.add(ts);
                    }
                    else{
                        teachers.add(ts);
                    }
                }
            }
        }

        return new R(true, new R(true, new IntelligenceArrangeExam(locs, rooms, personNums, classSums, rebuilds, teachers)), "智能排考完成！请根据实际情况做出调整");
    }

    @RequestMapping("/submitExam")
    @PostMapping
    public R submitExam(@RequestBody Map<String, Object> params){
        System.out.println(params.toString());
        System.out.println(params.get("queryData").toString());
        System.out.println(JSON.toJSONString(params.get("queryData")));
        QueryInfo queryInfo = JSON.parseObject(JSON.toJSONString(params.get("queryInfo")), QueryInfo.class);
        System.out.println(queryInfo.toString());
        List<QueryExam> QueryExamList = new ArrayList<>(JSON.parseArray(JSON.toJSONString(params.get("queryData")),QueryExam.class));
        System.out.println(QueryExamList.toString());

        return new R();
    }
}
