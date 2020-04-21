var add = function (type) {

    // 获取数据
    var info = document.getElementById('info').value;
    var price = document.getElementById('price').value;
    var id = document.getElementById('employee').value;

    console.log("信息: " + info + "\n价格: " + price + "\nID: " + id);

    if (info === "" || price === "" || id === "") {
        alert("一个字也不写？那你还记什么帐！！！");
        return;
    } else if (price < 0 || id < 200000000 || id > 203000000) {
        alert("再不输入正确的价格或者ID就扣工资！！！");
        return;
    }

    var formData = new FormData();

    formData.append('type', type);
    formData.append('info', info);
    formData.append('price', price);
    formData.append('id', id);

    console.log(formData);

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        // 通信成功时，状态值为4
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
            } else {
                alert('出错了！' + xhr.responseText);
                console.error(xhr.statusText);
            }

            location.reload();
        }
    };

    xhr.onerror = function (e) {
        console.error(xhr.statusText);
    };

    xhr.open('POST', '/expenditure/check', true);
    xhr.send(formData);
};
var deleteRecord = function (eid) {
    location.href = "/expenditure/delete?eid=" + eid;
};
var deleteCheck = function (eid) {

    var password = document.getElementById("inputPassword").value;

    var formData = new FormData();

    formData.append("eid", eid);
    formData.append("password", password);

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        // 通信成功时，状态值为4
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                if (xhr.responseText === "删除成功！") {
                    location.href = "/expenditure";
                }else {
                    location.reload();
                }
            } else {
                alert(xhr.status);
            }
        }
    };

    xhr.onerror = function (e) {
        console.error(xhr.statusText);
    };

    xhr.open('POST', '/expenditure/deleteCheck', true);
    xhr.send(formData);
};