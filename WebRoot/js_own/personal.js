$(function(){
    // $("#trs").append('<tr><td><button type="button" class="btn btn-danger">移出购物车</button></td></tr>');
    // 声明数组
    goodsList();
    var purchase = [];
    function goodsList(){
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
                    var content = '<tr><td><input type="checkbox" name="goods" value="'+data.message[i].id+'" id="'+i+'" class="checkbox_goods">'
                        +'<label for="'+i+'"><img src="'+data.message[i].imgList[0]+'" alt="" class="img_goods"></label>'
                        +'</td><td><strong>'+data.message[i].goodsName+'</strong><br>'
                        +'<p>'+data.message[i].color+data.message[i].screen+data.message[i].storage+'</p>'
                        +'</td><td>￥<span id="unit'+i+'">'+data.message[i].unitPrice+'</span>'
                        +'</td><td><div class="btn-group"><button id="num_minus'+i+'" type="button" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button>'
                        +'<div class="btn-group"><input id="num'+i+'" type="text" class="form-control input_size text-center" value="'+data.message[i].num+'"></div>'
                        +'<button id="num_plus'+i+'" type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></button>'
                        +'</div>'
                        +'</td><td>￥<span id="total'+i+'">'+data.message[i].totalPrice+'</span>'
                        +'</td><td><button id="moveout'+i+'"type="button" class="btn btn-danger" value="'+data.message[i].id+'">移出购物车</button></td></tr>';
                    $("#trs").append(content);
                    // 初始化按钮的disabled
                    if($("#num" + i).val()<=1){
                        //console.log("if_i:"+i);
                        $("#num_minus" + i).attr("disabled", true);
                    }
                }
                $("#trs").append('<tr><td></td><td></td><td></td><td></td><td><strong>总计:</strong>￥<span id="amount">0.00</span></td>'
                    + '<td><button type="button" id="settlement" class="btn btn-success">去结算<span class="glyphicon glyphicon-chevron-right"></span></button></td></tr>');
                // 初始化组件控制
                for (let i = 0; i < data.message.length; i++) {
                    // 加减按钮控制input的num
                    $("#num_minus" + i).on("click", function () {
                        var number = $("#num" + i).val();
                        if (number > 1) {
                            $("#num" + i).val(--number);
                            $("#total"+i).text($("#unit"+i).text()*number);
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
                        $("#total"+i).text($("#unit"+i).text()*number);
                        console.log("i:" + i);
                        console.log("num_plus:" + $("#num" + i).val());
                    });
                    // 处理checkbox的点击
                    $("#" + i).on("click", function () {
                        // 处理单选checkbox和全选checkbox checked状态的同步
                        if($("input:checkbox[name='goods']:checked").length<data.message.length){
                            $("input:checkbox[name='all']").prop("checked",false);
                        }
                        else{
                            $("input:checkbox[name='all']").prop("checked",true);
                        }
                        // 先清零再累计
                        $("#amount").text("0.00");
                        purchase = [];
                        let j = 0;
                        //console.log($("input:checkbox[name='goods']:checked").val());
                        // 遍历选中的checkbox
                        $.each($("input:checkbox[name='goods']:checked"), function () {
                            var id = $(this).attr("id");
                            //console.log($(this).val());
                            purchase[j] = $(this).val();
                            console.log(purchase);
                            //console.log(j);
                            //console.log(this);
                            //console.log("in"+$("input:checkbox[name='goods']:checked").length);
                            //console.log(parseFloat($("#amount").text()));
                            //console.log(parseFloat($("#total"+id).text()));
                            //console.log(parseFloat($("#amount").text())+parseFloat($("#total"+id).text()));
                            // 对JQuery text()方法取得的String进行数学相加
                            $("#amount").text(parseFloat($("#amount").text()) + parseFloat($("#total" + id).text()));
                            j++;
                        });
                    });
                    // 移出购物车
                    $("#moveout"+i).on("click", function () {
                        console.log($(this).val());
                        layer.confirm("确定将此商品移出购物车吗？",{
                            icon: 3,
                            btn: ["确定","再看看"]},function(){
                                $.ajax({
                                    type: "post",
                                    url: "servlet/MoveOut",
                                    dataType: "json",
                                    data: {
                                        id: $("#moveout"+i).val(),
                                    }, success: function (data) {
                                        if(data.status=="success"){
                                            layer.msg(data.message, { icon: 1 });
                                            $("#trs").empty();
                                            goodsList();
                                        }
                                        else{
                                            layer.msg(data.message, { icon: 2 });
                                        }
                                    },error: function(){
                                        layer.msg('抱歉，服务器异常，请稍后再试', { icon: 2 });
                                    }
                                });
                            },function(){});
                    });
                }
                // 处理checkbox的全选
                $("input:checkbox[name='all']").on("click", function () {
                    if (this.checked) {
                        $("#amount").text("0.00");
                        purchase = [];
                        var sum = 0;
                        $("input:checkbox[name='goods']").prop("checked", true);
                        for(var i=0;i<data.message.length;i++){
                            sum = sum + parseFloat($("#total" + i).text());
                            purchase[i] = $("input:checkbox[name='goods']:checked").val();
                        }
                        console.log(sum);
                        console.log(purchase);
                        $("#amount").text(sum);
                    }
                    else {
                        $("input:checkbox[name='goods']").prop("checked", false);
                        purchase = [];
                        $("#amount").text("0.00");
                    }
                });
                //结算
                $("#settlement").on("click", function () {
                    if (purchase.length == 0) {
                        layer.open({
                            icon: 2,
                            content: "先选择商品哦"
                        });
                    }
                });
            },error:function(data){
                console.log("ajax_personal:"+data);
            }
        });
    }
    

});
