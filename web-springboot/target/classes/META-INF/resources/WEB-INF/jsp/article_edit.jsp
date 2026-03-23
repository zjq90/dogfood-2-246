<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>写文章</title>
<link rel="icon" href="/static/image/icon/weibo_favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="/static/css/my_page.css">
</head>
<body>
<nav id="first">
    <div id="first_menu">
        <a href="/my_page">个人主页</a>
        <a href="/article_list">所有文章</a>
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
    <div id="article_form">
        <h2>写文章</h2>
        <form id="articleEditForm">
            <div class="form-group">
                <label>标题：</label>
                <input type="text" id="title" name="title" style="width: 100%; padding: 8px;"/>
            </div>
            <div class="form-group">
                <label>分类：</label>
                <select id="tagName" name="tagName" style="padding: 8px;">
                    <option value="生活">生活</option>
                    <option value="科技">科技</option>
                    <option value="文化">文化</option>
                    <option value="艺术">艺术</option>
                    <option value="其他">其他</option>
                </select>
            </div>
            <div class="form-group">
                <label>内容：</label>
                <textarea id="content" name="content" rows="15" style="width: 100%; padding: 8px;"></textarea>
            </div>
            <div class="form-group">
                <input type="button" value="发布文章" onclick="publishArticle()" style="padding: 10px 30px;"/>
            </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    function publishArticle() {
        var title = document.getElementById('title').value;
        var tagName = document.getElementById('tagName').value;
        var content = document.getElementById('content').value;
        
        if(!title) {
            alert('请输入文章标题');
            return;
        }
        if(!content) {
            alert('请输入文章内容');
            return;
        }
        
        fetch('/api/article/publish', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                title: title,
                tagName: tagName,
                content: content
            })
        })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                alert('发布成功！');
                window.opener.location.reload();
                window.close();
            } else {
                alert('发布失败：' + data.msg);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('发布失败，请稍后重试');
        });
    }
</script>
<style>
#article_form {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background: #fff;
}
.form-group {
    margin-bottom: 15px;
}
.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}
</style>
</html>
