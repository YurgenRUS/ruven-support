<%@ page import="com.voxme.webinv.utils.Localization" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Localization locale = (Localization) request.getAttribute("locale");
    String reason = (String) request.getAttribute("reason");
    String title = "WebINV";
    String redirectUrl = request.getContextPath() + "/home";
    if (reason.equals("company_disabled")) {
        title = "Company is not available";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0"/>
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <link rel="icon" href="../../favicon.ico">

    <title><%= title %></title>

    <link href="css/apologies.css" rel="stylesheet">

    <script src="js/jquery-2.1.4.min.js"></script>

    <script type="text/javascript">
        window.onload = function () {
            function countdown() {
                if (typeof countdown.counter == 'undefined') {
                    countdown.counter = 5; // initial count
                }
                if (countdown.counter > 0) {
                    document.getElementById('count').innerHTML = countdown.counter--;
                    setTimeout(countdown, 1000);
                }
                else {
                    location.href = '<%= redirectUrl %>';
                }
            }

            countdown();
        };
    </script>
</head>

<body>
<div id="content">
    <div id="main-body">
        <p class="sorry"><%= locale.getLocalized("webinv:apologies__title") %></p>
        <p class="description"><%= locale.getLocalized("webinv:apologies__company_disabled") %></p>
        <hr>
        <p class="description">
            <a href="<%= redirectUrl %>" class="underline">
                <%= locale.getLocalized("webinv:apologies__home_link") %> (<span id="count"></span>)
            </a>
        </p>
    </div>
</div>
</body>
</html>