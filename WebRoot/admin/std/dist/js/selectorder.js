$(function () {
    layui.use(['layer', 'table'], function () {
        var layer = layui.layer
            , table = layui.table;
        getOrder($("#orderid").val(),$("#user").val());
        let origin = '';
        $("#orderid").bind("input propertychange",function(){
            console.log("orderidout"+origin);
            if(isNaN($("#orderid").val())){
                $("#orderid").val(origin);
            }
            else{
                origin = $("#orderid").val();
                console.log("orderid"+origin);
            }
        });
        $("#order").on("click",function(){
            getOrder($("#orderid").val(),$("#user").val());
        });
        function getOrder(value,user){
            $.ajax({
                type: "post",
                dataType: "json",
                url: "../../../../../servlet/SelectOrder",
                data: {
                    message: "selectOrder",
                    value: value,
                    user: user
                }, success: function (data) {
                    console.log(data);
                    if (data.status == "success") {
                        $("#trs").empty();
                        for (let i = 0; i < data.message.length; i++) {
                            var pay;
                            var paymentTime;
                            if(data.message[i].pay==true){
                                pay="已支付";
                                paymentTime=data.message[i].paymentTime;
                            }
                            else{
                                pay="未支付" ;
                                paymentTime="暂无";
                            }
                            let content = '<tr><td>'+data.message[i].id+'</td><td>'+data.message[i].user+'</td><td>'+data.message[i].goodsName+'</td><td>'+data.message[i].sku+'</td>'
                                +'<td>'+data.message[i].categoryName+'</td><td>'+data.message[i].brandName+'</td><td>'+data.message[i].storage+'</td><td>'+data.message[i].color+'</td><td>'+data.message[i].screen+'</td>'
                                +'<td><span>￥</span>'+data.message[i].unitPrice.toFixed(2)+'</td><td>'+data.message[i].num+'</td><td><span>￥</span>'+data.message[i].totalPrice.toFixed(2)+'</td>'
                                +'<td>'+pay+'</td><td>'+data.message[i].createTime+'</td><td>'+paymentTime+'</td></tr>';
                            $("#trs").append(content);
                        }
                        table.init('order', {
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
        }
        
        //第一个实例
        // table.render({
        //     elem: '#demo'
        //     , url: '../../../../../servlet/SelectOrder' //数据接口
        //     , cols: [[ //表头
        //         { field: 'id', title: 'ID', width: 80, sort: true, fixed: 'left' }
        //         , { field: 'user', title: '用户名', width: 80 }
        //         , { field: 'goodsName', title: '性别', width: 80, sort: true }
        //         , { field: 'sku', title: '城市', width: 80 }
        //         , { field: 'categoryName', title: '签名', width: 177 }
        //         , { field: 'brandName', title: '积分', width: 80, sort: true }
        //         , { field: 'storage', title: '评分', width: 80, sort: true }
        //         , { field: 'color', title: '职业', width: 80 }
        //         , { field: 'screen', title: '财富', width: 135, sort: true }
        //         , { field: 'unitPrice', title: '财富', width: 135, sort: true }
        //         , { field: 'num', title: '财富', width: 135, sort: true }
        //         , { field: 'totalPrice', title: '财富', width: 135, sort: true }
        //         , { field: 'pay', title: '财富', width: 135, sort: true }
        //         , { field: 'createTime', title: '财富', width: 135, sort: true }
        //         , { field: 'paymentTime', title: '财富', width: 135, sort: true }
        //     ]]
        // });
    });
});