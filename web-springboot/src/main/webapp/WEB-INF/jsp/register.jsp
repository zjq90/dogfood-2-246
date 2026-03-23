<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册</title>
<link rel="icon" href="/static/image/icon/weibo_favicon.ico" type="image/x-icon">
</head>
<style type="text/css">
 body{
   background-color: #daecda;
}
 #register{
    height: 400px;
    width: 390px;
    background: #f8f8f8;
    text-align: center;
    position: absolute;
    left: 50%;
    top: 50%;
	border-radius: 20px;
    transform: translate(-50%,-50%);

}
#register>input{
    outline: none;
    border-radius: 3px;
    text-decoration: none;
    border-style: none;
    width: 300px;
    height: 42px;
    display: block;
    margin: auto;
    color: black;
    font-size: 14px;
}

#uname{
    background-color: ;
    padding-left: 10px;

}


#upwd{
    background-color: ;
    padding-left: 10px;

}
#upwd1{
    background-color: ;
    padding-left: 10px;
	
}

#entry{
    background-color: #0091ff;
    
    color: white;
}

#uname:focus {
    background: white;
    border: 1px solid grey;
}

#upwd:focus {
    background: white;
    border: 1px solid grey;
}
#entry:hover{
    background: blue;
}
#verify>input{
    margin-bottom: 10px;
    outline: none;
    border-radius: 3px;
    text-decoration: none;
    border-style: none;
    width: 200px;
    height: 42px;
    color: black;
    font-size: 14px;
}  

  a:link,a:visited{
  text-decoration:none;
  } 
</style>

<body>

	<form id="register_form">
		<div id="register">
			<div style="height: 60px;">
				<h1>注册账号</h1>
			</div>
			    <input type = "text" id="uname" name ="username" placeholder="请输入邮箱"  
			    	 onblur="isEmail(this.value)" /><br/> 
			    <input type = "password"  id="upwd" name ="password" placeholder="请输入密码" 
			    	pattern="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$"
			    			title="请输入6-20个字母、数字、下划线 作为密码"
			    			onblur="isPassword(this.value)"/><br/>
				<input type = "password"  id="upwd1" name ="password2" placeholder="请再次输入密码"  onblur="isRepeat()"/><br/>
				
				<input type = "button" id="entry" value = "注册"  onclick="return doRegister()"><br/>
			<a href="/login"><font color=blue>已有帐号？登录</font></a>
			<div id="msg" class="register_level">
			    <font color="red" id="errorMsg"></font> 
			</div>
		</div>
	</form>
<script type = "text/javascript">
    function doRegister(){
    	var username = document.getElementById("uname").value;
        var password = document.getElementById("upwd").value;
        var password2 = document.getElementById("upwd1").value;
        
        if(username == ""){
            alert("请输入用户名");
            return false;
        }
        if(password == ""){
            alert("请输入密码");
            return false;
        }
        if(password2 == ""){
            alert("请再次输入密码");
            return false;
        }
        if(password != password2) {
			alert("两次输入密码不一致！");
			return false;
		}
        
        fetch('/api/user/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                alert('注册成功！');
                window.location.href = '/login';
            } else {
                document.getElementById('errorMsg').innerText = data.msg;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('注册失败，请稍后重试');
        });
        
        return false;
    }
	
  	function isEmail(strEmail){
  		var reg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
  		if(strEmail != null && strEmail.search(reg) != -1)
  		{ 
  			
  		}
  		else{
  			alert("请输入正确的邮箱格式");
  			document.getElementById("uname").value="";
  			return false;
  		}
  	}

	function isPassword(strPwd){
		var passwordReg=/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,20}$/;
  		if(strPwd != "" && strPwd.search(passwordReg) != -1)
  		{
  		}else{
  			alert("密码6-20位，只允许字母、数字、下划线其中两项!!!");
  			return false;
  		}
  	}

	function isRepeat() {
		var upwd = document.getElementById("upwd");
		var upwd1 = document.getElementById("upwd1");
		if(upwd.value != upwd1.value) {
			alert("两次输入密码不一致！")
			upwd.value  = "";
			upwd1.value = "";
		}
	}

</script>
</body>
</html>
