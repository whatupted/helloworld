<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${c==1}">
	<script>
		alert("Ż��");
	</script>
</c:if>
<c:if test="${c!=1}">
	<script>
		alert("��� Ȯ��");
		history.go(-1);
	</script>

</c:if>