<template>
    <div class="root">
        <div class="main">
            <div class="moduleBgChange">
            <h2>这里是404组件</h2>
            <p>会出现这个页面，大概是请求的资源不存在233.</p>
            <p>2333</p>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name:'notfound'
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
