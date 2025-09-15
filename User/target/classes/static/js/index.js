window.onload = function() {
    loadHomeActivities();
    loadTopActivities();
}
let currentIndex = 0;

// 加载活动数据
function loadTopActivities() {
    $.ajax({
        url: '/api/activity/getTopActivities',
        method: 'GET',
        success: function(response) {
            const activityList = response.map((activity, index) => `
                    <div class="p-top-activity-item" onclick="window.location.href='/detail?id=${activity.id}'">
                        <div class="p-activity-icon">
                            <i class="fa ${getActivityIcon(index)}"></i>
                        </div>
                        <div class="p-activity-info">
                            <h5>排名 ${index + 1}: ${activity.activityName}</h5>
                            <p>${activity.activityDescription}</p>
                        </div>
                    </div>
                `).join('');
            $('.p-activity-slider').html(activityList);
            startAutoSlide(response.length);
        },
        // error: function() {
        //     alert('加载排行榜失败');
        // }
    });
}

// 根据排名返回图标
function getActivityIcon(index) {
    switch(index) {
        case 0: return 'fa-trophy';
        case 1: return 'fa-medal';
        case 2: return 'fa-certificate';
        default: return '';
    }
}

// 滑动到下一个活动
function slideToNext(totalActivities) {
    currentIndex = (currentIndex + 1) % totalActivities;
    $('.p-activity-slider').css('transform', `translateY(-${currentIndex * 60}px)`);
}

// 自动滑动启动
function startAutoSlide(totalActivities) {
    setInterval(() => slideToNext(totalActivities), 5000); // 每5秒滑动一次
}

// 加载首页的所有活动
function loadHomeActivities() {
    $.ajax({
        url: '/api/activity/getactivitybycatid?category_id=0',
        method: 'GET',
        datatype: 'json',
        success: function(response) {
            var activities = response;
            var activityList = '';
            activities.forEach(function(activity) {
                activityList += `
                        <div class="activity-item category-1" onclick="window.location.href='/detail?id=${activity.id}'">
                            <div class="category-badge">热门活动</div>
                            <h3 class="title">${activity.activityName}</h3>
                            <p class="description">${activity.activityDescription}</p>
                            <div class="meta-info">
                                <span class="date">${formatDate(activity.activityDate)}</span>
                            </div>
                        </div>
                    `;
            });
            $('#activity-list-home').html(activityList);
        },
        error: function() {
            alert('加载首页活动失败');
        }
    });
}

function loadCategoryActivities(categoryId) {
    // 验证categoryId是否在1-5范围内
    if (categoryId < 1 || categoryId > 5) return;

    $.ajax({
        url: `/api/activity/getactivitybycatid?category_id=${categoryId}`,
        method: 'GET',
        datatype: 'json',
        success: function(response) {
            var activities = response;
            var activityList = '';

            activities.forEach(function(activity) {
                // 根据不同的categoryId生成不同的HTML结构
                switch(categoryId) {
                    case 1: // 分类1样式 - 学习
                        activityList += `
                            <div class="activity-item category-1" onclick="window.location.href='/detail?id=${activity.id}'">
                                <div class="category-badge">学习活动</div>
                                <h3 class="title">${activity.activityName}</h3>
                                <p class="description">${activity.activityDescription}</p>
                                <div class="meta-info">
                                    <span class="date">${formatDate(activity.activityDate)}</span>
                                </div>
                            </div>
                            `;
                        break;

                    case 2: // 分类2样式 - 运动
                        activityList += `
                            <div class="activity-item category-2" onclick="window.location.href='/detail?id=${activity.id}'">
                                <div class="card">
                                    <h4>${activity.activityName}</h4>
                                    <p>${activity.activityDescription}</p>
                                    <a href="/detail?id=${activity.id}" class="btn btn-primary">查看详情</a>
                                </div>
                            </div>
                            `;
                        break;

                    case 3: // 分类3样式 - 音乐
                        activityList += `
                            <div class="activity-item category-3" onclick="window.location.href='/detail?id=${activity.id}'">
                                <div class="icon"><i class="fas fa-music"></i></div>
                                <div class="content">
                                    <h5>${activity.activityName}</h5>
                                    <p>${activity.activityDescription}</p>
                                </div>
                                <div class="arrow"><i class="fas fa-chevron-right"></i></div>
                            </div>
                            `;
                        break;

                    case 4: // 分类4样式 - 旅行
                        activityList = `<div class="activity-blocks-container">`; // 添加块状容器

                        activities.forEach(function(activity) {
                            activityList += `
        <div class="activity-block category-4" onclick="window.location.href='/detail?id=${activity.id}'">
            <div class="block-header">
                <i class="travel-icon">✈️</i>
                <h3>${activity.activityName}</h3>
            </div>
            <div class="block-content">
                <p class="description">${activity.activityDescription}</p>
                <div class="info-row">
                    <span class="date">📅 ${activity.activityDate || '待定'}</span>
                </div>
                <a href="/detail?id=${activity.id}" class="cta-button">查看详情</a>
            </div>
        </div>
        `;
                        });

                        activityList += `</div>`; // 关闭容器
                        $(`#activity-list-${categoryId}`).html(activityList);
                        break;
                        break;
                    case 5: // 分类5样式 - 美食
                        activityList += `
                            <div class="activity-item category-5" onclick="window.location.href='/detail?id=${activity.id}'">
                                <div class="timeline-item">
                                    <div class="timeline-marker"></div>
                                    <div class="timeline-content">
                                        <h4>${activity.activityName}</h4>
                                        <p>${activity.activityDescription}</p>
                                        <time>${formatDate(activity.activityDate)}</time>
                                    </div>
                                </div>
                            </div>
                            `;
                        break;
                }
            });

            $(`#activity-list-${categoryId}`).html(activityList);
        },
        error: function() {
            alert(`加载分类 ${categoryId} 活动失败`);
        }
    });
}

// 辅助函数：格式化日期
function formatDate(dateString) {
    if (!dateString) return '日期未设置';
    const date = new Date(dateString);
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString('zh-CN', options);
}

// 搜索功能
document.getElementById('search-btn').addEventListener('click', function() {
    var searchQuery = document.getElementById('search-input').value.trim();

    if (searchQuery) {
        window.location.href = `searchtext?query=${encodeURIComponent(searchQuery)}`;
    } else {
        window.location.reload();
    }
});

// 加载推荐活动
function loadprefer() {
    var userid = sessionStorage.getItem('userid');
    $.ajax({
        url: '/api/activity/getactbylistcat',
        method: 'GET',
        datatype: 'json',
        success: function(response) {
            var activities = response;
            var activityList = '';
            if (activities == null || activities.length === 0) {
                $('#activity-list-prefer').html('<h4>请先加入活动再来</h4>');
                return
            }
            activities.forEach(function(activity) {
                activityList += `
                    <div class="activity-item">
                        <a href="/detail?id=${activity.id}" class="activity-link">
                            <h5>${activity.activityName}</h5>
                            <p>${activity.activityDescription}</p>
                        </a>
                        <button class="btn btn-danger" onclick="removeInterest(${activity.categoryId})">不感兴趣</button>
                    </div>
                    `;
            });
            $('#activity-list-prefer').html(activityList);
        },
        error: function() {
            alert('请先登录');
        }
    });
}
// 不感兴趣按钮点击事件
function removeInterest(categoryId) {
    console.log(categoryId);
    if (confirm('确定不再推荐此类活动吗？')) {
        $.ajax({
            url: '/api/prefer/deleteprefers',
            method: 'POST',
            data: { category_id: categoryId },
            dataType: 'json',
            success: function(response) {
                if (response.status === 'success') {
                    alert('已删除该活动偏好');
                    loadprefer();
                } else {
                    alert('删除偏好失败: ' + response.message);
                }
            },
            error: function() {
                alert('删除偏好请求失败');
            }
        });
    }
}