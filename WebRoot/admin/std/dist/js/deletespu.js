$(function () {
    layui.use(['layer', 'table'], function () {
        var layer = layui.layer
            , table = layui.table;
        let origin = '';
        $("#spu").bind("input propertychange", function () {
            if (isNaN($("#spu").val())) {
                $("#spu").val(origin);
            }
            else {
                origin = $("#spu").val();
            }
        });
        $("#order").on("click",function(){
            if($("#spu").val()==''){
                layer.alert("请先输入信息", { icon: 3 });
            }
            else{
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "../../../../../servlet/SkuBelong",
                    data: {
                        message: "skuBelong",
                        spu: $("#spu").val()
                    }, success: function (data) {
                        $("#deleteinfo").css("display","");
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
            }
        });
        $("#ensure").on("click",function(){
            layer.confirm("确定要删除这件商品吗？", {
                icon: 3,
                btn: ["确定删除", "再看看"]
            }, function () {
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "../../../../../servlet/DeleteSpu",
                    data: {
                        message: "deleteSpu",
                        spu: $("#spu").val()
                    }, success: function (data) {
                        console.log(data);
                        if (data.status == "success") {
                            layer.alert(data.message, { icon: 1 });
                        }
                        if (data.status == "fail") {
                            layer.alert(data.message,);
                        }
                    }, error: function (data) {
                        console.log(data);
                        layer.alert("服务器异常，请稍后再试");
                        return;
                    }
                });
            }, function () {});
        });
    });
});