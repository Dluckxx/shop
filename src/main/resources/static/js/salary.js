var search = function () {
    var year = document.getElementById("year").value;
    var month = document.getElementById("month").value;

    window.location.href = "/salary?year=" + year + "&month=" + month;
};