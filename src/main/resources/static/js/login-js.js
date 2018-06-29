$(function() {
    $(".btn-login").on('click',function(){
        var us = $(".inp-username").val();
        var ps = $(".inp-password").val();
        if(us === "" || ps === "") {
            layer.msg('用户名或密码为空');
            return;
        }
        $.ajax({
            type:"POST",
            url:"getUserByUser",
            data:{'username':us,"password":ps},
            async:false,
            dataType:"json",
            success:function (data) {
                console.log(data.length)
                if (data.length != 0){
                    layer.msg("登陆成功");
                    setTimeout("window.location.href='patientList.html'",1000);
                }
                if (data.length == 0){
                    layer.msg("用户名或密码错误");
                }
            },
            error:function () {
                layer.msg("登陆失败");
            }
        })
        // $.ajax({
        //     type:"POST",
        //     url:"Login",
        //     data:{'username':us,"password":ps},
        //     async:false,
        //     dataType:"json",
        //     success:function (data) {
        //         console.log(data);
        //         layer.msg("登陆成功");
        //     },
        //     error:function (data) {
        //         console.log(data);
        //         layer.msg("登陆失败");
        //     }
        // })
    })
});