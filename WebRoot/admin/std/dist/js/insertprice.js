$(function () {
    layui.use(['layer', 'table'], function () {
        var layer = layui.layer
            , table = layui.table;
        $.ajax({
            type: "post",
            dataType: "json",
            url: "../../../../../servlet/InsertPrice",
            data: {
                message: "selectBrand"
            }, success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    $("#trs").empty();
                    for (let i = 0; i < data.message.length; i++) {
                        let content = '<tr><td>'+data.message[i].sku+'</td><td>'+data.message[i].goodsName+'</td>'
                            +'<td>'+data.message[i].storage+'</td><td>'+data.message[i].color+'</td><td>'+data.message[i].screen+'</td>'
                            +'<td>'+data.message[i].unitPrice+'</td><td>'+data.message[i].stock+'</td>'
                        $("#trs").append(content);
                    }
                    table.init('price', {
                        page: true
                        , limit: 20
                        , cellMinWidth: 40
                    }); 
                    table.on('edit(price)', function(obj){
                        var sku = obj.data.sku;
                        var field = obj.field;
                        if(field=="unitPrice"){
                            var price = obj.value //得到修改后的值
                            $.ajax({
                                type: "post",
                                dataType: "json",
                                url: "../../../../../servlet/UpdatePrice",
                                data: {
                                    sku: sku,
                                    price: price
                                },success: function (data) {
                                    console.log(data);
                                    if (data.status == "success") {
                                        layer.msg(data.message);
                                    }
                                    if (data.status == "fail") {
                                        layer.alert(data.message);
                                    }
                                }, error: function (data) {
                                    console.log(data);
                                    layer.alert("服务器异常，请稍后再试");
                                    return;
                                }
                            });
                        }
                        if(field=="stock"){
                            var stock = obj.value //得到修改后的值
                            $.ajax({
                                type: "post",
                                dataType: "json",
                                url: "../../../../../servlet/UpdateStock",
                                data: {
                                    sku: sku,
                                    stock: stock
                                },success: function (data) {
                                    console.log(data);
                                    if (data.status == "success") {
                                        layer.msg(data.message);
                                    }
                                    if (data.status == "fail") {
                                        layer.alert(data.message);
                                    }
                                }, error: function (data) {
                                    console.log(data);
                                    layer.alert("服务器异常，请稍后再试");
                                    return;
                                }
                            });
                        }
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