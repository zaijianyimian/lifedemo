// 表单提交事件
$('#addActivityForm').submit(function (event) {
    event.preventDefault();  // 阻止表单默认提交

    // 获取表单数据
    var formData = {
        activity_name: $('#activity_name').val(),
        category_id: $('#category_id').val(),
        activity_description: $('#activity_description').val()
    };

    // 发送 Ajax 请求
    $.ajax({
        url: '/api/activity/activity',  // 后端处理 URL
        method: 'POST',  // 请求方式
        data: formData,
        dataType: 'json',  // 请求数据
        success: function (response) {
            // 处理后端返回的 JSON 响应
            if (response.status === 'success') {
                $('#alertMessage').removeClass('d-none alert-danger').addClass('alert-success')
                    .text(response.message).show();
                setTimeout(function() {
                    window.location.href = '/index';
                }, 1500);
            } else {
                $('#alertMessage').removeClass('d-none alert-success').addClass('alert-danger')
                    .text(response.message).show();
            }
        },
        error: function () {
            // 如果发生错误，显示提示信息并跳转
            $('#alertMessage').removeClass('d-none alert-success').addClass('alert-danger')
                .text("发生错误，请稍后重试。").show();
            alert
            setTimeout(function() {
                window.location.href = '/index';
            }, 1500);
        }
    });
});