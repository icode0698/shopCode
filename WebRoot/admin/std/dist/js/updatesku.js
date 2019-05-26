$(function () {
    layui.use(['layer', 'table'], function () {
        var layer = layui.layer
            , table = layui.table;
        let originSpu = '';
        let originSku = '';
        $("#spu").bind("input propertychange", function () {
            if (isNaN($("#spu").val())) {
                $("#spu").val(originSpu);
            }
            else {
                originSpu = $("#spu").val();
            }
        });
        $("#sku").bind("input propertychange", function () {
            if (isNaN($("#sku").val())) {
                $("#sku").val(originSku);
            }
            else {
                originSku = $("#sku").val();
            }
        });
        getSku($("#category").val(), $("#spu").val(), $("#sku").val());
        $("#getsku").on("click", function () {
            getSku($("#category").val(), $("#spu").val(), $("#sku").val());
        });
        function getSku(category, spu, sku) {
            $.ajax({
                type: "post",
                dataType: "json",
                url: "../../../../../servlet/SelectSku",
                data: {
                    message: "selectBrand",
                    category: category,
                    spu: spu,
                    sku: sku
                }, success: function (data) {
                    console.log(data);
                    if (data.status == "success") {
                        $("#trs").empty();
                        for (let i = 0; i < data.message.length; i++) {
                            let content = '<tr><td>' + data.message[i].SKU + '</td><td>' + data.message[i].goodsID + '</td><td>' + data.message[i].goodsName + '</td><td>' + data.message[i].categoryName + '</td>'
                                + '<td>' + data.message[i].brandName + '</td><td>' + data.message[i].storage + '</td><td>' + data.message[i].color + '</td><td>' + data.message[i].screen + '</td><td>' + data.message[i].price.toFixed(2) + '</td><td>' + data.message[i].stock + '</td></tr>';
                            $("#trs").append(content);
                        }
                        table.init('sku', {
                            page: true
                            , limit: 20
                            , cellMinWidth: 40
                        });
                        table.on('edit(sku)', function (obj) {
                            var sku = obj.data.sku;
                            var field = obj.field;
                            if (field == "price") {
                                var price = obj.value //得到修改后的值
                                $.ajax({
                                    type: "post",
                                    dataType: "json",
                                    url: "../../../../../servlet/UpdatePrice",
                                    data: {
                                        sku: sku,
                                        price: price
                                    }, success: function (data) {
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
                            if (field == "stock") {
                                var stock = obj.value //得到修改后的值
                                $.ajax({
                                    type: "post",
                                    dataType: "json",
                                    url: "../../../../../servlet/UpdateStock",
                                    data: {
                                        sku: sku,
                                        stock: stock
                                    }, success: function (data) {
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
        }
    });
});