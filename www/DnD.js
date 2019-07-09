var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'DnD', 'coolMethod', [arg0]);
};


module.exports={
    on:function(arg0,success,error){
        exec(success,error,'DnD','on',[arg0])
    },
    off:function(arg0,success,error){
        exec(success,error,'DnD','off',[arg0])
    },
    alarm:function(arg0,success,error){
        exec(success,error,'DnD','alarm',[arg0])
    },
    priority:function(arg0,success,error){
        exec(success,error,'DnD','priority',[arg0])
    }
}

