<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>豆邮</title>
<link rel="icon" href="/static/image/icon/weibo_favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="/static/css/common.css">
<link rel="stylesheet" type="text/css" href="/static/css/my_page.css">
</head>
<body>
<nav id="first">
    <div id="first_menu">
        <a href="/api/user/logout" onclick="doLogout()">退出登录</a>
        <a href="/my_page">个人主页</a>
        <a href="/doumail">豆邮</a>
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
    <h2>豆邮</h2>
    <div id="contact_list"></div>
</div>
</body>
<script type="text/javascript">
    window.onload = function() {
        loadContacts();
    };
    
    function loadContacts() {
        fetch('/api/doumail/contacts')
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                renderContacts(data.data);
            } else {
                document.getElementById('contact_list').innerHTML = data.msg;
            }
        });
    }
    
    function renderContacts(contacts) {
        var html = '';
        var addedUsers = {};
        
        for(var i = 0; i < contacts.length; i++) {
            var contact = contacts[i];
            var userId = contact.fromUserId;
            var nick = contact.fromUserNick;
            var img = contact.fromUserImg;
            
            if(addedUsers[userId]) continue;
            addedUsers[userId] = true;
            
            html += '<div class="contact-item" onclick="openChat(' + userId + ')">';
            html += '<img src="' + (img || '/static/image/default.png') + '" class="contact-avatar"/>';
            html += '<div class="contact-info">';
            html += '<div class="contact-nickname">' + (nick || '匿名用户') + '</div>';
            html += '<div class="contact-msg">' + truncate(contact.chatMsg, 30) + '</div>';
            html += '</div>';
            html += '</div>';
        }
        
        document.getElementById('contact_list').innerHTML = html || '暂无豆邮';
    }
    
    function openChat(userId) {
        window.location.href = '/doumail_show?toUserId=' + userId;
    }
    
    function truncate(str, len) {
        if(!str) return '';
        if(str.length > len) return str.substring(0, len) + '...';
        return str;
    }
    
    function doLogout() {
        fetch('/api/user/logout', {method: 'POST'})
        .then(response => response.json())
        .then(data => { window.location.href = '/login'; });
    }
</script>
<style>
.contact-item {
    display: flex;
    align-items: center;
    border: 1px solid #ddd;
    margin: 10px;
    padding: 15px;
    background: #fff;
    cursor: pointer;
}
.contact-item:hover {
    background: #f5f5f5;
}
.contact-avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 15px;
}
.contact-info {
    flex: 1;
}
.contact-nickname {
    font-weight: bold;
}
.contact-msg {
    color: #888;
    font-size: 14px;
}
</style>
</html>
