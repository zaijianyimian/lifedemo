// 在页面加载时获取活动ID和用户ID，并填充到表单中/
window.onload = function() {
    const activityId = new URLSearchParams(window.location.search).get('id');  // 从URL中获取活动ID
    const userId = sessionStorage.getItem("userid");  // 从sessionStorage获取用户ID

    // 填充表单字段
    $('#activityId').val(activityId);
    $('#userId').val(userId);
};

// 提交举报表单
$('#reportForm').submit(function(event) {
    event.preventDefault(); // 阻止默认表单提交

    const userId = $('#userId').val(); // 获取用户ID
    const activityId = $('#activityId').val(); // 获取活动ID
    const reportDescription = $('#reportDescription').val(); // 获取举报描述

    // 发送AJAX请求提交举报
    $.ajax({
        url: '/reportactivity',  // 提交举报的接口
        method: 'POST',
        data: {
            userId: userId,
            activityId: activityId,
            reportDescription: reportDescription
        },
        success: function(response) {
            if (response.status === 'success') {
                alert('举报成功');
            } else {
                alert('举报失败: ' + response.message);
            }
        },
        error: function() {
            alert('提交举报时发生错误');
        }
    });
});