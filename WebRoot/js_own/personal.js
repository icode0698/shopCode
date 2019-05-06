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
            // 生成购物车列表
            for (var i = 0; i < data.message.length; i++) {
                var content = '<tr><td><input type="checkbox" name="goods" value="'+data.message[i].id+'" class="checkbox_goods">'
                    +'<img src="'+data.message[i].imgList[0]+'" alt="" class="img_goods">'
                    +'</td><td><strong>'+data.message[i].goodsName+'</strong><br>'
                    +'<p>'+data.message[i].color+data.message[i].screen+data.message[i].storage+'</p>'
                    +'</td><td><span>￥'+data.message[i].unitPrice+'</span>'
                    +'</td><td><div class="btn-group"><button id="num_minus'+i+'" type="button" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button>'
                    +'<div class="btn-group"><input id="num'+i+'" type="text" class="form-control input_size text-center" value="'+data.message[i].num+'"></div>'
                    +'<button id="num_plus'+i+'" type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></button>'
                    +'</div>'
                    +'</td><td><span>￥'+data.message[i].totalPrice+'</span>'
                    +'</td><td><button type="button" class="btn btn-danger" value="'+data.message[i].id+'">移出购物车</button></td></tr>';
                $("#trs").append(content);
                // 初始化按钮的disabled
                if($("#num" + i).val()<=1){
                    //console.log("if_i:"+i);
                    $("#num_minus" + i).attr("disabled", true);
                }
            }
            $("#trs").append('<tr><td></td><td></td><td></td><td></td><td><strong id="total">总计:</strong><span>￥0.00</span></td>'
                + '<td><button type="button" id="settlement" class="btn btn-success">去结算<span class="glyphicon glyphicon-chevron-right"></span></button></td></tr>');
            // 加减按钮控制input的num
            for (let i = 0; i < data.message.length; i++) {
                $("#num_minus" + i).on("click", function () {
                    var number = $("#num" + i).val();
                    if (number > 1) {
                        $("#num" + i).val(--number);
                        if (number == 1) {
                            $("#num_minus" + i).attr("disabled", true);
                        }
                    }
                    console.log("num_minus:" + $("#num" + i).val());
                });
                $("#num_plus" + i).on("click", function () {
                    var number = $("#num" + i).val();
                    if (number >= 1) {
                        $("#num_minus" + i).attr("disabled", false);
                    }
                    $("#num" + i).val(++number);
                    console.log("i:" + i);
                    console.log("num_plus:" + $("#num" + i).val());
                });
            }
        },fail:function(data){
            console.log("ajax_personal:"+data);
        }
    });
    function f2(){
        
    }
});
