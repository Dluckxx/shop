//销售和进货
var check = function (type, name) {
    var number = document.getElementById(name).children[0].value;

    console.log(number);

    //输入收到的钱的数量
    var price;
    if (type === "import") {
        price = prompt("请输入物品单价！");
    } else if (type === "export") {
        price = prompt("请输入总共收到的Money！");
    } else {
        alert("出错了！");
        return;
    }
    if (price === null) {
        return;
    }

    //判断输入是否为正确的值
    if (price === "" || isNaN(Number(price))) {
        alert("再不输入正确的值就扣工资！！！");
        return;
    }

    //确认记账信息
    var check;
    if (type === "import") {
        check = confirm("请认真核对进货信息：\n商品名：" + name + "\n商品数量：" + number + "\n单价：" + price);
    } else if (type === "export") {
        check = confirm("请认真核对销售信息：\n商品名：" + name + "\n商品数量：" + number + "\n总价：" + price);
    } else {
        alert("出错了！");
        return;
    }

    //如果用户选择否
    if (!check) {
        return;
    }

    //确认无误，继续运行上传修改数据
    var formData = new FormData();

    formData.append('type', type);
    formData.append('name', name);
    formData.append('price', price);
    formData.append('number', number);

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        // 通信成功时，状态值为4
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                location.reload();
            } else {
                console.error(xhr.statusText);
            }
        }
    };

    xhr.onerror = function (e) {
        console.error(xhr.statusText);
    };

    xhr.open('POST', '/stock/change', true);
    xhr.send(formData);
};

//删除进货/销售记录
var deleteRecord = function (type, id) {
    var eid = prompt("请输入员工ID:");

    if (eid === "" || isNaN(Number(eid))) {
        alert("ID乱输？？？");
        return;
    } else if (eid === null) {
        return;
    } else {
        eid = Number(eid);
    }

    var password = prompt("请输入管理员密码:");

    if (password === null) {
        return;
    }

    console.log("eid:" + eid + "\npassword:" + password + "\ntype:" + type + "\nid:" + id);

    var formData = new FormData();

    formData.append('type', type);
    formData.append('id', id);
    formData.append('eid', eid);
    formData.append('password', password);

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        // 通信成功时，状态值为4
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                location.reload();
            } else {
                alert(xhr.status);
            }
        }
    };

    xhr.onerror = function (e) {
        console.error(xhr.statusText);
    };

    xhr.open('POST', '/stock/delete', true);
    xhr.send(formData);
};

//带商品ID跳转到登陆界面
var login = function (sid) {
    location.href = "/stock/login?sid=" + sid;
};

//编辑商品界面
var edit = function (sid) {
    var eid = document.getElementById("eid").value;
    var password = document.getElementById("password").value;

    location.href = "/stock/edit?sid=" + sid + "&eid=" + eid + "&password=" + password;
};

//编辑商品
var editStock = function () {
    var sid = document.getElementById("sid").innerText;
    var name = document.getElementById("name").value;
    var number = document.getElementById("number").value;
    var buyPrice = document.getElementById("buyPrice").value;
    var salePrice = document.getElementById("salePrice").value;

    var formData = new FormData();

    formData.append('sid', sid);
    formData.append('name', name);
    formData.append('number', number);
    formData.append('buyPrice', buyPrice);
    formData.append('salePrice', salePrice);

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        // 通信成功时，状态值为4
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                location.href = "/stock";
            } else {
                console.error(xhr.statusText);
            }
        }
    };

    xhr.onerror = function (e) {
        console.error(xhr.statusText);
    };

    xhr.open('POST', '/stock/editStock', true);
    xhr.send(formData);
};

//删除商品
var deleteStock = function () {

    if (!confirm("确定要删除吗，所有涉及到的商品将会被更改为未知商品！")){
        return;
    }

    var sid = document.getElementById("sid").innerText;

    var data = new FormData();
    data.append("sid", sid);

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        // 通信成功时，状态值为4
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                location.href = "/stock";
            } else {
                console.error(xhr.statusText);
            }
        }
    };

    xhr.onerror = function (e) {
        console.error(xhr.statusText);
    };

    xhr.open('POST', '/stock/deleteStock', true);
    xhr.send(data);
};