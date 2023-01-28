<template>
    <div id="app1" style="margin: 0;">
        <!-- 左边部分 -->
        <div class="left">
            <!-- 日期 -->
            <div class="top1" translate="no">
                <i class="iconfont icon-danlieliebiao"></i>
                <span class="hour" translate="no">{{ date.hour }}:</span>
                <span class="minute" translate="no">{{ date.minute }}:</span>
                <span class="second" translate="no">{{ date.second }} </span>
                <span class="week" translate="no">{{ date.weekdayE }} </span>
                <span class="year" translate="no">{{ date.year }}.</span>
                <span class="month" translate="no">{{ date.month }}</span>
                <span class="day" translate="no">.{{ date.day }}</span>
                <!-- <Time></Time> -->
            </div>

            <!-- 考试安排情况 -->
            <div class="top2" v-show="ls === 0">
                <p>学生考试列表</p>
                <!-- 第一个：待完成 -->
                <!-- 链接考试情况列表页面 -->
                <a href="#/student" @click="dyw = 0">
                    <li class="scheduled">
                        <div>
                            <p>待完成</p>
                            <h1>{{ DoFinishList.length }}</h1>
                        </div>
                        <i class="iconfont icon-bianji"></i>
                        <i class="iconfont icon-youjiantou"></i>
                    </li>
                </a>
                <!-- 第二个：已完成 -->
                <!-- 链接同上 -->
                <a href="#/student" @click="dyw = 1">
                    <li class="ended">
                        <div>
                            <p>已完成</p>
                            <h1>{{ FinishList.length }}</h1>
                        </div>
                        <i class="iconfont icon-liebiao"></i>
                        <i class="iconfont icon-youjiantou"></i>
                    </li>
                </a>
                <!-- 第三个：未完成 -->
                <a href="#/student" @click="dyw = 2">
                    <li class="not_scheduled">
                        <div>
                            <p>未完成</p>
                            <h1>{{ UnFinishList.length }}</h1>
                        </div>
                        <i class="iconfont icon-shijian"></i>
                        <i class="iconfont icon-youjiantou"></i>
                    </li>
                </a>
            </div>

            <!-- 饼图 -->
            <div id="top3" v-show="ls === 0">
                <p>考情分析</p>
            </div>
        </div>

        <!-- 中间部分 -->
        <div class="main">
            <!-- 顶上横栏 -->
            <div class="head">

                <!-- 左半部分 -->
                <div class="left1">
                    <img :src="require('../images/' + this.user.img)" alt="">
                    <p>{{ user.name }}</p>
                    <span>，{{ date.T }} </span>
                </div>

                <!-- 右半部分 -->
                <div class="right1">
                    <!-- 搜索框 -->
                    <div class="search">
                        <form action="">
                            <input type="text" name="fname" placeholder="Search Here...">
                            <i class="iconfont icon-sousuo" aria-hidden="true"></i>
                        </form>
                    </div>

                    <!-- 链接消息中心页面 -->
                    <a href="#/student">
                        <i class="iconfont icon-xiaoxizhongxin"></i>
                    </a>
                </div>

            </div>

            <!-- 主体部分 -->
            <div class="main_body">

                <!-- 左半部分：时间列表 -->
                <div class="main_left" v-show="ls === 0">
                    <p>考试列表</p>
                    <div class="timeList">
                        <!-- 考试卡片 -->
                        <!-- 最近一门考试 -->
                        <!--                    <div class="latestTestCard">
                                                <h3 class="time">25min</h3>
                                                <p class="className">Java程序设计</p>
                                                <span class="testTime">2022年6月7日 18:30</span>
                                            </div>-->
                        <!-- 其他考试 -->
                        <div v-for="(item, i) in ExamList">
                            <div class="TestCard examlist" @click="selectExam(item.id, i)">
                                <h3 class="time">{{ item.timeD1 }}<br>{{ item.timeD2 }}</h3>
                                <p class="className">{{ item.examName }} <a href="#/student" style="font-size: 16px; color: #bbbbbb">{{item.way}}</a></p>
                                <span class="testTime">{{ dyw === 0 ? item.startTime : item.endTime }}</span>
                            </div>
<!--                            <div class="latestTestCard examlist" @click="selectExam(item.id, i)" v-show="i === 0">
                                <h3 class="time">{{ item.timeD1 }}<br>{{ item.timeD2 }}</h3>
                                <p class="className">{{ item.examName }}</p>
                                <span class="testTime">{{ dyw === 0 ? item.startTime : item.endTime }}</span>
                            </div>-->
                        </div>
                    </div>
                    <a href="#/student">
                        <span>共{{ ExamList.length }}条记录</span>
                    </a>
                </div>
                <div class="main_right" v-show="ls === 0">
                    <p>考试详情</p>
                    <!-- 时间卡片 -->
                    <div class="timeCard" v-show="dyw === 0">
                        <!-- 文字圆球 -->
                        <div class="textBall">
                        <span class="latestClassName">
                            {{ ExamList[index].examName.substring(0, ExamList[index].br) }}<br>
                            {{ ExamList[index].examName.substring(ExamList[index].br) }}
                        </span>
                            <p>&nbsp;距离开考还有</p>
                            <span class="countDown">{{ ExamList[index].timeD1 }}{{ ExamList[index].timeD2 }}</span>
                        </div>
                        <p class="notice">即将开始</p>
                        <button class="signIn">一键签到</button>
                    </div>
                    <div class="timeCard1" v-show="dyw === 1">
                        <!-- 文字圆球 -->
                        <div class="textBall1">
                        <span class="latestClassName">
                            {{ ExamList[index].examName.substring(0, ExamList[index].br) }}<br>
                            {{ ExamList[index].examName.substring(ExamList[index].br) }}
                        </span>
                            <p>&nbsp;考试结束</p>
                            <span class="countDown">{{ ExamList[index].timeD1 }}{{ ExamList[index].timeD2 }}</span>
                        </div>
                        <p class="notice">得分：{{ ExamList[index].score }}</p>
                        <button class="signIn">查看详情</button>
                    </div>
                    <div class="timeCard" v-show="dyw === 2">
                        <!-- 文字圆球 -->
                        <div class="textBall">
                        <span class="latestClassName">
                            {{ ExamList[index].examName.substring(0, ExamList[index].br) }}<br>
                            {{ ExamList[index].examName.substring(ExamList[index].br) }}
                        </span>
                            <p>&nbsp;考试结束</p>
                            <span class="countDown">{{ ExamList[index].timeD1 }}{{ ExamList[index].timeD2 }}</span>
                        </div>
                        <p class="notice">类型：{{ExamList[index].type}}</p>
                        <button class="signIn">查看详情</button>
                    </div>
                </div>
                <div v-show="ls === 1">
                    <div class="data">
                        <!-- 柱形图 -->
                        <h3 style="margin: 10px 0">考情汇总</h3>
                        <div id="term-list">
                            <el-select v-model="chooseTerm" :placeholder="termList[termList.length - 1]" name="item" >
                                <el-option
                                        v-for="(item, i) in termList"
                                        :key="item"
                                        :label="item"
                                        :value="item"
                                        @click.native="barChart(i)">
                                </el-option>
                            </el-select>
                        </div>
                        <div id="column_chart">
                        </div>
                    </div>

                    <!--                链接模块-->
<!--                    <div class="function">
                        &lt;!&ndash; 不会搞，先做个样子 &ndash;&gt;
                        <i class="iconfont icon-zuojiantou"></i>
                        <a href="#/student">
                            <li>
                                <p>查看<br>历史记录</p><i class="iconfont icon-shijian"></i>
                            </li>
                        </a>
                        <a href="#/student">
                            <li>
                                <p>查看<br>历史记录</p><i class="iconfont icon-jiekouzhongxin"></i>
                            </li>
                        </a>
                        <a href="#/student">
                            <li class="release">
                                <p>发布<br>考试任务</p><i class="iconfont icon-bianji"></i>
                            </li>
                        </a>
                        <i class="iconfont icon-youjiantou"></i>
                    </div>
                    <div class="bottom">
                        <li></li>
                        <li></li>
                        <li></li>
                    </div>-->
                </div>


            </div>
        </div>

        <!-- 右边部分 -->
        <div class="right">
            <div class="right_top1">
                <a href="#/student" @click="ls = 0">
                    <li v-show="ls === 0"><span class="this"><i class="iconfont icon-shouye"></i></span>
                        <p>首页</p>
                    </li>
                    <li v-show="ls !== 0"><i class="iconfont icon-shouye"></i>
                        <p>首页</p>
                    </li>
                </a>
                <a href="#/student" @click="barChart(termListIdx)">
                    <li v-show="ls === 1"><span class="this"><i class="iconfont icon-shuju"></i></span>
                        <p>数据</p>
                    </li>
                    <li v-show="ls !== 1"><i class="iconfont icon-shuju"></i>
                        <p>数据</p>
                    </li>
                </a>
                <a href="#/student" @click="ls = 2">
                    <li v-show="ls === 2"><span class="this"><i class="iconfont icon-xiaoxizhongxin"></i></span>
                        <p>消息</p>
                    </li>
                    <li v-show="ls !== 2"><i class="iconfont icon-xiaoxizhongxin"></i>
                        <p>消息</p>
                    </li>
                </a>
            </div>
            <div class="right_top2">
                <a href="#/student" @click="ls = 3">
                    <li v-show="ls === 3"><span class="this"><i class="iconfont icon-yonghu"></i></span>
                        <p>考生信息</p>
                    </li>
                    <li v-show="ls !== 3"><i class="iconfont icon-yonghu"></i>
                        <p>考生信息</p>
                    </li>
                </a>
                <a href="#/student" @click="ls = 4">
                    <li v-show="ls === 4"><span class="this"><i class="iconfont icon-dicengjiagou"></i></span>
                        <p>课程信息</p>
                    </li>
                    <li v-show="ls !== 4"><i class="iconfont icon-dicengjiagou"></i>
                        <p>课程信息</p>
                    </li>
                </a>
            </div>
            <div class="right_top3">
                <a href="#/student" @click="ls = 5">
                    <li v-show="ls === 5"><span class="this"><i class="iconfont icon-bianji"></i></span>
                        <p>发布</p>
                    </li>
                    <li v-show="ls !== 5"><i class="iconfont icon-bianji"></i>
                        <p>发布</p>
                    </li>
                </a>
                <a href="#/student" @click="ls = 6">
                    <li v-show="ls === 6"><span class="this"><i class="iconfont icon-jiekouzhongxin"></i></span>
                        <p>修改</p>
                    </li>
                    <li v-show="ls !== 6"><i class="iconfont icon-jiekouzhongxin"></i>
                        <p>修改</p>
                    </li>
                </a>
                <a href="#/student" @click="ls = 7">
                    <li v-show="ls === 7"><span class="this"><i class="iconfont icon-shijian"></i></span>
                        <p>历史</p>
                    </li>
                    <li v-show="ls !== 7"><i class="iconfont icon-shijian"></i>
                        <p>历史</p>
                    </li>
                </a>
            </div>
        </div>
    </div>
</template>

<style scoped src="../css/common.css">
</style>
<style scoped src="../iconfont/iconfont.css">
</style>
<style scoped src="../css/student.css">
</style>

<style lang="less" scoped src="../less/student.less">
</style>

<script>
    import Time from '@/components/common/Time.vue'

    export default {
        name: 'app',
        components: {
            Time
        },
        data() {
            return {
                date: {
                    year: '0000', month: '00', day: '00', weekday: '', hour: '00', minute: '00', second: '00',
                    monthE: '', weekdayE: 'Xxx', T: ''
                },
                datelist: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
                user: {
                    name: ''
                },
                term: '2022-2023-1',            //获取登录的学期
                termList: ['2020-2021-2', '2021-2022-1', '2021-2022-2', '2022-2023-1',],//历史学期列表
                termListIdx: 0,   //柱状图页面的索引
                chooseTerm: '',                 //柱状图选择的学期
                timer: null,
                DoFinishList: [
                    {
                        id: 1,
                        term: '2022-2023-1',
                        month: 12,
                        week: 15,
                        examName: '操作系统',
                        way: '线上',
                        startTimeStamp: 0,
                        startTime: '2022年12月15日12点10分',
                        endTime: '',
                        timeD1: '5',
                        timeD2: 'DAY',
                        br: 4
                    },
                    {
                        id: 2,
                        term: '2022-2023-1',
                        month: 12,
                        week: 15,
                        examName: '专业英语',
                        way: '线上',
                        startTimeStamp: 0,
                        startTime: '2022年12月17日12点05分',
                        endTime: '2022年12月17日14点15分',
                        timeD1: '7',
                        timeD2: 'DAY',
                        br: 4
                    },
                    {
                        id: 3,
                        term: '2022-2023-1',
                        month: 12,
                        week: 16,
                        examName: '软件体系结构',
                        way: '线上',
                        startTimeStamp: 0,
                        startTime: '2022年12月20日12点10分',
                        endTime: '',
                        timeD1: '10',
                        timeD2: 'DAY',
                        br: 2
                    },
                    {
                        id: 4,
                        term: '2022-2023-1',
                        month: 12,
                        week: 16,
                        examName: '计算机组成原理',
                        way: '线上',
                        startTimeStamp: 0,
                        startTime: '2022年12月21日12点10分',
                        endTime: '',
                        timeD1: '11',
                        timeD2: 'DAY',
                        br: 3
                    },
                    {
                        id: 5,
                        term: '2022-2023-1',
                        month: 12,
                        week: 16,
                        examName: 'PHP开发技术',
                        way: '线上',
                        startTimeStamp: 0,
                        startTime: '2022年12月23日12点10分',
                        endTime: '',
                        timeD1: '13',
                        timeD2: 'DAY',
                        br: 3
                    },
                    {
                        id: 6,
                        term: '2022-2023-1',
                        month: 12,
                        week: 17,
                        examName: 'Linux基础',
                        way: '线上',
                        startTimeStamp: 0,
                        startTime: '2022年12月26日12点10分',
                        endTime: '',
                        timeD1: '16',
                        timeD2: 'DAY',
                        br: 5
                    },
                    {
                        id: 7,
                        term: '2022-2023-1',
                        month: 12,
                        week: 17,
                        examName: '计算机网络',
                        way: '线上',
                        startTimeStamp: 0,
                        startTime: '2022年12月27日19点00分',
                        endTime: '',
                        timeD1: '17',
                        timeD2: 'DAY',
                        br: 3
                    },
                ],
                FinishList: [
                    {
                        id: 1,
                        term: '2021-2022-2',
                        month: 6,
                        week: 16,
                        examName: 'Java程序设计',
                        way: '线下',
                        startTimeStamp: 0,
                        startTime: '',
                        endTime: '2022年06月07日14点10分',
                        timeD1: '6',
                        timeD2: 'MONTH',
                        br: 4,
                        score: 90
                    },
                    {
                        id: 2,
                        term: '2021-2022-2',
                        month: 6,
                        week: 16,
                        examName: 'Web前端技术开发',
                        way: '线下',
                        startTimeStamp: 0,
                        startTime: '',
                        endTime: '2022年06月12日14点10分',
                        timeD1: '6',
                        timeD2: 'MONTH',
                        br: 5,
                        score: 81
                    },
                    {
                        id: 3,
                        term: '2021-2022-2',
                        month: 5,
                        week: 14,
                        examName: '数据库技术',
                        way: '线下',
                        startTimeStamp: 0,
                        startTime: '',
                        endTime: '2022年05月30日14点10分',
                        timeD1: '7',
                        timeD2: 'MONTH',
                        br: 3,
                        score: 87
                    },
                    {
                        id: 4,
                        term: '2022-2023-1',
                        month: 12,
                        week: 18,
                        examName: '美丽π课堂',
                        way: '网课',
                        startTimeStamp: 0,
                        startTime: '',
                        endTime: '2022年12月31日23点59分',
                        timeD1: '',
                        timeD2: '',
                        br: 2,
                        score: 100
                    },
                    {
                        id: 5,
                        term: '2022-2023-1',
                        month: 12,
                        week: 15,
                        examName: '美国文化',
                        way: '网课',
                        startTimeStamp: 0,
                        startTime: '',
                        endTime: '2022年12月18日23点59分',
                        timeD1: '',
                        timeD2: '',
                        br: 2,
                        score: 95
                    }
                ],
                UnFinishList: [
                    {
                        id: 1,
                        term: '2021-2022-2',
                        month: 6,
                        week: 16,
                        examName: '无人机实训考试',
                        way: '网课',
                        startTimeStamp: 0,
                        startTime: '',
                        endTime: '2022年08月31日23点59分',
                        timeD1: '4',
                        timeD2: 'MONTH',
                        br: 3,
                        type: '违纪',
                        reason: '考试期间违规使用手机一次，扣10分。'
                    },
                    {
                        id: 2,
                        term: '2021-2022-2',
                        month: 6,
                        week: 16,
                        examName: '短视频实训考试',
                        way: '网课',
                        startTimeStamp: 0,
                        startTime: '',
                        endTime: '2022年08月31日23点59分',
                        timeD1: '4',
                        timeD2: 'MONTH',
                        br: 3,
                        type: '缺考',
                        reason: ''
                    }
                ],
                ExamList: [],
                dyw: 0,         //点击左侧考试三种列表的索引
                index: 0,       //点击考试详情的索引
                ls: 0,          //右侧列表索引
            }
        },
        created() {
            // 每次进入界面时，先清除之前的所有定时器，然后启动新的定时器
            clearInterval(this.timer);
            this.timer = null;
            this.setTimer();
            this.getUser();
            this.init()
        },
        mounted() {
            this.myEcharts();
            this.selectExam(0, 0);
        },
        methods: {
            init() {
                var that = this;
                //获取登录学生的数据并进行归类
                this.axios.post("/examStudent/", this.user.username)
                    .then((res) => {
                        console.log(res.data.flag);
                        if(res.data.flag === true){


                        }
                    }).finally(() => {

                });
                this.ExamList = this.DoFinishList;
                this.termListIdx = this.termList.length - 1;
            },
            myEcharts() {
                var that = this;
                var bin = this.$echarts.init(document.getElementById('top3'));
                var sum = this.DoFinishList.length + this.FinishList.length + this.UnFinishList.length;
                console.log(sum, this.DoFinishList.length, this.FinishList.length, this.UnFinishList.length)
                var option = {
                    //标题
                    title: [{
                        text: '学生考试概览',
                        x: '34%',
                        y: '0%',
                        textStyle: {
                            fontFamily: '微软雅黑',
                            fontSize: 17,
                            color: '#606266',
                            fontWeight: 800
                        }
                    }],
                    //注记颜色，顺时针方向
                    color: ['#67c23a', '#409eff', '#e6a23c'],
                    //鼠标经过
                    tooltip: {
                        trigger: 'item',
                        formatter: '{b} :  ({d}%)'
                    },
                    //百分比
                    dataset: {
                        source: [
                            ['group', '可售面积占比'],
                            ['已完成', (that.FinishList.length / sum).toFixed(2)],
                            ['待完成', (that.DoFinishList.length / sum).toFixed(2)],
                            ['未完成', (that.UnFinishList.length / sum).toFixed(2)]
                        ]
                    },
                    series: [{
                        type: 'pie',
                        radius: [40, 70],
                        center: ['50%', '60%'],
                        encode: {
                            itemName: 'group',
                            value: '可售面积占比'
                        },
                        label: {
                            show: true,
                            formatter: '{b}: {d}%'
                        }
                    }]
                };
                bin.setOption(option);
            },
            barChart(idx) {
                var zhu = this.$echarts.init(document.getElementById('column_chart'));
                this.termListIdx = idx;
                let term = this.termList[idx];
                let months = term[term.length - 1] === '1' ?  ['9月', '10月', '11月', '12月', '1月', '2月'] : ['3月', '4月', '5月', '6月', '7月', '8月'];
                let F = [0, 0, 0, 0, 0, 0];
                let D = [0, 0, 0, 0, 0, 0];
                let U = [0, 0, 0, 0, 0, 0];
                let max = 0;
                if(term[term.length - 1] === '1'){
                    for (let i = 0; i < this.FinishList.length; i++) {
                        if(this.FinishList[i].term === term){
                            let month = this.FinishList[i].month - 9 >= 0 ? this.FinishList[i].month - 9 : this.FinishList[i].month;
                            F[month] ++;     //对应月考试数量加一
                            max = max >= F[month] ? max : F[month];
                        }
                    }
                    for (let i = 0; i < this.DoFinishList.length; i++) {
                        if(this.DoFinishList[i].term === term){
                            let month = this.DoFinishList[i].month - 9 >= 0 ? this.DoFinishList[i].month - 9 : this.DoFinishList[i].month;
                            D[month] ++;   //对应月考试数量加一
                            max = max >= D[month] ? max : D[month];
                        }
                    }
                    for (let i = 0; i < this.UnFinishList.length; i++) {
                        if(this.UnFinishList[i].term === term){
                            let month = this.DoFinishList[i].month - 9 >= 0 ? this.DoFinishList[i].month - 9 : this.DoFinishList[i].month;
                            U[month] ++;   //对应月考试数量加一
                            max = max >= U[month] ? max : U[month];
                        }
                    }
                }
                else{
                    for (let i = 0; i < this.FinishList.length; i++) {
                        if(this.FinishList[i].term === term){
                            let month = this.FinishList[i].month - 3 >= 0 ? this.FinishList[i].month - 3 : this.FinishList[i].month;
                            F[month] ++;     //对应月考试数量加一
                            max = max >= F[month] ? max : F[month];
                        }
                    }
                    for (let i = 0; i < this.DoFinishList.length; i++) {
                        if(this.DoFinishList[i].term === term){
                            let month = this.DoFinishList[i].month - 3 >= 0 ? this.DoFinishList[i].month - 3 : this.DoFinishList[i].month;
                            D[month] ++;   //对应月考试数量加一
                            max = max >= F[month] ? max : F[month];
                        }
                    }
                    for (let i = 0; i < this.UnFinishList.length; i++) {
                        if(this.UnFinishList[i].term === term){
                            let month = this.UnFinishList[i].month - 3 >= 0 ? this.UnFinishList[i].month - 3 : this.UnFinishList[i].month;
                            U[month] ++;   //对应月考试数量加一
                            max = max >= F[month] ? max : F[month];
                        }
                    }
                }

                var option1 = {
                    color: ['#67C23A', '#8cc5ff', '#F56C6C'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            crossStyle: {
                                color: '#999'
                            }
                        }
                    },
                    // 图例
                    legend: {
                        // 图例间距
                        itemGap: 45,
                        data: ['已完成', '待完成', '未完成'],
                        bottom: 10,
                        textStyle: {
                            color: '#909399',
                            fontSize: 14
                        }
                    },
                    // x轴
                    xAxis: [{
                        // 设置坐标轴字的颜色
                        axisLabel: {
                            color: '#909399',
                            fontSize: '14',
                            fontWeight: 'bold'
                        },
                        // 横坐标线颜色
                        axisLine: {
                            lineStyle: {
                                type: 'dashed',
                                color: '#dcdfe6', //左边线的颜色
                                width: '1' //坐标线的宽度
                            }
                        },
                        type: 'category',
                        name: '月份',
                        data: months
                    }],
                    // y轴
                    yAxis: [{
                        type: 'value',
                        name: '考试次数',
                        /*下面三行表示：固定刻度*/
                        max: max,
                        min: 0,
                        interval: Math.round(max / 3),

                        // 设置坐标轴字的颜色
                        axisLabel: {
                            color: '#909399',
                            fontSize: '14',
                            fontWeight: 'bold'
                        },
                        // 纵坐标线颜色
                        axisLine: {
                            lineStyle: {
                                type: 'solid',
                                color: '#dcdfe6', //左边线的颜色
                                width: '1' //坐标线的宽度
                            }
                        },
                        // 横的虚线
                        splitLine: {
                            show: true,
                            lineStyle: {
                                type: 'dashed',
                                color: '#dcdfe6'
                            }
                        }
                    }],
                    // 数据
                    series: [
                        {
                            name: '已完成',
                            type: 'bar',
                            data: F
                        },
                        {
                            name: '待完成',
                            type: 'bar',
                            data: D
                        },
                        {
                            name: '未完成',
                            type: 'bar',
                            data: U
                        }
                    ]
                };
                zhu.setOption(option1);
                this.ls = 1;
            },
            getUser() {
                this.user = JSON.parse(localStorage.getItem('user'));
                console.log(this.user.img);
                // this.user.img = Promise.resolve(require(this.user.img));
                //alert(this.user.username);
                //text : require('');
                //console.log(this.text);
                console.log(require('../images/' + this.user.img))//显示头像
                //查询考试任务
                //1.待完成任务


            },
            selectExam(doExamId, i) {
                let examList = document.getElementsByClassName('examlist');
                console.log(examList)
                examList[this.index].classList.remove('latestTestCard');
                examList[this.index].classList.add('TestCard');
                /*for (let j = 0; j < this.ExamList.length; j++) {
                    if(this.ExamList[j].id === doExamId){
                        this.index = j;
                        break;
                    }
                }*/
                this.index = i;
                examList[this.index].classList.remove('TestCard');
                examList[this.index].classList.add('latestTestCard');
            },
            setTimer() {
                var that = this;
                if (this.timer == null) {
                    this.timer = setInterval(() => {
                        var dt = new Date();
                        this.date.year = dt.getFullYear();
                        var month = dt.getMonth() + 1;
                        that.date.month = this.$options.methods.addTen(month);
                        var day = dt.getDate();
                        this.date.day = this.$options.methods.addTen(day);
                        this.date.weekday = dt.getDay();
                        this.date.weekdayE = this.datelist[this.date.weekday];
                        var hour = dt.getHours(); //获取时
                        this.date.hour = this.$options.methods.addTen(hour);
                        var minute = dt.getMinutes();
                        this.date.minute = this.$options.methods.addTen(minute);
                        var second = dt.getSeconds();
                        this.date.second = this.$options.methods.addTen(second);
                        if (hour >= 6 && hour < 9) that.date.T = '早上好';
                        if (hour >= 9 && hour < 12) that.date.T = '上午好';
                        if (hour >= 12 && hour < 15) that.date.T = '中午好';
                        if (hour >= 15 && hour < 18) that.date.T = '下午好';
                        if (hour >= 18 && hour < 21) that.date.T = '傍晚好';
                        if (hour >= 21 || hour < 6) that.date.T = '夜已深了，注意休息哦~';
                    }, 1000)
                }
            },
            addTen(e) {
                //console.log(e);
                e = e < 10 ? '0' + e : e;
                return e;
            }
        },
        watch: {//当前组件的侦听器
            'user.name'(newVal, oldVal) {
                console.log(newVal, oldVal);
            },
            'dyw'(newVal, oldVal) {
                console.log(newVal, oldVal);
                if (newVal !== oldVal) {

                    let examList = document.getElementsByClassName('examlist');
                    console.log(examList)
                    for (let i = 0; i < this.ExamList.length; i++) {
                        examList[i].classList.remove('latestTestCard');

                    }
                    examList[this.index].classList.add('TestCard');
                    this.index = 0;
                    this.ExamList = [];
                    console.log(localStorage.getItem('user'))
                    //this.selectExam(0, this.index);
                    if (newVal === 0)
                        this.ExamList = this.DoFinishList.concat();
                    else if (newVal === 1)
                        this.ExamList = this.FinishList.concat();
                    else if (newVal === 2)
                        this.ExamList = this.UnFinishList.concat();
                    examList[this.index].classList.remove('TestCard');
                    examList[this.index].classList.add('latestTestCard');
                }
            },
            'index'(newVal, oldVal) {
                console.log(newVal, oldVal);
            },
        },
        computed: {//当前组件的计算属性

        },
        filters: {//当前过滤器

        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }

    h1 {
        text-align: center;
    }

    #term-list {
        text-align: right;
    }
</style>
