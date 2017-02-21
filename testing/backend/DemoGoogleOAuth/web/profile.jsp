<%-- 
    Document   : profile
    Created on : Feb 20, 2017, 9:23:15 AM
    Author     : nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Google Profile</title>
    </head>
    <body>
        <h1>Your Google Profile</h1>
        <p>ID: ${profile.id}</p>
        <p>Name: ${profile.name}</p>
        <p>Email: ${profile.email}</p>
    </body>
</html>
