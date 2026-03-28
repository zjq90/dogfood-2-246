<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册豆瓣</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/image/icon/iDouBan_favicon.ico" type="image/x-icon">
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            background-color: #edf4ed;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        #register {
            background-color: #f8f8f8;
            width: 400px;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }
        
        h1 {
            text-align: center;
            color: #2ca160;
            margin-bottom: 30px;
        }
        
        #register input[type="text"],
        #register input[type="password"] {
            width: 100%;
            height: 45px;
            padding: 0 15px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            outline: none;
            transition: border-color 0.3s;
        }
        
        #register input:focus {
            border-color: #0091ff;
            box-shadow: 0 0 5px rgba(0, 145, 255, 0.3);
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
            margin-bottom: 15px;
        }
        
        #entry:hover {
            background-color: #0078d4;
        }
        
        .login-link {
            text-align: center;
            margin-top: 15px;
        }
        
        .login-link a {
            color: #0091ff;
            text-decoration: none;
        }
        
        .login-link a:hover {
            text-decoration: underline;
        }
        
        #msg {
            text-align: center;
            margin-top: 10px;
            min-height: 20px;
        }
        
        #errorMsg {
            color: #ff4d4f;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <form id="register_form">
        <div id="register">
            <h1>注册账号</h1>
            
            <input type="text" id="uname" name="username" placeholder="请输入邮箱" onblur="isEmail(this.value)"/>
            
            <input type="password" id="upwd" name="password" placeholder="请输入密码" 
                pattern="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$"
                title="请输入6-20个字母、数字、下划线作为密码"
                onblur="isPassword(this.value)"/>
            
            <input type="password" id="upwd1" name="password2" placeholder="请再次输入密码" onblur="isRepeat()"/>
            
            <input type="button" id="entry" value="注册" onclick="return doRegister()"/>
            
            <div class="login-link">
                <a href="${pageContext.request.contextPath}/login">已有帐号？登录</a>
            </div>
            
            <div id="msg">
                <span id="errorMsg"></span>
            </div>
        </div>
    </form>

    <script type="text/javascript">
        function doRegister() {
            var username = document.getElementById("uname").value;
            var password = document.getElementById("upwd").value;
            var password2 = document.getElementById("upwd1").value;
            
            if (username === "") {
                document.getElementById("errorMsg").innerText = "请输入用户名";
                return false;
            }
            if (password === "") {
                document.getElementById("errorMsg").innerText = "请输入密码";
                return false;
            }
            if (password2 === "") {
                document.getElementById("errorMsg").innerText = "请再次输入密码";
                return false;
            }
            if (password !== password2) {
                document.getElementById("errorMsg").innerText = "两次输入密码不一致！";
                return false;
            }
            
            fetch("${pageContext.request.contextPath}/user/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.code === 200) {
                    alert("注册成功！");
                    window.location.href = "${pageContext.request.contextPath}/login";
                } else {
                    document.getElementById("errorMsg").innerText = data.msg || "注册失败";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                document.getElementById("errorMsg").innerText = "注册失败，请稍后重试";
            });
            
            return false;
        }
        
        function isEmail(strEmail) {
            var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            if (strEmail !== null && strEmail !== "" && strEmail.search(reg) === -1) {
                document.getElementById("errorMsg").innerText = "请输入正确的邮箱格式";
                return false;
            }
            return true;
        }

        function isPassword(strPwd) {
            var passwordReg = /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,20}$/;
            if (strPwd !== "" && strPwd.search(passwordReg) === -1) {
                document.getElementById("errorMsg").innerText = "密码6-20位，只允许字母、数字、下划线其中两项";
                return false;
            }
            return true;
        }

        function isRepeat() {
            var upwd = document.getElementById("upwd").value;
            var upwd1 = document.getElementById("upwd1").value;
            if (upwd !== upwd1 && upwd1 !== "") {
                document.getElementById("errorMsg").innerText = "两次输入密码不一致！";
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
