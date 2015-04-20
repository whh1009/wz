<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../bookBase/Include.jsp" %>
<html>
<head>
    <title></title>
    <style>
        .row{margin-top:1em;}
        body {
        }
    </style>
    <script>
        var showColumn = "<%=request.getSession().getAttribute("showColumn")%>";
        var columnMap = "<%=application.getAttribute("__COLUMN_MAP")%>";
        var queryStr="";
        $(function () {
            initTableHead();
            bookList(1);
        });

        function initTableHead() {
            var tableHeader = "";//表头
            var columnFilter = ""; //字段筛选
            tableHeader += "<tr>";
            for (var j = 0; j < showColumn.split(",").length; j++) {
                var columnName = showColumn.split(",")[j].trim();
                var cname=$(columnMap).find("item[ename='" + columnName + "']").attr("cname");
                tableHeader += "<th name='"+columnName+"'>" + cname + "&nbsp;<a href='javascript:addContentFilterText()'><small><span class='glyphicon glyphicon-filter'></span><small></a></th>";
                columnFilter+="<li class=''><div class='checkbox'><label>&nbsp;&nbsp;&nbsp;<input type='checkbox' onchange='columnFilter(this, \""+columnName+"\")' checked='checked'> "+cname+"</label></div></li>"
            }
            tableHeader += "</tr>";
            $("#tableHead").html(tableHeader);
            $("#columnFilter").html(columnFilter);
        }

        //checkbox，是否显示字段
        function columnFilter(arg, columnName){
            if($(arg).prop("checked")==true){
                $("*[name='"+columnName+"']").show();
            } else {
                $("*[name='"+columnName+"']").hide();
            }
        }

        //添加内容过滤文本框
        function addContentFilterText() {
            $("th input").remove();
            $("th").each(function(){
                $(this).append("<input type='input' class='form-control' cusData='"+$(this).attr("name")+"'>");
            })
        }

        function contentFilter() {
            queryStr="";
            if($("th input").length>0) {
                var queryXml="<root>";
                $("th input").each(function() {
                    if($(this).val().trim()!=""){
                        queryXml+="<item columnName='"+$(this).attr("cusData")+"' columnVal='"+ $(this).val()+"' />";
                        //queryStr+=
                    }
                })
                return queryXml+"</root>";
            } else {
                return "";
            }

        }

        function exportExcel(){
            alert("export excel");
        }

        function exportResource(){
            alert("export resource");
        }

        function bookList(pageNumber) {
            $.ajax({
                url: "getBookListByPara",
                method: "post",
                data: {pageNumber: pageNumber},
                beforeSend: function (XMLHttpRequest) {
                    $("#tableContent").html("<div class='aa'><img src='${ctx}/images/loading.gif' /></div>");
                },
                success: function (data) {
                    var tableContent = "";
                    if (data.list == null) {
                        $("#tableContent").html("<p class='pull-center' color='red'>没有找到……</p>");
                        return;
                    }
                    for (var i = 0; i < data.list.length; i++) {
                        tableContent += "<tr>";
                        for (var j = 0; j < showColumn.split(",").length; j++) {
                            var columnName = showColumn.split(",")[j].trim();
                            if (columnName == "book_paper_price" || columnName == "book_ebook_price" || columnName == "book_ebook_dollar_price") {
                                if (data.list[i][columnName] == null) {
                                    tableContent += "<td name='"+columnName+"'>0.00</td>";
                                } else {
                                    tableContent += "<td name='"+columnName+"'>" + data.list[i][columnName].toFixed(2) + "</td>";
                                }
                            } else {
                                tableContent += "<td name='"+columnName+"'>" + data.list[i][columnName] + "</td>";
                            }
                        }
                        tableContent += "</tr>";
                    }
                    var tableFoot = "<tr><td colspan='" + showColumn.split(",").length + "'>" + initPageNav(data.pageSize, data.pageNumber, data.totalPage) + "</td></tr>";
                    $("#tableContent").html(tableContent);
                    $("#tableFoot").html(tableFoot);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $("#tableContent").html("<p class='text-danger'>" + textStatus + "  " + errorThrown + "</p>");
                }
            });
        }

        function initPageNav(pageSize, pageNumber, totalPage) {
            var pageNav = "<div class='row'><div class='col-sm-6'><p class='pagination'>当前第 " + pageNumber + " 页，共 " + totalPage + " 页， 每页显示 " + pageSize + " 条</p></div><div class='col-sm-6'><div><ul class='pagination pull-right'>";
            if (pageNumber == 1) {
                pageNav += "<li class='disabled'><span aria-hidden='true'>&laquo;</span></li>";
            } else {
                pageNav += "<li title='第1页'><a href='javascript:bookList(1)'><span aria-hidden='true'>&laquo;</span></a></li>";
            }
            if (totalPage <= 9) {
                for (var i = 1; i <= totalPage; i++) {
                    if (i == pageNumber) {
                        pageNav += "<li class='active'><a href='javascript:bookList(" + i + ")'>" + i + "</a></li>";
                    } else {
                        pageNav += "<li><a href='javascript:bookList(" + i + ")'>" + i + "</a></li>";
                    }
                }
            } else {
                if (pageNumber <= 5) {
                    for (var i = 1; i <= 9; i++) {
                        if (i == pageNumber) {
                            pageNav += "<li class='active'><a href='javascript:bookList(" + i + ")'>" + i + "</a></li>";
                        } else {
                            pageNav += "<li><a href='javascript:bookList(" + i + ")'>" + i + "</a></li>";
                        }
                    }
                    //pageNav+="<li><a href='#'>"+totalPage+"</a></li>";
                } else {
                    var lastPage = pageNumber + 4;
                    if (lastPage >= totalPage) {
                        lastPage = totalPage;
                    }
                    if (pageNumber > totalPage - 8) {
                        for (var i = totalPage - 8; i <= totalPage; i++) {
                            if (i == pageNumber) {
                                pageNav += "<li class='active'><a href='javascript:bookList(" + i + ")'>" + i + "</a></li>";
                            } else {
                                pageNav += "<li><a href='javascript:bookList(" + i + ")'>" + i + "</a></li>";
                            }
                        }
                    } else {
                        for (var i = pageNumber - 4; i <= lastPage; i++) {
                            if (i == pageNumber) {
                                pageNav += "<li class='active'><a href='javascript:bookList(" + i + ")'>" + i + "</a></li>";
                            } else {
                                pageNav += "<li><a href='javascript:bookList(" + i + ")'>" + i + "</a></li>";
                            }
                        }
                    }
                }
            }
            if (pageNumber == totalPage) {
                pageNav += "<li class='disabled'><span aria-hidden='true'>&raquo;</span></li>";
            } else {
                pageNav += "<li title='最末页'><a href='javascript:bookList(" + totalPage + ")'><span aria-hidden='true'>&raquo;</span></a></li>";
            }
            pageNav += "</ul></div></div></div>";
            return pageNav;
        }

    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-6">
            <%--<div class="input-group">--%>
                <%--<input type="text" class="form-control" placeholder="请输入关键词..." aria-label="">--%>
                <%--<div class="input-group-btn">--%>
                    <%--<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">Action <span class="caret"></span></button>--%>
                    <%--<ul class="dropdown-menu dropdown-menu-right" role="menu">--%>
                        <%--<li><a href="#">Action</a></li>--%>
                        <%--<li><a href="#">Another action</a></li>--%>
                        <%--<li><a href="#">Something else here</a></li>--%>
                        <%--<li class="divider"></li>--%>
                        <%--<li><a href="#">Separated link</a></li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
        <div class="col-sm-6">
            <div class="btn-group pull-right" role="group" aria-label="...">
                <button type="button" class="btn btn-default" title="内容筛选" onclick="contentFilter()">&nbsp;<span class="glyphicon glyphicon-filter"></span>&nbsp;内容筛选</button>
                <button type="button" class="btn btn-default" title="导出资源列表" onclick="exportResource()">&nbsp;<span class="glyphicon glyphicon-new-window"></span>&nbsp;导出资源</button>
                <button type="button" class="btn btn-default" title="导出Excel" onclick="exportExcel()">&nbsp;<span class="glyphicon glyphicon-export"></span> &nbsp;导出Excel</button>
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false" title="字段筛选">
                    &nbsp;<span class="glyphicon glyphicon-th-list"></span>&nbsp;字段筛选
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu" id="columnFilter"></ul>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered table-hover table-condensed">
                <thead id="tableHead"></thead>
                <tbody id="tableContent"></tbody>
                <tfoot id="tableFoot"></tfoot>
            </table>
        </div>
    </div>
</div>
</body>
</html>
