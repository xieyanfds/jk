/*$(document).ready(function(){
   $(".panel-title").toggle(function(){
     $(this).next(".panel-content").animate({height: 'toggle', opacity: 'toggle'}, "slow");
   },function(){
    // $(this).next(".panel-content").animate({height: 'show', opacity: 'show'}, "slow");
       alert(123);
   });
});*/
$(document).ready(function(){
    $(".panel-title").click(function(){
        $(this).next(".panel-content").animate({height: 'toggle', opacity: 'toggle'}, "slow");
    });
});