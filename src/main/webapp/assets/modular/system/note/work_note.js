layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 系统管理--用户管理
     */
    var Note = {
        tableId: "noteTable",    //表格id
        condition: {
            content: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Note.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'noteId', hide: false, sort: true, title: '编号'},
            {field: 'fileUrl', hide: false, sort: true, title: '附件地址'},
            {field: 'title', sort: true, title: '标题'},
            {field: 'content', sort: true, title: '内容'},
            {field: 'createrName', sort: true, title: '创建人'},
            {field: 'createDate', sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 280}
        ]];
    };
    /**
     * 点击查询按钮
     */
    Note.search = function () {
        var queryData = {};
        queryData['content'] = $("#content").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(Note.tableId, {where: queryData});
    };

    /**
     * 弹出添加工作记事对话框
     */
    Note.openAddNote = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加工作记事',
            content: Feng.ctxPath + '/work_note/note_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Note.tableId);
            }
        });
    };

    /**
     * 点击编辑用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    Note.onEditNote = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑工作记事',
            content: Feng.ctxPath + '/work_note/note_edit?noteId=' + data.noteId,
            end: function () {
            	document.getElementById("img_url").value = data.fileUrl;
                admin.getTempData('formOk') && table.reload(Note.tableId);
            }
        });
    };

    /**
     * 点击删除工作记事按钮
     *
     * @param data 点击按钮时候的行数据
     */
    Note.onDeleteNote = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/work_note/delete", function () {
                table.reload(Note.tableId);
                Feng.success("删除成功!");
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("noteId", data.noteId);
            ajax.start();
        };
        Feng.confirm("是否删除工作记事" + data.account + "?", operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Note.tableId,
        url: Feng.ctxPath + '/work_note/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Note.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Note.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Note.openAddNote();
    });


    // 工具条点击事件
    table.on('tool(' + Note.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'edit') {
            Note.onEditNote(data);
        } else if (layEvent === 'delete') {
        	Note.onDeleteNote(data);
        } 
    });

   

});
