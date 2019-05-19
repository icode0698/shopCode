$(function () {
    layui.use(['layer', 'table'], function () {
        var layer = layui.layer
            , table = layui.table;
        $.ajax({
            type: "post",
            dataType: "json",
            url: "../../../../../servlet/SelectBrand",
            data: {
                message: "selectBrand"
            }, success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    $("#trs").empty();
                    for (let i = 0; i < data.message.length; i++) {
                        let content = '<tr><td>' + data.message[i].brandID + '</td><td>' + data.message[i].brandName + '</td><td>' + data.message[i].insertTime + '</td></tr>';
                        $("#trs").append(content);
                    }
                    table.init('brand', {
                        page: true
                        , limit: 20
                        , cellMinWidth: 40
                    });
                }
                if (data.status == "fail") {
                    layer.alert("查询出现错误");
                }
            }, error: function () {
                layer.alert("服务器异常，请稍后再试");
                return;
            }
        });
    });
});