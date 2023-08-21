$(function(){
    $("#login-button").click(function(event){
        event.preventDefault();
      $("#register").text("Now registering");
      $('form').fadeOut(500);
      $('.wrapper').addClass('form-success');
   });
})