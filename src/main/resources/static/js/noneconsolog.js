// Ngăn chặn mở F12
document.onkeydown = function (event) {
    if (event.keyCode == 123) { // 123 là mã phím cho F12
        return false;
    }
}

// Ngăn chặn mở Console
document.addEventListener('contextmenu', function (e) {
    e.preventDefault();
});

// Hiển thị thông báo khi Console mở
function detectDevTools() {
    var start = new Date().getTime();
    console.profile();
    console.profileEnd();

    if (new Date().getTime() - start > 20) {
        console.log('Console is open');
    }

    setTimeout(detectDevTools, 1000);
}

detectDevTools();
