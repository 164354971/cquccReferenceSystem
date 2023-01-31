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
                        <el-select v-model="exam.term" placeholder="请选择"  style="width: 130px;" value-key="id"> <!--@change="setCollegeList">-->
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
                        <el-select v-model="exam.college" placeholder="请选择"  value-key="id"> <!--@change="setProfessionList">-->
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
                        <el-select v-model="exam.profession" placeholder="请选择"  value-key="id"> <!--@change="setGradeList">-->
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
                        <el-select v-model="exam.grade" placeholder="请选择"  value-key="id" style="width: 110px;">
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
                        <el-select v-model="exam.course" placeholder="请选择"  value-key="id">
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
                            >
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
                                inactive-text="否">
                        </el-switch>
                    </div>
                    <div >
                        <span>方式：</span>
                        <el-switch
                                v-model="exam.online"
                                active-color="#13ce66"
                                inactive-color="#409EFF"
                                active-text="线上"
                                inactive-text="线下">
                        </el-switch>
                    </div>

                    <div>
                        <div >
                            <el-button>清空内容</el-button>
                        </div>
                        <div style="margin-left: 12px;">
                            <el-button type="primary" @click="selectClassList">搜索信息</el-button>
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
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="room"
                                    sortable
                                    label="考场号"
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="num1"
                                    sortable
                                    label="考场座位数量"
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="num2"
                                    sortable
                                    label="建议座位数量"
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="personNum"
                                    sortable
                                    label="已选参考人数"
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    label="考试班级">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].classSumList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
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
                                    prop="rebuilds"
                                    label="考试重修学生">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].rebuildList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
                                               :id="'val' + scope.$index"
                                               :key="timer">
                                        <el-option
                                                v-for="rebuild in rebuildList"
                                                :key="rebuild.id"
                                                :label="rebuild.name"
                                                :value="rebuild.username"
                                                :disabled="rebuild.disabled">
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="teachers"
                                    label="监考老师">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].teacherList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
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
                                <div style="width: 300px;">
                                    <span>考试负责人：</span>
                                    <div>
                                        <el-input
                                                style="width: 140px;"
                                                v-model="majorTeacher.name"
                                                :disabled="true"
                                        >
                                        </el-input>
                                    </div>
                                </div>
                                <div>
                                    <el-button>*智能排考</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="primary">提交考试</el-button>
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
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="room"
                                    sortable
                                    label="考场号"
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="num1"
                                    sortable
                                    label="考场座位数量"
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="num2"
                                    sortable
                                    label="建议座位数量"
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="personNum"
                                    sortable
                                    label="已选参考人数"
                                    width="150"
                            >
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    label="考试班级">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].classSumList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
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
                                    prop="teachers"
                                    label="监考老师">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].teacherList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
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
                                <div style="width: 300px;">
                                    <span>考试负责人：</span>
                                    <div>
                                        <el-input
                                                style="width: 140px;"
                                                v-model="majorTeacher.name"
                                                :disabled="true"
                                        >
                                        </el-input>
                                    </div>
                                </div>
                                <div>
                                    <el-button>*智能排考</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="primary">提交考试</el-button>
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
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    label="考试班级">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].classSumList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
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
                                    prop="rebuilds"
                                    label="考试重修学生">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].rebuildList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
                                               :id="'val' + scope.$index"
                                               :key="timer">
                                        <el-option
                                                v-for="rebuild in rebuildList"
                                                :key="rebuild.id"
                                                :label="rebuild.name"
                                                :value="rebuild.username"
                                                :disabled="rebuild.disabled">
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    prop="teachers"
                                    label="监考老师">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].teacherList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
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
<!--                                    当前可用考场个数：<span>{{queryData.length}}</span>-->
                                </div>

                            </div>

                            <div>
                                <div style="width: 300px;">
                                    <span>考试负责人：</span>
                                    <div>
                                        <el-input
                                                style="width: 140px;"
                                                v-model="majorTeacher.name"
                                                :disabled="true"
                                        >
                                        </el-input>
                                    </div>
                                </div>
                                <div>
                                    <el-button>*智能排考</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="primary">提交考试</el-button>
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
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    label="考试班级">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].classSumList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
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
                                    prop="teachers"
                                    label="监考老师">
                                <template slot-scope="scope">
                                    <el-select v-model.trim="queryData[ scope.$index ].teacherList"
                                               multiple placeholder="请选择"
                                               @change="$forceUpdate()"
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
                                    <!--                                    当前可用考场个数：<span>{{queryData.length}}</span>-->
                                </div>

                            </div>

                            <div>
                                <div style="width: 300px;">
                                    <span>考试负责人：</span>
                                    <div>
                                        <el-input
                                                style="width: 140px;"
                                                v-model="majorTeacher.name"
                                                :disabled="true"
                                        >
                                        </el-input>
                                    </div>
                                </div>
                                <div>
                                    <el-button>*智能排考</el-button>
                                </div>
                                <div style="margin-left: 12px;">
                                    <el-button type="primary">提交考试</el-button>
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
                weekList:[1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                timeList:[],
                collegeList:[],
                gradeList:[],
                professionList:[],
                courseList:[],
                exam:{
                    term: {}, college:{}, profession:{}, grade:{}, course:{}, week:'', time:0,
                    startTime:'',//timeList[0]
                    endTime:'',//timeList[1]
                    rebuild:false, online:false
                },
                queryInfo:{},
                queryData:[
                    /*{loc: "主教学楼", room: "101", num1: 137, num2:82, personNum: 0, banjis: [], rebuilds: [], teachers: []},*/
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
                loading: false
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
            /*初始化*/
            init(){
                this.setUser();
                this.setTermList();
            },
            /**/
            setUser(){
                this.user = JSON.parse(localStorage.getItem('user'));
            },
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
            setProfessionList(){
                let obj = {college: this.exam.college, term: this.exam.term};
                console.log(this.exam.college)
                this.axios.post("/exam/professionList/", obj)
                    .then((res) => {
                        console.log(res.data);
                        if(res.data.flag === true){
                            this.professionList = res.data.data;
                        }
                    }).finally(() => {
                });
            },
            setGradeList(){
                this.gradeList = [];
                console.log(this.exam.profession.profession)
                for (const i in this.professionList) {
                    if(this.professionList[i].profession === this.exam.profession.profession){
                        this.gradeList.push(this.professionList[i]);
                    }
                }
            },
            setCourseList(){
                let obj = {college: this.exam.college, term: this.exam.term, profession: this.exam.profession};
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
                            if(examKV[key] === "时长"){
                                setTimeout(() => {
                                    this.$message.error(examKV[key] + "不可为0！请重试");
                                }, 10);
                            }else{
                                setTimeout(() => {
                                    this.$message.error(examKV[key] + "选项输入有误！请重试");
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
                        if(res.data.flag && res.data.data != ""){
                            this.$message.success(res.data.msg);
                            if(this.queryInfo.online){//如果是线上
                                this.classSumList = res.data.data.classSumList;
                                this.rebuildList = res.data.data.rebuildList;
                                this.teacherList = res.data.data.teacherList;
                                this.majorTeacher = res.data.data.majorTeacher;
                                for (let i = 1; i <= 10; i++) {
                                    let obj = {
                                        personNum: 0,
                                        classSumList: [], rebuildList: [], teacherList: []
                                    }
                                    this.queryData.push(obj);
                                }
                            }
                            else{
                                this.roomList = res.data.data.roomList;
                                this.classSumList = res.data.data.classSumList;
                                this.rebuildList = res.data.data.rebuildList;
                                this.teacherList = res.data.data.teacherList;
                                this.majorTeacher = res.data.data.majorTeacher;
                                for (const room of this.roomList) {
                                    let obj = {
                                        loc: room.loc, room: room.room, num1: room.num1, num2:room.num2, personNum: 0,
                                        classSumList: [], rebuildList: [], teacherList: []
                                    }
                                    this.queryData.push(obj);
                                }
                            }
                        }
                        else this.$message.error(res.data.msg);
                        this.loading = false;
                    }).finally(() => {
                    this.loading = false;
                });
            },
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
            querySearchCollege(queryString, cb) {
                var collegeList = this.collegeList;
                var results = queryString ? collegeList.filter(this.createFilter(queryString)) : collegeList;
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            createFilter(queryString) {
                return (item) => {
                    return (item.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            handleSelect(item) {
                console.log(item);
            },
            loadAll() {

            },
        },
        watch: {//当前组件的侦听器
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

