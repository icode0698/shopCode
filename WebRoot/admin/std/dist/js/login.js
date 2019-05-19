$(function () {
    $("#sub").on("click", function () {
        var admin = $("#admin").val();
        console.log("admin:" + admin);
        console.log("password:" + password);
        console.log(admin == "");
        console.log($("#password").val() == "");
        if (admin != "" && $("#password").val()) {
            $.ajax({
                type: "post",
                url: "../../../../servlet/AdminLogin",
                dataType: "json",
                data: {
                    "admin": admin,
                    "password": $("#password").val(),
                },
                success: function (data) {
                    console.log(data);
                    if (data.status == "success") {
                        $("#error_hide").addClass("hidden");
                        $("#success_hide").removeClass("hidden");
                        $("#success_tip").text(data.message);
                        setTimeout("location.href = 'admin.html'", 500);
                    }
                    if (data.status == "error") {
                        $("#error_hide").removeClass("hidden");
                        $("#error_tip").text(data.message);
                    }
                },
                error: function (XMLResponse) {
                    $("#error_hide").removeClass("hidden");
                    $("#error_tip").text("抱歉，服务器异常。\nXMLResponse_Status:" + XMLResponse.status);
                    console.log("error_status:" + XMLResponse.status);
                    return;
                }
            });
        }
    });
});