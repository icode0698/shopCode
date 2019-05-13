$(function () {
    $("#btn").on("click",function(){
        console.log($("#message").val());
        if($("#message").val()==""||$("#message").val()==null){
            layer.alert("留言不能为空哦",{icon: 0});
        }
        else{
            userMessage();
        }
    });
    $("#again").on("click", function () {
        $("#message_div").removeClass("hidden");
        $("#success_div").addClass("hidden");
    });
    function userMessage() {
        // 判断登录状态
        $.ajax({
            type: "post",
            dataType: "json",
            url: "servlet/Whether",
            data: {
                type: "ajax_whether",
                message: "getStatus"
            }, success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    $.ajax({
                        type: "post",
                        dataType: "json",
                        url: "servlet/Message",
                        data: {
                            type: "ajax_message",
                            message: $("#message").val()
                        }, success: function (data) {
                            if(data.status=="success"){
                                $("#success_tip").text(data.message);
                                $("#message_div").addClass("hidden");
                                $("#success_div").removeClass("hidden");
                            }
                            else{
                                $("#error_tip").text("抱歉，数据库操作异常");
                            }
                        }, error: function () {
                            console.log("服务器异常\najax_message:" + XMLResponse.status);
                            $("#error_tip").text("抱歉，服务器异常");
                        }
                    });
                }
                else {
                    layer.confirm(data.message, {
                        icon: 3,
                        btn: ["前往登录", "浏览首页"]
                    }, function () {
                        location.href = 'login.html';
                    }, function () {
                        location.href = 'index.html';
                    });
                }
            }, error: function () {
                console.log("服务器异常\najax_whether:" + XMLResponse.status);
            }
        });
    }
});