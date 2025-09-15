window.onload = function() {
    loadUserTable();
};

function loadUserTable() {
    $.ajax({
        url: '/api/user/getalluser',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            var tableBody = document.getElementById("userTableBody");
            tableBody.innerHTML = ""; // 清空现有内容

            var users = response; // 假设返回的是一个JSON数组

            // 遍历用户数组，生成表格行
            users.forEach(function(user) {
                var row = document.createElement("tr");

                // 用户ID列
                var cellId = document.createElement("td");
                cellId.textContent = user.id; // 用户ID
                row.appendChild(cellId);

                var cellName = document.createElement("td");
                cellName.textContent = user.name; // 用户姓名
                row.appendChild(cellName);

                var cellPassword = document.createElement("td");
                cellPassword.textContent = user.password; // 用户密码
                row.appendChild(cellPassword);

                var cellActions = document.createElement("td");
                cellActions.innerHTML = `
                        <button class="btn btn-warning btn-sm btn-action" onclick="openEditUserModal(${user.id}, '${user.name}', '${user.password}')">修改</button>
                        <button class="btn btn-danger btn-sm btn-action" onclick="openDeleteUserModal(${user.id})">删除</button>
                    `;
                row.appendChild(cellActions);

                tableBody.appendChild(row);
            });
        },
        error: function(error) {
            alert("加载用户数据失败！");
            console.error(error);
        }
    });
}

function searchUsers() {
    var searchText = document.getElementById("searchInput").value;
    document.getElementById("userTableBody").innerHTML = ""; // 清空表格内容
    if(searchText == null||searchText == ""){
        loadUserTable();
        return;
    }

    $.ajax({
        url: '/api/user/searchuser?name=' + searchText,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            var user = response;

            if (user) {
                var row = document.createElement("tr");

                var cellId = document.createElement("td");
                cellId.textContent = user.id;
                row.appendChild(cellId);

                var cellName = document.createElement("td");
                cellName.textContent = user.name;
                row.appendChild(cellName);

                var cellPassword = document.createElement("td");
                cellPassword.textContent = user.password;
                row.appendChild(cellPassword);

                var cellActions = document.createElement("td");
                cellActions.innerHTML = `
                    <button class="btn btn-warning btn-sm btn-action" onclick="openEditUserModal(${user.id}, '${user.name}', '${user.password}')">修改</button>
                    <button class="btn btn-danger btn-sm btn-action" onclick="openDeleteUserModal(${user.id})">删除</button>
                `;
                row.appendChild(cellActions);

                document.getElementById("userTableBody").appendChild(row);
            } else {
                console.error('用户数据格式错误');
                alert('没有找到符合条件的用户。');
            }
        },
        error: function(error) {
            console.error('请求失败', error);
            alert('查询失败！');
        }
    });
}


function openEditUserModal(id, name, password) {
    document.getElementById("editUserId").value = id;
    document.getElementById("editUserName").value = name;
    document.getElementById("editUserPassword").value = password;
    $('#editUserModal').modal('show');
}

function updateUser() {
    var id = document.getElementById("editUserId").value;
    var name = document.getElementById("editUserName").value;
    var password = document.getElementById("editUserPassword").value;

    $.ajax({
        url: '/api/user/updateuser',
        method: 'POST',
        data: JSON.stringify({ id: id, name: name, password: password }),
        contentType: 'application/json;charset=UTF-8',
        dataType: 'json',
        success: function(response) {
            if(response.status === 'success') {
                alert("用户信息更新成功！");
                $('#editUserModal').modal('hide');
                loadUserTable();

            }
            else {
                alert("用户信息更新失败！");
                console.error(response.message);
            }
            $('#editUserModal').modal('hide');
                    },
        error: function(error) {
            alert("更新成功！");
            loadUserTable();
            console.error(error);
        }
    });
}

function openDeleteUserModal(id) {
    if (confirm("确定要删除这个用户吗？")) {
        deleteUser(id);
    }
}

function deleteUser(id) {
    $.ajax({
        url: '/api/user/deleteuser?id=' + id,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if(response.status === 'success') {
                alert("用户删除成功！");
                loadUserTable();
            }else {
                alert("用户删除失败！");
            }
        },
        error: function(error) {
            alert("删除失败！");
            console.error(error);
        }
    });
}

function openAddUserModal() {
    $('#addUserModal').modal('show');
}
function closeAddUserModal() {
    $('#addUserModal').modal('hide');
}
function addUser() {
    var name = document.getElementById("addUserName").value;
    var password = document.getElementById("addUserPassword").value;

    $.ajax({
        url: '/api/user/adduser',
        method: 'POST',
        data: { name: name, password: password },
        dataType: 'json',
        success: function(response) {
            if(response.status === 'success') {
                alert("用户添加成功！");
                loadUserTable();
            }else {
                alert("用户添加失败！");
            }
                $('#addUserModal').modal('hide');
        },
        error: function(error) {
            alert("添加失败！");
            console.error(error);
        }
    });
}