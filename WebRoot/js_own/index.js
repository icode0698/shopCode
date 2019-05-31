$(function () {
    // 本地记录访问次数
    if (localStorage.getItem("times") == null || localStorage.getItem("times") == "" || localStorage.getItem("times") == undefined) {
        var count = 0;
        localStorage.setItem("times", ++count);
        console.log("times_if:" + localStorage.getItem("times").toString());
    }
    else {
        var count = localStorage.getItem("times");
        localStorage.setItem("times", ++count);
        console.log("times_else:" + localStorage.getItem("times").toString());
    }
    // 进页面获取个性推荐
    $.ajax({
        type: "post",
        url: "servlet/Recommend",
        dataType: "json",
        data: {
            type: "ajax_category",
            category: "手机"
        },
        success: function (data) {
            console.log(data);
            console.log("ajax_item_status:"+data.status);
            console.log("message.length:"+data.message.length);
            //$("#phone_row").empty();
            for(var i=0;i<data.message.length;i++){
                var content = '<div class="col-md-6 col_border ">' +
                    '<div class="div_out wow slideInUp">' +
                    '<img class="img_radius" src="'+data.message[i].imgList[0]+'" alt="item" />' +
                    '<div id="in'+i+'" class="div_in">' +
                    '<div id="div_text'+i+'" class="div_text">' +
                    '<h2 class="display_commend" id="h2_item'+i+'">'+data.message[i].goodsName+'</h2>' +
                    '<a href="details.html?spu='+data.message[i].goodsID+'&goods='+data.message[i].goodsName+'" id="span_item'+i+'" target="_blank" class="btn_shop btn_light">SHOW NOW</a>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>';
                $("#phone_row").append(content);
            }
        },
        error: function (data) {
            console.log("ajax_item_status:"+XMLResponse.status);
            console.log("servlet_status:"+data.status);
        }
    });
    // 处理动画效果
    $("#span_left").hide();
    $("#span_right").hide();
    $("#a_left").mouseover(function () {
        $("#span_left").stop().fadeIn("fast");
    });
    $("#a_left").mouseout(function () {
        $("#span_left").stop().fadeOut("fast");
    });
    $("#a_right").mouseover(function () {
        $("#span_right").stop().fadeIn("fast");
    });
    $("#a_right").mouseout(function () {
        $("#span_right").stop().fadeOut("fast");
    });
    // 处理分类的个性推荐
    $("#phone").click(function () {
        $.ajax({
            type: "post",
            url: "servlet/Recommend",
            dataType: "json",
            data: {
                type: "ajax_category",
                category: "手机"
            },
            success: function (data) {
                console.log(data);
                console.log("ajax_item_status:"+data.status);
                console.log("message.length:"+data.message.length);
                $("#phone_row").empty();//先清空div，防止重复添加
                for(var i=0;i<data.message.length;i++){
                    var content = '<div class="col-md-6 col_border">' +
                        '<div class="div_out wow slideInUp">' +
                        '<img class="img_radius" src="'+data.message[i].imgList[0]+'" alt="item" />' +
                        '<div id="in'+i+'" class="div_in">' +
                        '<div id="div_text'+i+'" class="div_text">' +
                        '<h2 class="display_commend" id="h2_item'+i+'">'+data.message[i].goodsName+'</h2>' +
                        '<a href="details.html?spu='+data.message[i].goodsID+'&goods='+data.message[i].goodsName+'" id="span_item'+i+'" target="_blank" class="btn_shop btn_light">SHOW NOW</a>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                    $("#phone_row").append(content);
                }
            },
            error: function (data) {
                console.log("ajax_item_status:"+data.status);
            }
        });
    });
    $("#tablet").click(function () {
        $.ajax({
            type: "post",
            url: "servlet/Recommend",
            dataType: "json",
            data: {
                type: "ajax_category",
                category: "平板"
            },
            success: function (data) {
                console.log(data);
                console.log("ajax_item_status:"+data.status);
                console.log("message.length:"+data.message.length);
                $("#tablet_row").empty();
                for(var i=0;i<data.message.length;i++){
                    var content = '<div class="col-md-6 col_border">' +
                        '<div class="div_out wow slideInUp">' +
                        '<img class="img_radius" src="'+data.message[i].imgList[0]+'" alt="item" />' +
                        '<div id="in'+i+'" class="div_in">' +
                        '<div id="div_text'+i+'" class="div_text">' +
                        '<h2 class="display_commend" id="h2_item'+i+'">'+data.message[i].goodsName+'</h2>' +
                        '<a href="details.html?spu='+data.message[i].goodsID+'&goods='+data.message[i].goodsName+'" id="span_item'+i+'" target="_blank" class="btn_shop btn_light">SHOW NOW</a>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                    $("#tablet_row").append(content);
                    
                }
            },
            error: function (data) {
                console.log("ajax_item_status:"+data.status);
            }
        });
    });
    $("#laptop").click(function () {
        $.ajax({
            type: "post",
            url: "servlet/Recommend",
            dataType: "json",
            data: {
                type: "ajax_category",
                category: "电脑"
            },
            success: function (data) {
                console.log(data);
                console.log("ajax_item_status:"+data.status);
                console.log("message.length:"+data.message.length);
                $("#laptop_row").empty();
                for(var i=0;i<data.message.length;i++){
                    var content = '<div class="col-md-6 col_border wow slideInUp">' +
                        '<div class="div_out">' +
                        '<img class="img_radius" src="'+data.message[i].imgList[0]+'" alt="item" />' +
                        '<div id="in'+i+'" class="div_in">' +
                        '<div id="div_text'+i+'" class="div_text">' +
                        '<h2 class="display_commend" id="h2_item'+i+'">'+data.message[i].goodsName+'</h2>' +
                        '<a href="details.html?spu='+data.message[i].goodsID+'&goods='+data.message[i].goodsName+'" id="span_item'+i+'" target="_blank" class="btn_shop btn_light">SHOW NOW</a>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                    $("#laptop_row").append(content);
                    
                }
            },
            error: function () {
                console.log("ajax_item_status:"+XMLResponse.status);
            }
        });
    });
    
    //localStorage.removeItem("times");
    /*
    // 顶部动态显示登录状态(sessionStorage)
    // console.log("sessiongStorage_user:" + sessionStorage.getItem("user"));
    // if (sessionStorage.getItem("user") != "" && sessionStorage.getItem("user") != null && sessionStorage.getItem("user") != undefined) {
    //     $("#login_no").addClass("hidden");
    //     $("#login_yes").removeClass("hidden");
    //     //$("#btn_user").text("我("+sessionStorage.getItem("user")+")");
    //     $("#span_user").text("我(" + sessionStorage.getItem("user") + ")");
    // }
    $("#nav_top").hover(function(){
        $("#nav_top").css("background-color","white");
    },function(){
        $("#nav_top").css();
    });
 // $("#h2_item1").hide();
    // $("#span_item1").hide();
    // $("#h2_item2").hide();
    // $("#span_item2").hide();
    // $("#in1").mouseover(function () {
    //     $("#h2_item1").stop().fadeIn("fast");
    //     $("#span_item1").stop().fadeIn("fast");
    // });
    // $("#in1").mouseout(function () {
    //     $("#h2_item1").stop().fadeOut("fast");
    //     $("#span_item1").stop().fadeOut("fast");
    // });
    // $("#in2").mouseover(function () {
    //     $("#h2_item2").stop().fadeIn("fast");
    //     $("#span_item2").stop().fadeIn("fast");
    // });
    // $("#in2").mouseout(function () {
    //     $("#h2_item2").stop().fadeOut("fast");
    //     $("#span_item2").stop().fadeOut("fast");
    // });
$(function(){
    $("#dropa").hover(
        function(){
            $("#list").fadeIn();
        },function(){
            $("#list").fadeOut();
        });
});
$(function () {
$(".dropdown").mouseover(function () {
$(this).addClass("open");
});
$(".dropdown").mouseleave(function(){
$(this).removeClass("open");
})
})*/
//处理图片轮播左右箭头
    // $("#a_left").hover(
    //     function(){
    //         $("#span_left").fadeIn();
    //     },function(){
    //         $("#span_left").fadeOut();
    //     }
    // );
    // $("#a_right").hover(
    //     function(){
    //         $("#span_right").fadeIn("fast");
    //     },function(){
    //         $("#span_right").fadeOut("fast");
    //     }
    // );
    //处理鼠标悬停nav半透明
    // $(function(){
    //     $("#nav_contain").hover(function(){
    //         $(".div_top").css("opacity","1");
    //     },function(){
    //         $(".div_top").css("opacity","0");
    //     });
    // });
//html请求servlet的session
    // $.ajax({
    //     typ: "post",
    //     url: "servlet/UserInfo",
    //     success: function(){
    //         console.log("see servlet.");
    //     }
    // });
    // 监听浏览器返回事件并清楚html的sessionstorage
    // pushHistory();
    // window.addEventListener("popstate", function(e) {
    //     sessionStorage.removeItem("user");
    //     window.history.back(-1);//压入了#，所以返回时要多返回一个页面
    // }, false);
    // function pushHistory() {
    //     var state = {
    //         title: "title",
    //         url: "#"
    //     };
    //     window.history.pushState(state, "title", "#");
    // }
    //通过class控制某一类div
    // $(".display_1").hide();
    // $(".btn_shop").hide();
    // $(".div_in").mouseover(function () {
    //     $(".display_1").stop().fadeIn("fast");
    //     $(".btn_shop").stop().fadeIn("fast");
    // });
    // $(".div_in").mouseout(function () {
    //     $(".display_1").stop().fadeOut("fast");
    //     $(".btn_shop").stop().fadeOut("fast");
    // });
});
    