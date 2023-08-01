let btnEvent = document.getElementById("button1");
btnEvent.onclick = function(){
    console.log("ボタンをクリックした1");
}
btnEvent.onclick = function(){
    console.log("ボタンをクリックした2");
}

btnEvent.addEventListener("click",()=>{
    console.log("ボタンクリックその3");
});
btnEvent.addEventListener("click",()=>{
    console.log("ボタンクリックその4");
});
