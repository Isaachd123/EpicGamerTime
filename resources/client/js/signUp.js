function resetNewUserForm() {
    const newUserForm = $('#newUserForm');
    newUserForm.submit(event => {
        event.preventDefault();
        $.ajax({
            url: '/user/new',
            type: 'POST',
            data: newUserForm.serialize(),
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
    })
}

function pageLoad() {
    resetNewUserForm();
    checkLogin()
}