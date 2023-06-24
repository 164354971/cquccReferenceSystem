package com.cqucc.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqucc.pojo.user.Admin;
import com.cqucc.pojo.user.Student;
import com.cqucc.pojo.user.User;
import com.cqucc.service.user.AdminService;
import com.cqucc.service.user.StudentService;
import com.cqucc.utils.R;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/loginChecks")
public class LoginController {

    @Resource
    private StudentService studentService;
    @Resource
    private AdminService adminService;

    @RequestMapping("/test")
    public R testName(String name){
        //System.out.println("111");
        return new R(true, "蒋小玲");
    }

    @PostMapping
    public R loginCheck(@RequestBody User user){
        System.out.println(user.toString());
        String s = user.isEmptyUser();
        if(s != null){
            return new R(true, "", s);
        }
        switch (user.getType()){
            case 0:
                return new R(true, "", "登录类型错误！请联系管理员");
            case 1:
                LambdaQueryWrapper<Admin> adminLqw = new LambdaQueryWrapper<>();
                adminLqw.eq(Strings.isNotEmpty(user.getUsername()), Admin::getUsername,user.getUsername());
                adminLqw.eq(Strings.isNotEmpty(user.getPassword()), Admin::getPassword,user.getPassword());
                Admin admin = adminService.getOne(adminLqw);
                if(admin != null) return new R(true, admin, "登录成功！");
                else return new R(true, "", "账号或密码错误！请重试");
            /*case 2:
                LambdaQueryWrapper<Teacher> teacherLqw = new LambdaQueryWrapper<>();
                //lqw.like(Strings.isNotEmpty(book.getType()), Book::getType, book.getType());
                teacherLqw.eq(Strings.isNotEmpty(user.getUsername()), Student::getUsername,user.getUsername());
                teacherLqw.eq(Strings.isNotEmpty(user.getPassword()), Student::getPassword,user.getPassword());
                Student stu = studentService.getOne(teacherLqw);
                if(stu != null) return new R(true, stu, "登录成功！");
                else return new R(true, "", "账号或密码错误！请重试");*/
            case 3:
                LambdaQueryWrapper<Student> studentLqw = new LambdaQueryWrapper<>();
                studentLqw.eq(Strings.isNotEmpty(user.getUsername()), Student::getUsername,user.getUsername());
                studentLqw.eq(Strings.isNotEmpty(user.getPassword()), Student::getPassword,user.getPassword());
                Student stu = studentService.getOne(studentLqw);
                if(stu != null) return new R(true, stu, "登录成功！");
                else return new R(true, "", "账号或密码错误！请重试");
        }
        return new R(false, "蒋小玲", "未知错误");
    }

    @RequestMapping("/test1")
    public List<R> func(String params) {
        // 将JSON字符串转为Java列表
        System.out.println(params);
        List<R> rs = JSONObject.parseArray(params, R.class);
        for (R r : rs) {
            System.out.println(r.toString());
        }
        return rs;
    }
}
