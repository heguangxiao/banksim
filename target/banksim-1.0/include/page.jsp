<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="margin:3px 2px 0" class="pagination aalternate fr">
    <ul>
        <c:if test="${totalPage>1}">
            <c:if test="${currentPage==1}">
                <li class="disabled"><a href="javascript:void(0)">First</a></li><li class="disabled"><a href="javascript:void(0)">Prev</a></li>
            </c:if>
            <c:if test="${currentPage!=1}">
                <li><a style="background: #bce8f1 none repeat scroll" href="${pageURL}page=1">First</a></li><li><a href="${pageURL}page=${currentPage - 1}">Prev</a></li>
            </c:if>
            <c:if test="${true}">
                <li class="active"><a style="background: #41bedd none repeat scroll" href="javascript:void(0)">${currentPage}</a> </li>
            </c:if>
            <c:if test="${currentPage + 1 <= totalPage}">
                <li><a href="${pageURL}page=${currentPage + 1}">${currentPage + 1}</a></li>
            </c:if>
            <c:if test="${currentPage + 2 <= totalPage}">
                <li><a href="${pageURL}page=${currentPage + 2}">${currentPage + 2}</a></li>
            </c:if>
            <c:if test="${totalPage - 2 > currentPage + 2}">
                <li><a href="javascript:void(0)">...</a></li><li><a href="${pageURL}page=${totalPage - 2}">${totalPage - 2}</a></li>
            </c:if>
            <c:if test="${totalPage - 1 > currentPage + 3}">
                <li><a href="${pageURL}page=${totalPage - 1}">${totalPage - 1}</a></li>
            </c:if>
            <c:if test="${totalPage > currentPage + 4}">
                <li><a href="${pageURL}page=${totalPage}">${totalPage}</a></li>
            </c:if>
            <c:if test="${currentPage < totalPage}">
                <li><a href="${pageURL}page=${currentPage+1}">Next</a></li>
                <li><a style="background: #bce8f1 none repeat scroll" href="${pageURL}page=${totalPage}">Last</a></li>
            </c:if>
            <c:if test="${currentPage == totalPage}">
                <li class="disabled"><a href="javascript:void(0)">Next</a></li>
            </c:if>
        </c:if>
    </ul>
</div>