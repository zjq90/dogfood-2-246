<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>文章详情</title>
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
					<a href="/article_list">所有文章</a>
				</div>
			</nav>
			
			<header id="second">
					<nav  id="second_menu">
							<div class="logo">
							 	<img alt="豆瓣logo" src="/static/image/豆瓣首页logo.jpg" width=175px height=58px>
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
				        <textarea id="comment_content" rows="3" cols="50" placeholder="写下你的评论..."></textarea>
				        <button onclick="addComment()">发表评论</button>
				    </div>
				    <div id="comment_list"></div>
				</div>
			</div>
		</body>
<script type="text/javascript">
    var articleId;
    
    window.onload = function() {
        var params = new URLSearchParams(window.location.search);
        articleId = params.get('id');
        if(articleId) {
            loadArticle();
            loadComments();
        }
    };
    
    function loadArticle() {
        fetch('/api/article/' + articleId)
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                var article = data.data;
                document.getElementById('article_title').innerText = article.title;
                document.getElementById('author_nick').innerText = article.authorNick || '未知';
                document.getElementById('published_time').innerText = formatDate(article.publishedTime);
                document.getElementById('page_view').innerText = article.pageView || 0;
                document.getElementById('star_num').innerText = article.starNum || 0;
                document.getElementById('collection_num').innerText = article.collectionNum || 0;
                document.getElementById('share_num').innerText = article.shareNum || 0;
                document.getElementById('article_content').innerHTML = article.content || '';
            } else {
                alert('获取文章详情失败：' + data.msg);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    
    function loadComments() {
        fetch('/api/interaction/comments/' + articleId)
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                renderComments(data.data);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    
    function renderComments(comments) {
        var html = '';
        for(var i = 0; i < comments.length; i++) {
            var comment = comments[i];
            html += '<div class="comment-item">';
            html += '<div class="comment-user">' + (comment.userComNick || '匿名') + '</div>';
            html += '<div class="comment-content">' + comment.comMsg + '</div>';
            html += '<div class="comment-meta">' + formatDate(comment.comTime) + ' | 点赞: ' + (comment.comStar || 0) + '</div>';
            html += '</div>';
        }
        document.getElementById('comment_list').innerHTML = html || '暂无评论';
    }
    
    function starArticle() {
        fetch('/api/interaction/star/' + articleId, {method: 'POST'})
        .then(response => response.json())
        .then(data => {
            alert(data.msg);
            if(data.code === 200) {
                loadArticle();
            }
        });
    }
    
    function collectArticle() {
        fetch('/api/interaction/collect/' + articleId, {method: 'POST'})
        .then(response => response.json())
        .then(data => {
            alert(data.msg);
            if(data.code === 200) {
                loadArticle();
            }
        });
    }
    
    function shareArticle() {
        fetch('/api/interaction/share/' + articleId, {method: 'POST'})
        .then(response => response.json())
        .then(data => {
            alert(data.msg);
            if(data.code === 200) {
                loadArticle();
            }
        });
    }
    
    function addComment() {
        var content = document.getElementById('comment_content').value;
        if(!content) {
            alert('请输入评论内容');
            return;
        }
        
        fetch('/api/interaction/comment', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({articleId: articleId, comMsg: content})
        })
        .then(response => response.json())
        .then(data => {
            alert(data.msg);
            if(data.code === 200) {
                document.getElementById('comment_content').value = '';
                loadComments();
                loadArticle();
            }
        });
    }
    
    function formatDate(dateStr) {
        if(!dateStr) return '';
        var date = new Date(dateStr);
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }
    
    function doLogout() {
        fetch('/api/user/logout', {method: 'POST'})
        .then(response => response.json())
        .then(data => { window.location.href = '/login'; });
    }
</script>
<style>
#article_detail {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background: #fff;
}
#article_meta {
    color: #888;
    margin: 15px 0;
}
#article_meta span { margin-right: 20px; }
#article_content {
    line-height: 1.8;
    margin: 20px 0;
}
#article_actions {
    margin: 20px 0;
}
#article_actions button {
    margin-right: 10px;
    padding: 8px 15px;
}
#comments {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background: #fff;
}
.comment-item {
    border-bottom: 1px solid #eee;
    padding: 10px 0;
}
.comment-user { font-weight: bold; }
.comment-meta { color: #888; font-size: 12px; margin-top: 5px; }
</style>
</html>
