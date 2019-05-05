$(function(){
    // $("#trs").append('<tr><td><button type="button" class="btn btn-danger">移出购物车</button></td></tr>');
    $.ajax({
        type: "post",
        url: "servlet/Personal",
        dataType: "json",
        data: {
            type: "ajax_personal"
        },success:function(data){
            console.log(data);
            console.log(data.status);
            console.log(data.message.length);
            for (var i = 0; i < data.message.length; i++) {
                var content = '<tr><td><input type="checkbox" name="goods" value="'+data.message[i].id+'" class="checkbox_goods">'
                    +'<img src="'+data.message[i].imgList[0]+'" alt="" class="img_goods">'
                    +'</td><td><strong>'+data.message[i].goodsName+'</strong><br>'
                    +'<p>'+data.message[i].color+data.message[i].screen+data.message[i].storage+'</p>'
                    +'</td><td><span>￥'+data.message[i].unitPrice+'</span>'
                    +'</td><td><div class="btn-group"><button id="num_minus" type="button" class="btn btn-default" disabled="true"><span class="glyphicon glyphicon-minus"></span></button>'
                    +'<div class="btn-group"><input id="num" type="text" class="form-control input_size text-center" value="'+data.message[i].num+'"></div>'
                    +'<button id="num_plus" type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></button>'
                    +'</div>'
                    +'</td><td><span>￥'+data.message[i].totalPrice+'</span>'
                    +'</td><td><button type="button" class="btn btn-danger">移出购物车</button></td></tr>';
                $("#trs").append(content);
            }
            $("#trs").append('<tr><td></td><td></td><td></td><td></td><td><strong id="total">总计:</strong><span>￥0.00</span></td><td><button type="button" id="settlement" class="btn btn-success">去结算<span class="glyphicon glyphicon-chevron-right"></span></button></td></tr>');
        },fail:function(data){
            console.log("ajax_personal:"+data);
        }
    });
    // 加减按钮控制input的num
    $("#num_minus").on("click", function () {
        var number = $("#num").val();
        if (number > 1) {
            $("#num").val(--number);
            if (number == 1) {
                $("#num_minus").attr("disabled", true);
            }
        }
        console.log("num_minus:" + $("#num").val());
    });
    $("#num_plus").on("click", function () {
        var number = $("#num").val();
        if (number >= 1) {
            $("#num_minus").attr("disabled", false);
        }
        $("#num").val(++number);
        console.log("num_plus:" + $("#num").val());
    });
});
