<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的主页</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/image/icon/iDouBan_favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/my_page.css">
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            background-color: #edf4ed;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        }
        
        nav#first {
            background-color: #2ca160;
            padding: 10px 0;
        }
        
        #first_menu {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 10px;
        }
        
        #first_menu a {
            color: white;
            text-decoration: none;
            padding: 8px 15px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        
        #first_menu a:hover {
            background-color: rgba(255, 255, 255, 0.2);
        }
        
        header#second {
            background-color: white;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        
        #second_menu {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            align-items: center;
            padding: 15px 20px;
        }
        
        .logo {
            margin-right: 30px;
        }
        
        .logo img {
            height: 50px;
            width: auto;
        }
        
        .navbar ul {
            display: flex;
            list-style: none;
            gap: 20px;
        }
        
        .navbar a {
            color: #333;
            text-decoration: none;
            font-size: 16px;
        }
        
        .navbar a:hover {
            color: #2ca160;
        }
        
        .search {
            margin-left: auto;
        }
        
        .search input[type="text"] {
            padding: 8px 15px;
            border: 1px solid #ddd;
            border-radius: 20px 0 0 20px;
            outline: none;
            width: 250px;
        }
        
        .search input[type="submit"] {
            padding: 8px 20px;
            background-color: #2ca160;
            color: white;
            border: none;
            border-radius: 0 20px 20px 0;
            cursor: pointer;
        }
        
        #main_content {
            max-width: 1200px;
            margin: 20px auto;
            display: flex;
            gap: 20px;
            padding: 0 20px;
        }
        
        #main_content-left {
            flex: 1;
        }
        
        #main_content-left-top {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        
        #welcome_msg {
            font-size: 18px;
            margin-bottom: 15px;
            color: #333;
        }
        
        #user_img {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            display: block;
            margin: 0 auto 20px;
        }
        
        #user_info_show {
            text-align: left;
        }
        
        #user_info_show > div:first-child {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        
        #user_info_show > div {
            margin-bottom: 10px;
            color: #666;
        }
        
        #user_info_show span {
            color: #333;
        }
        
        #main_content-right {
            flex: 2;
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            min-height: 400px;
        }
    </style>
</head>
<body>
    <nav id="first">
        <div id="first_menu">
            <a href="${pageContext.request.contextPath}/user/logout" onclick="doLogout()">退出登录</a>
            <a href="${pageContext.request.contextPath}/alter">账号管理</a>
            <a href="${pageContext.request.contextPath}/my_page">个人主页</a>
            <a href="${pageContext.request.contextPath}/doumail">豆邮</a>
            <a href="${pageContext.request.contextPath}/blacklist">黑名单</a>
            <a href="${pageContext.request.contextPath}/attention">我的关注</a>
            <a href="${pageContext.request.contextPath}/friend">我的好友</a>
            <a href="${pageContext.request.contextPath}/everyone">所有人</a>
            <a href="${pageContext.request.contextPath}/article_edit" target="_blank">写文章</a>
            <a href="${pageContext.request.contextPath}/article_list">所有文章</a>
            <a href="${pageContext.request.contextPath}/article_list?mine=true">我的文章</a>
            <a href="${pageContext.request.contextPath}/article_list?collection=true">我的收藏</a>
        </div>
    </nav>
    
    <header id="second">
        <nav id="second_menu">
            <div class="logo">
                <img alt="豆瓣logo" src="${pageContext.request.contextPath}/static/image/豆瓣首页logo.jpg">
            </div>
            <div class="navbar">
                <ul>
                    <li><a href="#">首页</a></li>
                    <li><a href="${pageContext.request.contextPath}/my_page">个人主页</a></li>
                    <li><a href="${pageContext.request.contextPath}/article_list">浏览发现</a></li>
                </ul>
            </div>
            <div class="search">
                <form onsubmit="searchContent(); return false;">
                    <input type="text" id="searchInput" placeholder="搜索你感兴趣的内容和人">
                    <input type="submit" value="搜索">
                </form>
            </div>
        </nav>
    </header>
    
    <div id="main_content">
        <div id="main_content-left">
            <div id="main_content-left-top">
                <div id="welcome_msg">
                    欢迎您：<span id="username"></span>
                </div>
                <img src="${pageContext.request.contextPath}/static/image/default.png" alt="我的头像" id="user_img"/>
                <div id="user_info_show">
                    <div>个人信息</div>
                    <div>昵称：<span id="nickname"></span></div>
                    <div>个性签名：<span id="signature"></span></div>
                    <div>自我介绍：<span id="selfIntroduc"></span></div>
                    <div>地址：<span id="address"></span></div>
                </div>
            </div>
        </div>
        
        <div id="main_content-right">
        </div>
    </div>

    <script type="text/javascript">
        window.onload = function() {
            loadUserInfo();
        };
        
        function loadUserInfo() {
            fetch("${pageContext.request.contextPath}/user/info")
            .then(response => response.json())
            .then(data => {
                if (data.code === 200) {
                    var user = data.data;
                    document.getElementById("username").innerText = user.username || user.nickname || "用户";
                    document.getElementById("nickname").innerText = user.nickname || "未设置";
                    document.getElementById("signature").innerText = user.signature || "未设置";
                    document.getElementById("selfIntroduc").innerText = user.selfIntroduc || "未设置";
                    document.getElementById("address").innerText = user.address || "未设置";
                    if (user.portrait) {
                        document.getElementById("user_img").src = user.portrait;
                    } else {
                        document.getElementById("user_img").src = "${pageContext.request.contextPath}/static/image/default.png";
                    }
                } else {
                    alert("获取用户信息失败：" + data.msg);
                    window.location.href = "${pageContext.request.contextPath}/login";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                window.location.href = "${pageContext.request.contextPath}/login";
            });
        }
        
        function doLogout() {
            fetch("${pageContext.request.contextPath}/user/logout", {
                method: "POST"
            })
            .then(response => {
                window.location.href = "${pageContext.request.contextPath}/login";
            })
            .catch(error => {
                console.error("Error:", error);
                window.location.href = "${pageContext.request.contextPath}/login";
            });
        }
        
        function searchContent() {
            var keyword = document.getElementById("searchInput").value;
            if (keyword) {
                window.location.href = "${pageContext.request.contextPath}/article_list?search=" + encodeURIComponent(keyword);
            }
        }
    </script>
</body>
</html>
