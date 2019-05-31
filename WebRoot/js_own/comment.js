$(function () {
    layui.use(['layer', 'form', 'rate'], function () {
        var layer = layui.layer
            , rate = layui.rate
            , form = layui.form;
        //拼接href字符串，找出订单编号
        var url = decodeURI(window.location.href);//href为......?id=.....
        console.log(url)
        var res = url.split("?")[1];
        console.log(res)
        //console.log("res_length:"+res.length);
        var para = {}; //定义key value的模型
        var arr = [];  //定义key value的来源
        arr = res.split("=");
        console.log(arr);
        para[arr[0]] = arr[1];//实现key value相对应
        console.log("id:" + para.id);
        let id = para.id;
        let rateValue = 0;
        rate.render({
            elem: '#commentRate'
            , half: true
            , text: true
            , choose: function (value) {
                rateValue = value;
                console.log(rateValue);
            }
        });
        if (typeof (id) == undefined || id == ''|| isNaN(id)) {
            layer.alert('错误的信息，请稍候再试', { icon: 2 }, function () { location.href = 'personal.html'; return; });
            // setTimeout(function() { location.href = 'personal.html';}, 2000);
        }
        else {
            $.ajax({
                type: "post",
                url: "servlet/OrderInfo",
                dataType: "json",
                data: {
                    id: id
                },success: function (data) {
                    console.log(data);
                    if (data.status == "success") {
                        $("#goods").text(data.goodsName);
                        $("#sku").text(data.sku);
                        $("#spu").text(data.spu);
                        $("#storage").text(data.storage);
                        $("#color").text(data.color);
                        $("#screen").text(data.screen);
                    }
                    if (data.status == "fail") {
                        layer.alert(data.message, { icon: 2 }, function () { location.href = 'personal.html';});
                    }
                },
                error: function (data) {
                    layer.alert("抱歉，服务器异常。", { icon: 2 }, function () { location.href = 'personal.html'; return; });
                    return;
                }
            });
            form.on('submit(ensure)', function (res) {
                console.log(res);
                console.log($("#commentContent").val());
                console.log($("#sku").text());
                layer.load();
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "servlet/Comment",
                    data: {
                        rateValue: rateValue,
                        sku: $("#sku").text(),
                        spu: $("#spu").text(),
                        commentContent: $("#commentContent").val()
                    }, success: function (data) {
                        console.log(data);
                        // setTimeout(function () {
                        //     layer.closeAll('loading');
                        // }, 2000);
                        layer.closeAll('loading');
                        if (data.status == "success") {
                            layer.alert(data.message, {icon:1}, function () { location.href = '';});
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
        }
    });
});