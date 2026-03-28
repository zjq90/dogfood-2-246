<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>文章详情</title>
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
        
        .logo img {
            height: 50px;
            width: auto;
        }
        
        #main_content {
            max-width: 900px;
            margin: 20px auto;
            padding: 0 20px;
        }
        
        #article_detail {
            background-color: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }
        
        #article_title {
            font-size: 28px;
            color: #333;
            margin-bottom: 15px;
            line-height: 1.4;
        }
        
        #article_meta {
            color: #888;
            font-size: 14px;
            padding-bottom: 20px;
            border-bottom: 1px solid #eee;
            margin-bottom: 20px;
        }
        
        #article_meta span {
            margin-right: 25px;
        }
        
        #article_content {
            line-height: 1.8;
            font-size: 16px;
            color: #333;
        }
        
        #article_actions {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
            display: flex;
            gap: 15px;
        }
        
        #article_actions button {
            padding: 10px 25px;
            border: 1px solid #2ca160;
            background-color: white;
            color: #2ca160;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        #article_actions button:hover {
            background-color: #2ca160;
            color: white;
        }
        
        #comments {
            background-color: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        
        #comments h3 {
            font-size: 20px;
            margin-bottom: 20px;
            color: #333;
        }
        
        #comment_form {
            margin-bottom: 25px;
        }
        
        #comment_content {
            width: 100%;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            resize: vertical;
            font-size: 14px;
        }
        
        #comment_form button {
            margin-top: 10px;
            padding: 10px 30px;
            background-color: #2ca160;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        
        .comment-item {
            padding: 15px 0;
            border-bottom: 1px solid #eee;
        }
        
        .comment-item:last-child {
            border-bottom: none;
        }
        
        .comment-user {
            font-weight: bold;
            color: #333;
            margin-bottom: 8px;
        }
        
        .comment-content {
            color: #666;
            line-height: 1.6;
            margin-bottom: 8px;
        }
        
        .comment-meta {
            color: #999;
            font-size: 12px;
        }
        
        .no-comments {
            text-align: center;
            color: #999;
            padding: 30px;
        }
    </style>
</head>
<body>
    <nav id="first">
        <div id="first_menu">
            <a href="${pageContext.request.contextPath}/user/logout" onclick="doLogout()">退出登录</a>
            <a href="${pageContext.request.contextPath}/alter">账号管理</a>
            <a href="${pageContext.request.contextPath}/my_page">个人主页</a>
            <a href="${pageContext.request.contextPath}/article_list">所有文章</a>
        </div>
    </nav>
    
    <header id="second">
        <nav id="second_menu">
            <div class="logo">
                <img alt="豆瓣logo" src="${pageContext.request.contextPath}/static/image/豆瓣首页logo.jpg">
            </div>
        </nav>
    </header>
    
    <div id="main_content">
        <div id="article_detail">
            <h1 id="article_title"></h1>
            <div id="article_meta">
                <span>作者：<span id="author_nick"></span></span>
                <span>发布时间：<span id="published_time"></span></span>
                <span>阅读：<span id="page_view"></span></span>
            </div>
            <div id="article_content"></div>
            <div id="article_actions">
                <button onclick="starArticle()">点赞 (<span id="star_num">0</span>)</button>
                <button onclick="collectArticle()">收藏 (<span id="collection_num">0</span>)</button>
                <button onclick="shareArticle()">转发 (<span id="share_num">0</span>)</button>
            </div>
        </div>
        
        <div id="comments">
            <h3>评论区</h3>
            <div id="comment_form">
                <textarea id="comment_content" rows="4" placeholder="写下你的评论..."></textarea>
                <button onclick="addComment()">发表评论</button>
            </div>
            <div id="comment_list"></div>
        </div>
    </div>

    <script type="text/javascript">
        var articleId;
        
        window.onload = function() {
            var params = new URLSearchParams(window.location.search);
            articleId = params.get("id");
            if (articleId) {
                loadArticle();
                loadComments();
            }
        };
        
        function loadArticle() {
            fetch("${pageContext.request.contextPath}/article/" + articleId)
            .then(response => response.json())
            .then(data => {
                if (data.code === 200) {
                    var article = data.data;
                    document.getElementById("article_title").innerText = article.title;
                    document.getElementById("author_nick").innerText = article.authorNick || "未知";
                    document.getElementById("published_time").innerText = formatDate(article.publishedTime);
                    document.getElementById("page_view").innerText = article.pageView || 0;
                    document.getElementById("star_num").innerText = article.starNum || 0;
                    document.getElementById("collection_num").innerText = article.collectionNum || 0;
                    document.getElementById("share_num").innerText = article.shareNum || 0;
                    document.getElementById("article_content").innerHTML = article.content || "";
                } else {
                    alert("获取文章详情失败：" + data.msg);
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
        }
        
        function loadComments() {
            fetch("${pageContext.request.contextPath}/interaction/comments/" + articleId)
            .then(response => response.json())
            .then(data => {
                if (data.code === 200) {
                    renderComments(data.data);
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
        }
        
        function renderComments(comments) {
            if (!comments || comments.length === 0) {
                document.getElementById("comment_list").innerHTML = "<div class='no-comments'>暂无评论，快来发表第一条评论吧！</div>";
                return;
            }
            
            var html = "";
            for (var i = 0; i < comments.length; i++) {
                var comment = comments[i];
                html += "<div class='comment-item'>";
                html += "<div class='comment-user'>" + escapeHtml(comment.userComNick || "匿名") + "</div>";
                html += "<div class='comment-content'>" + escapeHtml(comment.comMsg) + "</div>";
                html += "<div class='comment-meta'>" + formatDate(comment.comTime) + " | 点赞: " + (comment.comStar || 0) + "</div>";
                html += "</div>";
            }
            document.getElementById("comment_list").innerHTML = html;
        }
        
        function starArticle() {
            fetch("${pageContext.request.contextPath}/interaction/star/" + articleId, {method: "POST"})
            .then(response => response.json())
            .then(data => {
                alert(data.msg || (data.code === 200 ? "操作成功" : "操作失败"));
                if (data.code === 200) {
                    loadArticle();
                }
            });
        }
        
        function collectArticle() {
            fetch("${pageContext.request.contextPath}/interaction/collect/" + articleId, {method: "POST"})
            .then(response => response.json())
            .then(data => {
                alert(data.msg || (data.code === 200 ? "操作成功" : "操作失败"));
                if (data.code === 200) {
                    loadArticle();
                }
            });
        }
        
        function shareArticle() {
            fetch("${pageContext.request.contextPath}/interaction/share/" + articleId, {method: "POST"})
            .then(response => response.json())
            .then(data => {
                alert(data.msg || (data.code === 200 ? "操作成功" : "操作失败"));
                if (data.code === 200) {
                    loadArticle();
                }
            });
        }
        
        function addComment() {
            var content = document.getElementById("comment_content").value.trim();
            if (!content) {
                alert("请输入评论内容");
                return;
            }
            
            fetch("${pageContext.request.contextPath}/interaction/comment", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({articleId: articleId, comMsg: content})
            })
            .then(response => response.json())
            .then(data => {
                alert(data.msg || (data.code === 200 ? "评论成功" : "评论失败"));
                if (data.code === 200) {
                    document.getElementById("comment_content").value = "";
                    loadComments();
                    loadArticle();
                }
            });
        }
        
        function formatDate(dateStr) {
            if (!dateStr) return "";
            var date = new Date(dateStr);
            return date.getFullYear() + "-" + padZero(date.getMonth() + 1) + "-" + padZero(date.getDate());
        }
        
        function padZero(num) {
            return num < 10 ? "0" + num : num;
        }
        
        function escapeHtml(str) {
            if (!str) return "";
            return str.replace(/&/g, "&amp;")
                      .replace(/</g, "&lt;")
                      .replace(/>/g, "&gt;")
                      .replace(/"/g, "&quot;")
                      .replace(/'/g, "&#039;");
        }
        
        function doLogout() {
            fetch("${pageContext.request.contextPath}/user/logout", {method: "POST"})
            .then(response => {
                window.location.href = "${pageContext.request.contextPath}/login";
            });
        }
    </script>
</body>
</html>
