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
         </h1> 
      	<img style="display:block; margin:0 auto;" src="${rc.getContextPath()}/css/epam.jpg"><br>  
      	
      	<#if kitties?exists>
	      	<#list kitties as x>
	      		
	      		${x.id} ${x.name} ${x.message}<br>
	      		 
	      	</#list>
      	</#if>
       </div>
       
</BODY>
</HTML>
 