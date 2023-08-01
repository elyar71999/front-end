let label = document.getElementById("label")
let button1 = document.getElementById("change-text1")
let button2 =document.getElementById("change-text2")
let button3 =document.getElementById("change-text3")


button1.addEventListener('click',()=>{
    label.innerHTML = "リンゴ"
});

button2.addEventListener('click',()=>{
    label.innerHTML = "バナナ"
});
button3.addEventListener('click',()=>{
    label.innerHTML = "みかん"
});