$(function () {
    // 声明购物车全局变量
    var idList= [];
    var purchase = {};
    var lengthIndex = 0;
    // 判断登录状态
    $.ajax({
        type: "post",
        dataType: "json",
        url: "servlet/Whether",
        data: {
            type: "ajax_whether",
            message: "getStatus"
        }, success: function (data) {
            console.log(data);
            if (data.status == "success") {
                trolleyList();
                $("#li_info").on("click",function(){
                    info();
                });
                $("#li_trolley").on("click",function(){
                    trolleyList();
                });
                $("#li_order").on("click",function(){
                    orderList();
                });
                $("#li_about").on("click",function(){
                    about();
                });
            }
            else {
                layer.confirm(data.message, {
                    icon: 3,
                    btn: ["前往登录", "浏览首页"]
                }, function () {
                    location.href = 'login.html';
                }, function () {
                    location.href = 'index.html';
                });
            }
        }, error: function () {
            console.log("服务器异常\najax_whether:" + XMLResponse.status);
        }
    });
    function trolleyList() {
        $.ajax({
            type: "post",
            url: "servlet/Trolley",
            dataType: "json",
            data: {
                type: "ajax_trolley"
            }, success: function (data) {
                console.log(data);
                console.log(data.status);
                console.log(data.message.length);
                // 生成购物车列表
                $("#trolley_trs").empty();
                for (var i = 0; i < data.message.length; i++) {
                    var content = '<tr><td><div class="checkbox checkbox-primary"><input type="checkbox" name="goods" value="' + data.message[i].id + '" id="' + i + '" class="checkbox_goods">'
                        + '<label for="' + i + '"><img id="img'+i+'" value="'+data.message[i].sku+'"src="' + data.message[i].imgList[0] + '" alt="" class="img_goods"></label></div>'
                        + '</td><td><strong id="goodsName'+i+'">' + data.message[i].goodsName + '</strong><br>'
                        + '<p id="p'+i+'"><span id="color' + i + '">' + data.message[i].color + '</span><span id="screen' + i + '">' + data.message[i].screen +'</span><span id="storage' + i + '">'+ data.message[i].storage + '</span></p>'
                        + '</td><td>￥<span id="unit' + i + '">' + data.message[i].unitPrice + '</span>'
                        + '</td><td><div class="btn-group"><button id="num_minus' + i + '" type="button" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button>'
                        + '<div class="btn-group"><input id="num' + i + '" type="text" class="form-control input_size text-center" value="' + data.message[i].num + '"></div>'
                        + '<button id="num_plus' + i + '" type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></button>'
                        + '</div><p class="p_margin">库存 <mark id="stock' + i + '">' + data.message[i].stock + '</mark> 件</p>'
                        + '</td><td>￥<span id="total' + i + '">' + data.message[i].totalPrice + '</span>'
                        + '</td><td><button id="moveout' + i + '"type="button" class="btn btn-danger" value="' + data.message[i].id + '">移出购物车</button></td></tr>';
                    $("#trolley_trs").append(content);
                    // 初始化按钮的disabled
                    if ($("#num" + i).val() <= 1) {
                        //console.log("if_i:"+i);
                        $("#num_minus" + i).attr("disabled", true);
                    }
                    if ($("#num" + i).val() >= parseInt($("#stock" + i).text())) {
                        $("#num_plus" + i).attr("disabled", true);
                    }
                    // 初始化checkbox的disabled
                    if ($("#stock" + i).text() == 0 || $("#num" + i).val() > parseInt($("#stock" + i).text())) {
                        $("#" + i).prop("disabled", true);
                    }
                }
                $("#trolley_trs").append('<tr><td></td><td></td><td></td><td></td><td><strong>总计:</strong>￥<span id="amount">0.00</span></td>'
                    + '<td><button type="button" id="settlement" class="btn btn-success">去结算<span class="glyphicon glyphicon-chevron-right"></span></button></td></tr>');
                // 统计购物车列表能够结算的商品的数量
                $.each($("input:checkbox[name='goods']"), function () {
                    console.log($(this).prop("disabled"));
                    if($(this).prop("disabled")==false){
                        lengthIndex++;
                    }
                });
                //console.log("lengthIndex:"+lengthIndex);
                // 初始化组件控制
                for (let i = 0; i < data.message.length; i++) {
                    // 加减按钮控制input的num
                    $("#num_minus" + i).on("click", function () {
                        var number = $("#num" + i).val();
                        if (number > 1) {
                            $("#num" + i).val(--number);
                            $("#total" + i).text($("#unit" + i).text() * number);
                            if (number == 1) {
                                $("#num_minus" + i).attr("disabled", true);
                            }
                            if (number < parseInt($("#stock" + i).text())) {
                                $("#num_plus" + i).attr("disabled", false);
                            }
                            if(number <= parseInt($("#stock" + i).text())){
                                $("#" + i).prop("disabled", false);
                            }
                            if($("#"+i).prop("checked")){
                                $("#amount").text(parseFloat($("#amount").text())-parseFloat($("#unit"+i).text()));
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
                        if (number >= parseInt($("#stock" + i).text())) {
                            $("#num_plus" + i).attr("disabled", true);
                        }
                        $("#total" + i).text($("#unit" + i).text() * number);
                        if($("#"+i).prop("checked")){
                            $("#amount").text(parseFloat($("#amount").text())+parseFloat($("#unit"+i).text()));
                        }
                        console.log("i:" + i);
                        console.log("num_plus:" + $("#num" + i).val());
                    });
                    // 处理数量变化
                    // input绑定oninput和propertychange
                    $("#num"+i).bind("input propertychange",function(event){
                        console.log($("#num"+i).val());
                        if(isNaN($("#num"+i).val())||$("#num"+i).val()<0){
                            $("#total"+i).text("0");
                        }
                        else if($("#num"+i).val()>parseInt($("#stock"+i).text())){
                            $("#num"+i).val(parseInt($("#stock"+i).text()));
                        }
                        else {
                            // 更新tr小计
                            $("#total" + i).text($("#unit" + i).text() * $("#num" + i).val());
                        }
                        let amount = 0;
                        $.each($("input:checkbox[name='goods']"),function(){
                            // 更新table总计
                            // console.log(this);
                            if($(this).prop("checked")){
                                let i = $(this).attr("id");  // 取出checkbox为checked的id
                                // console.log(i);
                                // console.log($(this).prop("checked"));
                                // console.log($("#total"+i).text());
                                amount = amount+parseFloat($("#total"+i).text());
                            }
                            // console.log("amount:"+amount);
                        });
                        if(amount!=0){
                            $("#amount").text(amount);
                        }
                    });
                    // input绑定onchange
                    // $("#num"+i).on("change",function(){
                    //     console.log("++++++++++++change");
                    // });
                    // 处理checkbox的点击
                    $("#" + i).on("click", function () {
                        // console.log($(this).prop("disabled"));
                        // 处理单选checkbox和全选checkbox checked状态的同步
                        if ($("input:checkbox[name='goods']:checked").length < lengthIndex) {
                            $("input:checkbox[name='all']").prop("checked", false);
                        }
                        else {
                            $("input:checkbox[name='all']").prop("checked", true);
                        }
                        // 先清零再累计
                        $("#amount").text("0.00");
                        // 遍历选中的checkbox
                        $.each($("input:checkbox[name='goods']:checked"), function () {
                            var id = $(this).attr("id");
                            //console.log($(this).val());
                            //console.log(j);
                            //console.log(this);
                            //console.log("in"+$("input:checkbox[name='goods']:checked").length);
                            //console.log(parseFloat($("#amount").text()));
                            //console.log(parseFloat($("#total"+id).text()));
                            //console.log(parseFloat($("#amount").text())+parseFloat($("#total"+id).text()));
                            // 对JQuery text()方法取得的String进行数学相加
                            $("#amount").text(parseFloat($("#amount").text()) + parseFloat($("#total" + id).text()));
                        });
                    });
                    // 移出购物车
                    $("#moveout" + i).on("click", function () {
                        console.log($(this).val());
                        layer.confirm("确定将此商品移出购物车吗？", {
                            icon: 3,
                            btn: ["确定", "再看看"]
                        }, function () {
                            $.ajax({
                                type: "post",
                                url: "servlet/MoveOut",
                                dataType: "json",
                                data: {
                                    id: $("#moveout" + i).val(),
                                }, success: function (data) {
                                    if (data.status == "success") {
                                        layer.msg(data.message, { icon: 1 });
                                        trolleyList();
                                    }
                                    else {
                                        layer.msg(data.message, { icon: 2 });
                                    }
                                }, error: function () {
                                    layer.msg('抱歉，服务器异常，请稍后再试', { icon: 2 });
                                }
                            });
                        }, function () { });
                    });
                }

                // 处理checkbox的全选
                $("input:checkbox[name='all']").on("click", function () {
                    if (this.checked) {
                        // console.log(this);
                        // console.log($(this).prop("disabled"));
                        $.each($("input:checkbox[name='goods']"), function () {
                            var id = $(this).attr("id");
                            console.log(this);
                            console.log($(this).prop("disabled"));
                            if ($(this).prop("disabled")==false) {
                                //console.log($("input:checkbox[name='goods']:checked"));
                                $(this).prop("checked", true);
                                //console.log($("input:checkbox[name='goods']:checked"));
                            }
                        });
                        $("#amount").text("0.00");
                        $.each($("input:checkbox[name='goods']:checked"), function () {
                            var id = $(this).attr("id");
                            $("#amount").text(parseFloat($("#amount").text()) + parseFloat($("#total" + id).text()));
                        });
                    }
                    else {
                        $("input:checkbox[name='goods']").prop("checked", false);
                        $("#amount").text("0.00");
                    }
                });
                // 结算
                $("#settlement").on("click", function () {
                    // 汇总选中商品的信息
                    let index = 0;
                    idList = [];
                    let layerContent = '';
                    $.each($("input:checkbox[name='goods']:checked"), function () {
                        purchase = {};
                        let i = $(this).attr("id");
                        purchase.id = $("#"+i).val();
                        purchase.num = $("#num"+i).val();
                        purchase.sku = $("#img"+i).attr("value");
                        console.log("img"+i+":"+$("#img"+i).attr("value"));
                        if(isNaN(purchase.num)){
                            index = 1;
                            return false;
                        }
                        if(purchase.num<0){
                            index = 1;
                            return false;
                        }
                        console.log(purchase);
                        idList.push(purchase);
                        console.log(idList);
                        layerContent = layerContent + '<tr><td><strong>' + $("#goodsName"+i).text() + '</strong><br>'+$("#p"+i).text() +'</td>'+ '<td>' 
                            +"￥"+ $("#unit"+i).text() +'</td>'+ '<td>' +$("#num"+i).val() +'</td>'+'<td>' +"￥"+ $("#total"+i).text()+'</td></tr>';
                    });
                    console.log(idList);
                    if(index==1){
                        layer.open({
                            icon: 2,
                            content: "数量输入存在非法字符"
                        });
                    }
                    else if (idList.length == 0) {
                        layer.open({
                            icon: 2,
                            content: "要先选择商品哦"
                        });
                    }
                    else{
                        layerContent = '<table class="table"><thead><tr><th>商品</th><th>价格</th><th>数量</th><th>小计</th></tr></thead><tbody>' 
                            + layerContent + '<tr><td></td><td></td><td><strong>总计:</strong></td><td>￥'+ $("#amount").text()+'</td></tr></tbody></table>';
                        // console.log(layerContent);
                        layer.confirm(layerContent,{
                            anim: 1,
                            area: ['auto', 'auto'],
                            title: "结算购物车",
                            btn: ["确认结算", "再看看"]
                        },function(){
                            $.ajax({
                                type: "post",
                                dataType: "json",
                                url: "servlet/Settlement",
                                data: {
                                    idList: JSON.stringify(idList),
                                },success: function(data){
                                    if (data.status == "success") {
                                        layer.msg(data.message, { icon: 1 });
                                        trolleyList();
                                    }
                                    else {
                                        layer.msg(data.message, { icon: 2 });
                                    }
                                },error: function(){
                                    layer.msg('抱歉，服务器异常，请稍后再试', { icon: 2 });
                                }
                            });
                        },function(){});
                    }
                });
            }, error: function (data) {
                console.log("ajax_trolley:" + data);
            }
        });
    }
    function info(){

    }
    function orderList(){
        $.ajax({
            type: "post",
            dataType: "json",
            url: "servlet/Order",
            data: {
                type: "ajax_order",
            },success: function(data){
                console.log(data);
                console.log(data.status);
                console.log(data.message.length);
                // 生成购物车列表
                $("#order_trs").empty();
                for (var i = 0; i < data.message.length; i++) {
                    var content = '<tr><td><img id="img'+i+'" value="'+data.message[i].sku+'"src="' + data.message[i].imgList[0] + '" alt="" class="img_goods">'
                        + '</td><td><strong id="goodsName'+i+'">' + data.message[i].goodsName + '</strong><br>'
                        + '<p id="p'+i+'"><span id="color' + i + '">' + data.message[i].color + '</span><span id="screen' + i + '">' + data.message[i].screen +'</span><span id="storage' + i + '">'+ data.message[i].storage + '</span></p>'
                        + '</td><td>￥<span id="unit' + i + '">' + data.message[i].unitPrice + '</span>'
                        + '</td><td>'+ data.message[i].num
                        + '</td><td>￥<span id="total' + i + '">' + data.message[i].totalPrice + '</span>'
                        + '</td><td><button id="moveout' + i + '"type="button" class="btn btn-success" value="' + data.message[i].id + '">再次购买</button></td></tr>';
                    $("#order_trs").append(content);
                    // 初始化按钮的disabled
                    if ($("#num" + i).val() <= 1) {
                        //console.log("if_i:"+i);
                        $("#num_minus" + i).attr("disabled", true);
                    }
                    if ($("#num" + i).val() >= parseInt($("#stock" + i).text())) {
                        $("#num_plus" + i).attr("disabled", true);
                    }
                    // 初始化checkbox的disabled
                    if ($("#stock" + i).text() == 0 || $("#num" + i).val() > parseInt($("#stock" + i).text())) {
                        $("#" + i).prop("disabled", true);
                    }
                }
                $("#order_trs").append('<tr><td></td><td></td><td></td><td></td><td><strong>总计:</strong>￥<span id="amount">0.00</span></td><td></td></tr>');
            },error: function(){
                console.log("ajax_order:" + data);
            }
        });
    }
    function about(){

    }
});
