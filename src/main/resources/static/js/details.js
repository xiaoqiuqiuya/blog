// 发布评论
function postComment(commentId, articleId) {
    //获取内容
    var content = $('#reply_content_' + commentId).val();
    //发送ajax
    var comment = {
        'content': content,
        'commentId': commentId,
        'articleId': articleId
    }

    $.ajax({
        url: '/tabReply/reply',
        data: JSON.stringify(comment),
        dataType: "JSON",
        contentType: "application/json",
        method: "POST",
        success: function (data) {
            if (data.success) {
                layer.msg(data.message);
                $('#comment_send_' + commentId).attr("style", "display:none");
            } else {
                console.log(data.message);
            }
        }
    })

}

// 点赞评论
function likeComment(id) {
    $.ajax({
        url: "/tabCommentThumbs/likeComment/" + id,
        dataType: "JSON",
        method: "GET",
        success: function (data) {
            if (data.success) {
                var current = $('#comment_prise_btn_' + id).attr("class");
                var num = $('#comment_like_num_' + id).text();
                if (current == "comment-info-like1") {
                    //-1
                    $('#comment_prise_btn_' + id).attr("class", "comment-info-like");
                    $('#comment_like_num_' + id).text(Number(num)-1)  ;
                } else {
                    //+1
                    $('#comment_prise_btn_' + id).attr("class", "comment-info-like1");
                    $('#comment_like_num_' + id).text(Number(num)+1)  ;
                }
            }else{
                layer.msg(data.message);
            }
        }
    })
}

function likeReply(id) {
    $.ajax({
        url: "/tabReplyThumbs/likeReply/" + id,
        dataType: "JSON",
        method: "GET",
        success: function (data) {
            if (data.success) {
                var current = $('#reply_prise_btn_' + id).attr("class");
                var num = $('#reply_like_num_' + id).text();
                if (current == "reply-info-like1") {
                    //-1
                    $('#reply_prise_btn_' + id).attr("class", "reply-info-like");
                    $('#reply_like_num_' + id).text(Number(num)-1)  ;
                } else {
                    //+1
                    $('#reply_prise_btn_' + id).attr("class", "reply-info-like1");
                    $('#reply_like_num_' + id).text(Number(num)+1)  ;
                }
            }else{
                layer.msg(data.message);
            }
        }
    })
}




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
    //发布评论
    $('#comment_publish_btn').on('click', function () {
        var content = $('#comment_content');
        if (content.val().trim().length <= 0) {
            content.val('');
            content.attr('placeholder', '内容不能为空');
            return 0;
        }
        var articleId = $("#comment_publish_btn").data("id");
        // 需要的参数：content、articleId
        var comment = {
            "articleId": articleId,
            "content": content.val()
        }
        $.ajax({
            url: "/comment/publish",
            data: JSON.stringify(comment),
            dataType: 'JSON',
            method: 'POST',
            contentType: "application/json",
            success: function (data) {
                if (data.code != 200) {
                    // 评论失败
                    content.val('');
                    content.attr('placeholder', data.message);
                }
            }
        })
    })


})
//显示回复输入框
var temp = 0;

function showInput(id) {
    if (temp != 0) {
        $('#comment_send_' + temp).attr("style", "display:none");
    }
    $('#comment_send_' + id).attr("style", "display:inline");
    temp = id;
}
