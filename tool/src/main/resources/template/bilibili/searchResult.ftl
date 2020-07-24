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
				<td>标题</td>
				<td>${BV.title}</td>
			</tr>
			<tr>
				<td>BVID</td>
				<td>${BV.bvid}</td>
			</tr>
			<tr>
				<td>URL</td>
				<td><a href="${BV.coverImgUrl}" target="_blank">${BV.coverImgUrl}</a></td>
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
	<a href="${BV.coverImgUrl}"  target="_blank"><img src="${BV.coverImgUrl}" alt="视频封面" /></a>
	
	<br>
</div>

</body>
</html>