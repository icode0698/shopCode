NProgress.start();
// // NProgress.set(0.1);
//  NProgress.set(0.1);
// // NProgress.set(0.3);
NProgress.inc();
$(document)
      .ajaxStart(function () {
        // NProgress.set(0.1);
		NProgress.inc();
      })
      .ajaxStop(function () {
        // NProgress.done();
        setTimeout(function() { NProgress.done();}, 500);
      })
$(window).on('load',function() {
    setTimeout(function() { NProgress.done();}, 500);
});
//setTimeout(function() { NProgress.done(); $('.fade').removeClass('out'); }, 1000);