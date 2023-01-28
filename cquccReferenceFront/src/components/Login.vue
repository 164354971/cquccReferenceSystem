<template>
  <div class="body">
    <div>
		<div class="container">
			<div class="tit">慧监考登录端</div>
			<div style="color: #ea442b;position: relative;top:-20px">{{login_msg}}</div>
			<input id="username" name="username" type="text" placeholder="账号" v-model.trim="user.username">
			<input id="password" name="password" type="password" placeholder="密码" v-model.trim="user.password">
			<div>
				<template>
				  <el-select v-model="user.type" placeholder="请选择" style="width: 92px;margin-right: 8px;">
					<el-option
					  v-for="item in options"
					  :key="item.value"
					  :label="item.label"
					  :value="item.value">
					</el-option>
				  </el-select>
				</template>
				<input type="text" placeholder="验证码" v-model.trim="code" style="width: 90px;">
				<code><span id="code" @click="codeFlush()" style=""></span></code>
			</div>
			
			<button @click="login()" type="submit" style="width: 280px;
				height: 40px;
				margin: 35px auto 40px auto;
				border: none;
				background: linear-gradient(-200deg, #f8faff, #aac2ee);
				color: #fff;
				font-weight: bold;
				letter-spacing: 8px;
				border-radius: 10px;
				cursor: pointer;
				/*动画过度*/
				transition: 0.5s;">登录</button>
			<span>没有账号？<a href="#">去注册</a></span>
		</div>
	</div>
	<div class="square">
		<ul>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>
	<div class="circle">
		<ul>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>
  </div>
</template>

<style scoped src="../iconfont/iconfont.css">
</style>
<style scoped src="../css/login.css">
</style>
<style scoped src="../css/common.css">
</style>


<script>
import {mapMutations} from "vuex";

export default {
    name: 'Login',
    data() {
        return {
            login_msg: '  ',
            user:{
                type:3,
                username:'',
                password:''
            },
            code:'',
            options: [{
                  value: 1,
                  label: '管理员'
                }, {
                  value: 2,
                  label: '教师'
                }, {
                  value: 3,
                  label: '学生'
                }],
            codeArr:"123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789",
            str: ''

        }
    },
    mounted() {
        this.codeFlush();
        //alert(this.user.username);
    },
    methods: {
        ...mapMutations(['changeLogin']),
        codeFlush(){
            this.str = "";
            for(let i = 0; i <= 3; i++){
                var num = Math.abs(Math.floor(Math.random() * this.codeArr.length - 0.00001))
                console.log(num)
                this.str += this.codeArr[num];
            }
            console.log(this.str)
            document.getElementById("code").innerText = this.str;
        },
        async login() {
            var str = this.str.toLowerCase();
            /*if (str == this.code){*/
            if (true){
                const {data: res} = await this.axios.post('/loginChecks/', this.user);
                console.log(res);//如果获取了结果就在控制台打印
                if(res.flag == true){
                    if(res.data == ''){
                        this.login_msg = res.msg;
                    }
                    else{
                        //存储token
                        localStorage.setItem('user', JSON.stringify(res.data));
                        this.userToken = 'Bearer ' + this.user.username;
                        // 将用户token保存到vuex中
                        this.changeLogin({ user: JSON.stringify(res.data) });

                        //跳转到后台主页
                        console.log(res.data);
                        /* this.$router.push({
                            path: '/student',
                            query: {
                                user: res.data
                            },
                        }); */
                        switch(this.user.type){
                            case 1:this.$router.push({
                                name: 'Admin',
                                path: '/admin',
                                params: {
                                    user: JSON.stringify(res.data)
                                }
                                /*query: {
                                    key: 'key',
                                    msgKey: this.msg
                                }*/
                            });break;
                            case 2:break;
                            case 3:this.$router.push({
                                name: 'Student',
                                path: '/student',
                                params: {
                                    user: JSON.stringify(res.data)
                                }
                                /*query: {
                                    key: 'key',
                                    msgKey: this.msg
                                }*/
                            });break;
                        }
                    }
                }
            }else{
                this.login_msg = "验证码输入错误!请重新输入";
                this.codeFlush();
            }

        }
    },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	#code {
		font-size: 1.2rem;
		padding: 7px 20px;
		font-weight: 700;
		background-color: #d5d5de;
		margin-left: 8px;
	}
</style>
