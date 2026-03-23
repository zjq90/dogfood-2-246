<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>我的主页</title>
	<link rel="icon" href="/static/image/icon/weibo_favicon.ico" type="image/x-icon">
 <link rel="stylesheet" type="text/css" href="/static/css/my_page.css">
	</head>
		<body>
			<nav id="first">
				<div id="first_menu">
				    <a href="/api/user/logout" onclick="doLogout()">退出登录</a>
					<a href="/alter">账号管理</a>
					<a href="/my_page">个人主页</a>
					<a href="/doumail">豆邮</a>
					<a href="/blacklist">黑名单</a>
					<a href="/attention">我的关注</a>
					<a href="/friend">我的好友</a>
					<a href="/everyone">所有人</a>
					<a href="/article_edit" target="_blank">写文章</a>
					<a href="/article_list">所有文章</a>
					<a href="/article_list?mine=true">我的文章</a>
					<a href="/article_list?collection=true">我的收藏</a>
				</div>
			</nav>
			
			<header id="second">
					<nav  id="second_menu">
							<div class="logo">
							 	<img alt="豆瓣logo" src="/static/image/豆瓣首页logo.jpg" width=175px height=58px>
							</div>
							<div class="navbar">
								<ul>
									<li><a href="#">首页</a></li>
									<li><a href="/my_page">个人主页</a></li>
									<li><a href="/article_list">浏览发现</a></li>
								</ul>	
							</div>
							<div class="search">
									<form action="">
										<input type="text" placeholder="搜索你感兴趣的内容和人">
										<input  type="submit" value="">
									</form>
							</div>
					</nav>
			</header>
			<div id="main_content">
				<div id="main_content-left">
						<div id="main_content-left-top">
							 <div>
							 <div id="welcome_msg">
						        欢迎您： <span id="username"></span><br/>
						     </div>
							 <img src="" alt="我的头像" id="user_img" />
							 </div>
							<div id="user_info_show">
								<div>个人信息</div>
								<div>昵称：<span id="nickname"></span></div>
								<div>个性签名：<span id="signature"></span></div>
								<div>自我介绍：<span id="selfIntroduc"></span></div>
								<div>地址：<span id="address"></span></div>
							 </div>
							 <br>
						</div>
						<div id="main_content-left-centre">
					</div>
				</div>
				
				<div id="main_content-right">
				</div>
			</div>
		</body>
<script type="text/javascript">
    window.onload = function() {
        loadUserInfo();
    };
    
    function loadUserInfo() {
        fetch('/api/user/info')
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                var user = data.data;
                document.getElementById('username').innerText = user.username;
                document.getElementById('nickname').innerText = user.nickname || '未设置';
                document.getElementById('signature').innerText = user.signature || '未设置';
                document.getElementById('selfIntroduc').innerText = user.selfIntroduc || '未设置';
                document.getElementById('address').innerText = user.address || '未设置';
                if(user.portrait) {
                    document.getElementById('user_img').src = user.portrait;
                } else {
                    document.getElementById('user_img').src = '/static/image/default.png';
                }
            } else {
                alert('获取用户信息失败：' + data.msg);
                window.location.href = '/login';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    
    function doLogout() {
        fetch('/api/user/logout', {
            method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
            window.location.href = '/login';
        })
        .catch(error => {
            console.error('Error:', error);
            window.location.href = '/login';
        });
    }
</script>
</html>
