$(function(){
    let val = $("#searchvalue").val();
    if(val!=''){
        init(val);
    }
    else{
        $.ajax({
            type: "post",
            dataType: "json",
            url: "servlet/Search",
            success: function (data) {
                console.log(data);
                if (data.status == "success") {
                    $("#tip").empty();
                    $("#goods").empty();
                    if(data.message.length==0){
                        $("#tip").append("没有找到相关产品");
                    }
                    else{
                        $("#tip").append('<span style="margin-top:10px;margin-bottom:10px">为你找到<span style="font-size:18px;color:#01AAED;">'+data.message.length+'件</span>符合的商品</span>');
                        for (var i = 0; i < data.message.length; i++) {
                            var content = '<div class="col-sm-6 col-md-4 wow slideInUp">'
                                + '<div class="thumbnail">'
                                + '<img src="' + data.message[i].imgList[0] + '" alt="">'
                                + '<div class="caption">'
                                + '<h3>' + data.message[i].goodsName + '</h3>'
                                + '<p><a href="details.html?spu=' + data.message[i].goodsID + '&goods=' + data.message[i].goodsName + '" class="btn btn-primary" role="button">立即购买</a>'
                                + '</div>'
                                + '</div>';
                            $("#goods").append(content);
                        }
                    }
                }
                
            }, error: function () {
                $("#tip").append("服务器异常");
                console.log("服务器异常\najax_whether:" + XMLResponse.status);
            }
        });
    }
    $("#search").on("click",function(){
        let value = $("#searchvalue").val();
        console.log(value);
        console.log(isNaN(value));
        if(value!=''){
            init(value);
        }
    });
    function init(value){
        if(isNaN(value)){
            $.ajax({
                type: "post",
                dataType: "json",
                url: "servlet/SearchValue",
                data: {
                    value: value
                }, success: function (data) {
                    console.log(data);
                    if (data.status == "success") {
                        $("#tip").empty();
                        $("#goods").empty();
                        if(data.message.length==0){
                            $("#tip").append("没有找到相关产品");
                        }
                        else{
                            $("#tip").append('<span style="margin-top:10px;margin-bottom:10px">为你找到<span style="font-size:18px;color:#01AAED;">'+data.message.length+'件</span>符合的商品</span>');
                            for (var i = 0; i < data.message.length; i++) {
                                var content = '<div class="col-sm-6 col-md-4 wow slideInUp">'
                                    + '<div class="thumbnail">'
                                    + '<img src="' + data.message[i].imgList[0] + '" alt="">'
                                    + '<div class="caption">'
                                    + '<h3>' + data.message[i].goodsName + '</h3>'
                                    + '<p><a href="details.html?spu=' + data.message[i].goodsID + '&goods=' + data.message[i].goodsName + '" class="btn btn-primary" role="button">立即购买</a>'
                                    + '</div>'
                                    + '</div>';
                                $("#goods").append(content);
                            }
                        }
                    }
                    
                }, error: function (data) {
                    $("#tip").append("服务器异常");
                    console.log("服务器异常\najax_whether:" + data.status);
                }
            });
        }
        else{
            $.ajax({
                type: "post",
                dataType: "json",
                url: "servlet/SearchNum",
                data: {
                    value: value
                }, success: function (data) {
                    console.log(data);
                    if (data.status == "success") {
                        $("#tip").empty();
                        $("#goods").empty();
                        if(data.message.length==0){
                            $("#tip").append("没有找到相关产品");
                        }
                        else{
                            $("#tip").append('<span style="margin-top:10px;margin-bottom:10px">为你找到<span style="color:#01AAED;">'+data.message.length+'件</span>符合的商品</span>');
                            for (var i = 0; i < data.message.length; i++) {
                                var content = '<div class="col-sm-6 col-md-4 wow slideInUp">'
                                    + '<div class="thumbnail">'
                                    + '<img src="' + data.message[i].imgList[0] + '" alt="">'
                                    + '<div class="caption">'
                                    + '<h3>' + data.message[i].goodsName + '</h3>'
                                    + '<p><a href="details.html?spu=' + data.message[i].goodsID + '&goods=' + data.message[i].goodsName + '" class="btn btn-primary" role="button">立即购买</a>'
                                    + '</div>'
                                    + '</div>';
                                $("#goods").append(content);
                            }
                        }
                    }
                    
                }, error: function () {
                    $("#tip").append("服务器异常");
                    console.log("服务器异常\najax_whether:" + XMLResponse.status);
                }
            });
        }
    }
});