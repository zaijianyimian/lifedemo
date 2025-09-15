// 获取所有举报信息并展示在表格中
function loadReports() {
    $.ajax({
        url: '/api/report/report',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
                const reports = response;
                const tableBody = $('#reportsTable tbody');
                tableBody.empty(); // 清空表格内容

                reports.forEach(report => {
                    const row = `<tr>
                                        <td>${report.id}</td>
                                        <td>${report.userId}</td>
                                        <td>${report.activityId}</td>
                                        <td>${report.reportDescription}</td>
                                        <td><button class="btn btn-process" onclick="processReport(${report.id})"><i class="fa fa-check"></i> 处理</button></td>
                                      </tr>`;
                    tableBody.append(row);
                });
        },
        error: function() {
            alert('请求失败，请稍后重试');
        }
    });
}

// 处理举报
function processReport(reportId) {
    $.ajax({
        url: '/api/report/report',
        method: 'DELETE',
        data: { reportId: reportId },
        dataType: 'json',
        success: function(response) {
            if (response.status === 'success') {
                alert('举报处理成功');
                loadReports();  // 重新加载举报列表
            } else {
                alert('举报处理失败');
            }
        },
        error: function() {
            alert('请求失败，请稍后重试');
        }
    });
}

// 页面加载完成后加载所有举报
$(document).ready(function() {
    loadReports();
});