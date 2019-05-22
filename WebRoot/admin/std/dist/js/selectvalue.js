$(function () {
    layui.use(['layer','table'], function () {
        var layer = layui.layer
            ,table = layui.table;
        $.ajax({
            type: "post",
            dataType: "json",
            url: "../../../../../servlet/SelectValue",
            data: {
                message: "selectValue"
            }, success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    $("#storagetrs").empty();
                    for (let i = 0; i < data.storageList.length; i++) {
                        let content = '<tr><td>'+data.storageList[i].id+'</td><td>'+data.storageList[i].name+'</td></tr>';
                        $("#storagetrs").append(content);
                    }
                    table.init('storage', {
                        page: true
                        , limit: 20
                        , cellMinWidth: 40
                    }); 
                    $("#colortrs").empty();
                    for (let i = 0; i < data.colorList.length; i++) {
                        let content = '<tr><td>'+data.colorList[i].id+'</td><td>'+data.colorList[i].name+'</td></tr>';
                        $("#colortrs").append(content);
                    }
                    table.init('color', {
                        page: true
                        , limit: 20
                        , cellMinWidth: 40
                    }); 
                    $("#screentrs").empty();
                    for (let i = 0; i < data.screenList.length; i++) {
                        let content = '<tr><td>'+data.screenList[i].id+'</td><td>'+data.screenList[i].name+'</td></tr>';
                        $("#screentrs").append(content);
                    }
                    table.init('screen', {
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