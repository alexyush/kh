<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "spring.ftl" as spring/> 
<!DOCTYPE html>
<html lang="en" xmlns:form="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8"/> 
    <link rel="stylesheet" href="${rc.getContextPath()}/css/style.css"/> 
</head>  
<BODY>      
<div class="container">
       <header>
        <h1>
         You are Welcome
           <#if Session.user?exists> 
          <div class="logout">
            <span id="currentUserLogin">
               ${Session.user.getLogin()}
            </span>
            <a href="${rc.getContextPath()}/logout">
              <i class="icon-off"></i>
            </a>
          </div>
          </#if>
        </h1>
      </header> 
      	<div class="form-actions"> 
      		<a id="createCourseButton" class="btn btn-primary" href="${rc.getContextPath()}/courses/">Courses</a>            
            <a id="createCourseButton" class="btn btn-danger" href="${rc.getContextPath()}/">About</a>
          </div> 
      	<img style="display:block; margin:0 auto;" src="${rc.getContextPath()}/css/404.jpg"><br>   
</BODY>
</HTML>
 