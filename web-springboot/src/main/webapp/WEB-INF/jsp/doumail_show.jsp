<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>豆邮对话</title>
<link rel="icon" href="/static/image/icon/weibo_favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="/static/css/common.css">
<link rel="stylesheet" type="text/css" href="/static/css/my_page.css">
</head>
<body>
<nav id="first">
    <div id="first_menu">
        <a href="/doumail">返回豆邮列表</a>
        <a href="/my_page">个人主页</a>
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
    <div id="chat_container">
        <div id="chat_messages"></div>
        <div id="chat_input">
            <textarea id="message_content" rows="3" placeholder="输入消息..."></textarea>
            <button onclick="sendMessage()">发送</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var toUserId;
    var currentPage = 1;
    
    window.onload = function() {
        var params = new URLSearchParams(window.location.search);
        toUserId = params.get('toUserId');
        if(toUserId) {
            loadMessages();
        }
    };
    
    function loadMessages() {
        fetch('/api/doumail/list/' + toUserId + '?currentPage=' + currentPage + '&pageSize=50')
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                renderMessages(data.data.objects);
            }
        });
    }
    
    function renderMessages(messages) {
        var html = '';
        for(var i = 0; i < messages.length; i++) {
            var msg = messages[i];
            var isMine = msg.fromUserId != toUserId;
            html += '<div class="message ' + (isMine ? 'message-mine' : 'message-other') + '">';
            html += '<div class="message-content">' + msg.chatMsg + '</div>';
            html += '<div class="message-time">' + formatTime(msg.chatTime) + '</div>';
            html += '</div>';
        }
        document.getElementById('chat_messages').innerHTML = html;
        var container = document.getElementById('chat_messages');
        container.scrollTop = container.scrollHeight;
    }
    
    function sendMessage() {
        var content = document.getElementById('message_content').value;
        if(!content) {
            alert('请输入消息内容');
            return;
        }
        
        fetch('/api/doumail/send', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                toUserId: toUserId,
                chatMsg: content
            })
        })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                document.getElementById('message_content').value = '';
                loadMessages();
            } else {
                alert('发送失败：' + data.msg);
            }
        });
    }
    
    function formatTime(timeStr) {
        if(!timeStr) return '';
        var date = new Date(timeStr);
        return date.getHours() + ':' + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
    }
</script>
<style>
#chat_container {
    max-width: 600px;
    margin: 20px auto;
    background: #fff;
}
#chat_messages {
    height: 400px;
    overflow-y: auto;
    padding: 20px;
    border: 1px solid #ddd;
}
.message {
    margin-bottom: 15px;
    max-width: 70%;
}
.message-mine {
    margin-left: auto;
    text-align: right;
}
.message-other {
    margin-right: auto;
}
.message-content {
    display: inline-block;
    padding: 10px 15px;
    border-radius: 10px;
    background: #e8e8e8;
}
.message-mine .message-content {
    background: #0091ff;
    color: #fff;
}
.message-time {
    font-size: 12px;
    color: #888;
    margin-top: 5px;
}
#chat_input {
    display: flex;
    padding: 10px;
    border: 1px solid #ddd;
    border-top: none;
}
#chat_input textarea {
    flex: 1;
    padding: 10px;
    resize: none;
}
#chat_input button {
    margin-left: 10px;
    padding: 10px 20px;
}
</style>
</html>
