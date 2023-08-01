let arr = [1,2,3,"Alice",12.5];
console.log(arr);
console.log(arr.length);
arr.push("追加しました");
console.log(arr);
arr.pop();
console.log(arr);

let arr2 = new Array(5);
arr2[0] = 23;
console.log(arr2);

for(let a of arr){
    console.log(a);
}