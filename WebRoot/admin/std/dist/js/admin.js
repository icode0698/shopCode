$(function(){
    $("#out").on("click",function(){
        $.ajax({
            type: "post",
            url: "../../../../servlet/LoginOut",
            dataType: "json",
            success: function (data) {
                console.log(data);
                if(data.status=="success"){
                    location.href = 'login.html';
                }
                if (data.status == "error") {
                    layer.alert(data.message,{icon:2},function(){location.href = 'login.html';return;});
                }
            },
            error: function (XMLResponse) {
                layer.alert("抱歉，服务器异常。",{icon:2},function(){location.href = 'login.html';return;});
                return;
            }
        });
    });
});