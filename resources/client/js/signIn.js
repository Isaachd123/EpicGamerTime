function resetLoginForm() {
    const loginForm = $('#loginForm');
    loginForm.submit(event => {
        event.preventDefault();
        $.ajax({
            url: '/user/login',
            type: 'POST',
            data: loginForm.serialize(),
            success: response => {
                if (response.startsWith('Error:')) {
                    alert(response);
                } else {
                    Cookies.set("token", response);
                    window.location.href = "/client/home.html";
                }
            }
        });
    });
}

function checkLogin() {
    $.ajax({
        url: '/user/get',
        type: 'GET',
        success: username => {
        if (username !== "") {
        window.location.href = "/client/home.html";
    }
}
});
}

function pageLoad() {
    resetLoginForm();
    checkLogin();
}