$(function () {
    // let picIndex = 0;
    layui.use(['layer', 'form', 'upload'], function () {
        var layer = layui.layer
            , form = layui.form
            , upload = layui.upload;
            form.verify({
                nickname: function (value) {
                    if(/^\s+/.test(value)){
                     return '不能有连续的空格';
                    }
                }
                //我们既支持上述函数式的方式，也支持下述数组的形式
                //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
                , pass: [
                    /^[\S]{6,20}$/
                    , '6到20位字符，不能有空格'
                ]
                ,same:function(){
                    if($("#repass").val()!=$("#pass").val()){
                        return "两次密码输入不一致";
                    }
                }
            });
            upload.render({
                elem: '#choose'
                ,url: 'servlet/Upload'
                ,data: {
                    id: function(){
                        return $("#pic").val();
                    }
                    ,spu: function(){
                        return $("#spu").val();
                    }
                }
                ,auto: false
                ,bindAction: '#ensure'
                , choose: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        $("#pictip").text(file.name);
                        $('#demo').attr('src', result); //图片链接（base64）
                        // picIndex = 1;
                        console.log(file);
                    });
                }
                , done: function (data) {
                    if(data.status=="success"){
                        console.log('上传成功');
                    }
                    if (data.status=="fail") {
                        console.log('上传失败，请稍候再试');
                    }
                }
                , error: function () {
                    console.log('上传失败，请稍候再试');
                }
            });
            form.on('submit(ensure)', function(data){
                console.log(data.field);
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "servlet/Register",
                    data: {
                        type: "ajax_register",
                        user: data.field.user,
                        nickName: data.field.nickname,
                        pass: data.field.pass,
                        // picIndex: picIndex
                    }, success: function (data) {
                        if(data.status=="success"){
                    layer.confirm(data.message, {
                        icon: 1,
                        btn: ["前往登录", "浏览首页"]
                    }, function () {
                        location.href = 'login.html';
                    }, function () {
                        location.href = 'index.html';
                    });
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
        // $("#sub").click(function () {
        //     var user = $("#user").val();
        //     console.log("user:" + user);
        //     console.log("password:" + password);
        //     console.log(user == "");
        //     console.log($("#password").val() == "");
        //     if (user == "") {
        //         $("#div_hide").removeClass("hidden");
        //         $("#error_hide").removeClass("hidden");
        //         $("#error_tip").text("UserID不可为空");
        //         return;
        //     }
        //     if ($("#password").val() == "") {
        //         $("#div_hide").removeClass("hidden");
        //         $("#error_hide").removeClass("hidden");
        //         $("#error_tip").text("Password不可为空");
        //         return;
        //     }
        //     else {
        //         $.ajax({
        //             type: "post",
        //             url: "servlet/Register",
        //             dataType: "json",
        //             async: false,
        //             data: {
        //                 "user": user,
        //                 "password": $("#password").val(),
        //                 type: "ajax_register"
        //             },
        //             success: function (data) {
        //                 console.log("json.status:" + data.status);
        //                 console.log("json.user:" + data.user);
        //                 if (data.status == "success") {
        //                     $("#error_hide").addClass("hidden");
        //                     $("#div_hide").removeClass("hidden");
        //                     $("#success_hide").removeClass("hidden");
        //                     $("#success_tip").text(data.message);
        //                     setTimeout("location.href = 'index.html'", 1000);
        //                 }
        //                 if (data.status == "error") {
        //                     $("#div_hide").removeClass("hidden");
        //                     $("#error_hide").removeClass("hidden");
        //                     $("#error_tip").text(data.message);
        //                     //return;
        //                 }
        //             },
        //             error: function (XMLResponse) {
        //                 $("#div_hide").removeClass("hidden");
        //                 $("#error_hide").removeClass("hidden");
        //                 $("#error_tip").text("抱歉，服务器异常\nXMLResponse_status:" + XMLResponse.status);
        //                 console.log("error_status:" + XMLResponse.status);
        //                 //return;
        //             }
        //         });
        //     }
        // });
    });
});