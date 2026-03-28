<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录豆瓣</title>
<link rel="icon" href="/static/image/icon/weibo_favicon.ico" type="image/x-icon">

<style type="text/css">
body {
	margin: 0;
	background-color: #22C3AA;
	background-repeat:no-repeat;
    background-position:0% 0%;
	background-size:contain;
}
</style>
<style type="text/css">
		*{
			margin:0;
			padding:0;
		}
		ul{
			list-style:none;
		}
		.clearfix:before,.clear:after{
			display:table;
			content:"";
		}
		.clearfix:after{
			clear:both;
		}
		.clearfix{
			*zoom:1;
		}
		 body{
		   background-color:#edf4ed;
		
		}
		 a:link,a:visited{
		  text-decoration:none;
		  } 
</style>
</head>
<style type="text/css">
#login {
    background-color: #f8f8f8;
    width: 400px;
    height: 400px;
    float: right;
    margin-right: 100px;
    border-radius: 15px;
    margin-top: 150px;
}
#header_h1{
	 padding: 20px 128px;
	 color: #2ca160;
}
#uname,#upwd {
    margin-bottom: 10px;
    outline: none;
    border-radius: 3px;
    text-decoration: none;
    border-style: none;
    width: 300px;
    height: 42px;
    display: block;
    color: black;
    font-size: 14px;
}

#uname,#upwd{
    background-color: ;
    padding-left: 10px;

}
#entry{
    background-color: #0091ff;
          width: 300px;
    height: 42px;
    margin-bottom: 10px;
    margin-left:50px;
    
    outline: none;
    border-radius: 3px;
    
    
    text-decoration: none;
    border-style: none;
    font-size: 14px;
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

.login_level{
	 width: 350px;
	 height: 50px;
	 margin-left:50px;
	 center:center;
}
#select{
	 width: 350px;
	 height: 30px;
	 margin-left:50px;
	 center:center;

}

#login_bottom_find{
      margin-left: 50px;
    border-style: none;
    width: 300px;
    height: 42px;
    color: black;
    font-size: 14px;
}
#login_bottom_sign{
  color:blue;
      margin-left:180px;
    border-style: none;
    width: 300px;
    height: 42px;
    color: black;
    font-size: 14px;
}
</style>
<form action="/user/login" method="post" id="login_form">
<div id="login">
		<div ><h1 id="header_h1">登录豆瓣</h1>
		    </div>
	<div id="name" class="login_level">
	   <input type="text" id="uname" name="uname" placeholder="请输入用户名" value="${rememberedUsername}">
	 </div>
	 <div id="pwd" class="login_level">  
	   <input type="password" id="upwd" name="upwd" placeholder="请输入密码" value="${rememberedPassword}"> 
	 </div>
	 
	 <div  id="select"> 
		 <label>
			    <input type="checkbox" name="auto" id="auto"/>自动登录 
	     </label>
	      <label>
			    <input type="checkbox" name="remember" value="1" id="remember"/>记住密码
		 </label>
	 </div>
		 <div>
			    <input type="submit" value="登录" id="entry" />
		</div>
		
		<div>
			    <a href="/user/register" id="login_bottom_find"><font color=blue>忘记密码</font></a>
				<a href="/user/register" id="login_bottom_sign"><font color=blue>注册账号</font></a>
	    </div>
	    <div id="msg" class="login_level">
	    	<font color="red">${errorMsg}</font> 
	    </div>
	</div>
</form>

<script type="text/javascript">
    // 回显记住的用户名和密码
    window.onload = function() {
        var errorMsg = document.getElementById('msg').getElementsByTagName('font')[0];
        if (errorMsg && errorMsg.innerText.trim() === '') {
            errorMsg.style.display = 'none';
        }
    }
</script>

</body>
</html>
