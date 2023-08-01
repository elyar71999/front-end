let obj = {name:"Alice",id:122,isStudent:true};
console.log(obj);

console.log(obj.name);

obj.id =30;
console.log(obj.id);
obj["isStudent"] = false;
console.log(obj["isStudent"]);

for(let key in obj){
    console.log(key + ":" +obj[key]);
}