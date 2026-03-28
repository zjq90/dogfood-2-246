<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>账号管理</title>
	<link rel="icon" href="/static/image/icon/weibo_favicon.ico" type="image/x-icon">
 <link rel="stylesheet" type="text/css" href="/static/css/common.css">
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
					</nav>
			</header>
			<div id="main_content">
				<div id="main_content-left">
						<div id="main_content-left-top">
							 <div>
							 <div id="welcome_msg">
						        修改个人信息
						     </div>
							 <img src="" alt="我的头像" id="user_img" onclick="document.getElementById('photoInput').click()" style="cursor:pointer;"/>
							 <input type="file" id="photoInput" style="display:none" accept="image/*" onchange="uploadPhoto(this)"/>
							 </div>
							<div id="user_info_show">
								<form id="userForm">
								<div>昵称：<input type="text" id="nickname" name="nickname"/></div>
								<div>个性签名：<input type="text" id="signature" name="signature"/></div>
								<div>自我介绍：<textarea id="selfIntroduc" name="selfIntroduc" rows="3" cols="30"></textarea></div>
								<div>地址：<input type="text" id="address" name="address"/></div>
								<div><input type="button" value="保存修改" onclick="saveUserInfo()"/></div>
								</form>
							 </div>
							 <br>
						</div>
				</div>
				
				<div id="main_content-right">
				    <h3>修改密码</h3>
				    <form id="pwdForm">
				        <div>原密码：<input type="password" id="oldPassword" name="oldPassword"/></div>
				        <div>新密码：<input type="password" id="newPassword" name="newPassword"/></div>
				        <div>确认密码：<input type="password" id="confirmPassword" name="confirmPassword"/></div>
				        <div><input type="button" value="修改密码" onclick="changePassword()"/></div>
				    </form>
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
                document.getElementById('nickname').value = user.nickname || '';
                document.getElementById('signature').value = user.signature || '';
                document.getElementById('selfIntroduc').value = user.selfIntroduc || '';
                document.getElementById('address').value = user.address || '';
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
    
    function saveUserInfo() {
        var data = {
            nickname: document.getElementById('nickname').value,
            signature: document.getElementById('signature').value,
            selfIntroduc: document.getElementById('selfIntroduc').value,
            address: document.getElementById('address').value
        };
        
        fetch('/api/user/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                alert('保存成功！');
            } else {
                alert('保存失败：' + data.msg);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('保存失败，请稍后重试');
        });
    }
    
    function uploadPhoto(input) {
        if(input.files && input.files[0]) {
            var formData = new FormData();
            formData.append('photo', input.files[0]);
            
            fetch('/api/upload/image', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if(data.code === 200) {
                    var imageUrl = data.data.imageUrl;
                    document.getElementById('user_img').src = imageUrl;
                    
                    fetch('/api/user/portrait?portrait=' + encodeURIComponent(imageUrl), {
                        method: 'POST'
                    })
                    .then(response => response.json())
                    .then(data => {
                        if(data.code === 200) {
                            alert('头像更新成功！');
                        }
                    });
                } else {
                    alert('上传失败：' + data.msg);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('上传失败，请稍后重试');
            });
        }
    }
    
    function changePassword() {
        var oldPwd = document.getElementById('oldPassword').value;
        var newPwd = document.getElementById('newPassword').value;
        var confirmPwd = document.getElementById('confirmPassword').value;
        
        if(!oldPwd || !newPwd || !confirmPwd) {
            alert('请填写完整信息');
            return;
        }
        if(newPwd !== confirmPwd) {
            alert('两次输入的新密码不一致');
            return;
        }
        
        fetch('/api/user/password?oldPassword=' + encodeURIComponent(oldPwd) + '&newPassword=' + encodeURIComponent(newPwd), {
            method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                alert('密码修改成功！');
                document.getElementById('pwdForm').reset();
            } else {
                alert('修改失败：' + data.msg);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('修改失败，请稍后重试');
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
