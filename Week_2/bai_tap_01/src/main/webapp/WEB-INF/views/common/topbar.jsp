<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- ví dụ topbar.jsp -->
<div class="topbar">
  <c:choose>
    <!-- khi chưa login -->
    <c:when test="${sessionScope.account == null}">
      <ul class="list-inline right-topbar">
        <li><a href="${pageContext.request.contextPath}/login">Đăng nhập</a> | 
            <a href="${pageContext.request.contextPath}/register">Đăng ký</a></li>
        <li><i class="search fa fa-search"></i></li>
      </ul>
    </c:when>

    <!-- khi đã login -->
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
