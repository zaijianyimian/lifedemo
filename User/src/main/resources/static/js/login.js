function toggleForm() {
    var loginForm = document.getElementById('login-form');
    var registerForm = document.getElementById('register-form');
    if (loginForm.style.display === "none") {
        loginForm.style.display = "block";
        registerForm.style.display = "none";
    } else {
        loginForm.style.display = "none";
        registerForm.style.display = "block";
    }
}

// 生成随机验证码并显示
function generateCaptcha() {
    var captcha = Math.random().toString(36).substr(2, 6).toUpperCase();
    document.getElementById("captcha-text").textContent = captcha;
    document.getElementById("captcha-hidden").value = captcha; // 将验证码存储在隐藏字段中
}

// 验证验证码输入
function validateCaptcha(event) {
    var inputCaptcha = document.getElementById("captcha-input").value;
    var generatedCaptcha = document.getElementById("captcha-text").textContent;
    if (inputCaptcha !== generatedCaptcha) {
        event.preventDefault();
        alert("验证码错误，请重新输入！");
        generateCaptcha();
    }
}

// 页面加载时生成验证码
window.onload = generateCaptcha;

// 登录表单的 Ajax 请求
$('#loginForm').submit(function(event) {
    event.preventDefault();  // 阻止表单默认提交

    var formData = $(this).serialize();  // 获取表单数据
    $.ajax({
        url: '/api/user/login',  // 登录接口
        type: 'POST',
        data: formData,
        dataType: 'json',
        success: function(response) {
            if (response.status === 'true') {
                sessionStorage.setItem('username', document.getElementById('login-username').value);
                sessionStorage.setItem('userid', response.userid);
                console.log(sessionStorage.getItem('username'));
                console.log(sessionStorage.getItem('userid'));
                window.location.href = '/index';
            } else {
                alert(response.message);
            }
        },
        error: function() {
            alert("发生错误，请稍后重试。");
        }
    });
});

// 注册表单的 Ajax 请求
$('#registerForm').submit(function(event) {
    event.preventDefault();  // 阻止表单默认提交

    var formData = $(this).serialize();  // 获取表单数据
    $.ajax({
        url: '/api/user/register',  // 注册接口
        type: 'POST',
        data: formData,
        dataType: 'json',
        success: function(response) {
            if (response.status === 'true') {
                alert('注册成功');
                window.location.reload(); // 注册成功跳转到首页
            } else {
                alert(response.message); // 显示错误信息
            }
        },
        error: function() {
            alert("发生错误，请稍后重试。");
        }
    });
});