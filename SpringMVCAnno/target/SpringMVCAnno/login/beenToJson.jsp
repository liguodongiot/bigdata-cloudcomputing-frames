<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
    <head>
        <script type="text/javascript" src="/js/jquery-1.8.2.js"></script>
    </head>

    <body>
        <input type="button" value="Emp转Json" /><p>

        <input type="button" value="List<Emp>转Json" /><p>

        <input type="button" value="Map<String,Object>转Json" /><p>

        <!-- Emp转JSON -->
        <script type="text/javascript">
            $(":button:first").click(function(){
                var url = "${pageContext.request.contextPath}/emp/beentojson.action";
                var sendData = null;
                $.post(url,sendData,function(backData,textStaut,ajax){
                    //alert(ajax.responseText);
                    var hiredate = backData.hiredate;
                    var date = new Date(hiredate);
                    alert(date.getFullYear()+"年"+(date.getMonth()+1)+"月"+(date.getDate())+"日");
                });
            });
        </script>

       <%-- &lt;%&ndash;Emp转JSON&ndash;%&gt;
        <script type="text/javascript">
            $(":button:first").click(function(){

                var url = "${pageContext.request.contextPath}/emp/beentojson.action";
                var sendDada = null;

                //alert(url);

                $.post(
                        url,
                        sendDada,
                        function(backData,textStatus,ajax){
                            //alert(ajax.responseText);
                            var hiredate = backData.hiredate;
                            var date = new Date(hiredate);
                            alert(date.getFullYear()+"年"+(date.getMonth()+1)+"月"+(date.getDay())+"日");
                        }
                );
                //alert(url);
            });
        </script>
--%>

        <%--List转JSON--%>
        <script type="text/javascript">
            $(":button:eq(1)").click(function(){

                var url = "${pageContext.request.contextPath}/emp/listbeentojson.action";
                var sendDada = null;

                //alert(url);

                $.post(
                        url,
                        sendDada,
                        function(backData,textStatus,ajax){
                            alert(ajax.responseText);
                           /* var hiredate = backData.hiredate;
                            var date = new Date(hiredate);
                            alert(date.getFullYear()+"年"+(date.getMonth()+1)+"月"+(date.getDay())+"日");*/
                        }
                );
                alert(url);
            });
        </script>


    </body>
</html>