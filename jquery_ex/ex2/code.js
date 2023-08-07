let list = $('#list');

let newLink = $("<a>");
newLink.html("developer.mozilla.org");

list.append(newLink);
let newItem = $("<li>");
list.append(newItem);
newItem.append(newLink);

let newText = $("<p>");
newText.html("And more...");
list.after(newText);


//list.remove();

list.empty();

$("#button1").click(()=>{
    console.log("hello");
});

$("#button1").on("click",()=>{
    console.log("Goodbye");
});

$("#button1").on("click",()=>{
    console.log("Goodbye2");
});