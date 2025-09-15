let query = document.getElementById("query").value;
window.onload = () => {
    console.log(query);
    if (query!= ""&&query!=null) {
        loadloadActivities(query);
    }
}
$(document).ready(function() {
    var query = getQueryParameter('query');
    loadActivities(query);
});

// 获取URL中的查询参数
function getQueryParameter(param) {
    var urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);  // 获取查询参数的值
}

function loadActivities(query) {
    $.ajax({
        url: `/api/activity/searchactname?activityName=${query}`,
        method: 'GET',
        datatype: 'json',
        success: function(response) {
            var activities = response;
            var activityList = '';

            if (activities.length === 0) {
                $('#activity-list').html('<p>没有找到目标活动。</p>');
            } else {
                activities.forEach(function(activity) {
                    activityList += `
                                        <div class="activity-item"
                                                id="activity-${activity.id}"
                                                onclick="window.location.href='/detail?id=${activity.id}'">

                                        <div class="activity-content">
                                                 <h5 class="activity-title">
                                                    ${activity.activityName}
                                                 </h5>
                                                 <p class="activity-desc">
                                                    ${activity.activityDescription}
                                                </p>
                                        </div>

                                            <button class="participant-btn"
                                                onclick="event.stopPropagation(); showParticipantsModal(${activity.id})">
                                            <i class="fas fa-users"></i>
                                            <span class="btn-text">显示参与者</span>
                                            </button>
                                        </div>
                                        `;
                });
                $('#activity-list').html(activityList);
            }
        },
        error: function() {
            alert('加载活动失败');
        }
    });
}

function showParticipantsModal(activityId) {
    $.ajax({
        url: `/api/user/getuserbyactivityid?activityId=${activityId}`,
        method: 'GET',
        datatype: 'json',
        success: function(response) {
            var participants = response;
            var modalList = '';
            participants.forEach(function(participant) {
                modalList += `<li>${participant.name}</li>`;
            });
            $('#modal-participants-list').html(modalList);
            $('#participantsModal').modal('show');
        },
        error: function() {
            alert('加载参与者失败');
        }
    });
}