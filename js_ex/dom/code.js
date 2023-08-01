let text = document.getElementById("text-1");
console.log(text);

console.log(text.innerHTML);

text.innerHTML="hello";

let link = document.getElementById("link-1");
console.log(link);
console.log(link.innerHTML);
console.log(link.href);
link.href = "https://developer.mozilla.org";
link.target = "_blank";

let ex1 = document.getElementsByClassName("ex-1");
console.log(ex1);
console.log(ex1 [0]);

let ex2 = document.getElementsByTagName("div");
console.log(ex2);