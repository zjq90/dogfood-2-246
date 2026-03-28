<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录豆瓣</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/image/icon/iDouBan_favicon.ico" type="image/x-icon">
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            margin: 0;
            background-color: #edf4ed;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        .login-container {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
            max-width: 1200px;
            padding: 20px;
        }
        
        #login {
            background-color: #f8f8f8;
            width: 400px;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }
        
        #header_h1 {
            text-align: center;
            color: #2ca160;
            margin-bottom: 30px;
            font-size: 28px;
        }
        
        .login_level {
            margin-bottom: 15px;
        }
        
        #uname, #upwd {
            width: 100%;
            height: 45px;
            padding: 0 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            outline: none;
            transition: border-color 0.3s;
        }
        
        #uname:focus, #upwd:focus {
            border-color: #0091ff;
            box-shadow: 0 0 5px rgba(0, 145, 255, 0.3);
        }
        
        #select {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
            font-size: 14px;
            color: #666;
        }
        
        #select label {
            cursor: pointer;
        }
        
        #entry {
            width: 100%;
            height: 45px;
            background-color: #0091ff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        
        #entry:hover {
            background-color: #0078d4;
        }
        
        .login-links {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
            font-size: 14px;
        }
        
        .login-links a {
            color: #0091ff;
            text-decoration: none;
        }
        
        .login-links a:hover {
            text-decoration: underline;
        }
        
        #msg {
            margin-top: 15px;
            text-align: center;
            min-height: 20px;
        }
        
        #errorMsg {
            color: #ff4d4f;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div id="login">
            <h1 id="header_h1">登录豆瓣</h1>
            
            <div class="login_level">
                <input type="text" id="uname" name="username" placeholder="请输入用户名">
            </div>
            
            <div class="login_level">
                <input type="password" id="upwd" name="password" placeholder="请输入密码">
            </div>
            
            <div id="select">
                <label>
                    <input type="checkbox" name="auto" id="auto"/> 自动登录
                </label>
                <label>
                    <input type="checkbox" name="remember" id="remember"/> 记住密码
                </label>
            </div>
            
            <div class="login_level">
                <input type="button" onclick="doLogin()" value="登录" id="entry"/>
            </div>
            
            <div class="login-links">
                <a href="${pageContext.request.contextPath}/user/findPassword">忘记密码</a>
                <a href="${pageContext.request.contextPath}/user/register">注册账号</a>
            </div>
            
            <div id="msg">
                <span id="errorMsg"></span>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        function doLogin() {
            var username = document.getElementById("uname").value;
            var password = document.getElementById("upwd").value;
            var remember = document.getElementById("remember").checked;
            var auto = document.getElementById("auto").checked;
            
            if (username === "") {
                document.getElementById("errorMsg").innerText = "请输入用户名";
                return;
            }
            if (password === "") {
                document.getElementById("errorMsg").innerText = "请输入密码";
                return;
            }
            
            var formData = new URLSearchParams();
            formData.append("uname", username);
            formData.append("upwd", password);
            if (remember) formData.append("remember", "on");
            if (auto) formData.append("auto", "on");
            
            fetch("${pageContext.request.contextPath}/user/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: formData.toString()
            })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                } else {
                    return response.text().then(text => {
                        try {
                            return JSON.parse(text);
                        } catch (e) {
                            window.location.href = "${pageContext.request.contextPath}/page/home";
                        }
                    });
                }
            })
            .then(data => {
                if (data && data.code !== 200) {
                    document.getElementById("errorMsg").innerText = data.msg || "登录失败";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                document.getElementById("errorMsg").innerText = "登录失败，请稍后重试";
            });
        }
        
        document.getElementById("upwd").addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                doLogin();
            }
        });
    </script>
</body>
</html>
