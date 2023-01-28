package com.cqucc.controller.exam.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqucc.pojo.exam.*;
import com.cqucc.pojo.user.User;
import com.cqucc.service.exam.*;
import com.cqucc.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private GradeService gradeService;
    @Resource
    private CourseService courseService;

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

}
