$(function () {
    layui.use(['layer',  'form'], function () {
        var layer = layui.layer
            , form = layui.form;
        $.ajax({
            type: "post",
            dataType: "json",
            url: "../../../../../servlet/NowBrand",
            success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    $("#now").text(data.message);
                    $("#brandID").val(parseInt(data.message)+1);
                }
                if (data.status == "fail") {
                    layer.alert("查询出现错误");
                }
            }, error: function (data) {
                console.log(data);
                layer.alert("服务器异常，请稍后再试");
                return;
            }
        });
        form.on('submit(ensure)', function (data) {
            console.log(data.field);
            $.ajax({
                type: "post",
                dataType: "json",
                url: "../../../../../servlet/InsertBrand",
                data: {
                    brandID: $("#brandID").val(),
                    brandName: $("#brandName").val(),
                }, success: function (data) {
                    console.log(data);
                    if (data.status == "success") {
                        layer.alert(data.message, {icon:1}, function () { location.href="";});
                        return false;
                    }
                    if (data.status == "fail") {
                        layer.alert(data.message, {icon:2});
                        return false;
                    }
                }, error: function (data) {
                    console.log(data);
                    layer.alert("服务器异常，请稍后再试", {icon:2});
                    return;
                }
            });
            return false;
        });
    });
});