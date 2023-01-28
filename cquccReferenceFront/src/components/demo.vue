<template>
  <slide-verify :l="42"
     :r="10"
     :w="310"
     :h="155"
     @success="onSuccess"
     @fail="onFail"
     @refresh="onRefresh"
     :slider-text="text"
     ></slide-verify>
</template>

<script>

export default {
  name: 'App',
   data(){
    return {
     msg: '1',
     text: '向右滑',
    }
   },
   methods: {
    onSuccess(){
     this.msg = 'login success'
    },
    onFail(){
     this.msg = ''
    },
    onRefresh(){
     this.msg = ''
    }
   }
}
</script>

