$(function () {
    layui.use('layer', function () {
        var layer = layui.layer;
        $("#top_btn_login").click(function () {
            location.href = "login.html";
        });
        $("#top_btn_reg").click(function () {
            location.href = "register.html";
        });
        // 顶部动态显示登录状态(session)
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
                    if (data.online == false) {
                        $.ajax({
                            type: "post",
                            dataType: "json",
                            url: "servlet/Whether",
                            data: {
                                type: "ajax_whether",
                                message: "logout"
                            }, success: function () {
                                layer.alert('管理员强制用户下线', { icon: 2 });
                                console.log("管理员强制用户下线");
                            }, error: function () {
                                console.log("服务器异常\najax_whether:" + XMLResponse.status);
                                return;
                            }
                        });
                    }
                    else{
                        $("#login_no").addClass("hidden");
                        $("#login_yes").removeClass("hidden");
                        $("#span_user").text(data.nickName);
                        $("#headPic").attr("src", data.headPic);
                    }
                }
            }, error: function () {
                console.log("服务器异常\najax_whether:" + XMLResponse.status);
                return;
            }
        });
        // 处理退出登录
        $("#btn_out").click(function () {
            $.ajax({
                type: "post",
                dataType: "json",
                url: "servlet/Whether",
                data: {
                    type: "ajax_whether",
                    message: "logout"
                }, success: function () {
                    location.href = "index.html";
                }, error: function () {
                    console.log("服务器异常\najax_whether:" + XMLResponse.status);
                    return;
                }
            });
        });
    });
});
