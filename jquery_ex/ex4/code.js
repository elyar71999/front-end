$(function () {
    $(".sample_btn").click(function(){
        $.ajax({
            url:'test.html',
            type:'GET',
            dataType:'html'
        }).done(function(data){
            $(".result").html(data);
        }).fail(function(){
            console.log("通信が失敗した")
        });
    });
})