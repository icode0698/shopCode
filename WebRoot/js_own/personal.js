$(function () {
    // 声明购物车全局变量
    var user = "";
    var idList= [];
    var purchase = {};
    var lengthIndex = 0;
    layui.use(['layer','table'], function () {
        var layer = layui.layer
            ,table = layui.table;
    // 判断登录状态
    $.ajax({
        type: "post",
        dataType: "json",
        url: "servlet/Whether",
        data: {
            type: "ajax_whether",
            message: "getStatus"
        }, success: function (data) {
            user = data.user;
            console.log(data);
            if (data.status == "success") {
                trolleyList();
                // orderList();
                $("#li_info").on("click",function(){
                    $("#userInfo").removeClass("hidden");
                    $("#modifyInfo").addClass("hidden");
                    $("#success_div").addClass("hidden");
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
});
    function trolleyList() {
        $("#trolley_trs").empty();
        $("#allgoods").prop("checked",false);
        $.ajax({
            type: "post",
            url: "servlet/Trolley",
            dataType: "json",
            data: {
                type: "ajax_trolley"
            }, success: function (data) {
                console.log(data);
                // console.log(data.status);
                // console.log(data.message.length);
                // 生成购物车列表
                let numOrigin = [];
                for (var i = 0; i < data.message.length; i++) {
                    numOrigin[i] = data.message[i].num;
                    let content = '<tr class="wow slideInRight" data-wow-duration="0.7s" id="trolleytr'+i+'"><td><div class="checkbox checkbox-primary"><input type="checkbox" name="goods" value="' + data.message[i].id + '" id="' + i + '" class="checkbox_goods">'
                        + '<label for="' + i + '"><img id="img'+i+'" value="'+data.message[i].sku+'"src="' + data.message[i].imgList[0] + '" alt="" class="img_goods"></label></div>'
                        + '</td><td><strong id="goodsName'+i+'">' + data.message[i].goodsName + '</strong><br>'
                        + '<p id="p'+i+'"><span id="color' + i + '">' + data.message[i].color + '</span><span id="screen' + i + '">' + data.message[i].screen +'</span><span id="storage' + i + '">'+ data.message[i].storage + '</span></p>'
                        + '</td><td>￥<span id="unit' + i + '"></span>'
                        + '</td><td id="numtd'+i+'"><div class="btn-group"><button id="num_minus' + i + '" type="button" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button>'
                        + '<div class="btn-group"><input id="num' + i + '" type="text" class="form-control input_size text-center" value="' + data.message[i].num + '"></div>'
                        + '<button id="num_plus' + i + '" type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></button>'
                        + '</div><p class="p_margin">库存<span class="stock_size text-primary" id="stock' + i + '"> ' + data.message[i].stock + ' </span>件</p>'
                        + '</td><td><strong>￥<span id="total' + i + '"></span></strong>'
                        + '</td><td><button id="moveout' + i + '"type="button" class="btn btn-danger" value="' + data.message[i].id + '">移出购物车</button></td></tr>';
                    $("#trolley_trs").append(content);
                    $("#unit"+i).text(data.message[i].unitPrice.toFixed(2));
                    $("#total"+i).text(data.message[i].totalPrice.toFixed(2));
                    $("#trolleytr"+i).attr("data-wow-delay",0.05*(i%11)+"s");
                    // 初始化按钮的disabled
                    if ($("#num" + i).val() <= 1) {
                        $("#num_minus" + i).attr("disabled", true);
                    }
                    if ($("#num" + i).val() >= parseInt($("#stock" + i).text())) {
                        $("#num_plus" + i).attr("disabled", true);
                    }
                    // 初始化checkbox的disabled
                    if ($("#stock" + i).text() == 0 || $("#num" + i).val() > parseInt($("#stock" + i).text())) {
                        $("#" + i).prop("disabled", true);
                        $("#trolleytr" + i).addClass("danger");
                    }
                    // 初始化input text的disabled
                    if ($("#stock" + i).text() == 0) {
                        $("#num" + i).prop("disabled", true);
                    }
                    // 初始化库存字体颜色
                    if ($("#stock" + i).text() < 10) {
                        $("#stock" + i).removeClass("text-primary");
                        $("#stock" + i).addClass("text-danger");
                    }
                }
                $("#trolley_trs").append('<tr><td></td><td></td><td></td><td></td><td>总计:<span class="span_font">￥</span><strong class="strong_font" id="amount">0.00</strong></td>'
                    + '<td><button type="button" id="settlement" class="btn btn-success">去结算<span class="glyphicon glyphicon-chevron-right"></span></button></td></tr>');
                // 统计购物车列表能够结算的商品的数量
                $.each($("input:checkbox[name='goods']"), function () {
                    // console.log($(this).prop("disabled"));
                    if($(this).prop("disabled")==false){
                        lengthIndex++;
                    }
                });
                // console.log("lengthIndex:"+lengthIndex);
                // 初始化组件控制
                for (let i = 0; i < data.message.length; i++) {
                    // 加减按钮控制input的num
                    $("#num_minus" + i).on("click", function () {
                        var number = $("#num" + i).val();
                        if (number > 1) {
                            $("#num" + i).val(--number);
                            $("#total" + i).text(($("#unit" + i).text() * number).toFixed(2));
                            if (number == 1) {
                                $("#num_minus" + i).attr("disabled", true);
                            }
                            if (number < parseInt($("#stock" + i).text())) {
                                $("#num_plus" + i).attr("disabled", false);
                            }
                            if(number <= parseInt($("#stock" + i).text())){
                                $("#" + i).prop("disabled", false);
                                $("#trolleytr" + i).removeClass("danger");
                            }
                            if($("#"+i).prop("checked")){
                                $("#amount").text((parseFloat($("#amount").text())-parseFloat($("#unit"+i).text())).toFixed(2));
                            }
                        }
                        // console.log("num_minus:" + $("#num" + i).val());
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
                        $("#total" + i).text(($("#unit" + i).text() * number).toFixed(2));
                        if($("#"+i).prop("checked")){
                            $("#amount").text((parseFloat($("#amount").text())+parseFloat($("#unit"+i).text())).toFixed(2));
                        }
                        // console.log("i:" + i);
                        // console.log("num_plus:" + $("#num" + i).val());
                    });
                    // 处理数量变化
                    // input绑定oninput和propertychange,动态更新小计和总价,防止num输入非数字字符
                    $("#num"+i).bind("input propertychange",function(){
                        console.log($("#num"+i).val());
                        console.log(isNaN($("#num"+i).val()));
                        if(isNaN($("#num"+i).val())){
                            $("#num"+i).val(numOrigin[i]);
                            // $("#total"+i).text("0.00");
                            // $("#numtd"+i).addClass("danger");
                            // $("#numtd"+i).addClass("tdradius");
                        }
                        else if($("#num" + i).val() > parseInt($("#stock" + i).text())){
                            $("#num" + i).val(parseInt($("#stock" + i).text()));
                        }
                            $("#" + i).prop("disabled", false);
                            $("#numtd" + i).removeClass("danger");
                            $("#numtd" + i).removeClass("tdradius");
                            $("#trolleytr" + i).removeClass("danger");
                        // 更新tr小计
                        $("#total" + i).text(($("#unit" + i).text() * $("#num" + i).val()).toFixed(2));
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
                        $("#amount").text(amount.toFixed(2));
                    });
                    // 防止num输入0和小数
                    $("#num"+i).blur(function(){
                        if($("#num"+i).val()==0){
                            $("#num"+i).val(numOrigin[i]);
                        }
                        var reg = new RegExp("\\.");
                        if(reg.test($("#num"+i).val())){
                            layer.msg("请不要输入小数", {time: 1200, shift: 6}, function(){});
                            $("#num"+i).val(numOrigin[i]);
                        }
                        // 动态更新小计和总价
                        $("#total" + i).text(($("#unit" + i).text() * $("#num" + i).val()).toFixed(2));
                        if ($("#num" + i).val() <= parseInt($("#stock" + i).text())) {
                            $("#numtd" + i).removeClass("danger");
                            $("#numtd" + i).removeClass("tdradius");
                            $("#trolleytr" + i).removeClass("danger");
                        }
                        else{
                            $("#" + i).prop("disabled", true);
                            $("#numtd" + i).addClass("danger");
                            $("#numtd" + i).addClass("tdradius");
                            $("#trolleytr" + i).addClass("danger");
                        }
                        let amount = 0;
                        $.each($("input:checkbox[name='goods']"),function(){
                            if($(this).prop("checked")){
                                let i = $(this).attr("id");
                                amount = amount+parseFloat($("#total"+i).text());
                            }
                        });
                        $("#amount").text(amount.toFixed(2));
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
                        // 处理单选交互效果
                        console.log("++++++:"+$(this).prop("checked"));
                        if($(this).prop("checked")){
                            console.log("i:"+i);
                            $("#trolleytr"+i).addClass("info");
                        }
                        else{
                            $("#trolleytr"+i).removeClass("info");
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
                            $("#amount").text((parseFloat($("#amount").text()) + parseFloat($("#total" + id).text())).toFixed(2));
                        });
                    });
                    // 移出购物车
                    $("#moveout" + i).on("click", function () {
                        // console.log($(this).val());
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
                            // console.log(this);
                            // console.log($(this).prop("disabled"));
                            if ($(this).prop("disabled")==false) {
                                //console.log($("input:checkbox[name='goods']:checked"));
                                $(this).prop("checked", true);
                                // 处理全选交互效果
                                $("#trolleytr"+id).addClass("info");
                                //console.log($("input:checkbox[name='goods']:checked"));
                            }
                        });
                        $("#amount").text("0.00");
                        $.each($("input:checkbox[name='goods']:checked"), function () {
                            var id = $(this).attr("id");
                            $("#amount").text((parseFloat($("#amount").text()) + parseFloat($("#total" + id).text())).toFixed(2));
                        });
                    }
                    else {
                        // $("input:checkbox[name='goods']").prop("checked", false);
                        // 处理全选交互效果
                        $.each($("input:checkbox[name='goods']"), function () {
                            var id = $(this).attr("id");
                            if ($(this).prop("disabled")==false) {
                                $(this).prop("checked", false);
                                $("#trolleytr"+id).removeClass("info");
                            }
                        });
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
                        if(purchase.num<=0){
                            index = 1;
                            return false;
                        }
                        // console.log(purchase);
                        idList.push(purchase);
                        // console.log(idList);
                        layerContent = layerContent + '<tr><td><strong>' + $("#goodsName"+i).text() + '</strong><br>'+$("#p"+i).text() +'</td>'+ '<td>' 
                            +"￥"+ $("#unit"+i).text() +'</td>'+ '<td>' +$("#num"+i).val() +'</td>'+'<td>' +"￥"+ $("#total"+i).text()+'</td></tr>';
                    });
                    // console.log(idList);
                    if(index==1){
                        layer.open({
                            icon: 2,
                            content: "数量输入存在非法字符！"
                        });
                    }
                    else if (idList.length == 0) {
                        layer.open({
                            icon: 2,
                            content: "要先选择商品哦！"
                        });
                    }
                    else{
                        layerContent = '<table class="table table-bordered"><thead><tr><th>商品</th><th>价格</th><th>数量</th><th>小计</th></tr></thead><tbody>' 
                            + layerContent + '<tr><td></td><td></td><td>总计:</td><td><span class="span_font">￥</span><strong class="strong_font">'+ $("#amount").text()+'</strong></td></tr></tbody></table>';
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
                                        var contentError = '<p>'+data.message+'</p><p class="tips_size text-muted">Tips：'+data.tip+'</p>'
                                        layer.open({
                                            icon: 2,
                                            content: contentError
                                        });
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
    // layui表单验证
    layui.use('form', function () {
        var form = layui.form;
        form.verify({
            nickname: function (value) {
                if(/^\s+/.test(value)){
                 return '昵称不能是连续的空格';
                }
            }
            //我们既支持上述函数式的方式，也支持下述数组的形式
            //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
            , pass: [
                /^[\S]{6,20}$/
                , '6到20位字符，不能有空格'
            ]
            ,orginsame: function(){
                if($("#orginpass").val()==$("#newpass").val()){
                    return "新密码建议不要和旧密码相同";
                }
            }
            ,same:function(){
                if($("#repass").val()!=$("#newpass").val()){
                    return "新密码两次输入不一致";
                }
            }
        });
        form.on('submit(ensure)', function(data){
            console.log(data.field);
            $.ajax({
                type: "post",
                dataType: "json",
                url: "servlet/ModifyInfo",
                data: {
                    type: "ajax_modifyinfo",
                    nickName: data.field.nickname,
                    orginPass: data.field.orginpass,
                    newPass: data.field.newpass
                }, success: function (data) {
                    if(data.status=="success"){
                        info();
                        $("#userInfo").addClass("hidden");
                        $("#modifyInfo").addClass("hidden");
                        $("#success_div").removeClass("hidden");
                    }
                    else{
                        layer.alert(data.message,{icon:2});
                    }
                }, error: function () {
                    console.log("服务器异常\najax_whether:" + XMLResponse.status);
                    return;
                }
            });
            return false; 
          });
    });
    // 返回个人信息
    $("#goto").on("click", function () {
        info();
        $("#userInfo").removeClass("hidden");
        $("#modifyInfo").addClass("hidden");
        $("#success_div").addClass("hidden");
    });
    $("#modify").on("click", function () {
        $("#userInfo").addClass("hidden");
        $("#modifyInfo").removeClass("hidden");
        $("#success_div").addClass("hidden");
    });
    // 重新修改
    $("#again").on("click", function () {
        $("#userInfo").addClass("hidden");
        $("#modifyInfo").removeClass("hidden");
        $("#success_div").addClass("hidden");
    });
    function info(){
        $.ajax({
            type: "post",
            dataType: "json",
            url: "servlet/Info",
            data: {
                type: "ajax_info"
            }, success: function (data) {
                if(data.status=="success"){
                    $("#user").text(data.user);
                    $("#nickName").text(data.nickName);
                    $("#lastTime").text(data.lastTime);
                    $("#regTime").text(data.regTime);
                    $("#viewCount").text(data.viewCount);
                    $("#demo").attr("src",data.headPic);
                }
                else{
                    layer.msg("服务器异常，请稍候再试。");
                }
            }, error: function () {
                console.log("服务器异常\najax_whether:" + XMLResponse.status);
                return;
            }
        });
    }
    function orderList(){
        $("#ordertab").empty();
        $("#pageul").empty();
        $.ajax({
            type: "post",
            dataType: "json",
            url: "servlet/Order",
            data: {
                type: "ajax_order",
            },success: function(data){
                console.log(data);
                // console.log(data.status);
                // console.log(data.message.length);
                // 生成订单列表
                let contentHeader = '';
                let contentSum = '';
                let itemNum = 14;
                let pageNum = Math.ceil(data.message.length/itemNum);
                let pageNow = 0;
                let pageTotal = 0;
                console.log(pageNum);
                $("#pageul").append('<li id="liPrevious"><a href="#pagePrevious"><i class="fa fa-arrow-left" aria-hidden="true"></i> 上一页</a></li>');
                $("#liPrevious").on("click",function(){
                    if(pageNow==0){
                        layer.msg("已经是第一页了");
                    }
                    else {
                        $("#orderPage" + pageNow).removeClass("active");
                        $("#orderPage" + (pageNow - 1)).addClass("active");
                        $("#orderPage"+(pageNow-1)).addClass("in");
                        $("#pageli"+pageNow).removeClass("active");
                        $("#pageli"+(pageNow-1)).addClass("active");
                        pageNow = pageNow-1;
                        window.scrollTo(0, 0);
                    }
                });
                for(let j=0; j<pageNum; j++){
                    contentHeader = '<div class="tab-pane fade in" id="orderPage'+j+'"><div class="table-responsive table_border"><table id="orderTable'+j+'" class="table table-hover tab-pane fade in">'
                        +'<thead><tr><th>序号</th><th>图片</th><th>商品</th><th>价格</th><th>数量</th><th>已支付</th><th>操作</th></tr></thead><tbody id="order_trs'+j+'">';
                    contentSum = '';
                    for (let i = itemNum*j; i < itemNum*(j+1)&&i<data.message.length; i++) {
                        let orderNumber = "";
                        if(i<9){
                            orderNumber = "0"+(i+1);
                        }
                        else{
                            orderNumber = i+1;
                        }
                        let content = '<tr class="wow slideInRight" data-wow-duration="0.7s" id="order_tr'+i+'"><td>'+orderNumber+'</td><td><img id="order_img'+i+'" value="'+data.message[i].sku+'"src="' + data.message[i].imgList[0] + '" alt="" class="img_goods">'
                            + '</td><td><strong id="strong'+i+'" value="success"><span value="'+data.message[i].spu+'" id="order_goodsName'+i+'">' + data.message[i].goodsName + '</span></strong><br>'
                            + '<p id="p'+i+'"><span id="order_color' + i + '">' + data.message[i].color + '</span><span id="order_screen' + i + '">' + data.message[i].screen +'</span><span id="order_storage' + i + '">'+ data.message[i].storage + '</span></p>'
                            + '</td><td>￥<span id="order_unit' + i + '"></span>'
                            + '</td><td><span id="num'+i+'">'+ data.message[i].num+'</span>'
                            + '</td><td><strong>￥<span id="order_total' + i + '"></span></strong>'
                            + '</td><td><button id="delete' + i + '"type="button" class="btn btn-danger btn_margin" value="' + data.message[i].id + '">删除订单</button>'
                            + '<button id="again' + i + '"type="button" class="btn btn-success btn_margin" value="' + data.message[i].id + '">再次购买</button>'
                            + '<button id="comment' + i + '"type="button" class="btn btn-info btn_margin" value="' + data.message[i].id + '">评价</button></td></tr>';
                        contentSum = contentSum + content;
                        // console.log(content);
                        // $("#ordertab").append(content);
                    }
                    contentSum = contentHeader + contentSum + '</tbody></table></div></div>';
                    // console.log(contentSum);
                    $("#ordertab").append(contentSum);
                    if(j==0){
                        $("#pageul").append('<li id="pageli'+j+'" value="'+j+'" class="active"><a href="#orderPage'+j+'" data-toggle="tab" onclick="javascript:window.scrollTo(0, 0);">'+(j+1)+'</a></li>');
                        $("#orderPage"+j).addClass("active");
                    }
                    else{
                        $("#pageul").append('<li id="pageli'+j+'" value="'+j+'"><a href="#orderPage'+j+'" data-toggle="tab" onclick="javascript:window.scrollTo(0, 0);">'+(j+1)+'</a></li>');
                    }
                    $("#pageli"+j).on("click",function(){
                        pageNow = parseInt($(this).attr("value"));
                        // console.log(pageNow);
                    });
                    pageTotal = j;
                }
                $("#pageul").append('<li id="liNext"><a href="#pageNext">下一页 <i class="fa fa-arrow-right" aria-hidden="true"></i></a></li>');
                $("#liNext").on("click",function(){
                    console.log("pageTotal:"+pageTotal);
                    console.log("pageNow:"+pageNow);
                    if(pageNow==pageTotal){
                        layer.msg("已经是最后一页了");
                    }
                    else{
                        $("#orderPage"+pageNow).removeClass("active");
                        $("#orderPage"+(pageNow+1)).addClass("in");
                        $("#orderPage"+(pageNow+1)).addClass("active");
                        $("#pageli"+pageNow).removeClass("active");
                        $("#pageli"+(pageNow+1)).addClass("active");
                        pageNow = pageNow+1;
                        window.scrollTo(0, 0);
                    }
                });
                for (let i = 0; i < data.message.length; i++) {
                    $("#order_unit" + i).text(data.message[i].unitPrice.toFixed(2));
                    $("#order_total" + i).text(data.message[i].totalPrice.toFixed(2));
                    $("#order_tr" + i).attr("data-wow-delay", 0.05 * (i % itemNum) + "s");
                    $("#order_tr" + i).on("click", function () {
                        var orderInfo = "订单编号：" + data.message[i].id + "<br>" + "商品名称：" + data.message[i].goodsName + "<br>" + "品牌：" + data.message[i].brandName + "<br>" + "商品SKU：" + data.message[i].sku + "<br>"
                            + "存储容量：" + data.message[i].storage + "<br>" + "颜色：" + data.message[i].color + "<br>" + "屏幕尺寸：" + data.message[i].screen + "<br>" + "购买数量：" + data.message[i].num + "<br>"
                            + "价格：￥" + data.message[i].unitPrice.toFixed(2) + "<br>" + "小计：￥" + data.message[i].totalPrice.toFixed(2) + "<br>" + "已支付：￥" + data.message[i].totalPrice.toFixed(2) + "<br>"
                            + "创建时间：" + data.message[i].createTime + "<br>" + "支付时间：" + data.message[i].paymentTime + "<br>";
                        layer.confirm(orderInfo, {
                            title: "订单信息",
                            btn: ["再次购买", "确定"]
                        }, function () {
                            var url = 'details.html?spu=' + $("#order_goodsName" + i).attr("value");
                            window.open(url);
                        }, function () {});
                    });
                    // console.log(i);
                    // console.log($("#num"+i).attr("id"));
                    // console.log($("#order_goodsName"+i).attr("id")+":"+$("#order_goodsName"+i).attr("value")+'&goods='+$("#order_goodsName"+i).text());
                    // console.log($("#order_unit"+i).attr("id"));
                    // 再次购买
                    $("#again" + i).on("click", function (e) {
                        // console.log(i);
                        // console.log($("#num"+i).attr("id"));
                        // console.log($("#order_img"+i).attr("id")+":"+$("#order_goodsName"+i).attr("value"));
                        // console.log(this);
                        //阻止click事件冒泡
                        e.stopPropagation();
                        // console.log($("#strong"+i).attr("value"));
                        let url = 'details.html?spu=' + $("#order_goodsName" + i).attr("value");
                        // console.log(url);
                        window.open(url);
                    });
                    // 评价
                    $("#comment" + i).on("click", function (e) {
                        //阻止click事件冒泡
                        e.stopPropagation();
                        let url = 'comment.html?id=' + $(this).attr("value");
                        window.open(url);
                    });
                    // 删除订单
                    $("#delete" + i).on("click", function (e) {
                        e.stopPropagation();
                        var contentDelte = "订单编号：" + data.message[i].id + "，确定删除订单吗？";
                        layer.confirm(contentDelte, {
                            title: "删除订单",
                            btn: ["确定删除", "再看看"]
                        },function(){
                            $.ajax({
                                type: "post",
                                dataType: "json",
                                url: "servlet/Delete",
                                data: {
                                    type: "ajax_delete",
                                    id: data.message[i].id
                                },success:function(data){
                                    if(data.status=="success"){
                                        layer.msg(data.message,{icon:1});
                                        orderList();
                                    }
                                    else{
                                        layer.msg(data.message,{icon:1});
                                    }
                                },error:function(){
                                    layer.msg('抱歉，服务器异常，请稍后再试', { icon: 2 });
                                }
                            });
                        },function(){});
                    });
                }
            },error: function(){
                console.log("ajax_order:" + data);
            }
        });
    }
    function about(){

    }
    // 上传图片
    layui.use('upload', function(){
        upload = layui.upload;
        //普通图片上传
        var uploadInst = upload.render({
            elem: '#uppic'
            , url: 'servlet/Upload'
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $("#pictip").text(file.name);
                    $('#demo').attr('src', result); //图片链接（base64）
                    console.log(file);
                });
            }
            , done: function (data) {
                if(data.status=="success"){
                    return layer.msg('上传成功',{icon:1});
                }
                if (data.status=="fail") {
                    return layer.msg('上传失败，请稍候再试',{icon:2});
                }
            }
            , error: function () {
                return layer.msg('上传失败，请稍候再试',{icon:2});
            }
        });
    });
});
