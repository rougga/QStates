let addDateToSessionStorage = function () {
    sessionStorage.setItem("date1", $("#date1").val());
    sessionStorage.setItem("date2", $("#date2").val());
};
let filterDate = function (date) {
    if (date) {
        return date;
    } else {
        return moment().format("YYYY-MM-DD");
    }
};

let setDates = function () {
    let date1 = filterDate(sessionStorage.getItem("date1"));
    let date2 = filterDate(sessionStorage.getItem("date2"));
    $("#date1").val(date1);
    $("#date2").val(date2);
};
let updateLinks = function () {
    let date1 = filterDate(sessionStorage.getItem("date1"));
    let date2 = filterDate(sessionStorage.getItem("date2"));
    let agencesLink = "&date1=" + date1 + "&date2=" + date2;
    $.each($(".d"), function (i, v) {
        let link = $(v).attr("href");
        link = link.substring(0, link.indexOf("d=d") + 3);
        link += agencesLink;
        $(v).attr("href", link);
    });
};
                