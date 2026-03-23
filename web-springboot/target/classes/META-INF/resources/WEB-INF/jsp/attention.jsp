<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的关注</title>
<link rel="icon" href="/static/image/icon/weibo_favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="/static/css/my_page.css">
</head>
<body>
<nav id="first">
    <div id="first_menu">
        <a href="/api/user/logout" onclick="doLogout()">退出登录</a>
        <a href="/my_page">个人主页</a>
        <a href="/everyone">所有人</a>
        <a href="/friend">我的好友</a>
        <a href="/attention">我的关注</a>
        <a href="/blacklist">黑名单</a>
    </div>
</nav>

<header id="second">
    <nav id="second_menu">
        <div class="logo">
            <img alt="豆瓣logo" src="/static/image/豆瓣首页logo.jpg" width=175px height=58px>
        </div>
    </nav>
</header>

<div id="main_content">
    <h2>我的关注</h2>
    <div id="user_list"></div>
    <div id="pagination"></div>
</div>
</body>
<script type="text/javascript">
    var currentPage = 1;
    var pageSize = 10;
    
    window.onload = function() {
        loadFollowing();
    };
    
    function loadFollowing() {
        fetch('/api/friend/following?currentPage=' + currentPage + '&pageSize=' + pageSize)
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                renderUsers(data.data);
            } else {
                document.getElementById('user_list').innerHTML = data.msg;
            }
        });
    }
    
    function renderUsers(pageData) {
        var users = pageData.objects;
        var html = '';
        
        for(var i = 0; i < users.length; i++) {
            var user = users[i];
            html += '<div class="user-item">';
            html += '<img src="' + (user.portrait || '/static/image/default.png') + '" class="user-avatar"/>';
            html += '<div class="user-info">';
            html += '<div class="user-nickname">' + (user.nickname || user.username) + '</div>';
            html += '</div>';
            html += '<div class="user-actions">';
            html += '<button onclick="unfollowUser(' + user.userId + ')">取消关注</button>';
            html += '</div>';
            html += '</div>';
        }
        
        document.getElementById('user_list').innerHTML = html || '暂无关注';
        renderPagination(pageData);
    }
    
    function renderPagination(pageData) {
        var html = '';
        if(pageData.currentPage > 1) {
            html += '<a href="javascript:goToPage(' + (pageData.currentPage - 1) + ')">上一页</a>';
        }
        html += '<span>第 ' + pageData.currentPage + ' / ' + pageData.totalPage + ' 页</span>';
        if(pageData.currentPage < pageData.totalPage) {
            html += '<a href="javascript:goToPage(' + (pageData.currentPage + 1) + ')">下一页</a>';
        }
        document.getElementById('pagination').innerHTML = html;
    }
    
    function goToPage(page) {
        currentPage = page;
        loadFollowing();
    }
    
    function unfollowUser(userId) {
        fetch('/api/friend/follow/' + userId, {method: 'DELETE'})
        .then(response => response.json())
        .then(data => {
            alert(data.msg);
            if(data.code === 200) {
                loadFollowing();
            }
        });
    }
    
    function doLogout() {
        fetch('/api/user/logout', {method: 'POST'})
        .then(response => response.json())
        .then(data => { window.location.href = '/login'; });
    }
</script>
<style>
.user-item {
    display: flex;
    align-items: center;
    border: 1px solid #ddd;
    margin: 10px;
    padding: 15px;
    background: #fff;
}
.user-avatar {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    margin-right: 15px;
}
.user-info {
    flex: 1;
}
.user-nickname {
    font-weight: bold;
}
#pagination {
    text-align: center;
    margin: 20px;
}
</style>
</html>
