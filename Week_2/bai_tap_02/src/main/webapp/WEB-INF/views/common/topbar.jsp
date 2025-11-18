<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="topbar">
  <c:choose>
    <c:when test="${sessionScope.account == null}">
      <ul class="list-inline right-topbar">
        <li><a href="${pageContext.request.contextPath}/login">Đăng nhập</a> | 
            <a href="${pageContext.request.contextPath}/register">Đăng ký</a></li>
        <li><i class="search fa fa-search"></i></li>
      </ul>
    </c:when>
    <c:otherwise>
      <ul class="list-inline right-topbar">
        <li>
          <a href="${pageContext.request.contextPath}/member/myaccount">
            ${sessionScope.account.fullname}
          </a>
          |
          <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </li>
        <li><i class="search fa fa-search"></i></li>
      </ul>
    </c:otherwise>
  </c:choose>
</div>
