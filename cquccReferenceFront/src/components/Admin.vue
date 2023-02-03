<template>
    <div id="body" class="container">
        <div class="left-box">
            <img src="../images/城科考试通.png" alt="" width="250" style="margin: 4px 0">
            <ul>
                    <li class="item active" @click="clickList">
                        <i class="icon icon-file"></i>
                        发布考试任务
                    </li>
                    <li class="item" @click="clickList">
                        <i class="icon icon-album" ></i>
                        编辑人员信息
                    </li>
                    <li class="item" @click="clickList">
                        <i class="icon icon-like"></i>
                        编辑课程信息
                    </li>
                    <li class="item" @click="clickList">
                        <i class="icon icon-vault"></i>
                        修改考试信息
                    </li>
                    <li class="item" @click="clickList">
                        <i class="icon icon-favorite"></i>
                        线上考题审核
                    </li>
                    <hr>
                    <li class="item" @click="clickList">
                        <i class="icon icon-transfer"></i>
                        申请中心
                    </li>
                    <li class="item" @click="clickList">
                        <i class="icon icon-transfer"></i>
                        数据统计
                    </li>
                </ul>
            <div class="user-info">
                <img src="../images/jzz.jpg" alt="">
                <span>管理员：{{user.name}}</span>
            </div>
        </div>
        <div class="right-box">
            <div class="top">
                <span class="current-tag">发布考试任务</span>
                <div class="btn">
                    <i class="icon icon-search"></i>
                </div>
                <!-- 当前时间 -->
                <div class="showTime" style="position: relative; right: 60px;top: 2px"></div>
                <div class="btn" @click="">
                    <i class="icon icon-add"></i>
                </div>
            </div>
            <div class="rm" style="display: block">
                <div class="edit">
                    <div>
                        <span>学期：</span>
                        <el-select v-model="exam.term" placeholder="请选择"  style="width: 130px;" value-key="id"
                                   :disabled="submitFlag"> <!--@change="setCollegeList">-->
                            <el-option
                                    v-for="item in termList"
                                    :key="item.id"
                                    :label="item.term"
                                    :value="item"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div >
                        <span>学院：</span>
                        <el-select v-model="exam.college" placeholder="请选择"  value-key="id"
                                   :disabled="submitFlag"><!--@change="setProfessionList">-->
                            <el-option
                                    v-for="item in collegeList"
                                    :key="item.id"
                                    :label="item.college"
                                    :value="item"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div >
                        <span>专业：</span>
                        <el-select v-model="exam.profession" placeholder="请选择"  value-key="id"
                                   :disabled="submitFlag"><!--@change="setGradeList">-->
                            <el-option
                                    v-for="item in professionList"
                                    :key="item.id"
                                    :label="item.profession"
                                    :value="item"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div >
                        <span>年级：</span>
                        <el-select v-model="exam.grade" placeholder="请选择"  value-key="id" style="width: 110px;"
                                   :disabled="submitFlag">
                            <el-option
                                    v-for="item in gradeList"
                                    :key="item.id"
                                    :label="item.grade"
                                    :value="item"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div >
                        <span>考试课程：</span>
                        <el-select v-model="exam.course" placeholder="请选择"  value-key="id"
                                   :disabled="submitFlag">
                            <el-option
                                    v-for="item in courseList"
                                    :key="item.id"
                                    :label="item.course"
                                    :value="item"
                            >
                            </el-option>
                        </el-select>

                    </div>
                    <div >
                        <span>日期：</span>
                        <el-date-picker
                            v-model="timeList"
                            type="datetimerange"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            :picker-options="pickerOptions"
                            :disabled="submitFlag">
                        </el-date-picker>
                    </div>
                    <div >
                        <span>时长：</span>
                        <el-input
                                style="width: 70px;"
                                v-model="exam.time"
                                :disabled="true"
                                >
                        </el-input>
                        <span> 分钟</span>
                    </div>
                    <div >
                        <span>周次：</span>
                        <el-input
                                style="width: 50px;"
                                v-model="exam.week"
                                :disabled="true"
                                >
                        </el-input>
                        <span> 周</span>
                    </div>
                    <div >
                        <span>重修生：</span>
                        <el-switch
                                v-model="exam.rebuild"
                                active-text="是"
                                inactive-text="否"
                                :disabled="submitFlag">
                        </el-switch>
                    </div>
                    <div >
                        <span>方式：</span>
                        <el-switch
                                v-model="exam.online"
                                active-color="#13ce66"
                                inactive-color="#409EFF"
                                active-text="线上"
                                inactive-text="线下"
                                :disabled="submitFlag">
                        </el-switch>
                    </div>

                    <div>
                        <div >
                            <el-button :disabled="submitFlag" @click="clearInfo">清空内容</el-button>
                        </div>
                        <div style="margin-left: 12px;">
                            <el-button type="primary" @click="selectClassList" :disabled="submitFlag">搜索信息</el-button>
                        </div>
                    </div>


                </div>
                <div class="table">
<!--                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"-->
                    <div v-show="exam.online === false && exam.rebuild === true">
                        <el-table
                                v-loading="loading"
                                element-loading-text="拼命加载中"
                                :data="queryData"
                                height="500"
                                border
                                stripe
                                style="width: 100%;"
                        >
                            <el-table-column
                                    align="center"
                                    prop="loc"
                                    label="教学楼"
                                    width="160"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="room"
                                    sortable
                                    label="考场号"
                                    width="160"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="num2"
                                    sortable
                                    label="教室座位个数"
                                    width="160"
                            >
                                <template slot-scope="scope">
                                    {{ scope.row.num2 }} / {{ scope.row.num1 }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="personNum"
                                    sortable
                                    label="已选参考人数"
                                    width="160"
                            >
                                <template slot-scope="scope">
                                    <el-tag size="medium" :type="scope.row.personNum <= scope.row.num2 ? '' : 'danger'">{{ scope.row.personNum }}</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="classSumList"
                                    label="考试班级">
                                <template slot-scope="scope">
                                    <el-select
                                            v-model.trim="scope.row[scope.column.property]"
                                            multiple placeholder="请选择"
                                            @change="changeQueryDataClassSumList(scope)"
                                            :id="'val' + scope.$index"
                                            :key="timer">
                                        <el-option
                                                v-for="classSum in classSumList"
                                                :key="classSum.id"
                                                :label="classSum.banji + '班'"
                                                :value="classSum.banji"
                                                :disabled="classSum.disabled"
                                        >
                                            <span style="float: left">{{ classSum.banji }}班</span>
                                            <span style="float: right; color: #8492a6; font-size: 13px">{{ classSum.sum }}人</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="rebuildList"
                                    label="考试重修学生">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="scope.row[scope.column.property]"
                                               multiple placeholder="请选择"
                                               @change="changeQueryDataRebuildList(scope)"
                                               :id="'val' + scope.$index"
                                               :key="timer">
                                        <el-option
                                                v-for="rebuild in rebuildList"
                                                :key="rebuild.id"
                                                :label="rebuild.name"
                                                :value="rebuild.username"
                                                :disabled="rebuild.disabled"
                                        >
                                            <span style="float: left">{{ rebuild.grade }}级{{ rebuild.name }} </span>
                                            <span style="float: right; margin-left: 4px;color: #8492a6; font-size: 13px"> {{ rebuild.username }}</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="teacherList"
                                    label="监考老师">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="scope.row[scope.column.property]"
                                               multiple placeholder="请选择"
                                               @change="changeQueryDataTeacherList(scope)"
                                               :id="'val' + scope.$index"
                                               :key="timer">
                                        <el-option
                                                v-for="teacher in teacherList"
                                                :key="teacher.id"
                                                :label="teacher.name"
                                                :value="teacher.username"
                                                :disabled="teacher.disabled"
                                        >
                                            <span style="float: left">{{ teacher.name }}</span>
                                            <span style="float: right; color: #8492a6; font-size: 13px">{{ teacher.username }}</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="table-footer">
                            <div>
                                <div style="float: left;margin-left: 0;">
                                    当前可用考场个数：<span>{{queryData.length}}</span>
                                </div>
                            </div>

                            <div>
                                <div style="width: 280px;">
                                    <span>考试负责人：</span>
                                    <div>
                                        <el-tag size="medium">{{ majorTeacher.name }}  {{majorTeacher.teacherId}}</el-tag>
                                    </div>
                                </div>
                                <div>
                                    <el-button type="warning" @click="modifyInfo" :disabled="submitSum">修改头部信息</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="success" :disabled="submitSum" @click="intelligenceArrangeExam" >*智能排考</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="primary" :disabled="submitSum">提交考试</el-button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div v-show="exam.online === false && exam.rebuild === false">
                        <el-table
                                v-loading="loading"
                                element-loading-text="拼命加载中"
                                :data="queryData"
                                height="500"
                                border
                                stripe
                                style="width: 100%;"
                        >
                            <el-table-column
                                    align="center"
                                    prop="loc"
                                    label="教学楼"
                                    width="160"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="room"
                                    sortable
                                    label="考场号"
                                    width="160"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="num2"
                                    sortable
                                    label="教室座位个数"
                                    width="160"
                            >
                                <template slot-scope="scope">
                                    {{ scope.row.num2 }} / {{ scope.row.num1 }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="personNum"
                                    sortable
                                    label="已选参考人数"
                            >
                                <template slot-scope="scope">
                                    <el-tag size="medium" :type="scope.row.personNum <= scope.row.num2 ? '' : 'danger'">{{ scope.row.personNum }}</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="classSumList"
                                    label="考试班级">
                                <template slot-scope="scope">
                                    <el-select
                                            v-model.trim="scope.row[scope.column.property]"
                                            multiple placeholder="请选择"
                                            @change="changeQueryDataClassSumList(scope)"
                                            :id="'val' + scope.$index"
                                            :key="timer">
                                        <el-option
                                                v-for="classSum in classSumList"
                                                :key="classSum.id"
                                                :label="classSum.banji + '班'"
                                                :value="classSum.banji"
                                                :disabled="classSum.disabled"
                                        >
                                            <span style="float: left">{{ classSum.banji }}班</span>
                                            <span style="float: right; color: #8492a6; font-size: 13px">{{ classSum.sum }}人</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="teacherList"
                                    label="监考老师">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="scope.row[scope.column.property]"
                                               multiple placeholder="请选择"
                                               @change="changeQueryDataTeacherList(scope)"
                                               :id="'val' + scope.$index"
                                               :key="timer">
                                        <el-option
                                                v-for="teacher in teacherList"
                                                :key="teacher.id"
                                                :label="teacher.name"
                                                :value="teacher.username"
                                                :disabled="teacher.disabled"
                                        >
                                            <span style="float: left">{{ teacher.name }}</span>
                                            <span style="float: right; color: #8492a6; font-size: 13px">{{ teacher.username }}</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="table-footer">
                            <div>
                                <div style="float: left;margin-left: 0;">
                                    当前可用考场个数：<span>{{queryData.length}}</span>
                                </div>

                            </div>

                            <div>
                                <div style="width: 280px;">
                                    <span>考试负责人：</span>
                                    <div>
                                        <el-tag size="medium">{{ majorTeacher.name }}  {{majorTeacher.teacherId}}</el-tag>
                                    </div>
                                </div>
                                <div>
                                    <el-button type="warning" @click="modifyInfo" :disabled="submitSum">修改头部信息</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="success" :disabled="submitSum" @click="intelligenceArrangeExam" >*智能排考</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="primary" :disabled="submitSum">提交考试</el-button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div v-show="exam.online === true && exam.rebuild === true">
                        <el-table
                                v-loading="loading"
                                element-loading-text="拼命加载中"
                                :data="queryData"
                                height="500"
                                border
                                stripe
                                style="width: 100%;"
                        >
                            <el-table-column
                                    align="center"
                                    prop="personNum"
                                    sortable
                                    label="已选参考人数"
                            >
                                <template slot-scope="scope">
                                    <el-tag size="medium" :type="scope.row.personNum <= scope.row.num2 ? '' : 'danger'">{{ scope.row.personNum }}</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="classSumList"
                                    label="考试班级">
                                <template slot-scope="scope">
                                    <el-select
                                            v-model.trim="scope.row[scope.column.property]"
                                            multiple placeholder="请选择"
                                            @change="changeQueryDataClassSumList(scope)"
                                            :id="'val' + scope.$index"
                                            :key="timer">
                                        <el-option
                                                v-for="classSum in classSumList"
                                                :key="classSum.id"
                                                :label="classSum.banji + '班'"
                                                :value="classSum.banji"
                                                :disabled="classSum.disabled"
                                        >
                                            <span style="float: left">{{ classSum.banji }}班</span>
                                            <span style="float: right; color: #8492a6; font-size: 13px">{{ classSum.sum }}人</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="rebuildList"
                                    label="考试重修学生">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="scope.row[scope.column.property]"
                                               multiple placeholder="请选择"
                                               @change="changeQueryDataRebuildList(scope)"
                                               :id="'val' + scope.$index"
                                               :key="timer">
                                        <el-option
                                                v-for="rebuild in rebuildList"
                                                :key="rebuild.id"
                                                :label="rebuild.name"
                                                :value="rebuild.username"
                                                :disabled="rebuild.disabled"
                                        >
                                            <span style="float: left">{{ rebuild.grade }}级{{ rebuild.name }} </span>
                                            <span style="float: right; margin-left: 4px;color: #8492a6; font-size: 13px"> {{ rebuild.username }}</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="teacherList"
                                    label="监考老师">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="scope.row[scope.column.property]"
                                               multiple placeholder="请选择"
                                               @change="changeQueryDataTeacherList(scope)"
                                               :id="'val' + scope.$index"
                                               :key="timer">
                                        <el-option
                                                v-for="teacher in teacherList"
                                                :key="teacher.id"
                                                :label="teacher.name"
                                                :value="teacher.username"
                                                :disabled="teacher.disabled"
                                        >
                                            <span style="float: left">{{ teacher.name }}</span>
                                            <span style="float: right; color: #8492a6; font-size: 13px">{{ teacher.username }}</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="table-footer">
                            <div>
                                <div style="float: left;margin-left: 0;">
                                    <el-button type="primary" icon="el-icon-edit" @click="addRoom" :disabled="submitSum">添加考场</el-button>
                                </div>
                                <div style="float: left;margin-left: 12px;">
                                    <el-button type="danger" icon="el-icon-delete" @click="removeRoom" :disabled="submitSum">删除考场</el-button>
                                </div>
                            </div>

                            <div>
                                <div style="width: 280px;">
                                    <span>考试负责人：</span>
                                    <div>
                                        <el-tag size="medium">{{ majorTeacher.name }}  {{majorTeacher.teacherId}}</el-tag>
                                    </div>
                                </div>
                                <div>
                                    <el-button type="warning" @click="modifyInfo" :disabled="submitSum">修改头部信息</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="success" :disabled="submitSum" @click="intelligenceArrangeExam" >*智能排考</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="primary" :disabled="submitSum">提交考试</el-button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div v-show="exam.online === true && exam.rebuild === false">
                        <el-table
                                v-loading="loading"
                                element-loading-text="拼命加载中"
                                :data="queryData"
                                height="500"
                                border
                                stripe
                                style="width: 100%;"
                        >
                            <el-table-column
                                    align="center"
                                    prop="personNum"
                                    sortable
                                    label="已选参考人数"
                            >
                                <template slot-scope="scope">
                                    <el-tag size="medium" :type="scope.row.personNum <= scope.row.num2 ? '' : 'danger'">{{ scope.row.personNum }}</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="classSumList"
                                    label="考试班级">
                                <template slot-scope="scope">
                                    <el-select
                                            v-model.trim="scope.row[scope.column.property]"
                                            multiple placeholder="请选择"
                                            @change="changeQueryDataClassSumList(scope)"
                                            :id="'val' + scope.$index"
                                            :key="timer">
                                        <el-option
                                                v-for="classSum in classSumList"
                                                :key="classSum.id"
                                                :label="classSum.banji + '班'"
                                                :value="classSum.banji"
                                                :disabled="classSum.disabled"
                                        >
                                            <span style="float: left">{{ classSum.banji }}班</span>
                                            <span style="float: right; color: #8492a6; font-size: 13px">{{ classSum.sum }}人</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="teacherList"
                                    label="监考老师">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="scope.row[scope.column.property]"
                                               multiple placeholder="请选择"
                                               @change="changeQueryDataTeacherList(scope)"
                                               :id="'val' + scope.$index"
                                               :key="timer">
                                        <el-option
                                                v-for="teacher in teacherList"
                                                :key="teacher.id"
                                                :label="teacher.name"
                                                :value="teacher.username"
                                                :disabled="teacher.disabled"
                                        >
                                            <span style="float: left">{{ teacher.name }}</span>
                                            <span style="float: right; color: #8492a6; font-size: 13px">{{ teacher.username }}</span>
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="table-footer">
                            <div>
                                <div style="float: left;margin-left: 0;">
                                    <el-button type="primary" icon="el-icon-edit" @click="addRoom" :disabled="submitSum">添加考场</el-button>
                                </div>
                                <div style="float: left;margin-left: 12px;">
                                    <el-button type="danger" icon="el-icon-delete" @click="removeRoom" :disabled="submitSum">删除考场</el-button>
                                </div>
                            </div>

                            <div>
                                <div style="width: 280px;">
                                    <span>考试负责人：</span>
                                    <div>
                                        <el-tag size="medium">{{ majorTeacher.name }}  {{majorTeacher.teacherId}}</el-tag>
                                    </div>
                                </div>
                                <div>
                                    <el-button type="warning" @click="modifyInfo" :disabled="submitSum">修改头部信息</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="success" :disabled="submitSum" @click="intelligenceArrangeExam" >*智能排考</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="primary" :disabled="submitSum">提交考试</el-button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="rm"></div>
            <div class="rm"></div>
            <div class="rm"></div>
            <div class="rm"></div>
            <div class="rm"></div>
            <div class="rm">
            </div>
            <div class="handler"></div>
        </div>
    </div>

</template>

<style scoped src="../css/Admin.css">
</style>
<style scoped src="../fonts/font.css">
</style>

<script>
    export default {
        name: "",
        data(){
            return{
                pickerOptions:{
                    disabledDate(time){
                        return true;
                    }
                },
                user:{},
                termList:[],
                timeList:[],
                collegeList:[],
                gradeList:[],
                professionList:[],
                professionListCopy:[],
                courseList:[],
                exam:{
                    term: {}, college:{}, profession:{}, grade:{}, course:{}, week:0, time:0,
                    startTime:'',//timeList[0]
                    endTime:'',//timeList[1]
                    rebuild:true, online:false
                },
                examData:{},
                queryInfo:{},
                queryData:[
                    /*{loc: "主教学楼", room: "101", num1: 137, num2:82, personNum: 0, classSumList: [], rebuildList: [], teacherList: []},*/
                ],
                roomList:[],
                classSumList:[],
                rebuildList:[],
                teacherList:[],
                majorTeacher:{
                    teacherId:'',
                    name:''
                },
                timer:'',
                loading: false,
                submitFlag: false,
                submitSum: true
            }
        },
        created(){

        },
        mounted() {
            this.init();            //获取数据
            this.clickList();
            this.clickBar();
        },
        methods:{
            /*初始化 ==》 setUser() setTermList()*/
            init(){
                this.setUser();
                this.setTermList();
            },
            /*根据token获取登录用户信息 =》 user */
            setUser(){
                this.user = JSON.parse(localStorage.getItem('user'));
            },
            /*获取可用学期信息 =》 termList*/
            setTermList(){
                this.axios.get("/exam/termList/")
                    .then((res) => {
                        console.log(res.data);
                        if(res.data.flag === true){
                            this.termList = res.data.data;
                        }
                    }).finally(() => {

                });
            },
            /*根据学期（exam.term）所在时间段得到可编辑的日期时间 =》 Date */
            setDate(){
                if(this.exam.term.term === '' || this.exam.term.term === null)
                {
                    this.pickerOptions = Object.assign({}, this.pickerOptions, {
                        disabledDate: (time) => {
                            return false;
                        },
                    });
                    return;
                }
                this.pickerOptions = Object.assign({}, this.pickerOptions, {
                    disabledDate: (time) => {
                        //禁用这一天的日期  如需禁用之前之后，可把=== 替换成 > 或 <
                        let disable = time < new Date(this.exam.term.startTime) - 24 * 60 * 60 * 1000;
                        //循环后端返回的数据，禁用时间大于contractStartTime小于contractEndTime，注意contractStartTime要减去一天，才能真正禁用contractStartTime这一天。
                        /*data.data.forEach((item) => {
                            disable =
                                disable ||
                                (time.getTime() > new Date(item.contractStartTime).getTime() - 8.64e7 &&
                                    time.getTime() < new Date(item.contractEndTime).getTime()); //减去一天8.64e7
                        });*/
                        disable = disable || time.getTime() > new Date(this.exam.term.endTime).getTime() + 24 * 60 * 60 * 1000;
                        return disable;
                    },
                });
            },
            /*根据学期（exam.term）获取在此学期及之前创建的学院信息列表 =》collegeList */
            setCollegeList(){
                console.log(this.exam.term)
                this.axios.post("/exam/collegeList/", this.exam.term)
                    .then((res) => {
                        console.log(res.data);
                        if(res.data.flag === true){
                            this.collegeList = res.data.data;
                        }
                    }).finally(() => {
                });
            },
            /*根据学院（exam.college）获取此学院所有的专业信息列表 =》professionList */
            setProfessionList(){
                let obj = {college: this.exam.college, term: this.exam.term};
                console.log(this.exam.college)
                this.axios.post("/exam/professionList/", obj)
                    .then((res) => {
                        console.log(res.data);
                        if(res.data.flag === true){
                            this.professionListCopy = res.data.data;
                            for (let i = 0; i < this.professionListCopy.length; i++) {
                                let flag = true;
                                for (let j = 0; j < this.professionList.length; j++) {
                                    if(this.professionList[j].profession === this.professionListCopy[i].profession){
                                        flag = false;break;
                                    }
                                }
                                if(flag){
                                    this.professionList.push(this.professionListCopy[i]);
                                }
                            }
                        }
                    }).finally(() => {
                });
            },
            /*根据专业（exam.profession）获取所有开设此专业的年级信息列表 =》professionList */
            setGradeList(){
                this.gradeList = [];
                console.log(this.exam.profession.profession)
                for (const i in this.professionListCopy) {
                    if(this.professionListCopy[i].profession === this.exam.profession.profession){
                        this.gradeList.push(this.professionListCopy[i]);
                    }
                }
            },
            /*根据年级（exam.grade）获取此年级专业所有的课程信息列表 =》courseList */
            setCourseList(){
                let obj = {college: this.exam.college, term: this.exam.term, profession: this.exam.grade};
                console.log("this.exam.profession", this.exam.profession);
                this.axios.post("/exam/courseList/", obj)
                    .then((res) => {
                        console.log(res.data);
                        if(res.data.flag === true){
                            this.courseList = res.data.data;
                        }
                    }).finally(() => {
                });
            },
            /*通过setDate()的 this.timeList 获取考试课程的开始考试时间与结束考试时间 =》 exam.startTime,exam.endTime */
            setExamTime(){
                this.exam.startTime = this.timeList[0];
                this.exam.endTime = this.timeList[1];
                let d = this.exam.term.startTime + ' 00:00:00'

                console.log("this.exam.startTime",this.exam.startTime);
                console.log("this.exam.endTime",this.exam.endTime);
                console.log("this.exam.term.startTime",d);

                let d1 = new Date(this.exam.startTime);
                let d2 = new Date(this.exam.endTime);
                let d3 = new Date(d);
                console.log("d3", d3);
                console.log("d1", d1);
                /*console.log(parseInt(d2 - d1));//两个时间相差的毫秒数
                console.log(parseInt(d2 - d1) / 1000);//两个时间相差的秒数*/
                console.log((parseInt(d1 - d3) / 1000 / 60 / 24 / 7 ) + 1)
                if(parseInt(d1 - d3) > 0){
                    this.exam.week = parseInt(parseInt(d1 - d3) / 1000 / 60 / 60 / 24 / 7 ) + 1;
                }else{
                    this.exam.week = 1;
                }
                if(parseInt(d2 - d1) > 0) {
                    this.exam.time = parseInt(d2 - d1) / 1000 / 60;//两个时间相差的分钟数
                }else if(parseInt(d2 - d1) === 0){
                    this.exam.time = 0;
                }

                console.log("this.exam.week", this.exam.week);
                console.log("this.exam.time", this.exam.time);
            },
            /*此函数将重新编辑考试信息，将清空已查询到的排考信息*/
            modifyInfo(){
                this.$confirm('此操作将重新编辑考试信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.submitFlag = false;
                    this.queryData = [];
                    this.roomList = [];
                    this.classSumList = [];
                    this.rebuildList = [];
                    this.teacherList = [];
                    this.classSumList = [];
                    this.rebuildList = [];
                    this.teacherList = [];
                    this.majorTeacher = {
                        teacherId:'', name:''
                    };
                    this.submitSum = true;
                    this.$message({
                        type: 'success',
                        message: '操作完成'
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            },
            /*此函数将清空头部信息*/
            clearInfo(){
                this.$confirm('此操作将清空考试信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.termList = [];
                    this.timeList = [];
                    this.collegeList = [];
                    this.gradeList = [];
                    this.professionList = [];
                    this.courseList = [];
                    this.exam = {
                        term: {}, college:{}, profession:{}, grade:{}, course:{}, week:0, time:0,
                        startTime:'',//timeList[0]
                        endTime:'',//timeList[1]
                        rebuild:false, online:false
                    }
                    this.queryInfo = {};
                    this.setTermList();
                    this.$message({
                        type: 'success',
                        message: '操作完成'
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            },
            /*根据this.exam查询对应信息 =》 queryData:[],roomList:[], classSumList:[], rebuildList:[], teacherList:[], majorTeacher:{},*/
            selectClassList(){
                console.log('this.exam', this.exam);
                let examKV = {};
                examKV["term"] = "学期";
                examKV["college"] = "学院";
                examKV["profession"] = "专业";
                examKV["grade"] = "年级";
                examKV["course"] = "课程";
                examKV["week"] = "周次";
                examKV["time"] = "时长";
                examKV["startTime"] = "开始时间";
                examKV["endTime"] = "结束时间";
                examKV["rebuild"] = "重修生";
                examKV["online"] = "考试方式";
                let flag = true;
                for (let key in this.exam) {
                    if (this.exam.hasOwnProperty(key)) {
                        if(JSON.stringify(this.exam[key]) !== "{}" && this.exam[key] !== 0 && this.exam[key] !== '') {
                            console.log(key, this.exam[key]);
                        }else{
                            flag = false;
                            if(examKV[key] === "时长" || examKV[key] === "周次"){
                                setTimeout(() => {
                                    this.$message.error(examKV[key] + " 值不可为 0 ！请检查后重新编辑");
                                }, 10);
                            }else{
                                setTimeout(() => {
                                    this.$message.error(examKV[key] + " 选项选择有误！请检查后重新编辑");
                                }, 10);
                            }

                        }
                    }
                }
                if(!flag) return;
                this.loading = true;
                let queryInfo = {
                    term: this.exam.term.term,
                    college:this.exam.college.college,
                    profession:this.exam.profession.profession,
                    grade:this.exam.grade.grade,
                    course:this.exam.course.course,
                    week:this.exam.week,
                    time:this.exam.time,
                    startTime:this.exam.startTime,
                    endTime:this.exam.endTime,
                    rebuild:this.exam.rebuild,
                    online:this.exam.online,
                };
                this.queryInfo = queryInfo;
                console.log('queryInfo', queryInfo);
                this.queryData = [];
                this.roomList = [];
                this.classSumList = [];
                this.rebuildList = [];
                this.teacherList = [];
                this.majorTeacher = {};
                this.axios.post("/exam/queryInfo", this.queryInfo)
                    .then((res) => {
                        console.log(res.data);
                        if(res.data.flag && res.data.data != ""){//获取到数据
                            this.submitFlag = true;
                            this.examData = res.data.data;
                            setTimeout(() => {
                                this.$message.success(res.data.msg);
                            }, 10);

                            this.roomList = this.isNull(res.data.data.roomList);
                            this.classSumList = this.isNull(res.data.data.classSumList);
                            this.rebuildList = this.isNull(res.data.data.rebuildList);
                            this.teacherList = this.isNull(res.data.data.teacherList);
                            this.majorTeacher = this.isNull(res.data.data.majorTeacher);
                            if(this.majorTeacher === ""){
                                this.$notify({
                                    title: '提示',
                                    message: '本课程未设置考试负责人！请考虑后台添加该考试负责人数据',
                                    type: 'warning'
                                });
                            }
                            let i = 0;
                            if(!this.queryInfo.online){//线下
                                for (const room of this.roomList) {
                                    let obj = {
                                        loc: room.loc, room: room.room, num1: room.num1, num2:room.num2, personNum: 0,
                                    }
                                    this.queryData.push(obj);
                                }
                                setTimeout(() => {
                                    this.$message.success("当前时段可用考场数量为： " + this.roomList.length + " 个");
                                }, 10);
                            }
                            else{//线上
                                this.queryData.push({personNum: 0});
                            }
                            if(this.queryInfo.rebuild){
                                setTimeout(() => {
                                    this.$message.success("当前重修考生人数为： " + this.rebuildList.length + " 人");
                                }, 10);
                            }
                            setTimeout(() => {
                                this.$message.success("当前时段空闲监考教师人数为： " + this.teacherList.length + " 人");
                            }, 10);
                            this.submitSum = false;
                        }
                        else this.$message.error(res.data.msg);
                        this.loading = false;
                    }).finally(() => {
                    this.loading = false;
                });
            },
            /*判断查询到的数据是否为null*/
            isNull(e){
                if(e !== null)
                    return e;
                else
                    return "";
            },
            /*左右滑条*/
            clickBar(){
                let handler=document.querySelector('.handler');
                let left_box=document.querySelector('.left-box');
                let right_box=document.querySelector('.right-box');
                let screenWidth = window.screen.width;
                handler.addEventListener('click',function(){
                    if(!this.classList.contains('close')){
                        left_box.style.width=0;
                        right_box.style.maxWidth=screenWidth +'px';
                        this.classList.add('close');
                    }else{
                        left_box.style.width=260+'px';
                        right_box.style.maxWidth=(screenWidth-260) + 'px';
                        this.classList.remove('close');
                    }
                })
            },
            /*切换页面函数*/
            clickList(){
                // 获取要操作的元素
                let items=document.querySelectorAll('.item');
                let current_tag=document.querySelector('.current-tag');
                let rm=document.querySelectorAll('.rm');

                // 设置选中项的样式
                function setActive(){
                    items.forEach((item)=>{
                        item.classList.remove('active');
                    })
                    this.classList.add('active');
                    current_tag.innerText=this.innerText;
                }
                // 为每一个li设置点击事件
                for(let ix = 0; ix < items.length; ix++){
                    items[ix].addEventListener('click',function (){
                        for(let i = 0; i < items.length; i++){
                            items[i].classList.remove('active');
                            rm[i].style.display = 'none';
                        }
                        this.classList.add('active');
                        current_tag.innerText=this.innerText;
                        rm[ix].style.display = 'block';
                    });
                }
            },
            /*s刷新数据s*/
            changeQueryDataClassSumList(scope){
                this.$forceUpdate();
                //console.log(scope.$index, scope.row);
                //1.全部禁用
                for (let k = 0; k < this.classSumList.length; k++) {
                    this.$set(this.classSumList[k],'disabled', false);
                }
                //2.显示该行人数
                for(let i = 0; i < this.queryData.length; i++){
                    //2.1.统计所选人数
                    this.queryData[i].personNum = 0;
                    for (let j = 0; j < this.queryData[i].classSumList.length; j++) {
                        for (let k = 0; k < this.classSumList.length; k++) {
                            if(this.queryData[i].classSumList[j] === this.classSumList[k].banji){
                                this.queryData[i].personNum += this.classSumList[k].sum;
                                //2.2 再逐一封禁
                                this.$set(this.classSumList[k],'disabled', true);
                            }
                        }
                    }
                    this.queryData[i].personNum += this.queryData[i].rebuildList.length;
                }
                this.$forceUpdate();
            },
            changeQueryDataRebuildList(scope){

                //console.log(scope.$index, scope.row);
                //1.全部禁用
                for (let k = 0; k < this.rebuildList.length; k++) {
                    this.$set(this.rebuildList[k],'disabled', false);
                }
                //2.显示该行人数
                for(let i = 0; i < this.queryData.length; i++){
                    //2.1.统计所选人数
                    this.queryData[i].personNum = 0;
                    for (let j = 0; j < this.queryData[i].classSumList.length; j++) {
                        for (let k = 0; k < this.classSumList.length; k++) {
                            if(this.queryData[i].classSumList[j] === this.classSumList[k].banji){
                                this.queryData[i].personNum += this.classSumList[k].sum;
                                break;
                            }
                        }
                    }
                    //2.2 再逐一封禁
                    for (let j = 0; j < this.queryData[i].rebuildList.length; j++) {
                        for (let k = 0; k < this.rebuildList.length; k++) {
                            if(this.queryData[i].rebuildList[j] === this.rebuildList[k].username){
                                this.$set(this.rebuildList[k],'disabled', true);
                                break;
                            }
                        }
                    }
                    this.queryData[i].personNum += this.queryData[i].rebuildList.length;
                }
                this.$forceUpdate();
            },
            changeQueryDataTeacherList(scope){
                this.$forceUpdate();
                for (let k = 0; k < this.teacherList.length; k++) {
                    this.$set(this.teacherList[k],'disabled', false);
                }
                for(let i = 0; i < this.queryData.length; i++){
                    for (let j = 0; j < this.queryData[i].teacherList.length; j++) {
                        for (let k = 0; k < this.teacherList.length; k++) {
                            if(this.queryData[i].teacherList[j] === this.teacherList[k].username){
                                //2.2 再逐一封禁
                                this.$set(this.teacherList[k],'disabled', true);
                            }
                        }
                    }
                }
                this.$forceUpdate();
            },
            /*增删线上考场*/
            addRoom(){
                this.queryData.push({personNum: 0});
            },
            removeRoom(){
                this.queryData.pop();
            },
            /*智能安排考试*/
            intelligenceArrangeExam(){
                this.$confirm('智能排考操作将自行安排考场, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.axios.post("/exam/intelligenceArrangeExam", this.examData)
                        .then((res) => {
                            console.log(this.examData)
                        })

                    this.$message({
                        type: 'success',
                        message: '操作完成'
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            }
        },
        watch: {//当前组件的侦听器
            'queryData.classSumList'(newVal, oldVal){
                  console.log(newVal)
            },
            'exam.term'(newVal, oldVal) {
                console.log("exam.term newVal:",newVal, "oldVal:",oldVal);
                if(JSON.stringify(newVal) !== JSON.stringify(oldVal)){
                    this.exam.college = {}
                    this.setDate();
                    this.setCollegeList();
                }
            },
            'exam.college'(newVal, oldVal) {
                console.log("exam.college newVal:",newVal, "oldVal:",oldVal);
                if(JSON.stringify(newVal) !== JSON.stringify(oldVal)){
                    this.exam.profession = {}
                    this.setProfessionList();
                }
            },
            'exam.profession'(newVal, oldVal) {
                console.log("exam.profession newVal:",newVal, "oldVal:",oldVal);
                if(JSON.stringify(newVal) !== JSON.stringify(oldVal)){
                    this.exam.grade = {}
                    this.setGradeList();
                }
            },
            'exam.grade'(newVal, oldVal) {
                console.log("exam.grade newVal:",newVal, "oldVal:",oldVal);
                if(JSON.stringify(newVal) !== JSON.stringify(oldVal)){
                    this.exam.course = {}
                    this.setCourseList();
                }
            },
            'timeList'(newVal, oldVal) {
                console.log("timeList newVal:",newVal, "oldVal:",oldVal);
                if(JSON.stringify(newVal) !== JSON.stringify(oldVal)){
                    this.setExamTime();
                }
            },
        }
    }
</script>

<style scoped>

</style>

