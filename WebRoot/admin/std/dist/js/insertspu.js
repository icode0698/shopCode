$(function () {
    layui.use(['layer',  'form', 'upload'], function () {
        var layer = layui.layer
            , form = layui.form
            , upload = layui.upload;
        $.ajax({
            type: "post",
            dataType: "json",
            url: "../../../../../servlet/NowSpu",
            success: function (data) {
                //console.log(data);
                if (data.status == "success") {
                    $("#now").text(data.spu);
                    $("#spu").val(parseInt(data.spu)+1);
                    for(let i=0;i<data.brandList.length;i++){
                        $("#brandlist").append('<option value="'+data.brandList[i].brandID+'">'+data.brandList[i].brandName+'</option>');
                    }
                    for(let i=0;i<data.categoryList.length;i++){
                        $("#categorylist").append('<option value="'+data.categoryList[i].categoryID+'">'+data.categoryList[i].categoryName+'</option>');
                    }
                    form.render('select');
                    for(let i=0;i<data.storageList.length;i++){
                        $("#storagediv").append('<input type="checkbox" lay-filter="storage" value="'+data.storageList[i].id+'" name="storage" lay-skin="primary" title="'+data.storageList[i].value+'">');
                    }
                    for(let i=0;i<data.colorList.length;i++){
                        $("#colordiv").append('<input type="checkbox" lay-filter="color" value="'+data.colorList[i].id+'" name="color" lay-skin="primary" title="'+data.colorList[i].value+'">');
                    }
                    for(let i=0;i<data.screenList.length;i++){
                        $("#screendiv").append('<input type="checkbox" lay-filter="screen" value="'+data.screenList[i].id+'" name="screen" lay-skin="primary" title="'+data.screenList[i].value+'">');
                    }
                    form.render('checkbox');
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
        $("#pic").focus(function(){
            $.ajax({
                type: "post",
                dataType: "json",
                url: "../../../../../servlet/NowImg",
                success: function (data) {
                    console.log(data);
                    if (data.status == "success") {
                        console.log(data);
                        $("#pictipdiv").css("display","");
                        $("#picidtip").text(data.message);
                        $("#pic").val(parseInt(data.message)+1);
                    }
                }, error: function (data) {
                    console.log(data);
                    layer.alert("服务器异常，请稍后再试", {icon:2});
                    return;
                }
            });
        });
        upload.render({
            elem: '#choose'
            ,url: '../../../../../servlet/InsertImg'
            ,data: {
                id: function(){
                    return $("#pic").val();
                }
                ,spu: function(){
                    return $("#spu").val();
                }
            }
            ,auto: false
            ,bindAction: '#insert'
            , choose: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $("#pictip").text(file.name);
                    $('#demo').attr('src', result); //图片链接（base64）
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
        form.on('submit(ensure)', function (res) {
            // console.log(res.field);
            layer.load();
            let storageList = [];
            let colorList = [];
            let screenList = [];
            $.each($("input:checkbox[name='storage']:checked"), function () {
                storageList.push($(this).attr("value"));
            });
            $.each($("input:checkbox[name='color']:checked"), function () {
                colorList.push($(this).attr("value"));
            });
            $.each($("input:checkbox[name='screen']:checked"), function () {
                screenList.push($(this).attr("value"));
            });
            console.log(storageList);
            console.log(colorList);
            console.log(screenList);
            $.ajax({
                type: "post",
                dataType: "json",
                url: "../../../../../servlet/InsertSpu",
                data: {
                    spu: $("#spu").val(),
                    name: $("#name").val(),
                    brandID: ""+res.field.brand,
                    categoryID: ""+res.field.category,
                    picID: ""+res.field.pic,
                    storageList: JSON.stringify(storageList),
                    colorList: JSON.stringify(colorList),
                    screenList: JSON.stringify(screenList)
                }, success: function (data) {
                    console.log(data);
                    layer.closeAll('loading');
                    if (data.status == "success") {
                        layer.alert(data.message, {icon:1}, function () { location.href="";});
                        return false;
                    }
                    if (data.status == "fail") {
                        layer.alert(data.message, {icon:2});
                        return false;
                    }
                }, error: function (data) {
                    console.log(data);
                    layer.alert("服务器异常，请稍后再试", {icon:2});
                    return;
                }
            });
            return false;
        });
    });
});