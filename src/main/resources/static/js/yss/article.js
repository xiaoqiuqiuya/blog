layui.use(['jquery', 'laydate', 'laypage'], function () {
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laypage = layui.laypage


    var ins1 = laydate.render({
        elem: '#test-n1'
        , position: 'static'
        , showBottom: false
        , mark: {
            '0-0-2': 'this day'
        }
        , done: function (value, date) {
            if (date.date === 2) { //点击2017年8月15日，弹出提示语
                ins1.hint('是谁把你带到我身旁');
            }
        }
    });
    article.Init($);//初始化共用js

    var href = window.location.href;
    //当前页
    var curr = href.substring(href.lastIndexOf("=") + 1);
    //每页显示数
    var limit = href.substring(href.indexOf("=") + 1, href.indexOf("&&"));
    limit == "" ? limit = 5 : limit;

    // 获取总条数

    $.ajax({
        url: "/totalArticle"
        , method: "GET",
        success: function (count) {
            console.log(count);
            laypage.render({
                elem: 'page'
                , count: count
                , layout: ['limit', 'prev', 'page', 'next']
                , curr: curr
                , limit: limit
                , limits: [5, 10, 20]
                , jump: function (obj, first) {
                    if (!first) {
                        //do something
                        curr = obj.curr;
                        limit = obj.limit;
                        window.location.href = "article?size=" + obj.limit + "&&current=" + obj.curr;
                    }
                }
            });

        }
    })

});
var article = {};
article.Init = function ($) {
    //var $ = layui.jquery,
    var slider = 0;
    blogtype();
    //类别导航开关点击事件
    $('.category-toggle').click(function (e) {
        e.stopPropagation();    //阻止事件冒泡
        categroyIn();
    });
    //类别导航点击事件，用来关闭类别导航
    $('.article-category').click(function () {
        categoryOut();
    });
    //遮罩点击事件
    $('.blog-mask').click(function () {
        categoryOut();
    });
    $('.f-qq').on('click', function () {
        window.open('http://connect.qq.com/widget/shareqq/index.html?url=' + $(this).attr("href") + '&sharesource=qzone&title=' + $(this).attr("title") + '&pics=' + $(this).attr("cover") + '&summary=' + $(this).attr("desc") + '&desc=你的分享简述' + $(this).attr("desc"));
    });
    $("body").delegate(".fa-times", "click", function () {
        $(".search-result").hide().empty();
        $("#searchtxt").val("");
        $(".search-icon i").removeClass("fa-times").addClass("fa-search");
    });

    //显示类别导航
    function categroyIn() {
        $('.category-toggle').addClass('layui-hide');
        $('.article-category').unbind('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend');
        $('.blog-mask').unbind('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend');
        $('.blog-mask').removeClass('maskOut').addClass('maskIn');
        $('.blog-mask').removeClass('layui-hide').addClass('layui-show');
        $('.article-category').removeClass('categoryOut').addClass('categoryIn');
        $('.article-category').addClass('layui-show');
    }

    //隐藏类别导航
    function categoryOut() {
        $('.blog-mask').on('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
            $('.blog-mask').addClass('layui-hide');
        });
        $('.article-category').on('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
            $('.article-category').removeClass('layui-show');
            $('.category-toggle').removeClass('layui-hide');
        });
        $('.blog-mask').removeClass('maskIn').addClass('maskOut').removeClass('layui-show');
        $('.article-category').removeClass('categoryIn').addClass('categoryOut');
    }

    function blogtype() {
        $('#category li').hover(function () {
            $(this).addClass('current');
            var num = $(this).attr('data-index');
            $('.slider').css({'top': ((parseInt(num) - 1) * 40) + 'px'});
        }, function () {
            $(this).removeClass('current');
            $('.slider').css({'top': slider});
        });
        $(window).scroll(function (event) {
            var winPos = $(window).scrollTop();
            if (winPos > 750)
                $('#categoryandsearch').addClass('fixed');
            else
                $('#categoryandsearch').removeClass('fixed');
        });
    };
};