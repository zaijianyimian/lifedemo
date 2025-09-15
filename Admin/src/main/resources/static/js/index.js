// $(document).ready(function() {
//     $('#loginForm').submit(function(event) {
//         event.preventDefault();
//
//         var formData = {
//             name: $('#name').val(),
//             password: $('#password').val()
//         };
//
//         $.ajax({
//             url: '/api/login/adminlogin',
//             method: 'POST',
//             data: formData,
//             dataType: 'json',
//             success: function(response) {
//                 console.log(response);
//                 if (response.statuc==='failure') {
//                     alert("账号或密码错误，请重新登录！");
//                 } else {
//                     window.location.href = '/usermanagement';
//                 }
//             },
//             error: function() {
//                 alert('登录请求失败，请稍后重试');
//             }
//         });
//     });
// });