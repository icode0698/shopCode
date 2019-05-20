$(function () {
    layui.use(['layer',  'form'], function () {
        var layer = layui.layer
            , form = layui.form;
        $.ajax({
            type: "post",
            dataType: "json",
            url: "../../../../../servlet/NowValue",
            success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    $("#storagenow").text(data.storageNowID);
                    $("#storageID").val(parseInt(data.storageNowID)+1);
                    $("#colornow").text(data.colorNowID);
                    $("#colorID").val(parseInt(data.colorNowID)+1);
                    $("#screennow").text(data.screenNowID);
                    $("#screenID").val(parseInt(data.screenNowID)+1);
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
        form.on('submit(storageensure)', function (data) {
            console.log(data.field);
            $.ajax({
                type: "post",
                dataType: "json",
                url: "../../../../../servlet/InsertValue",
                data: {
                    message: "storage",
                    storageID: $("#storageID").val(),
                    storageName: $("#storageName").val(),
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
        form.on('submit(colorensure)', function (data) {
            console.log(data.field);
            $.ajax({
                type: "post",
                dataType: "json",
                url: "../../../../../servlet/InsertValue",
                data: {
                    message: "color",
                    colorID: $("#colorID").val(),
                    colorName: $("#colorName").val(),
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
        form.on('submit(screenensure)', function (data) {
            console.log(data.field);
            $.ajax({
                type: "post",
                dataType: "json",
                url: "../../../../../servlet/InsertValue",
                data: {
                    message: "screen",
                    screenID: $("#screenID").val(),
                    screenName: $("#screenName").val(),
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