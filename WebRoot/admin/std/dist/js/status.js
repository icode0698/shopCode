$(function () {
    layui.use('layer', function () {
        var layer = layui.layer;
        $.ajax({
            type: "post",
            url: "../../../../servlet/AdminStatus",
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    console.log(data);
                    $("#admin").text(data.admin);
                }
                else {
                    layer.alert(data.message, { icon: 2 }, function () { location.href = 'login.html'; return; });
                    setTimeout(function() { location.href = 'login.html';}, 1000);
                    return;
                }
            },
            error: function (XMLResponse) {
                layer.alert("抱歉，服务器异常。", { icon: 2 }, function () { location.href = 'login.html'; return; });
                return;
            }
        });
    });
});
