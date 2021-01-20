layui.use(['layer', 'form', 'jquery'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;

    var likenum = document.getElementById("btn_like").getElementsByTagName("span");
    // 按钮添加事件:取消点赞
    $("#btn_like").on("click", function () {
        var articleId = $("#btn_like").data("id");
        $.ajax({
            url: "/like/" + articleId,
            method: "GET",
            dataType: "JSON",
            contentType: "application/json",
            success: function (data) {
                if (data.result.code == 200) {
                    // 页面点赞数 -1
                    let span = document.getElementById("btn_like").getElementsByTagName("span");
                    // 获取原来的点赞数
                    let num = span[0].innerText;
                    // 获取后来显示的元素
                    let span1 = document.getElementById("btn_unlike").getElementsByTagName("span");
                    // 点赞数-1
                    span1[0].innerText = Number(num) - 1;
                    $("#btn_unlike").attr("style", "display:inline-block");
                    $("#btn_like").attr("style", "display:none");
                }

            }
        })
    })
    //按钮添加事件：点赞
    $("#btn_unlike").on("click", function () {
        var articleId = $("#btn_unlike").data("id");
        $.ajax({
            url: "/like/" + articleId,
            method: "GET",
            dataType: "JSON",
            contentType: "application/json",
            success: function (data) {
                if (data.result.code == 200) {
                    let span = document.getElementById("btn_unlike").getElementsByTagName("span");
                    let num = span[0].innerText;

                    let span1 = document.getElementById("btn_like").getElementsByTagName("span");
                    span1[0].innerText = Number(num) + 1;
                    console.log("点赞:" + span1[0].innerText);
                    $("#btn_like").attr("style", "display:inline-block");
                    $("#btn_unlike").attr("style", "display:none");
                }
                if (data.result.code == 403) {
                    $("#btn_msg").attr("style", "display:block");
                }
            }
        })
    })


})