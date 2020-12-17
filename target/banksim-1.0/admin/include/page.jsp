<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="margin:3px 2px 0" class="pagination aalternate fr">
    <ul>
        <c:if test="${totalPage>1}">
            
            <c:if test="${currentPage==1}">
                <li class="disabled">
                    <a href="javascript:void(0)">First</a>
                </li>
                <li class="disabled">
                    <a href="javascript:void(0)">Prev</a>
                </li>
            </c:if>

            <c:if test="${currentPage!=1}">
                <li>
                    <a style="background: #bce8f1 none repeat scroll" href="${pageURL}page=1">First</a>
                </li>
                <li>
                    <a href="${pageURL}page=${currentPage - 1}">Prev</a>
                </li>
            </c:if>
                
        </c:if>
    </ul>
</div>