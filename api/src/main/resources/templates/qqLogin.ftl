<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>QQ 第三方登陆</title>
</head>
<body>
<p>操作中... </p>
<p>请稍后 </p>
<p>${msg}</p>
<script>
    window.onload = function () {
        window.opener.postMessage("${uidAndToken}", "${domain}");
        window.close();
    }
</script>
</body>
</html>