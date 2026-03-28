<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>写文章</title>
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
        
        #article_form {
            background-color: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        
        #article_form h2 {
            color: #333;
            margin-bottom: 25px;
            font-size: 24px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
            font-size: 14px;
        }
        
        .form-group input[type="text"],
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            outline: none;
            transition: border-color 0.3s;
        }
        
        .form-group input:focus,
        .form-group select:focus,
        .form-group textarea:focus {
            border-color: #2ca160;
        }
        
        .form-group textarea {
            resize: vertical;
            min-height: 300px;
            line-height: 1.6;
        }
        
        .form-group input[type="button"] {
            padding: 12px 40px;
            background-color: #2ca160;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        
        .form-group input[type="button"]:hover {
            background-color: #238a4d;
        }
        
        .form-actions {
            margin-top: 30px;
            text-align: center;
        }
    </style>
</head>
<body>
    <nav id="first">
        <div id="first_menu">
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
        <div id="article_form">
            <h2>写文章</h2>
            <form id="articleEditForm">
                <div class="form-group">
                    <label>标题：</label>
                    <input type="text" id="title" name="title" placeholder="请输入文章标题"/>
                </div>
                <div class="form-group">
                    <label>分类：</label>
                    <select id="tagName" name="tagName">
                        <option value="生活">生活</option>
                        <option value="科技">科技</option>
                        <option value="文化">文化</option>
                        <option value="艺术">艺术</option>
                        <option value="其他">其他</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>内容：</label>
                    <textarea id="content" name="content" placeholder="请输入文章内容..."></textarea>
                </div>
                <div class="form-group form-actions">
                    <input type="button" value="发布文章" onclick="publishArticle()"/>
                </div>
            </form>
        </div>
    </div>

    <script type="text/javascript">
        function publishArticle() {
            var title = document.getElementById("title").value.trim();
            var tagName = document.getElementById("tagName").value;
            var content = document.getElementById("content").value.trim();
            
            if (!title) {
                alert("请输入文章标题");
                return;
            }
            if (!content) {
                alert("请输入文章内容");
                return;
            }
            
            fetch("${pageContext.request.contextPath}/article/publish", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    title: title,
                    tagName: tagName,
                    content: content
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.code === 200) {
                    alert("发布成功！");
                    if (window.opener) {
                        window.opener.location.reload();
                    }
                    window.location.href = "${pageContext.request.contextPath}/article_list";
                } else {
                    alert("发布失败：" + (data.msg || "未知错误"));
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("发布失败，请稍后重试");
            });
        }
    </script>
</body>
</html>
