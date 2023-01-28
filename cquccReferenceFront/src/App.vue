<template>
  <div>
		<!-- 路由占位符 -->
		<router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'app',

}
</script>

<style >
  ::-webkit-scrollbar {
    width: 10px;
    height: 8px;
    background-color: #ebeef5;
  }
  ::-webkit-scrollbar-thumb {
/*    box-shadow: inset 0 0 10px rgba(0, 0, 0, .3);
    -webkit-box-shadow: inset 0 0 10px rgba(0, 0, 0, .3);*/
    background-color: rgba(65,168,255,0.25);
  }
  ::-webkit-scrollbar-track{
    /*box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);*/
    border-radius: 3px;
    background: rgba(255, 255, 255, 1);
  }
</style>