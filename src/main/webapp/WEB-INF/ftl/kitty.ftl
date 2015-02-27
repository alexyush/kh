<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "spring.ftl" as spring/> 
<!DOCTYPE html>
<html lang="en" xmlns:form="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8"/> 
    <link rel="stylesheet" href="${rc.getContextPath()}/css/style.css"/> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
     <#--<script src="${rc.getContextPath()}/jq/getObject.js"></script> -->
</head>  
<BODY>      
<div class="container">
       <header>
        <h1>
         You are Welcome 
         </h1> 
      	
      	<div class="homelesskitty">
			<p class="kitty-id"></p>
        	<p class="kitty-name"></p>
			<p class="kitty-message"></p>
		</div>
		
       </div>
       
        <script type="text/javascript">
		$(document).ready(function() {
			    $.ajax({
			        url: "${rc.getContextPath()}/get/2"
			    }).then(function(data) {
			       $('.kitty-id').append(data.id);
			       $('.kitty-name').append(data.recordPhotoUrl);
			       $('.kitty-message').append(data.message);
			    });
			});
       </script>
      
       
</BODY>
</HTML>
 