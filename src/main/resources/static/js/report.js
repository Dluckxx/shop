var search = function () {
    var year = document.getElementById("year").value;
    var month = document.getElementById("month").value;
    location.href = ("/report?year=" + year + "&month=" + month);
};