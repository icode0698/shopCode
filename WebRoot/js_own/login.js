$(document).ready(function(){
  //sessionStorage.removeItem("user");
    $("#sub").click(function(){
      var user = $("#user").val();
      console.log("user:"+user);
      console.log("password:"+password);
      console.log(user=="");
      console.log($("#password").val()=="");
      if(user==""){
        $("#div_hide").removeClass("hidden");
        $("#error_hide").removeClass("hidden");
        //$("#div_hide").addClass("wow shake");
        //$("#div_hide").fadeToggle();
        $("#error_tip").text("UserID不可为空");
        return;
      }
      if($("#password").val()==""){
        $("#div_hide").removeClass("hidden");
        $("#error_hide").removeClass("hidden");
        //$("#div_hide").fadeToggle();
        $("#error_tip").text("Password不可为空");
        return;
      }
      else{
        $.ajax({
        type: "post",
        url: "servlet/Login",
        dataType: "json",
        data:{
          "user": user,
          "password": $("#password").val(),
          type: "ajax_login"
        },
        success:function(data){
          //alert(data);
          //var str = JSON.parse(data);
          //alert("data:"+data+"\nstr:"+str+"\nstatus:"+str.status+"\nuser:"+str.user+"\ntype:"+str.type);
          //alert("status:"+str.status+"\nuser:"+str.user+"\ntype:"+str.type);
          //$("#id").val(str.user);
          //$("#password").val(str.status);'
          console.log("json.status:"+data.status);
          console.log("json.user:"+data.user);
          if(data.status=="success"){
            //sessionStorage.setItem("user",data.user);
            //console.log("sessionStorage_user_login:"+sessionStorage.getItem("user"));
            $("#error_hide").addClass("hidden");
            $("#div_hide").removeClass("hidden");
            $("#success_hide").removeClass("hidden");
            $("#success_tip").text(data.message);
            //location.href = 'index.html';
            setTimeout("location.href = 'index.html'",1000);
          }
          if(data.status=="error"){
            $("#div_hide").removeClass("hidden");
            $("#error_hide").removeClass("hidden");
            $("#error_tip").text(data.message);
            //return;
          }
        },
        error:function(XMLResponse){
          $("#div_hide").removeClass("hidden");
            $("#error_hide").removeClass("hidden");
            $("#error_tip").text("抱歉，服务器异常。\nXMLResponse_Status:"+XMLResponse.status);
            console.log("error_status:"+XMLResponse.status);
            return;
        }
       });
      }
    });
  });
//   //ajax 请求开始进度条开始
// $(window).ajaxStart(() => {
//     NProgress.start();
// });
// //    ajax 请求结束进度条结束
// $(window).ajaxComplete(() => {
//     NProgress.done();
// });