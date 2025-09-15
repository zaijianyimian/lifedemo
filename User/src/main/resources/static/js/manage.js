window.onload = function() {
        getSessionUser();
        getJoinedActivities();
        getPublishedActivities()
    }

    // 获取当前用户信息（假设用户名和密码已存储在 session 中）

    function getSessionUser() {
        $.ajax({
            url: '/api/user/getuserinfo',  // 后端提供的接口，返回当前登录用户信息
            method: 'GET',
            dataType: 'json',
            success: function (response) {
                $('#username').val(response.name);
                $('#userId').val(response.id);  // 存储用户id
                $('#currentPassword').val(response.password);  // 存储原始密码
            },
            error: function () {
                alert("请先登录");
                return false;
            }
        });
    }

    // 获取用户参与的活动列表
   function getJoinedActivities() {
       $.ajax({
           url: '/api/join/getalljoins',  // 获取用户参与的活动
           method: 'GET',
           datatype: 'json',
           success: function (response) {
               var activities = response;
               var activityList = $('#activityList');
               activityList.empty();  // 清空活动列表

               activities.forEach(function (activity) {
                   var activityHtml = `
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <span>${activity.activityName}</span>
                                <button class="btn btn-danger" onclick="leaveActivity(${activity.id})">退出</button>
                            </div>
                        `;
                   activityList.append(activityHtml);
               });
           },
           error: function () {
               alert('请求失败，请稍后重试');
           }
       });
   }

    // 获取用户发布的活动列表
   function getPublishedActivities() {
       $.ajax({
           url: '/api/activity/getpublishedactivities',  // 获取用户发布的活动
           method: 'GET',
           success: function (response) {
               var activities = response;
               var publishedActivityList = $('#publishedActivityList');
               publishedActivityList.empty();  // 清空活动列表

               if (activities.length === 0) {
                   publishedActivityList.html('<p>没有发布的活动。</p>');
               } else {
                   activities.forEach(function (activity) {
                       var activityHtml = `
                                                <div class="d-flex justify-content-between align-items-center mb-3">
                                                     <span>${activity.activityName}</span>
                                                <div class="d-flex justify-content-end">
                                                <button class="btn btn-warning mx-1" onclick="editActivity(${activity.id})">修改</button>
                                                <button class="btn btn-danger mx-1" onclick="deleteActivity(${activity.id})">删除</button>
            </div>
        </div>
    `;
                       publishedActivityList.append(activityHtml);
                   });

               }

           },
           error: function () {
               alert('请求失败，请稍后重试');
           }
       });
   }

    // 修改密码弹窗
    $('#changePasswordBtn').click(function() {
        $('#changePasswordModal').modal('show');
    });

    // 修改密码表单提交
    $('#changePasswordForm').submit(function(event) {
        event.preventDefault();  // 阻止默认表单提交

        var currentPassword = $('#currentPassword').val();  // 获取隐藏字段中的原始密码
        var newPassword = $('#newPassword').val();  // 获取新密码
        var confirmPassword = $('#confirmPassword').val();  // 获取确认密码

        // 判断新密码是否与当前密码相同
        if (newPassword === currentPassword) {
            alert("新密码与原密码相同！");
            return;
        }

        // 验证密码一致性
        if (newPassword !== confirmPassword) {
            $('#errorMessage').removeClass('d-none');
            return;
        }

        // 清除错误信息
        $('#errorMessage').addClass('d-none');

        var name = $('#username').val();
        var id = $('#userId').val();

        $.ajax({
            url: '/api/user/updateuser',  // 后端更新用户密码的接口
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                id: id,
                name: name,
                password: newPassword
            }),
            dataType: 'json',
            success: function(response) {
                if (response.status === 'success') {
                    alert('密码更新成功');
                    $('#changePasswordModal').modal('hide');
                } else {
                    alert('密码更新失败');
                }
            },
            error: function() {
                alert("请求失败，请稍后重试");
            }
        });
    });

// 退出活动
function leaveActivity(activityId) {
    var userId = $('#userId').val();

    $.ajax({
        url: '/api/join/deletejoin',  // 后端删除用户与活动关联的接口
        method: 'POST',
        data: {
            userid: userId,
            activityid: activityId
        },
        dataType: 'json',
        success: function(response) {
            if (response.status === 'success') {
                alert('成功退出活动');
                getJoinedActivities();
            } else {
                alert('退出活动失败');
            }
        },
        error: function() {
            alert('请求失败，请稍后重试');
        }
    });
}

// 编辑活动
function editActivity(activityId) {
    // 跳转到编辑活动页面，或展示编辑表单
    window.location.href = `/editactivity?id=${activityId}`;
}

// 删除活动
function deleteActivity(activityId) {

    $.ajax({
        url: '/api/activity/deleteactivity',  // 后端删除活动的接口
        method: 'GET',
        data: {
            id: activityId
        },
        dataType: 'json',
        success: function(response) {
            if (response.status === 'success') {
                alert('活动删除成功');
                getPublishedActivities();
            } else {
                alert('活动删除失败');
            }
        },
        error: function() {
            alert('请求失败，请稍后重试');
        }
    });
}