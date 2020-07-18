<template>
    <div class="root">
        <div class="main">
            <div class="moduleBgChange">
            <h2>这里是500组件</h2>
            <p>会出现这个页面，大概是服务器内部错误</p>
            <p>2333</p>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name:'error'
}
</script>

<style lang="less" scoped>

.main{
  margin: 11% 0;
  padding: 0 10%;
  text-align: center;
  .moduleBgChange{
    margin-left: 35%;
    padding: 5px;
    width: 300px;
    height: 300px;
  }
}

</style>
