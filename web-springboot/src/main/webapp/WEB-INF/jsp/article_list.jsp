<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>文章列表</title>
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
            padding: 0 20px;
        }
        
        #article_list {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        
        .article-item {
            border: 1px solid #eee;
            margin-bottom: 15px;
            padding: 20px;
            cursor: pointer;
            border-radius: 8px;
            transition: box-shadow 0.3s;
        }
        
        .article-item:hover {
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }
        
        .article-item h3 {
            color: #333;
            margin-bottom: 10px;
            font-size: 18px;
        }
        
        .article-meta {
            color: #888;
            font-size: 13px;
            margin-bottom: 10px;
        }
        
        .article-meta span {
            margin-right: 20px;
        }
        
        .article-content {
            color: #666;
            font-size: 14px;
            line-height: 1.6;
        }
        
        #pagination {
            text-align: center;
            margin: 30px 0;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 15px;
        }
        
        #pagination a {
            padding: 8px 20px;
            background-color: #2ca160;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        
        #pagination a:hover {
            background-color: #238a4d;
        }
        
        #pagination span {
            color: #666;
        }
        
        .no-articles {
            text-align: center;
            padding: 50px;
            color: #999;
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
                <form onsubmit="searchArticles(); return false;">
                    <input type="text" id="searchContent" placeholder="搜索你感兴趣的内容">
                    <input type="submit" value="搜索">
                </form>
            </div>
        </nav>
    </header>
    
    <div id="main_content">
        <div id="article_list">
        </div>
        <div id="pagination">
        </div>
    </div>

    <script type="text/javascript">
        var currentPage = 1;
        var pageSize = 10;
        var searchKeyword = "";
        var loadMode = "all";
        
        window.onload = function() {
            var params = new URLSearchParams(window.location.search);
            var mine = params.get("mine");
            var collection = params.get("collection");
            var search = params.get("search");
            
            if (search) {
                searchKeyword = search;
                document.getElementById("searchContent").value = search;
                loadMode = "search";
            } else if (mine === "true") {
                loadMode = "mine";
            } else if (collection === "true") {
                loadMode = "collection";
            }
            
            loadArticles();
        };
        
        function loadArticles() {
            var url = "";
            
            if (loadMode === "mine") {
                url = "${pageContext.request.contextPath}/article/my?currentPage=" + currentPage + "&pageSize=" + pageSize;
            } else if (loadMode === "collection") {
                url = "${pageContext.request.contextPath}/article/collection?currentPage=" + currentPage + "&pageSize=" + pageSize;
            } else if (loadMode === "search" && searchKeyword) {
                url = "${pageContext.request.contextPath}/article/search?searchContent=" + encodeURIComponent(searchKeyword) + "&currentPage=" + currentPage + "&pageSize=" + pageSize;
            } else {
                url = "${pageContext.request.contextPath}/article/list?currentPage=" + currentPage + "&pageSize=" + pageSize;
            }
            
            fetch(url)
            .then(response => response.json())
            .then(data => {
                if (data.code === 200) {
                    renderArticles(data.data);
                } else {
                    document.getElementById("article_list").innerHTML = "<div class='no-articles'>" + (data.msg || "获取文章列表失败") + "</div>";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                document.getElementById("article_list").innerHTML = "<div class='no-articles'>加载失败，请稍后重试</div>";
            });
        }
        
        function renderArticles(pageData) {
            var articles = pageData.objects || pageData.list || [];
            var html = "";
            
            if (articles.length === 0) {
                html = "<div class='no-articles'>暂无文章</div>";
            } else {
                for (var i = 0; i < articles.length; i++) {
                    var article = articles[i];
                    html += "<div class='article-item' onclick='viewArticle(" + article.articleId + ")'>";
                    html += "<h3>" + escapeHtml(article.title) + "</h3>";
                    html += "<div class='article-meta'>";
                    html += "<span>作者：" + escapeHtml(article.authorNick || article.authorName || "未知") + "</span>";
                    html += "<span>发布时间：" + formatDate(article.publishedTime) + "</span>";
                    html += "<span>阅读：" + (article.pageView || 0) + "</span>";
                    html += "<span>点赞：" + (article.starNum || 0) + "</span>";
                    html += "<span>评论：" + (article.commentNum || 0) + "</span>";
                    html += "</div>";
                    html += "<div class='article-content'>" + truncate(article.content, 200) + "</div>";
                    html += "</div>";
                }
            }
            
            document.getElementById("article_list").innerHTML = html;
            renderPagination(pageData);
        }
        
        function renderPagination(pageData) {
            var totalPage = pageData.totalPage || Math.ceil((pageData.total || 0) / pageSize);
            var current = pageData.currentPage || currentPage;
            
            var html = "";
            if (current > 1) {
                html += "<a href='javascript:goToPage(" + (current - 1) + ")'>上一页</a>";
            }
            html += "<span>第 " + current + " / " + (totalPage || 1) + " 页</span>";
            if (current < totalPage) {
                html += "<a href='javascript:goToPage(" + (current + 1) + ")'>下一页</a>";
            }
            document.getElementById("pagination").innerHTML = html;
        }
        
        function goToPage(page) {
            currentPage = page;
            loadArticles();
        }
        
        function searchArticles() {
            searchKeyword = document.getElementById("searchContent").value;
            currentPage = 1;
            loadMode = searchKeyword ? "search" : "all";
            loadArticles();
        }
        
        function viewArticle(articleId) {
            window.location.href = "${pageContext.request.contextPath}/article_show?id=" + articleId;
        }
        
        function formatDate(dateStr) {
            if (!dateStr) return "";
            var date = new Date(dateStr);
            return date.getFullYear() + "-" + padZero(date.getMonth() + 1) + "-" + padZero(date.getDate());
        }
        
        function padZero(num) {
            return num < 10 ? "0" + num : num;
        }
        
        function truncate(str, len) {
            if (!str) return "";
            str = str.replace(/<[^>]+>/g, "");
            if (str.length > len) {
                return str.substring(0, len) + "...";
            }
            return str;
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
    </script>
</body>
</html>
