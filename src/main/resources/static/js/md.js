$(function() {
    editormd("test-editor", {
        width  : "100%",
        height : 720,
        path   : "../static/js/plugins/lib/",
        saveHTMLToTextarea : true, // 保存 HTML 到 Textarea
        toolbarAutoFixed:true,//工具栏自动固定定位的开启与禁用
        //开启本地上传
        imageUpload: true,
        imageFormats: ["jpg","jpeg","gif","png","bmp","webp"],
        placeholder:"请详细描述该问题，支持markdown语法",
        toolbarIcons : function() {
            //显示到工具栏的项目
            return  ["undo", "redo", "|", "bold", "del", "italic", "hr", "image", "table", "datetime", "|", "preview", "watch", "|", "fullscreen", ];
        },
    });
});