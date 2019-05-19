$(function () {
    layui.use(['layer', 'table'], function () {
        var layer = layui.layer
            , table = layui.table;
        $.ajax({
            type: "post",
            dataType: "json",
            url: "../../../../../servlet/SelectSku",
            data: {
                message: "selectBrand"
            }, success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    $("#trs").empty();
                    for (let i = 0; i < data.message.length; i++) {
                        let content = '<tr><td>' + data.message[i].SKU + '</td><td>' + data.message[i].goodsID + '</td><td>' + data.message[i].goodsName + '</td><td>' + data.message[i].categoryName + '</td>'
                            + '<td>' + data.message[i].brandName + '</td><td>' + data.message[i].storage + '</td><td>' + data.message[i].color + '</td><td>' + data.message[i].screen + '</td><td><span>￥</span>' + data.message[i].price.toFixed(2) + '</td><td>' + data.message[i].stock + '</td></tr>';
                        $("#trs").append(content);
                    }
                    table.init('sku', {
                        page: true
                        , limit: 20
                        , cellMinWidth: 40
                    }); 
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
    });
});