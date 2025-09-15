// 获取活动ID并加载活动数据
const urlParams = new URLSearchParams(window.location.search);
let activityId = document.getElementById("activity").value;

// 在页面加载时通过活动ID获取活动信息并填充到表单中
window.onload = function () {
    if (activityId) {
        $.ajax({
            url: `/api/activity/activity/${activityId}`,
            method: 'GET',
            dataType: 'json',
            success: function(data) {

                    const activity = data;
                    $("#activityId").val(activity.id);
                    $("#activity_name").val(activity.activityName);
                    $("#category_id").val(activity.categoryId);
                    $("#activity_description").val(activity.activityDescription);
            },
            error: function(xhr, status, error) {
                console.error("获取活动信息失败", error);
            }
        });
    }
};

// 提交表单
$("#editActivityForm").submit(function(event) {
    event.preventDefault(); // 阻止表单的默认提交

    // 使用AJAX提交表单数据
    $.ajax({
        url: '/api/activity/activity',
        method: 'PUT',
        data: $(this).serialize(), // 序列化表单数据
        dataType: 'json',
        success: function(data) {
            if (data.status === "success") {
                alert("活动更新成功");
                window.location.href = "/manage";  // 跳转到活动列表页面
            } else {
                alert("活动更新失败：" + data.message);
            }
        },
        error: function(xhr, status, error) {
            alert("发生错误，请稍后再试");
        }
    });
});