var change = function (date, shift, row) {
    if (row < 3) {
        alert("班次已锁定！");
        return;
    }
    var id = prompt("请输入您的ID");

    if (id === null)
        return;

    var formData = new FormData();

    formData.append('date', date);
    formData.append('shift', shift);
    formData.append('id', id);

    console.log(formData);

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        // 通信成功时，状态值为4
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                if (xhr.responseText === 'success') {
                    location.reload();
                } else {
                    alert(xhr.responseText);
                    location.reload();
                }
            } else {
                console.error(xhr.statusText);
            }
        }
    };

    xhr.onerror = function (e) {
        console.error(xhr.statusText);
    };

    xhr.open('POST', '/change', true);
    xhr.send(formData);
};