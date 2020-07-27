<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
	<meta name="author" content="li-guohao">
	<meta name="Keywords" content="liguohao,li-guohao,小豪,个人小站,website,homepage,blog,anime,video,acg,ACG,小站,主页,博客,动漫,视频,二次元">
	<meta name="description " content="小豪的工具箱栈">
	<meta name="referrer" content="no-referrer">
	<link rel="icon" href="/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
	<title>小豪的工具箱站点</title>
	<style type="text/css">
		.main{
			margin: 5px 5%;
			background-color: #ffffff;
		}
		.BVNumInput{
		    box-sizing: border-box;
		    padding-inline-start: 20px;
		    border-radius: 20px;
		}
		.hotCoverDiv{
			width:100%;
			/*border:1px red solid;*/
			background-color: #f6f6f6;
			border-radius: 10px;
			margin: 10px 0;
		}
		.hotCoverDiv .unit {
			width: 80%;
			background-color: #F6F6F6;
			margin: 15px;
			border-radius: 10px;
		}
		.hotCoverDiv .unit img{
			width: 90%;
			border-radius: 5px;
		}
		button{
			margin: 5px 2px;
		}
		.buttonSelected {
			color:blue;
		}
		.div_foot {
		    position: absolute;
		    height: 50px;
		    text-align: center;
		    line-height: 50px;
		    width: 100%;
		}
	</style>
	<script type="text/javascript">
		function windowsLocationHref(href){
			location.href = href;
		}
	</script>
</head>
<body>
<div align="center" class="main">
	<h2>导航</h2>
	<a href="https://liguohao.cn" >小豪的主栈</a>
	<h2>公告</h2>
	<p>工具箱站点目前就只有<strong>BV号或者AV号提取站视频封面</strong>功能，其它功能敬请期待</p>
	<h2>根据BV号查询</h2>	
	<form action="/bilibili/getCover"  method="get">
		<input name="number" class="BVNumInput" title="BV号或者AV号" placeholder="请输入BV号或者AV号"/>
		<button style="border-radius: 5px;height:20px;" type="submit">发送</button>
	</form>
	<hr>
	<h2>热门展示</h2>
	<!--分页组件-->
	<div align="center" >
		<#if curentPage!=1>
			<button onclick="windowsLocationHref('/findAllByPaging?curentPage=${curentPage-1}&pageSize=${pageSize}')" type="button">上一页</button>
		</#if>
		<#list 1..((count / pageSize  )+1) as index>
			<#if curentPage==index>
				<button class="buttonSelected" onclick="windowsLocationHref('/findAllByPaging?curentPage=${index}&pageSize=${pageSize}')" type="button">第${index}页</button>
			</#if>
			<#if curentPage!=index>
				<button onclick="windowsLocationHref('/findAllByPaging?curentPage=${index}&pageSize=${pageSize}')" type="button">第${index}页</button>
			</#if>
		</#list>
		<#if curentPage lt ((count / pageSize))>
			<button onclick="windowsLocationHref('/findAllByPaging?curentPage=${curentPage+1}&pageSize=${pageSize}')" type="button">下一页</button>
		</#if>
	</div>
	<!--图片区域-->
	<div class="hotCoverDiv">
		<#list VideoList as bv>
			<div class="unit" align="center">
				<span><a target="_blank" href="/bilibili/getCover?number=${bv.bvid}">${bv.title}</a> -- 已查询${bv.searchCount}次</span>
				<a target="_blank" href="${bv.pic}"><img src="${bv.pic}" alert="图片无法访问，链接是：${bv.pic}"/></a>
			</div>
		</#list>
	</div>
	<!--分页组件-->
	<div align="center" >
		<#if curentPage!=1>
			<button onclick="windowsLocationHref('/findAllByPaging?curentPage=${curentPage-1}&pageSize=${pageSize}')" type="button">上一页</button>
		</#if>
		<#list 1..((count / pageSize  )+1) as index>
			<#if curentPage==index>
				<button class="buttonSelected" onclick="windowsLocationHref('/findAllByPaging?curentPage=${index}&pageSize=${pageSize}')" type="button">第${index}页</button>
			</#if>
			<#if curentPage!=index>
				<button onclick="windowsLocationHref('/findAllByPaging?curentPage=${index}&pageSize=${pageSize}')" type="button">第${index}页</button>
			</#if>
		</#list>
		<#if curentPage lt ((count / pageSize))>
			<button onclick="windowsLocationHref('/findAllByPaging?curentPage=${curentPage+1}&pageSize=${pageSize}')" type="button">下一页</button>
		</#if>
	</div>
	
</div>

<div class="div_foot">
        <a href="https://liguohao.cn">小站</a>  &nbsp;&nbsp;&nbsp;&nbsp;  <a href="https://liguohao.cn">@liguohao.cn</a>
        Made By
        <a href="https://liguohao.cn"> li-guohao</a>
        <a href="http://beian.miit.gov.cn/" target="_blank">备案号：赣ICP备19013362号-3</a>
</div>
</body>
</html>