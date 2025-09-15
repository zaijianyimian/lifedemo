let activityId = document.getElementById("activity-id").value;

window.onload = function () {
    console.log(activityId);
    loadActivityDetails(activityId);
    console.log(categoryId);
}
$(document).ready(function() {// 获取活动 ID
    loadActivityDetails(activityId);  // 加载活动详情
    loadComments(activityId);  // 加载评论
    checkIfJoined(activityId);  // 判断用户是否已加入活动
});


// 加载活动详情的函数
function loadActivityDetails(activityId) {
    $.ajax({
        url: `/api/activity/getActivityById?id=${activityId}`,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
                var activity = response;
                $('#activity-name').text(activity.activityName);
                $('#activity-description').text(activity.activityDescription);
                $('#activity-date').text(new Date(activity.activityDate).toLocaleString());
                $('#activity-category').text(getCategoryName(activity.categoryId));
            categoryId = activity.categoryId;
            console.log(categoryId); // 在这里输出，确保有值
            // 在这里调用其他需要categoryId的函数
            loadRelatedActivities(categoryId);
        },
        error: function() {
            alert('加载活动详情时发生错误');
        }
    });
}
let categoryId;

function getCategoryName(categoryId) {
    switch (categoryId) {
        case 1: return "学习";
        case 2: return "运动";
        case 3: return "音乐";
        case 4: return "旅行";
        case 5: return "美食";
        default: return "未知类别";
    }
}

// 判断用户是否已经加入该活动
function checkIfJoined(activityId) {
    var userId = sessionStorage.getItem('userid');
    $.ajax({
        url: '/api/join/isjoin',
        method: 'GET',
        data: {
            userid: userId,
            activityid: activityId
        },
        dataType: 'json',
        success: function(response) {
            if (response.status === 'success') {
                $('#join-button').text('退出活动');
                $('#join-button').removeClass('btn-primary').addClass('btn-secondary');
            } else {
                $('#join-button').text('加入活动');
                $('#join-button').removeClass('btn-secondary').addClass('btn-primary');
            }
        },

    });
}

// 切换加入和退出活动
function toggleJoinActivity() {

    if ($('#join-button').text() === '加入活动') {
        $.ajax({
            url: '/api/join/addjoin',
            method: 'POST',
            data: {
                activityid: activityId,
                categoryId: categoryId
            },
            dataType: 'json',
            success: function(response) {
                if (response.status === 'success') {
                    $('#join-button').text('退出活动');
                    $('#join-button').removeClass('btn-primary').addClass('btn-secondary');
                    alert('成功加入活动');
                } else {
                    alert('加入活动失败');
                }
            },
            // error: function() {
            //     alert('请求失败，请稍后重试');
            // }
        });
    } else {
        $.ajax({
            url: '/api/join/deletejoin',
            method: 'POST',
            data: {
                activityid: activityId
            },
            dataType: 'json',
            success: function(response) {
                if (response.status === 'success') {
                    $('#join-button').text('加入活动');
                    $('#join-button').removeClass('btn-secondary').addClass('btn-primary');
                    alert('成功退出活动');
                } else {
                    alert('退出活动失败');
                }
            },
            // error: function() {
            //     alert('请求失败，请稍后重试');
            // }
        });
    }
}

// 分享活动链接
function shareActivity() {
    var currentUrl = window.location.href;
    navigator.clipboard.writeText(currentUrl).then(function() {
        alert('页面链接已复制到剪切板！');
    }).catch(function(error) {
        console.error('复制失败：', error);
        alert('复制链接失败，请手动复制');
    });
}

// 打开评论弹窗
function openCommentModal() {
    $('#commentModal').modal('show');
}

// 提交评论
function submitComment() {
    var commentContent = $('#comment-content').val();
    if (!commentContent) {
        alert('评论内容不能为空');
        return;
    }

    var activityId = getActivityIdFromURL();

    $.ajax({
        url: '/api/comment/addcomment',
        method: 'POST',
        data: {
            activityid: activityId,
            content: commentContent
        },
        dataType: 'json',
        success: function(response) {
            if (response.status === 'success') {
                $('#commentModal').modal('hide');
                alert("评论成功");
                loadComments(activityId);
            } else {
                alert('评论失败,请检查是否登录');
            }
        },
        // error: function() {
        //     alert('请求失败，请稍后重试');
        // }
    });
}

// 加载评论
function loadComments(activityId) {
    $.ajax({
        url: `/api/comment/getcomments?activity_id=${activityId}`,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response&&response.length > 0) {
                var comments = response;
                var commentSection = $('#comment-section');
                commentSection.empty();

                var loggedInUsername = sessionStorage.getItem('username');

                console.log(loggedInUsername);

                comments.forEach(function(comment) {
                    var isOwnComment = (comment.userName === loggedInUsername);

                    var commentHtml = `
    <div class="comment mb-3 p-3 bg-light rounded">
        <div class="d-flex justify-content-between align-items-start mb-2">
            <div class="d-flex align-items-center">
                <span class="badge bg-primary rounded-pill me-2">
                    <i class="fas fa-user"></i>
                </span>
                <h6 class="mb-0 fw-bold text-primary">${comment.userName}</h6>
            </div>
            <small class="text-muted">
                <i class="far fa-clock me-1"></i>
                ${new Date(comment.commentDate).toLocaleString()}
            </small>
        </div>
        <p class="mb-2 ps-4">${comment.commentText}</p>
        ${isOwnComment ? `
        <div class="text-end">
            <button class="btn btn-outline-danger btn-sm" onclick="deleteComment(${comment.id})">
                <i class="fas fa-trash-alt me-1"></i>删除
            </button>
        </div>` : ''}
    </div>
`;
                    commentSection.append(commentHtml);
                });
            } else {
                $('#comment-section').html('<p>没有评论。</p>');
            }
        },
        error: function() {
            alert('加载评论时发生错误');
        }
    });
}

function deleteComment(commentId) {
    var activityId = getActivityIdFromURL();
    $.ajax({
        url: `/api/comment/deletecomment`,
        method: 'POST',
        data: {
            commentId: commentId,
            activityId: activityId
        },
        dataType: 'json',
        success: function(response) {
            if (response.status === 'success') {
                alert('评论已删除');
                loadComments(activityId);
            } else {
                alert('删除评论失败');
            }
        },
        // error: function() {
        //     alert('请求失败，请稍后重试');
        // }
    });
}
// 举报弹窗打开
function openReportModal() {
    $('#reportModal').modal('show');
}

// 提交举报
function submitReport() {
    var reportDescription = $('#report-description').val();
    if (!reportDescription) {
        alert('举报内容不能为空');
        return;
    }

    $.ajax({
        url: '/api/report/createreport',
        method: 'POST',
        data: {
            activityid: activityId,
            description: reportDescription
        },
        dataType: 'json',
        success: function(response) {
            if (response.status === 'success') {
                $('#reportModal').modal('hide');
                alert('举报成功');
            } else {
                alert('举报失败');
            }
        },
        // error: function() {
        //     alert('请求失败，请稍后重试');
        // }
    });
}
