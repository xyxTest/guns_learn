

layui.use(['layer', 'form', 'admin', 'laydate', 'ax','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();
    var upload = layui.upload, $ = layui.jquery;
    var uploadInst = upload.render({
	    elem: '#upload1' //绑定元素
	    ,url: /*[[@{/upload/img}]]*/'/sys_files/upload_files' //上传接口
	    ,before: function(obj){
	        //预读本地文件示例，不支持ie8
	        obj.preview(function(index, file, result){
	            $('#demo1').attr('src', result); //图片链接（base64）
	        });
	    }
	    ,done: function(res){
	        //如果上传失败
	        if(res.code > 0){
	            return layer.msg('上传失败');
	        }
	        //上传成功
	        alert("上传成功"+res.data);
	        document.getElementById("img_url").value = res.data;
	
	    }
	    ,error: function(){
	        //演示失败状态，并实现重传
	        var demoText = $('#demoText');
	        demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
	        demoText.find('.demo-reload').on('click', function(){
	            uploadInst.upload();
	        });
    	}
    });
    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/work_note/add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });
});