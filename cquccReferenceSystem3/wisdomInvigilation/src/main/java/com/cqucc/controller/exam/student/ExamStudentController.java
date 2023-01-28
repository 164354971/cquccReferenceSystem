package com.cqucc.controller.exam.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqucc.pojo.exam.student.ExamStudent;
import com.cqucc.service.exam.student.ExamStudentService;
import com.cqucc.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/examStudent")
public class ExamStudentController {

    @Resource
    private ExamStudentService examStudentService;

    @PostMapping
    public R doExamList(@RequestBody String username){
        username = username.substring(0, username.length() - 1); //去掉末尾的=
        //System.out.println(username);
        QueryWrapper<ExamStudent> qw = new QueryWrapper<>();
        qw.eq("username", username);
        List<ExamStudent> examStudents = examStudentService.list(qw);
        return new R(true, examStudents, "");
    }

}
