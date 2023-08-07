$(function(){
    let text = $("#text-1");
    console.log(text.html());
    text.html("goodbye");


    let link = $("#link-1");
    console.log(link.attr("href"));

    link.attr("href","https://kinarino.jp/cat8/38722");
    link.attr("target","_blank");

    let texts = $(".text-class");
    text.css("color","red");
});