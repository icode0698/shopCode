$(function () {
    layui.use(['layer','table'], function () {
        var layer = layui.layer
            ,table = layui.table;
        $.ajax({
            type: "post",
            dataType: "json",
            url: "../../../../../servlet/SelectSpu",
            data: {
                message: "selectBrand"
            }, success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    $("#trs").empty();
                    for (let i = 0; i < data.message.length; i++) {
                        let content = '<tr><td>'+data.message[i].goodsID+'</td><td>'+data.message[i].goodsName+'</td><td>'+data.message[i].categoryName+'</td><td>'+data.message[i].brandName+'</td><td>'+data.message[i].insertTime+'</td></tr>';
                        $("#trs").append(content);
                    }
                    table.init('spu', {
                        page: true
                        , limit: 20
                        , cellMinWidth: 40
                    }); 
                    table.on('edit(spu)', function (obj) {
                        var id = obj.data.spu;
                        var field = obj.field;
                        var value = obj.value //得到修改后的值
                        $.ajax({
                            type: "post",
                            dataType: "json",
                            url: "../../../../../servlet/UpdateSpu",
                            data: {
                                type: field,
                                id: id,
                                data: value  
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