<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../bookBase/Include.jsp" %>
<html>
<head>
    <title></title>
    <style>
        body{font-size: 14px;}
    </style>
    <script>
        var showColumn = "<%=request.getAttribute("showColumn")%>";
        $(function () {
            bookList(1);

        });

        function bookList(pageNumber) {
            $.ajax({
                url: "getBookListByPara",
                method: "post",
                data:{pageNumber:pageNumber},
                beforeSend: function (XMLHttpRequest) {
                    //alert('远程调用开始...');
                    $("#tableContent").html("<div class='aa'><img src='${ctx}/images/loading.gif' /></div>");
                },
                success: function (data) {
                    var tableContent = "";
                    for (var i = 0; i < data.list.length; i++) {
                        tableContent += "<tr>";
                        for (var j = 0; j < showColumn.split(",").length; j++) {
                            var columnName = showColumn.split(",")[j].trim();
                            if(columnName=="book_paper_price"||columnName=="book_ebook_price"||columnName=="book_ebook_dollar_price") {
                                tableContent += "<td>" + data.list[i][columnName].toFixed(2) + "</td>";
                            } else {
                                tableContent += "<td>" + data.list[i][columnName] + "</td>";
                            }
                        }
                        tableContent += "</tr>";
                    }

                    var tableFoot="<tr><td colspan='"+showColumn.split(",").length+"'>"+initPageNav(data.pageSize, data.pageNumber, data.totalPage)+"</td></tr>";
                    $("#tableContent").html(tableContent);
                    $("#tableFoot").html(tableFoot);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $("#tableContent").html("<p class='text-danger'>" + textStatus + "  " + errorThrown + "</p>");
                }
            });
        }

        function initPageNav(pageSize, pageNumber, totalPage){
            var pageNav="<nav><ul class='pagination pull-right'><li class='center-block'><span>当前第 "+pageNumber+" 页，共 "+totalPage+" 页， 每页显示 "+pageSize+" 条</span></li>";
            if(pageNumber==1) {
                pageNav+="<li class='disabled'><span aria-hidden='true'>&laquo;</span></li>";
            } else {
                pageNav+="<li title='第1页'><a href='javascript:bookList(1)'><span aria-hidden='true'>&laquo;</span></a></li>";
            }
            if(totalPage<=9){
                for(var i=1;i<=totalPage;i++) {
                    if(i==pageNumber){
                        pageNav+="<li class='active'><a href='javascript:bookList("+i+")'>"+i+"</a></li>";
                    } else {
                        pageNav+="<li><a href='javascript:bookList("+i+")'>"+i+"</a></li>";
                    }
                }
            } else {
                if(pageNumber<=5) {
                    for(var i=1;i<=9;i++) {
                        if(i==pageNumber){
                            pageNav+="<li class='active'><a href='javascript:bookList("+i+")'>"+i+"</a></li>";
                        } else {
                            pageNav+="<li><a href='javascript:bookList("+i+")'>"+i+"</a></li>";
                        }
                    }
                    //pageNav+="<li><a href='#'>"+totalPage+"</a></li>";
                } else {
                    var lastPage = pageNumber+4;
                    if(lastPage>=totalPage) {
                        lastPage=totalPage;
                    }
                    if(pageNumber>totalPage-8) {
                        for(var i=totalPage-8;i<=totalPage;i++) {
                            if(i==pageNumber){
                                pageNav+="<li class='active'><a href='javascript:bookList("+i+")'>"+i+"</a></li>";
                            } else {
                                pageNav+="<li><a href='javascript:bookList("+i+")'>"+i+"</a></li>";
                            }
                        }
                    } else {
                        for(var i=pageNumber-4;i<=lastPage;i++) {
                            if(i==pageNumber){
                                pageNav+="<li class='active'><a href='javascript:bookList("+i+")'>"+i+"</a></li>";
                            } else {
                                pageNav+="<li><a href='javascript:bookList("+i+")'>"+i+"</a></li>";
                            }
                        }
                    }

                }
            }
            if(pageNumber==totalPage) {
                pageNav+="<li class='disabled'><span aria-hidden='true'>&raquo;</span></li>";
            } else {
                pageNav+="<li title='最末页'><a href='javascript:bookList("+totalPage+")'><span aria-hidden='true'>&raquo;</span></a></li>";
            }
            pageNav+="</ul></nav>";
            return pageNav;
        }

    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered table-hover table-condensed">
                <thead id="tableHead">

                </thead>
                <tbody id="tableContent">

                </tbody>
                <tfoot id="tableFoot"></tfoot>
            </table>

        </div>

    </div>
</div>
</body>
</html>
