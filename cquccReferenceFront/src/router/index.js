import Vue from 'vue'
import VueRouter from 'vue-router';
import Login from "@/components/Login.vue"
import Student from "@/components/Student.vue"
import Admin from "@/components/Admin.vue"
import demo from "@/components/demo.vue"


//调用Vue.use函数，把vue-router安装为Vue的插件
Vue.use(VueRouter) 

//创建路由实例对象
const router = new VueRouter({
	//router是一个数组，作用：定义“hash地址” 与组件之间的关系
	routes:[
		{ path: '/', redirect: '/login'},
		/* { path: '/demo', component: demo }, */
		//登录的路由规则
		{ path: '/login', component: Login },
		{ path: '/admin', component: Admin, name: 'Admin' },
		{ path: '/student', component: Student, name: 'Student', children: [

		]},
	]
})

router.beforeEach(function(to, from, next){
	if(to.path === '/'){
		next();
	}else {
		let token = localStorage.getItem('user');
		//alert(token)
		if (token === 'null' || token === '') {
			next('/login');
		} else {
			next();
		}
	}
})

//向外共享路由的实例对象
export default router