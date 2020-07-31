<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="referrer" content="no-referrer">
<title>查询结果展示</title>

<style type="text/css">
	.main{
		margin: 5px 5%;
	}
	td {
		border: 1px pink solid;
		border-radius: 20px;
		padding: 5px;
	}
	.mailAddressInput{
	    box-sizing: border-box;
	    padding-inline-start: 20px;
	    border-radius: 20px;
	}
</style>
</head>
<body>
<div align="center" class="main">
	<h2>视频信息</h2>
	<table >
		<thead>
			<tr>
				<td>名称</td>
				<td>值</td>
			</tr>	
		</thead>
		<tbody>
			<tr>
				<td>AV号</td>
				<td><a href="https://www.bilibili.com/video/av${BV.aid?c}" target="_blank">av${BV.aid?c}</a></td>
			</tr>
			<tr>
				<td>BV号</td>
				<td><a href="https://www.bilibili.com/video/${BV.bvid}" target="_blank">${BV.bvid}</a></td>
			</tr>
			<tr>
				<td>标题</td>
				<td><a href="https://www.bilibili.com/video/av${BV.aid?c}" target="_blank">${BV.title}</a></td>
			</tr>
			<tr>
				<td>封面图片URL</td>
				<td><a href="${BV.pic}" target="_blank">${BV.pic}</a></td>
			</tr>
			<tr>
				<td>上传时间</td>
				<td>Unix时间戳：${(BV.pubdate)?c}</td>
			</tr>
			<tr>
				<td>视频总计持续时长</td>
				<td>${BV.duration}秒</td>
			</tr>
			<tr>
				<td>查询次数</td>
				<td>${BV.searchCount}次</td>
			</tr>
			<tr >
				<td>视频简介</td>
				<td >${BV.desc?replace("\n","<br>")}</td>
			</tr>
		</tbody>
	</table>
	<!--
	<h2>发送到您的邮箱</h2>	
	<form action="/mail/sendCover"  method="post">
		<input name="mailAddress" class="mailAddressInput" title="您的邮箱地址" placeholder="请输入您的邮箱地址"/>
		<button style="border-radius: 5px;height:20px;" type="submit">发送</button>
	</form>
	-->
	<h2>视频封面</h2>	
	<a href="${BV.pic}"  target="_blank"><img width="100%" src="${BV.pic}" alt="视频封面" /></a>
	
	<br>
</div>

</body>
</html>