layui.use(['layer', 'jquery', 'form'], function () {
        var layer = layui.layer;
        var $ = layui.jquery;
        $('#btn-publish').on('click', function () {
            layer.open({
                title: '发布文章'
                , content: $('#publish-temp')
                , area: '40%'
            });
        })

    }
)
;