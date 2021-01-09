### 2020年12月30日
- 后台
    - 设计表结构并创建 `tab_article` 表，通过mybatis-plus工具生成代码
    
- 前端页面：
    - 发布文章信息选择框

`待解决` ~~开始获取文章的信息，其中tags还没有定义好，permission一直为on，checkbox还没有获取值~~

`待解决`~~获取到了所有的值，但是是两个对象，看看怎样合并为一个对象，如果再不行就直接传两个对象就行了~~ 

### 2020年12月31日
- 数据库
    - 修改数据库表 `tab_article` 添加字段 `article_status` 记录当前文章的状态
    - 所有状态  草稿(save)、发布(post)、（打回、被管理员删除、存在争议）
### 2021年1月2日
- 后端
    - 统一返回结果的封装
    - 发布文章时的各个字段非空判断

### 2021年1月4日
`待解决` 正式发布的文章应该跟草稿文章分开放，不要放在同一个表里面，并且文章草稿可以有空值！应该把`tab_article`中的`article_status`删除

### 2021年1月6日
- 后端
   - 修改配置文件，添加`hikari`配置，设置数据库连接空闲超时时间

### 2021年1月7日

- 后端
  - 首页文章列表展示，处理分页（1，5）
- 前端
  - 首页文章列表自动渲染、修改样式
  - 发布文章页面，修改标签选择排版

### 2021年1月8日

- 后端

  - 根据前端文章展示页获取文章id查询，返回查询结果

- 前端

  - 文章详情页排版布局

  - 注意markdown插件预览时引用的顺序

  - ```javascript
    <!--测试可用-->
    <script src="jquery.min.js"></script>
    <script src="marked.min.js"></script>
    <link href="editormd.preview.min.css" rel="stylesheet">
    <script src="prettify.min.js"></script>
    <script src="editormd.min.js"/>
    <!--测试可用-->
    <script>
        window.onload = function () {
            // 渲染markdown
            $(function () {
                editormd.markdownToHTML("article-preview")
              	//还可以定义一下其他参数
            })
        };
    </script>
    
    ```











