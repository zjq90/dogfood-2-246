<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>文章列表</title>
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
		</body>
<script type="text/javascript">
    var currentPage = 1;
    var pageSize = 10;
    var searchContent = '';
    
    window.onload = function() {
        var params = new URLSearchParams(window.location.search);
        var mine = params.get('mine');
        var collection = params.get('collection');
        
        if(mine === 'true') {
            loadMyArticles();
        } else if(collection === 'true') {
            loadCollectionArticles();
        } else {
            loadArticles();
        }
    };
    
    function loadArticles() {
        var url = '/api/article/list?currentPage=' + currentPage + '&pageSize=' + pageSize;
        if(searchContent) {
            url = '/api/article/search?searchContent=' + encodeURIComponent(searchContent) + '&currentPage=' + currentPage + '&pageSize=' + pageSize;
        }
        
        fetch(url)
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                renderArticles(data.data);
            } else {
                alert('获取文章列表失败：' + data.msg);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    
    function loadMyArticles() {
        fetch('/api/article/my?currentPage=' + currentPage + '&pageSize=' + pageSize)
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                renderArticles(data.data);
            } else {
                alert('获取我的文章失败：' + data.msg);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    
    function loadCollectionArticles() {
        fetch('/api/article/collection?currentPage=' + currentPage + '&pageSize=' + pageSize)
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                renderArticles(data.data);
            } else {
                alert('获取收藏文章失败：' + data.msg);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    
    function renderArticles(pageData) {
        var articles = pageData.objects;
        var html = '';
        
        for(var i = 0; i < articles.length; i++) {
            var article = articles[i];
            html += '<div class="article-item" onclick="viewArticle(' + article.articleId + ')">';
            html += '<h3>' + article.title + '</h3>';
            html += '<div class="article-meta">';
            html += '<span>作者：' + (article.authorNick || '未知') + '</span>';
            html += '<span>发布时间：' + formatDate(article.publishedTime) + '</span>';
            html += '<span>阅读：' + (article.pageView || 0) + '</span>';
            html += '<span>点赞：' + (article.starNum || 0) + '</span>';
            html += '<span>评论：' + (article.commentNum || 0) + '</span>';
            html += '</div>';
            html += '<div class="article-content">' + truncate(article.content, 200) + '</div>';
            html += '</div>';
        }
        
        document.getElementById('article_list').innerHTML = html;
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
        loadArticles();
    }
    
    function searchArticles() {
        searchContent = document.getElementById('searchContent').value;
        currentPage = 1;
        loadArticles();
    }
    
    function viewArticle(articleId) {
        window.location.href = '/article_show?id=' + articleId;
    }
    
    function formatDate(dateStr) {
        if(!dateStr) return '';
        var date = new Date(dateStr);
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }
    
    function truncate(str, len) {
        if(!str) return '';
        str = str.replace(/<[^>]+>/g, '');
        if(str.length > len) {
            return str.substring(0, len) + '...';
        }
        return str;
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
<style>
.article-item {
    border: 1px solid #ddd;
    margin: 10px;
    padding: 15px;
    cursor: pointer;
    background: #fff;
}
.article-item:hover {
    background: #f5f5f5;
}
.article-meta {
    color: #888;
    font-size: 12px;
    margin: 10px 0;
}
.article-meta span {
    margin-right: 15px;
}
.article-content {
    color: #666;
    font-size: 14px;
}
#pagination {
    text-align: center;
    margin: 20px;
}
#pagination a, #pagination span {
    margin: 0 10px;
}
</style>
</html>
