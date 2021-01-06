layui.use(['layer', 'jquery', 'form', 'tree'], function () {
        var layer = layui.layer;
        var $ = layui.jquery;
        var form = layui.form;
        form.on('submit(cancel)', function (data) {
            console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
            console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
            console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
        $('#btn-publish').on('click', function () {
            layer.open({
                type: 1
                , skin: "my-open-skin"
                , title: '发布文章'
                , content: $('#publish-temp')
                , area: '50%'
                , btn: ['取消', '保存为草稿', '发布文章']
                , btn3: function (index, layero) {
                    saveOrPost("post",form);
                    return false;
                }, btn2: function (index, layero) {
                    saveOrPost("save",form);
                    return false;
                }, btn1: function (index, layero) {
                    layer.closeAll();
                    return false;
                }
            });
        })
        // 文章分类选择
        var lastSelceted = null;
        form.on("select(article-type)", function (data) {
            lastSelceted = setArticleType(data.value, lastSelceted);
        })

        //表单校验
        form.verify({
            permissionVerify: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                    return '用户名不能有特殊字符';
                }
                if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                    return '用户名首尾不能出现下划线\'_\'';
                }
                if (/^\d+\d+\d$/.test(value)) {
                    return '用户名不能全为数字';
                }
                //如果不想自动弹出默认提示框，可以直接返回 true，这时你可以通过其他任意方式提示（v2.5.7 新增）
                if (value === 'xxx') {
                    alert('用户名不能为敏感词');
                    return true;
                }
            }
        });
    }
)
;

// 保存或发布文章
function saveOrPost(option,form) {
    var articleTitle = $('#article-title').val();
    var articleContent = $('#article-content').val();
    var articleTags = new Array();
    // 获取tags
    var tag = document.getElementsByClassName("publish-article-tag");
    for (var data of tag) {
        articleTags.push(data.getAttribute("data"));
    }
    //     authorId
    // 创建传输对象
    var TabArticle = {
        "articleId":0,
        "articleTitle": articleTitle,
        "articleContent": articleContent,
        "articleTags": articleTags.toString(),
        "articleStatus": "post"
    }
    var formData = form.val("publish-form");
    for (let formDataKey in formData) {
        TabArticle[formDataKey] = formData[formDataKey]
    }
    // console.log(article)
    console.log(JSON.stringify(TabArticle));

    // 发送ajax请求
    new Promise((resolve, reject) => {
        $.ajax({
            path:"/publish",
            data:JSON.stringify(TabArticle),
            dataType:"JSON",
            contentType:"application/json",
            method: "POST",
            success:function (data) {
                resolve(data);
            },
            error:function (data) {
                reject(data);
            }
        })
    }).then((data) => {
        console.log(data.data.id);
    }).catch((data) => {
        console.log(data.data.id);
    })
}

// 修改文章类型
function setArticleType(type, lastSelected) {
    // 隐藏上次选择的
    if (lastSelected != null) {
        document.getElementsByClassName("publish-article-tips-" + lastSelected)[0].style.display = "none";
    }
    if (type == "original" || type == "") {
        document.getElementsByClassName("publish-article-tips")[0].style.display = "none";
    }
    if (type.length > 0 && type != "original") {
        document.getElementsByClassName("publish-article-tips")[0].style.display = "block";
        document.getElementsByClassName("publish-article-tips-" + type)[0].style.display = "block";
        lastSelected = type;
    }
    return lastSelected;
}

//显示标签选择
function showTags() {
    var temp = document.getElementById("TagBox");
    if (temp.style.display == "none" || temp.style.display == "") {
        temp.style.display = "block";
    } else {
        temp.style.display = "none";
    }
}

// 已选标签集合
var selectList = new Array();
$(document).ready(function () {
    // 获取标签分类
    var list = $(".tabBox-list");
    // 默认第一个选中
    list[0].classList.add("tabBox-list-isActive");
    // tag列表
    var itemList = $(".tabBox-list-item");
    // 保存上次点击的id，第一次点击默认为0
    var clickIndex = 0;
    // 遍历标签分类
    for (i = 0; i < list.length; i++) {
        // 每个标签分类绑定事件
        list[i].onclick = function () {
            // 去除上次选中id的样式
            list[clickIndex].classList.remove("tabBox-list-isActive");
            // 隐藏上次选中的tag类
            itemList[clickIndex].style.display = "none";
            // 更新clickIndex为当前选中的
            //当前点击的id，因为数据库里id从1开始所以需要减一操作
            clickIndex = this.id.split("-")[1] - 1;
            // 本次选中添加样式
            list[clickIndex].classList.add("tabBox-list-isActive");
            // 显示本次选中的类别中的tag
            itemList[clickIndex].style.display = "block";
            // 获取本次点击的类别下的tag
            var itemSpan = itemList[clickIndex].getElementsByTagName("span");
            // 遍历tag
            for (var j = 0; j < itemSpan.length; j++) {
                // tag绑定点击事件
                itemSpan[j].onclick = function () {
                    // 判断：如果tag已选则退出
                    if (selectList.indexOf(this.id) == -1) {
                        // 判断：如果已选tag长度大于5则不再进行操作
                        // Q:为什么是4???
                        if (selectList.length < 4) {
                            // 把当前选中的id添加到数组中
                            selectList.push(this.id);
                            updateTagNum("-");
                            this.style.color = "#ccccd8";
                            addTag(this.innerText, this.id);
                        } else if (selectList.length == 4) {
                            // 把当前选中的id添加到数组中
                            selectList.push(this.id);
                            updateTagNum("-");
                            this.style.color = "#ccccd8";
                            addTag(this.innerText, this.id);
                            // 所有span颜色变为灰色
                            var span = document.getElementsByClassName("tabBox-listBox")[0].getElementsByTagName("span");
                            for (i = 0; i < span.length; i++) {
                                span[i].style.color = "#ccccd8";
                            }
                        } else {
                            tagOver();
                        }
                    }
                }
            }
        }
    }
})

// 把标签添加到已选标签区域
function addTag(tagName, tagID) {
    var targetSpan = $(".publish-article-tag-list")[0];
    var insertSpan = document.createElement("span");
    var icon = document.createElement("i");
    icon.classList.add("layui-icon", "layui-icon-close");
    icon.addEventListener("click", tagRemove);
    insertSpan.classList.add("publish-article-tag");
    insertSpan.innerText = tagName;
    insertSpan.setAttribute("name", tagID);
    insertSpan.setAttribute("data", tagID);
    insertSpan.appendChild(icon);
    targetSpan.appendChild(insertSpan);
}

// 标签数量超出允许范围
function tagOver() {
    // 弹出提示
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.msg("最多只能选择五个标签");
    })
}

// 删除标签
function tagRemove() {
    // 更新剩余可选数量
    updateTagNum("+");
    // 获取需要去除的数组元素
    var removeId = this.parentElement.getAttribute("data");
    // 去除数组元素
    selectList.splice(selectList.indexOf(removeId), 1);
    // 颜色恢复
    var span = $(".tabBox-listBox")[0].getElementsByTagName("span");
    for (i = 0; i < span.length; i++) {
        span[i].style.color = "#4d4d4d";
    }
    // 已选中的标签颜色变为禁用色
    for (selected of selectList) {
        document.getElementById(selected).style.color = "#ccccd8";
    }
    // 去除span
    this.parentElement.remove();
}

// 可选数量：操作 +/-
function updateTagNum(option) {
    var tagNum = document.getElementById("tag-num");
    var temp = Number(tagNum.innerText);
    if (option == "+") {
        temp += 1;
    }
    if (option == "-") {
        temp -= 1;
    }
    tagNum.innerText = temp;
}
