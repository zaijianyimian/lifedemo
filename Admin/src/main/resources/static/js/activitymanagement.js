window.onload = function() {
    loadActivityTable();
};
// 定义类别映射对象，将数字类别ID转换为对应的中文类别名称
var categoryMap = {
    1: "学习",
    2: "运动",
    3: "音乐",
    4: "旅行",
    5: "美食"
};

function loadActivityTable() {
    $.ajax({
        url: '/api/activity/getallactivity',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            var tableBody = document.getElementById("activityTableBody");// 获取表格主体元素
            tableBody.innerHTML = "";  // 清空现有的表格内容
            var activities = response;  // 使用 response.activities 访问活动数组

            // 遍历活动数组，填充表格
            activities.forEach(function(activity) {
                var row = document.createElement("tr"); // 创建表格行元素


                // 活动ID列
                var cellId = document.createElement("td"); // 创建单元格元素
                cellId.textContent = activity.id; // 设置单元格内容为活动ID
                row.appendChild(cellId);// 将单元格添加到行中


                // 活动名称列
                var cellName = document.createElement("td");
                cellName.textContent = activity.activityName; // 设置单元格内容为活动名称
                row.appendChild(cellName);

                // 类别列：使用映射显示类别名称
                var cellCategory = document.createElement("td");
                cellCategory.textContent = categoryMap[activity.categoryId] || "未知类别";// 若类别ID不存在则显示"未知类别"
                row.appendChild(cellCategory);

                // 活动描述列
                var cellDescription = document.createElement("td");
                cellDescription.textContent = activity.activityDescription;// 设置单元格内容为活动描述
                row.appendChild(cellDescription);

                // 活动日期列
                var cellDate = document.createElement("td");
                cellDate.textContent = activity.activityDate;  // 注意：您可能需要处理日期格式
                row.appendChild(cellDate);

                // 操作列：删除按钮
                var cellActions = document.createElement("td");
                cellActions.innerHTML = `
                    <button class="btn btn-danger btn-sm btn-action" onclick="openDeleteActivityModal(${activity.id})">删除</button>
                `;
                row.appendChild(cellActions);

                tableBody.appendChild(row);  // 将行添加到表格的主体部分
            });
        },
        error: function(error) {
            alert("加载活动数据失败！");
            console.error(error);// 在控制台打印错误信息

        }
    });
}


function searchActivity() {
    var searchText = document.getElementById("searchInput").value;
    document.getElementById("activityTableBody").innerHTML = ""; // 清空表格内容
    if(searchText == null || searchText == "") {
        loadActivityTable();
        return;
    }

    $.ajax({
        url: '/api/activity/activity/' + searchText,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            var activity = response;

            if (activity) { // 确保活动对象存在
                var row = document.createElement("tr");

                var cellId = document.createElement("td");
                cellId.textContent = activity.id;
                row.appendChild(cellId);

                var cellName = document.createElement("td");
                cellName.textContent = activity.activityName;
                row.appendChild(cellName);

                var cellCategory = document.createElement("td");
                cellCategory.textContent = categoryMap[activity.categoryId] || "未知类别"; // 使用映射显示类别名称
                row.appendChild(cellCategory);

                var cellDescription = document.createElement("td");
                cellDescription.textContent = activity.activityDescription;
                row.appendChild(cellDescription);

                var cellDate = document.createElement("td");
                cellDate.textContent = activity.activityDate;
                row.appendChild(cellDate);

                var cellActions = document.createElement("td");
                cellActions.innerHTML = `
                        <button class="btn btn-danger btn-sm btn-action" onclick="openDeleteActivityModal(${activity.id})">删除</button>
                    `;
                row.appendChild(cellActions);

                document.getElementById("activityTableBody").appendChild(row);

            } else {
                console.error('活动数据格式错误');
                alert('没有找到符合条件的活动。');
            }
        },
        error: function(error) {
            console.error('请求失败', error);
            alert('查询失败！');
        }
    });
}

function openDeleteActivityModal(id) {
    $('#deleteActivityModal').modal('show');
    window.selectedActivityId = id;
}

function deleteActivity() {
    let activityId = window.selectedActivityId;

    $.ajax({
        url: '/api/activity/activity/' + activityId,
        method: 'DELETE',
        dataType: 'json',
        success: function(response) {
            if(response.status === 'success') {
                alert("活动删除成功！");
                loadActivityTable();
                $('#deleteActivityModal').modal('hide');
            }
            else {
                alert("该活动不存在或已删除！");
            }
        },
        error: function(error) {
            alert("删除失败！");
            console.error(error);
        }
    });
}